package kasinoSimulaattori.simu.model;

import kasinoSimulaattori.eduni.distributions.ContinuousGenerator;
import kasinoSimulaattori.eduni.distributions.Negexp;
import kasinoSimulaattori.simu.framework.Kello;
import kasinoSimulaattori.simu.framework.Tapahtuma;
import kasinoSimulaattori.simu.framework.Tapahtumalista;
import kasinoSimulaattori.util.Sijainti;

public class Sisaankaynti extends Palvelupiste {

	public Sisaankaynti(Negexp generator, Tapahtumalista tapahtumalista, Sijainti sijainti) {
		super(generator, tapahtumalista, sijainti);
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
