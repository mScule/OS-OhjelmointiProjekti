package simu.model;

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

		Trace.out(Trace.Level.INFO, "pelin jono: " + jono.size() + "\npelin pelaajat: " + pelaajatPoydassa);

		for (int i = 0; i < pelaajatPoytaan; i++) {
			TapahtumanTyyppi tyyppi = arvoTapahtuma();
			jono.get(i).setStatus(tyyppi);
			Trace.out(Trace.Level.INFO, "Aloitetaan uusi palvelu, asiakas " + jono.get(i).getId() + " ["
					+ this.getClass().toString() + " " + getId() + " ]");
			System.out.println(jono.get(i));

			tapahtumalista.lisaa(
					new Tapahtuma(tyyppi, Kello.getInstance().getAika() + palveluaika, TapahtumanTyyppi.PELI,
							getId(), jono.get(i).getId()));
		}

		// Lisätään blackjack pöytään pelaajia jonosta.
		// Jos joku pöydän pelipiste jää tyhjäksi pöytä ei ole varattu vielä kokonaan.
		// Lasketaan samalla myös monta pelaajaa pöydässä on jo pelaamassa.
		pelaajatPoydassa = 0;
		for (int i = 0; i < pelipisteet.length; i++) {
			varattu = true;
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
					lisaaPalveltuAsiakas();
					varattu = false;
					return a;
				}
			}
		}
		System.err.println("Asiakasta " + poistettavanAsiakkaanID + "ei löytynyt jonosta.");
		return null;
	}
}
