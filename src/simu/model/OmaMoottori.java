package simu.model;

import java.util.HashMap;

import eduni.distributions.Negexp;
import eduni.distributions.Normal;
import simu.framework.Kello;
import simu.framework.Moottori;
import simu.framework.Saapumisprosessi;
import simu.framework.Tapahtuma;

public class OmaMoottori extends Moottori {
	public static TapahtumanTyyppi seuraava;
	private Saapumisprosessi saapumisprosessi;

	public OmaMoottori() {
		
		palvelupisteet = new Palvelupiste[4];

		palvelupisteet[0] = new Sisaankaynti(new Normal(10, 6), tapahtumalista);
		palvelupisteet[1] = new Baari(new Normal(10, 10), tapahtumalista);
		palvelupisteet[2] = new Peli(new Normal(5, 3), tapahtumalista);
		palvelupisteet[3] = new Uloskaynti(new Normal(10, 6), tapahtumalista);
		

		saapumisprosessi = new Saapumisprosessi(new Negexp(15, 5), tapahtumalista, TapahtumanTyyppi.SISAANKAYNTI);
	}

	@Override
	protected void alustukset() {
		saapumisprosessi.generoiSeuraava(); // Ensimmäinen saapuminen järjestelmään
	}

	@Override
	protected void suoritaTapahtuma(Tapahtuma t) { // B-vaiheen tapahtumat

		Asiakas a;

		//int seuraava = 0;
/*
		switch (a.getStatus()) {
		case SISAANKAYNTI:
			seuraava = 0;
			break;
		case ULOSKAYNTI:
			seuraava = 3;
			break;
		case BAARI:
			seuraava = 1;
			break;
		case PELI:
			seuraava = 2;
			break;
		}*/

		switch (t.getTyyppi()) {

		// TODO: Määrittele mihin palvelupisteeseen asiakas seuraavaksi siirretään
		// asiakkaan ominaisuuksien ja jonkun satunnaisesti generoidun luvun avulla.
		case SISAANKAYNTI:
			palvelupisteet[0].lisaaJonoon(new Asiakas());
			saapumisprosessi.generoiSeuraava();
			break;
		case ULOSKAYNTI:
			a = palvelupisteet[t.getLahtoSijainti()].otaJonosta();
			palvelupisteet[1].lisaaJonoon(a);
			a.setPoistumisaika(Kello.getInstance().getAika());
			// a.raportti();
			break;
		case BAARI:
			a = palvelupisteet[t.getLahtoSijainti()].otaJonosta();
			palvelupisteet[2].lisaaJonoon(a);
			break;
		case PELI:
			a = palvelupisteet[t.getLahtoSijainti()].otaJonosta();
			palvelupisteet[2].lisaaJonoon(a);
		}
	}

	@Override
	protected void tulokset() {
		System.out.println("Simulointi päättyi kello " + Kello.getInstance().getAika());
		System.out.println("Tulokset ... puuttuvat vielä");
	}

}
