package kasinoSimulaattori.simu.model;

import kasinoSimulaattori.eduni.distributions.ContinuousGenerator;
import kasinoSimulaattori.eduni.distributions.Negexp;
import kasinoSimulaattori.eduni.distributions.Uniform;
import kasinoSimulaattori.simu.framework.Kello;
import kasinoSimulaattori.simu.framework.Tapahtuma;
import kasinoSimulaattori.simu.framework.Tapahtumalista;
import kasinoSimulaattori.simu.framework.Trace;
import kasinoSimulaattori.simu.model.Asiakas.Ominaisuus;

public class Baari extends Palvelupiste {

	public Baari(Negexp generator, Tapahtumalista tapahtumalista) {
		super(generator, tapahtumalista);
	}

	@Override
	public void aloitaPalvelu() {
		varattu = true;
		super.aloitaPalvelu();
		double palveluaika = negexpGenerator.sample();
		Trace.out(Trace.Level.INFO,"generator:" + negexpGenerator);
		Trace.out(Trace.Level.INFO,"palveluaika: " + palveluaika);
		lisaaPalveluAikaa(palveluaika);
		Asiakas a = jono.peek();

		a.setOminaisuus(Ominaisuus.PAIHTYMYS, a.getOminaisuudet(Ominaisuus.PAIHTYMYS)
				+ new Uniform(0, 0.2, 1337).sample());

		// Arvotaan tapahtumantyyppi (Muu kuin sisäänkäynti)
		TapahtumanTyyppi tyyppi = arvoTapahtuma();

		a.setStatus(tyyppi);
		tapahtumalista.lisaa(new Tapahtuma(tyyppi, Kello.getInstance().getAika() + palveluaika, TapahtumanTyyppi.BAARI,
				getId(), a.getId()));
	}

}