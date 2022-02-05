package simu.model;

import eduni.distributions.ContinuousGenerator;
import simu.framework.Kello;
import simu.framework.Tapahtuma;
import simu.framework.Tapahtumalista;
import simu.framework.Trace;

public class Peli extends Palvelupiste {

	private int pelaajatPoydassa;

	public Peli(ContinuousGenerator generator, Tapahtumalista tapahtumalista) {
		super(generator, tapahtumalista);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void aloitaPalvelu() {
		double palveluaika = generator.sample();

		// Asiakkaat otetaan sisään
		// Laske Monta pelaajaa pöydässä on kokonaisuudessaan:
		laskePelaajatPoydassa();

		Trace.out(Trace.Level.INFO, " ["
				+ this.getClass().toString() + " " + getId() + " ] jono: " + jono.size());

		for (int i = 0; i < pelaajatPoydassa; i++) {
			if (!jono.get(i).isPalveltavana()) {
				TapahtumanTyyppi tyyppi = arvoTapahtuma();
				jono.get(i).setPalveltavana(true);
				jono.get(i).setStatus(tyyppi);
				Trace.out(Trace.Level.INFO, "Aloitetaan uusi palvelu, asiakas " + jono.get(i).getId() + " ["
						+ this.getClass().toString() + " " + getId() + " ]");
				System.out.println(jono.get(i));

				tapahtumalista.lisaa(
						new Tapahtuma(tyyppi, Kello.getInstance().getAika() + palveluaika, TapahtumanTyyppi.PELI,
								getId(), jono.get(i).getId()));
			}
		}

		if (pelaajatPoydassa == 7) {
			varattu = true;
		}
	}

	private void laskePelaajatPoydassa() {
		if (jono.size() >= 7)
			pelaajatPoydassa = 7;
		else
			pelaajatPoydassa = jono.size();
	}

	@Override
	public Asiakas otaJonostaIDnMukaan(int poistettavanAsiakkaanID) { // Poistetaan palvelussa ollut asiakas asiakkaan
																		// ID:n mukaan
		varattu = false;

		laskePelaajatPoydassa();

		for (int i = 0; i < pelaajatPoydassa; i++) {
			if (poistettavanAsiakkaanID == jono.get(i).getId()) {
				jono.get(i).setPalveltavana(false);
				return jono.remove(i);
			}
		}
		System.err.println("Asiakasta " + poistettavanAsiakkaanID + "ei löytynyt jonosta.");
		return null;
	}
}
