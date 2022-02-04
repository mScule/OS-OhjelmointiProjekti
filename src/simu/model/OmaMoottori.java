package simu.model;

import eduni.distributions.Negexp;
import eduni.distributions.Normal;
import simu.framework.Kello;
import simu.framework.Moottori;
import simu.framework.Saapumisprosessi;
import simu.framework.Tapahtuma;
import java.util.HashMap;

public class OmaMoottori extends Moottori {
	public static TapahtumanTyyppi seuraava;
	private Saapumisprosessi saapumisprosessi;

	public OmaMoottori() {

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
				new Peli(new Normal(10, 6), tapahtumalista)
		});

		saapumisprosessi = new Saapumisprosessi(new Negexp(15, 5), tapahtumalista, TapahtumanTyyppi.SISAANKAYNTI);
	}

	@Override
	protected void alustukset() {
		saapumisprosessi.generoiSeuraava(); // Ensimmäinen saapuminen järjestelmään
	}

	@Override
	protected void suoritaTapahtuma(Tapahtuma t) { // B-vaiheen tapahtumat

		Asiakas a;

		// int seuraava = 0;
		/*
		 * switch (a.getStatus()) {
		 * case SISAANKAYNTI:
		 * seuraava = 0;
		 * break;
		 * case ULOSKAYNTI:
		 * seuraava = 3;
		 * break;
		 * case BAARI:
		 * seuraava = 1;
		 * break;
		 * case PELI:
		 * seuraava = 2;
		 * break;
		 * }
		 */

		// Palvelupisteet haetaan tyypin mukaan
		TapahtumanTyyppi thisTyyppi = t.getlahtoSijaintiTyypi();

		if (t.getTyyppi() != TapahtumanTyyppi.SISAANKAYNTI) {
			// Haetaan nykyinen palvelupiste, josta haetaan asiakas
			Palvelupiste thisPalvelupiste = palvelupisteet.get(thisTyyppi)[t.getLahtoSijaintiID()];
			a = thisPalvelupiste.otaJonosta();

			// Viedään asiakas haluttun tyyppiseen pisteeseen jossa on lyhyin jono.
			Palvelupiste[] pisteet = palvelupisteet.get(t.getTyyppi());
			int lyhyinJono = pisteet[0].jono.size(), lyhyinIndex = 0;
			for(int i = 1; i < pisteet.length; i++) {
				if(pisteet[i].jono.size() < lyhyinJono) {
					lyhyinJono = pisteet[i].jono.size();
					lyhyinIndex = i;
				}
			}

			pisteet[lyhyinIndex].lisaaJonoon(a);

		} else {
			palvelupisteet.get(TapahtumanTyyppi.SISAANKAYNTI)[0].lisaaJonoon(new Asiakas());
			saapumisprosessi.generoiSeuraava();
		}

		/*
		 * 
		 * switch (t.getTyyppi()) {
		 * 
		 * // TODO: Määrittele mihin palvelupisteeseen asiakas seuraavaksi siirretään
		 * // asiakkaan ominaisuuksien ja jonkun satunnaisesti generoidun luvun avulla.
		 * 
		 * case SISAANKAYNTI:
		 * palvelupisteet[t.getLahtoSijainti()].lisaaJonoon(new Asiakas());
		 * saapumisprosessi.generoiSeuraava();
		 * break;
		 * case ULOSKAYNTI:
		 * a = palvelupisteet[t.getLahtoSijainti()].otaJonosta();
		 * palvelupisteet[t.getLahtoSijainti()].poistu();
		 * a.setPoistumisaika(Kello.getInstance().getAika());
		 * // a.raportti();
		 * break;
		 * case BAARI:
		 * a = palvelupisteet[t.getLahtoSijainti()].otaJonosta();
		 * palvelupisteet[t.getLahtoSijainti()].lisaaJonoon(a);
		 * break;
		 * case PELI:
		 * a = palvelupisteet[t.getLahtoSijainti()].otaJonosta();
		 * palvelupisteet[t.getLahtoSijainti()].lisaaJonoon(a);
		 * }
		 */
	}

	@Override
	protected void tulokset() {
		System.out.println("Simulointi päättyi kello " + Kello.getInstance().getAika());
		System.out.println("Tulokset ... puuttuvat vielä");
	}

}
