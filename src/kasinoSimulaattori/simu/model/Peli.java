package kasinoSimulaattori.simu.model;

import java.util.PriorityQueue;

import kasinoSimulaattori.eduni.distributions.ContinuousGenerator;
import kasinoSimulaattori.eduni.distributions.Negexp;
import kasinoSimulaattori.eduni.distributions.Uniform;
import kasinoSimulaattori.simu.framework.Kello;
import kasinoSimulaattori.simu.framework.Tapahtuma;
import kasinoSimulaattori.simu.framework.Tapahtumalista;
import kasinoSimulaattori.simu.framework.Trace;
import kasinoSimulaattori.simu.model.Asiakas.Ominaisuus;
import kasinoSimulaattori.util.Sijainti;

public class Peli extends Palvelupiste {

	private final double yhdenPelinKesto = 10;
	private int pelaajatPoydassa;
	private int pelipaikkojenMaara = 7;
	private Asiakas[] pelipisteet = new Asiakas[pelipaikkojenMaara];
	private double jononpituus = 0;
	private Uniform pelitUniform;
	// Lista blackjack pöydästä poistuvien asiakkaiden poistumisajoista.
	private PriorityQueue<Double> poistumisajatLista = new PriorityQueue<Double>();

	public Peli(Negexp generator, Tapahtumalista tapahtumalista, Sijainti sijainti, Uniform nextTapahtumaUniform) {
		super(generator, tapahtumalista, sijainti, nextTapahtumaUniform);
		pelitUniform = Kasino.getPelitUniform();
	}

	public int getPelaajatPoydassa() {
		return pelaajatPoydassa;
	}

	// Pelikohtaiset säädöt:

	// public int getMinBet() {
	// return minBet;
	// }

	// public void setMinBet(int minBet) {
	// this.minBet = minBet;
	// }

	// public int getMaxBet() {
	// return maxBet;
	// }

	// public void setMaxBet(int maxBet) {
	// this.maxBet = maxBet;
	// }

	// public double getPelinVoittoprosentti() {
	// return pelinVoittoprosentti;
	// }

	// public void setPelinVoittoprosentti(double pelinVoittoprosentti) {
	// this.pelinVoittoprosentti = pelinVoittoprosentti;
	// }

	// public double getPelinTasapeliprosentti() {
	// return pelinTasapeliprosentti;
	// }

	// public void setPelinTasapeliprosentti(double pelinTasapeliprosentti) {
	// this.pelinTasapeliprosentti = pelinTasapeliprosentti;
	// }

	@Override
	public void aloitaPalvelu() {

		// Hae pelin asetukset
		double pelinVoittoprosentti = Kasino.getBlackjackVoittoprosentti();
		Trace.out(Trace.Level.INFO, "pelinVoittoprosentti: " + pelinVoittoprosentti);
		double pelinTasapeliprosentti = Kasino.getBlackjackTasapeliprosentti();
		double minBet = Kasino.getMinBet();
		double maxBet = Kasino.getMaxBet();

		Trace.out(Trace.Level.INFO, "Kasino.getKasinonRahat(): " + Kasino.getKasinonRahat());
		double varakkuusYksiDouble = Kasino.asiakkaanVarakkuus1Double;

		Asiakas asiakas = jono.get(0);

		double palveluaika = yhdenPelinKesto;

		boolean jatkaa = true;
		boolean poistuu = false;

		while (jatkaa) {

			// Alenna asiakkaan päihtyneisyyttä, kun hän pelaa
			if ((asiakas.getOminaisuudet(Ominaisuus.PAIHTYMYS) - 0.005) > 0)
				asiakas.setOminaisuus(Ominaisuus.PAIHTYMYS, (asiakas.getOminaisuudet(Ominaisuus.PAIHTYMYS) - 0.01));

			double asiakkaanMieliala = asiakas.getOminaisuudet(Ominaisuus.MIELIALA);
			double asiakkaanVarakkuus = asiakas.getOminaisuudet(Ominaisuus.VARAKKUUS);
			double asiakkaanUhkarohkeus = asiakas.getOminaisuudet(Ominaisuus.UHKAROHKEUS);
			double asiakkaanPaihtymys = asiakas.getOminaisuudet(Ominaisuus.PAIHTYMYS);

			if (Kasino.getKasinonRahat() <= 0) {
				// Lopeta simulointi.
				Kasino.setVararikko(true);
				break;
			}

			if (asiakkaanVarakkuus <= 0 || asiakkaanMieliala <= 0) {
				// Asiakas poistuu kasinolta.
				poistuu = true;
				jatkaa = false;
				break;
			}

			double asiakkaanKokOminaisuudet = asiakkaanMieliala * asiakkaanVarakkuus * asiakkaanUhkarohkeus
					* asiakkaanPaihtymys;

			// TODO pelaa peli:
			double bet = pelitUniform.sample() * asiakkaanKokOminaisuudet;

			if (bet * varakkuusYksiDouble > maxBet) {
				bet = maxBet / varakkuusYksiDouble;
			} else if (bet * varakkuusYksiDouble < minBet) {
				bet = minBet / varakkuusYksiDouble;
			}

			double pelinTulos = pelitUniform.sample();
			Trace.out(Trace.Level.INFO, "pelinTulos: " + pelinTulos);

			if (pelinTulos <= pelinVoittoprosentti) {
				// VOITTO ASIAKKAALLE
				asiakas.setOminaisuus(Ominaisuus.VARAKKUUS, (asiakkaanVarakkuus + bet));
				if ((asiakas.getAsiakkaanVoitto()) > 0) {

					Trace.out(Trace.Level.INFO, "voitto statsit: " + asiakkaanMieliala + bet +
							asiakas.getAsiakkaanVoitto());

					asiakas.setOminaisuus(Ominaisuus.MIELIALA,
							(asiakkaanMieliala + bet + asiakas.getAsiakkaanVoitto()));
				} else {
					asiakas.setOminaisuus(Ominaisuus.MIELIALA, (asiakkaanMieliala + bet));
				}
				Kasino.loseMoney((bet * varakkuusYksiDouble));

				Trace.out(Trace.Level.INFO, "VOITTO: " + "BET: " + (bet *
						varakkuusYksiDouble) + "\n" + asiakas + "\n");
				Trace.out(Trace.Level.INFO, "Kasino.getKasinonRahat(): " + Kasino.getKasinonRahat());
			} else if (pelinTulos > pelinVoittoprosentti
					&& pelinTulos <= (pelinVoittoprosentti + pelinTasapeliprosentti)) {
				// TASAPELI
				Trace.out(Trace.Level.INFO, "TASAPELI");

			} else {
				// HÄVIÖ ASIAKKAALLE
				asiakas.setOminaisuus(Ominaisuus.VARAKKUUS, (asiakkaanVarakkuus - bet));
				asiakas.setOminaisuus(Ominaisuus.MIELIALA, (asiakkaanMieliala - bet + asiakas.getAsiakkaanVoitto()));
				Kasino.gainMoney((bet * varakkuusYksiDouble));

				Trace.out(Trace.Level.INFO, "HÄVIÖ: " + "BET: " + (bet *
						varakkuusYksiDouble) + "\n" + asiakas + "\n");
				Trace.out(Trace.Level.INFO, "Kasino.getKasinonRahat(): " + Kasino.getKasinonRahat());
			}

			asiakkaanMieliala = asiakas.getOminaisuudet(Ominaisuus.MIELIALA);
			asiakkaanVarakkuus = asiakas.getOminaisuudet(Ominaisuus.VARAKKUUS);
			asiakkaanUhkarohkeus = asiakas.getOminaisuudet(Ominaisuus.UHKAROHKEUS);
			asiakkaanPaihtymys = asiakas.getOminaisuudet(Ominaisuus.PAIHTYMYS);

			asiakkaanKokOminaisuudet = asiakkaanMieliala * asiakkaanVarakkuus * asiakkaanUhkarohkeus
					* asiakkaanPaihtymys;

			// Laske numero, jonka asiakkaa kokonaisominaisuuksien pitää voittaa, jotta
			// asiakas jatkaa pelaamista. Lasketaan 4 samplella, koska asiakkaalla on 4 eri
			// ominaisuutta.
			double jatkaakoSample = pelitUniform.sample() * pelitUniform.sample() * pelitUniform.sample()
					* pelitUniform.sample();

			Trace.out(Trace.Level.INFO, "asiakkaanKokOminaisuudet: " + asiakkaanKokOminaisuudet);
			Trace.out(Trace.Level.INFO, "jatkaakoSample: " + jatkaakoSample);
			Trace.out(Trace.Level.INFO, "\n");

			if (jatkaakoSample > asiakkaanKokOminaisuudet) {
				jatkaa = false;
			} else {
				palveluaika += yhdenPelinKesto;
			}
		}

		if (!Kasino.isVararikko()) {

			// double palveluaika = generator.sample();
			lisaaPalveluAikaa(palveluaika);

			Trace.out(Trace.Level.INFO, "Aloitetaan pelipalvelu:" + " ["
					+ this.getClass().toString() + " " + getId() + " ]");

			// Asiakas otetaan sisään
			TapahtumanTyyppi tyyppi;
			if (!poistuu) {
				tyyppi = arvoTapahtuma();
			} else {
				tyyppi = TapahtumanTyyppi.ULOSKAYNTI;
			}
			asiakas.setStatus(tyyppi);
			Trace.out(Trace.Level.INFO, "Aloitetaan uusi palvelu, asiakas " + asiakas.getId() + " ["
					+ this.getClass().toString() + " " + getId() + " ]");
			Trace.out(Trace.Level.INFO, asiakas);
			double poistumisaika = Kello.getInstance().getAika() + palveluaika;

			tapahtumalista.lisaa(
					new Tapahtuma(tyyppi, poistumisaika, TapahtumanTyyppi.PELI,
							getId(), asiakas.getId()));
			poistumisajatLista.add(poistumisaika);

			// Lisätään blackjack pöytään pelaajia jonosta.
			// Jos joku pöydän pelipiste jää tyhjäksi pöytä ei ole varattu vielä kokonaan.
			// Lasketaan samalla myös monta pelaajaa pöydässä on jo pelaamassa.
			pelaajatPoydassa = 0;
			varattu = true;
			for (int i = 0; i < pelipisteet.length; i++) {
				if (pelipisteet[i] == null) {
					if (jono.size() != 0) {
						pelipisteet[i] = jono.poll();
						pelaajatPoydassa++;
					}
				} else {
					pelaajatPoydassa++;
				}
				if (pelipisteet[i] == null)
					varattu = false;
			}

			Trace.out(Trace.Level.INFO, "pelin jono: " + jono.size() + "\npelin pelaajat: " + pelaajatPoydassa);

			if (varattu) {
				jononpituus += (poistumisajatLista.peek() - Kello.getInstance().getAika());
				Trace.out(Trace.Level.INFO, "jononpituus peli: " + jononpituus);
			}
		}
	}

	// Poistetaan palvelussa ollut asiakas asiakkaan ID:n mukaan
	@Override
	public Asiakas otaJonostaIDnMukaan(int poistettavanAsiakkaanID) {

		for (int i = 0; i < pelipisteet.length; i++) {
			if (pelipisteet[i] != null) {
				if (poistettavanAsiakkaanID == pelipisteet[i].getId()) {
					Asiakas a = pelipisteet[i];
					pelipisteet[i] = null;
					pelaajatPoydassa--;
					lisaaPalveltuAsiakas();
					poistumisajatLista.poll();
					varattu = false;
					return a;
				}
			}
		}
		System.err.println("Asiakasta " + poistettavanAsiakkaanID + "ei löytynyt jonosta.");
		return null;
	}

	public double getJononpituus() {
		return jononpituus;
	}
}
