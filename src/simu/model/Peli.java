package simu.model;

import java.util.PriorityQueue;

import eduni.distributions.ContinuousGenerator;
import simu.framework.Kello;
import simu.framework.Tapahtuma;
import simu.framework.Tapahtumalista;
import simu.framework.Trace;

public class Peli extends Palvelupiste {

	private int pelaajatPoytaan;
	private int pelaajatPoydassa;
	private int pelipaikkojenMaara = 7;
	private Asiakas[] pelipisteet = new Asiakas[pelipaikkojenMaara];
	private double jononpituus = 0;

	// Lista blackjack pöydästä poistuvien asiakkaiden poistumisajoista.
	private PriorityQueue<Double> poistumisajatLista = new PriorityQueue<Double>();

	public Peli(ContinuousGenerator generator, Tapahtumalista tapahtumalista) {
		super(generator, tapahtumalista);
	}

	@Override
	public void aloitaPalvelu() {
		double palveluaika = generator.sample();
		lisaaPalveluAikaa(palveluaika);

		Trace.out(Trace.Level.INFO, "Aloitetaan pelipalvelu:" + " ["
				+ this.getClass().toString() + " " + getId() + " ]");

		// Asiakkaat otetaan sisään
		// Laske monta pelaajaa pöytään otetaan:
		laskePoytaanOtettavatPelaajat();

		for (int i = 0; i < pelaajatPoytaan; i++) {
			TapahtumanTyyppi tyyppi = arvoTapahtuma();
			jono.get(i).setStatus(tyyppi);
			Trace.out(Trace.Level.INFO, "Aloitetaan uusi palvelu, asiakas " + jono.get(i).getId() + " ["
					+ this.getClass().toString() + " " + getId() + " ]");
			System.out.println(jono.get(i));
			double poistumisaika = Kello.getInstance().getAika() + palveluaika;

			tapahtumalista.lisaa(
					new Tapahtuma(tyyppi, poistumisaika, TapahtumanTyyppi.PELI,
							getId(), jono.get(i).getId()));
			poistumisajatLista.add(poistumisaika);
		}

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

	private void laskePoytaanOtettavatPelaajat() {
		if (jono.size() >= pelipaikkojenMaara)
			pelaajatPoytaan = pelipaikkojenMaara - pelaajatPoydassa;
		else if (jono.size() > (pelipaikkojenMaara - pelaajatPoydassa))
			pelaajatPoytaan = pelipaikkojenMaara - pelaajatPoydassa;
		else
			pelaajatPoytaan = jono.size();
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
