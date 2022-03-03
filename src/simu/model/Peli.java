package simu.model;

import java.util.PriorityQueue;

import eduni.distributions.ContinuousGenerator;
import simu.framework.Kello;
import simu.framework.Tapahtuma;
import simu.framework.Tapahtumalista;
import simu.framework.Trace;

public class Peli extends Palvelupiste {

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

		// Asiakas otetaan sisään
		TapahtumanTyyppi tyyppi = arvoTapahtuma();
		jono.get(0).setStatus(tyyppi);
		Trace.out(Trace.Level.INFO, "Aloitetaan uusi palvelu, asiakas " + jono.get(0).getId() + " ["
				+ this.getClass().toString() + " " + getId() + " ]");
		System.out.println(jono.get(0));
		double poistumisaika = Kello.getInstance().getAika() + palveluaika;

		tapahtumalista.lisaa(
				new Tapahtuma(tyyppi, poistumisaika, TapahtumanTyyppi.PELI,
						getId(), jono.get(0).getId()));
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
