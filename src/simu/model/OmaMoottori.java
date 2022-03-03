package simu.model;

import controller.IKontrolleriMtoV;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;
import simu.framework.Kello;
import simu.framework.Moottori;
import simu.framework.Saapumisprosessi;
import simu.framework.Tapahtuma;
import simu.framework.Trace;
import simu.framework.Trace.Level;

import java.util.HashMap;

public class OmaMoottori extends Moottori implements IOmaMoottori {
	private Kello kello = Kello.getInstance();

	private Saapumisprosessi saapumisprosessi;

	private int saapuneidenAsiakkaidenMaara = 0, poistuneidenAsiakkaidenMaara = 0;

	private double poistumisajatSummattuna = 0.0;

	public static TapahtumanTyyppi seuraava;

	public OmaMoottori(IKontrolleriMtoV kontrolleri) {
		super(kontrolleri);

		palvelupisteet = new HashMap<TapahtumanTyyppi, Palvelupiste[]>();

		palvelupisteet.put(TapahtumanTyyppi.SISAANKAYNTI, new Sisaankaynti[] {
				new Sisaankaynti(new Normal(10, 6), tapahtumalista)
		});

		palvelupisteet.put(TapahtumanTyyppi.ULOSKAYNTI, new Uloskaynti[] {
				new Uloskaynti(new Normal(10, 6), tapahtumalista)
		});

		palvelupisteet.put(TapahtumanTyyppi.BAARI, new Baari[] {
				new Baari(new Normal(10, 10), tapahtumalista)
		});

		palvelupisteet.put(TapahtumanTyyppi.PELI, new Peli[] {
				new Peli(new Normal(50, 6), tapahtumalista)
		});

		saapumisprosessi = new Saapumisprosessi(new Negexp(10, 5), tapahtumalista, TapahtumanTyyppi.SISAANKAYNTI);
	}

	@Override
	protected void alustukset() {
		saapumisprosessi.generoiSeuraava(); // Ensimmäinen saapuminen järjestelmään
	}

	@Override
	protected void suoritaTapahtuma(Tapahtuma t) { // B-vaiheen tapahtumat

		Asiakas a;

		// Palvelupisteet haetaan tyypin mukaan
		TapahtumanTyyppi thisTyyppi = t.getTyypiLahtoSijainti();
		TapahtumanTyyppi seuraavanTapahtumanTyyppi = t.getTyyppiPaamaara();

		if (seuraavanTapahtumanTyyppi != TapahtumanTyyppi.SISAANKAYNTI) {
			// Haetaan nykyinen palvelupiste, josta haetaan asiakas
			Palvelupiste thisPalvelupiste = null;
			for (Palvelupiste p : palvelupisteet.get(thisTyyppi)) {
				if (t.getLahtoSijaintiID() == p.getId())
					thisPalvelupiste = p;
			}

			// Jos tapahtuma on palvelupisteestä siirtyminen toiseen paikkaan, poista
			// asiakas pelipisteen jonosta tapahtumaan liittyvän asiakkaan ID:n mukaan.
			// Muissa tapauksissa poista jonon ensimmäinen asiakas.
			if (thisTyyppi == TapahtumanTyyppi.PELI) {
				a = thisPalvelupiste.otaJonostaIDnMukaan(t.getPalveltavanAsiakkaanID());
			} else {
				a = thisPalvelupiste.otaJonosta();
			}

			// Jos asiakas ei ole poistumassa, viedään asiakas haluttun tyyppiseen
			// pisteeseen jossa on lyhyin jono.
			// Jos asiakas poistuu, ei lisätä asiakasta enää uudelleen mihinkään jonoon ja
			// kasvatetaan kirjanpidossa poistuneiden asiakkaiden lukumäärää.
			if (seuraavanTapahtumanTyyppi != TapahtumanTyyppi.POISTUMINEN) {
				Palvelupiste[] pisteet = palvelupisteet.get(seuraavanTapahtumanTyyppi);
				int lyhyinJono = pisteet[0].jono.size(), lyhyinIndex = 0;
				for (int i = 1; i < pisteet.length; i++) {
					if (pisteet[i].jono.size() < lyhyinJono) {
						lyhyinJono = pisteet[i].jono.size();
						lyhyinIndex = i;
					}
				}
				pisteet[lyhyinIndex].lisaaJonoon(a);
			} else {
				System.out.println("Asiakas " + a.getId() + " poistuu kasinolta.");
				System.out.println(a);
				poistuneidenAsiakkaidenMaara++;
				poistumisajatSummattuna += a.getPoistumisaika();
			}
		} else {
			// TODO: TEMP visualisointi
			if (kontrolleri != null) {
				kontrolleri.visualisoiAsiakas();
			}

			Asiakas uusiA = new Asiakas();
			System.out.println(uusiA);
			palvelupisteet.get(TapahtumanTyyppi.SISAANKAYNTI)[0].lisaaJonoon(uusiA);
			saapuneidenAsiakkaidenMaara++;
			saapumisprosessi.generoiSeuraava();
		}
	}

	@Override
	protected void tulokset() {
		Trace.out(Trace.Level.INFO,
				"\nTulokset:\n\n" +
						"Simulointi päättyi kello " + kello.getAika() + "\n" +
						"Saapuneiden asiakkaiden määrä: " + saapuneidenAsiakkaidenMaara + "\n" +
						"Poistuneiden asiakkaiden määrä: " + poistuneidenAsiakkaidenMaara + "\n" +
						"Keskimääräinen läpimenoaika: " + poistumisajatSummattuna / poistuneidenAsiakkaidenMaara);

		Palvelupiste sisaankaynti = palvelupisteet.get(TapahtumanTyyppi.SISAANKAYNTI)[0];
		Palvelupiste uloskaynti = palvelupisteet.get(TapahtumanTyyppi.ULOSKAYNTI)[0];
		Palvelupiste baari = palvelupisteet.get(TapahtumanTyyppi.BAARI)[0];
		Peli peli = (Peli) palvelupisteet.get(TapahtumanTyyppi.PELI)[0];

		double kokonaisoleskeluaika = sisaankaynti.getPalveluaika() +
				uloskaynti.getPalveluaika() +
				baari.getPalveluaika() +
				peli.getPalveluaika();

		double kokonaisjononpituus = sisaankaynti.getPalveluaika() +
				uloskaynti.getPalveluaika() +
				baari.getPalveluaika() +
				peli.getJononpituus();

		Trace.out(Trace.Level.INFO,
				"Kokonaisoleskeluaika: " + kokonaisoleskeluaika + "\n" +
						"Keskimääräinen jononpituus: " + kokonaisjononpituus / Kello.getInstance().getAika());

		Trace.out(Trace.Level.INFO,
				"Palveluajat:\n" +
						"\tSisäänkäynti: " + sisaankaynti.getPalveluaika() + "\n" +
						"\tUloskäynti: " + uloskaynti.getPalveluaika() + "\n" +
						"\tBaari: " + baari.getPalveluaika() + "\n" +
						"\tPeli: " + peli.getPalveluaika() + "\n" +

						"Käyttöaste:\n" +
						"\tSisäänkäynti: " + sisaankaynti.getPalveluaika() / kello.getAika() + "\n" +
						"\tUloskäynti: " + uloskaynti.getPalveluaika() / kello.getAika() + "\n" +
						"\tBaari: " + baari.getPalveluaika() / kello.getAika() + "\n" +
						"\tPeli: " + peli.getPalveluaika() / kello.getAika() + "\n" +

						"Suoritusteho:\n" +
						"\tSisäänkäynti: " + sisaankaynti.getPalvellutAsiakkaat() / kello.getAika() + "\n" +
						"\tUloskäynti: " + uloskaynti.getPalvellutAsiakkaat() / kello.getAika() + "\n" +
						"\tBaari: " + baari.getPalvellutAsiakkaat() / kello.getAika() + "\n" +
						"\tPeli: " + peli.getPalvellutAsiakkaat() / kello.getAika() + "\n" +

						"Keskimääräinen palveluaika:\n" +
						"\tSisäänkäynti: " + sisaankaynti.getPalveluaika() / sisaankaynti.getPalvellutAsiakkaat() + "\n"
						+
						"\tUloskäynti: " + uloskaynti.getPalveluaika() / uloskaynti.getPalvellutAsiakkaat() + "\n" +
						"\tBaari: " + baari.getPalveluaika() / baari.getPalvellutAsiakkaat() + "\n" +
						"\tPeli: " + peli.getPalveluaika() / peli.getPalvellutAsiakkaat());
	}

	// IOmaMoottori

	@Override
	public double[] getTulokset() {
		double[] tulokset = new double[IOmaMoottori.TULOSTEN_MAARA];

		// Aika
		tulokset[IOmaMoottori.TULOS_AIKA] = kello.getAika();

		// Saapuneiden asiakkaiden määrä
		tulokset[IOmaMoottori.TULOS_SAAPUNEIDEN_ASIAKKAIDEN_MAARA] = saapuneidenAsiakkaidenMaara;

		// Poistuneiden asiakkaiden määrä
		tulokset[IOmaMoottori.TULOS_POISTUNEIDEN_ASIAKKAIDEN_MAARA] = poistuneidenAsiakkaidenMaara;

		// Keskimääräinen läpimenoaika
		double keskimaarainenLapimenoaika = 0.0;
		if (poistuneidenAsiakkaidenMaara != 0)
			keskimaarainenLapimenoaika = poistumisajatSummattuna / poistuneidenAsiakkaidenMaara;

		tulokset[IOmaMoottori.TULOS_KESKIMAARAINEN_LAPIMENOAIKA] = keskimaarainenLapimenoaika;

		// Kokonaisoleskeluaika
		double kokonaisoleskeluaika = 0.0;

		for (Palvelupiste[] pisteet : palvelupisteet.values()) {
			for (Palvelupiste p : pisteet) {
				kokonaisoleskeluaika += p.getPalveluaika();
			}
		}

		double kokonaisjononpituus = 0.0;

		for (Palvelupiste[] pisteet : palvelupisteet.values()) {
			for (Palvelupiste p : pisteet) {
				if (p instanceof Peli) {
					kokonaisjononpituus += ((Peli)p).getJononpituus();
				} else
					kokonaisjononpituus += p.getPalveluaika();
			}
		}

		tulokset[IOmaMoottori.TULOS_KOKONAISOLESKELUAIKA] = kokonaisoleskeluaika;

		// Keskimääräinen jononpituus
		double keskimaarainenjononpituus = kokonaisjononpituus / kello.getAika();
		tulokset[IOmaMoottori.TULOS_KESKIMAARAINEN_JONONPITUUS] = keskimaarainenjononpituus;

		// Raha
		// TODO: Raha tulos
		tulokset[IOmaMoottori.TULOS_RAHA] = Kasino.getKasinonRahat();

		return tulokset;
	}

	@Override
	public Palvelupiste[] getPalvelupisteet(int palvelu) {
		switch (palvelu) {
			case IOmaMoottori.PALVELUTYYPPI_SISAANKAYNTI:
				return palvelupisteet.get(TapahtumanTyyppi.SISAANKAYNTI).clone();
			case IOmaMoottori.PALVELUTYYPPI_ULOSKAYNTI:
				return palvelupisteet.get(TapahtumanTyyppi.ULOSKAYNTI).clone();
			case IOmaMoottori.PALVELUTYYPPI_BAARI:
				return palvelupisteet.get(TapahtumanTyyppi.BAARI).clone();
			case IOmaMoottori.PALVELUTYYPPI_PELI:
				return palvelupisteet.get(TapahtumanTyyppi.PELI).clone();
			default:
				Trace.out(Level.ERR, "getPalvelupisteet() - Tuntematon palvelutyyppi.");
				return null;
		}
	}
}
