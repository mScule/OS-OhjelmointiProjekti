package kasinoSimulaattori.simu.model;

import kasinoSimulaattori.eduni.distributions.Negexp;
import kasinoSimulaattori.eduni.distributions.Uniform;
import kasinoSimulaattori.simu.framework.Kello;
import kasinoSimulaattori.simu.framework.Tapahtuma;
import kasinoSimulaattori.simu.framework.Tapahtumalista;
import kasinoSimulaattori.util.Sijainti;

/**
 * Kasinon sisäänkäynti luokka. Asiakkaita palvellaan ensimmäisenä tässä
 * luokassa, kun he saapuvat kasinolle.
 * 
 * @author Jonathan Methuen, Vilhelm Niemi
 */
public class Sisaankaynti extends Palvelupiste {

	/**
	 * Palvelupisteen konstruktori
	 * 
	 * @param negexpGenerator      Palvelupisteen palveluaikojen arvontaan käytetty
	 *                             Negexp jakauma
	 * @param tapahtumalista       Viittaus simulaation tapahtumalistaan
	 * @param sijainti             Palvelupisteen sijainti x- ja y-akselilla
	 * @param nextTapahtumaUniform Seuraavan tapahtuman arvontaan käytetty Uniform
	 *                             jakauma
	 */
	public Sisaankaynti(Negexp negexpGenerator, Tapahtumalista tapahtumalista, Sijainti sijainti,
			Uniform nextTapahtumaUniform) {
		super(negexpGenerator, tapahtumalista, sijainti, nextTapahtumaUniform);
	}

	@Override
	public void aloitaPalvelu() {
		varattu = true;
		super.aloitaPalvelu();

		double palveluaika = negexpGenerator.sample();
		lisaaPalveluAikaa(palveluaika);

		Asiakas a = jono.peek();

		TapahtumanTyyppi tyyppi = arvoTapahtuma();

		a.setStatus(tyyppi);

		tapahtumalista.lisaa(new Tapahtuma(tyyppi, Kello.getInstance().getAika() + palveluaika,
				TapahtumanTyyppi.SISAANKAYNTI, getId(), a.getId()));
	}
}
