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
			new Peli(new Normal(30, 1), tapahtumalista)
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

			a = thisPalvelupiste.otaJonosta();

			// Jos asiakas ei ole poistumassa, viedään asiakas haluttun tyyppiseen pisteeseen jossa on lyhyin jono.
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
			}
		} else {
			palvelupisteet.get(TapahtumanTyyppi.SISAANKAYNTI)[0].lisaaJonoon(new Asiakas());
			saapumisprosessi.generoiSeuraava();
		}
	}

	@Override
	protected void tulokset() {
		System.out.println("Simulointi päättyi kello " + Kello.getInstance().getAika());
		System.out.println("Tulokset ... puuttuvat vielä");
	}
}
