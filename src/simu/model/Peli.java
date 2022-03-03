package simu.model;

import java.util.PriorityQueue;

import eduni.distributions.ContinuousGenerator;
import eduni.distributions.Uniform;
import simu.framework.Kello;
import simu.framework.Tapahtuma;
import simu.framework.Tapahtumalista;
import simu.framework.Trace;
import simu.model.Asiakas.Ominaisuus;

public class Peli extends Palvelupiste {

	private final double yhdenPelinKesto = 10;
	private int pelaajatPoydassa;
	private int pelipaikkojenMaara = 7;
	private Asiakas[] pelipisteet = new Asiakas[pelipaikkojenMaara];
	private double jononpituus = 0;
	private Uniform uniform;
	private int minBet = 100;
	private int maxBet = 1000;
	private double pelinVoittoprosentti = 0.4222;
	private double pelinTasapeliprosentti = 0.0848;

	// Lista blackjack pöydästä poistuvien asiakkaiden poistumisajoista.
	private PriorityQueue<Double> poistumisajatLista = new PriorityQueue<Double>();

	public Peli(ContinuousGenerator generator, Tapahtumalista tapahtumalista) {
		super(generator, tapahtumalista);
	}

	@Override
	public void aloitaPalvelu() {
		System.out.println("Kasino.getKasinonRahat(): " + Kasino.getKasinonRahat());
		double varakkuusYksiDouble = Kasino.asiakkaanVarakkuus1Double;
		uniform = Kasino.getPelitUniform();

		Asiakas asiakas = jono.get(0);

		double palveluaika = yhdenPelinKesto;

		boolean jatkaa = true;
		boolean poistuu = false;

		while (jatkaa) {
			double asiakkaanMieliala = asiakas.getOminaisuudet(Ominaisuus.MIELIALA);
			double asiakkaanVarakkuus = asiakas.getOminaisuudet(Ominaisuus.VARAKKUUS);
			double asiakkaanUhkarohkeus = asiakas.getOminaisuudet(Ominaisuus.UHKAROHKEUS);
			double asiakkaanPaihtymys = asiakas.getOminaisuudet(Ominaisuus.PAIHTYMYS);

			if (Kasino.getKasinonRahat() <= 0) {
				// TODO: lopeta simulointi.
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
			double bet = uniform.sample() * asiakkaanKokOminaisuudet;

			if (bet * varakkuusYksiDouble > maxBet) {
				bet = maxBet / varakkuusYksiDouble;
			} else if (bet * varakkuusYksiDouble < minBet) {
				bet = minBet / varakkuusYksiDouble;
			}

			// bet = minBet / varakkuusYksiDouble;

			double pelinTulos = uniform.sample();

			if (pelinTulos <= pelinVoittoprosentti) {
				// VOITTO ASIAKKAALLE
				asiakas.setOminaisuus(Ominaisuus.VARAKKUUS, (asiakkaanVarakkuus + bet));
				asiakas.setOminaisuus(Ominaisuus.MIELIALA, (asiakkaanVarakkuus + bet));
				Kasino.loseMoney((bet * varakkuusYksiDouble));

				System.out.println("VOITTO: " + asiakas + "BET: " + (bet *
						varakkuusYksiDouble));
				System.out.println("Kasino.getKasinonRahat(): " + Kasino.getKasinonRahat());
			} else if (pelinTulos > pelinVoittoprosentti
					&& pelinTulos <= (pelinVoittoprosentti + pelinTasapeliprosentti)) {
				// TASAPELI
				System.out.println("TASAPELI");

			} else {
				// HÄVIÖ ASIAKKAALLE
				asiakas.setOminaisuus(Ominaisuus.VARAKKUUS, (asiakkaanVarakkuus - bet));
				asiakas.setOminaisuus(Ominaisuus.MIELIALA, (asiakkaanVarakkuus - bet));
				Kasino.gainMoney((bet * varakkuusYksiDouble));

				System.out.println("HÄVIÖ " + asiakas + "BET: " + (bet *
						varakkuusYksiDouble));
				System.out.println("Kasino.getKasinonRahat(): " + Kasino.getKasinonRahat());
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
			double jatkaakoSample = uniform.sample() * uniform.sample() * uniform.sample() * uniform.sample();

			System.out.println("asiakkaanKokOminaisuudet: " + asiakkaanKokOminaisuudet);
			System.out.println("jatkaakoSample: " + jatkaakoSample);
			System.out.println();

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
			System.out.println(asiakas);
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
				System.out.println("jononpituus peli: " + jononpituus);
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
