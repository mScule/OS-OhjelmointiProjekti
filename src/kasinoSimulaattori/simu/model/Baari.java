package kasinoSimulaattori.simu.model;

import kasinoSimulaattori.eduni.distributions.Negexp;
import kasinoSimulaattori.eduni.distributions.Uniform;
import kasinoSimulaattori.simu.framework.Kello;
import kasinoSimulaattori.simu.framework.Tapahtuma;
import kasinoSimulaattori.simu.framework.Tapahtumalista;
import kasinoSimulaattori.simu.framework.Trace;
import kasinoSimulaattori.simu.model.Asiakas.Ominaisuus;
import kasinoSimulaattori.util.Sijainti;

/**
 * Kasinon baari palvelupiste. Voi kasvattaa kasinon asiakkaan päihtyneisyys
 * arvoa, kun asiakas käy tällä palvelupisteellä.
 * 
 * @author Jonathan Methuen
 */
public class Baari extends Palvelupiste {

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
	public Baari(Negexp negexpGenerator, Tapahtumalista tapahtumalista, Sijainti sijainti,
			Uniform nextTapahtumaUniform) {
		super(negexpGenerator, tapahtumalista, sijainti, nextTapahtumaUniform);
	}

	@Override
	public void aloitaPalvelu() {
		varattu = true;
		super.aloitaPalvelu();
		double palveluaika = negexpGenerator.sample();
		Trace.out(Trace.Level.INFO, "generator:" + negexpGenerator);
		Trace.out(Trace.Level.INFO, "palveluaika: " + palveluaika);
		lisaaPalveluAikaa(palveluaika);
		Asiakas a = jono.peek();

		a.setOminaisuus(Ominaisuus.PAIHTYMYS, a.getOminaisuus(Ominaisuus.PAIHTYMYS)
				+ new Uniform(0, 0.2, 1337).sample());

		// Arvotaan tapahtuman tyyppi (muu kuin sisäänkäynti)
		TapahtumanTyyppi tyyppi = arvoTapahtuma();

		a.setStatus(tyyppi);
		tapahtumalista.lisaa(new Tapahtuma(tyyppi, Kello.getInstance().getAika() + palveluaika, TapahtumanTyyppi.BAARI,
				getId(), a.getId()));
	}

}
