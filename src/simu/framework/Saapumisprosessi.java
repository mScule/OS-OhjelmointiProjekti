package simu.framework;

import eduni.distributions.*;
import simu.model.Kasino;
import simu.model.TapahtumanTyyppi;

public class Saapumisprosessi {

	private ContinuousGenerator generaattori;
	private Tapahtumalista tapahtumalista;
	private TapahtumanTyyppi tyyppi;

	public Saapumisprosessi(ContinuousGenerator g, Tapahtumalista tl, TapahtumanTyyppi tyyppi) {
		this.generaattori = g;
		this.tapahtumalista = tl;
		this.tyyppi = tyyppi;
	}

	public void generoiSeuraava() {
		Tapahtuma t = new Tapahtuma(tyyppi, Kello.getInstance().getAika() + generaattori.sample(), null, 0, 0);
		tapahtumalista.lisaa(t);
	}

	public void setKeskimSaapumisvaliaika(double keskimSaapumisaika) {
		double valiaikaMultiplier = 1;

		for (int i = 0; i < 6; i++) {
			// System.out.println("valiaikaMultiplier: " + valiaikaMultiplier);
			// System.out.println(
			// 		"(0.42 / Kasino.getBlackjackVoittoprosentti(): " + (0.42 / Kasino.getBlackjackVoittoprosentti()));
			valiaikaMultiplier = valiaikaMultiplier * (0.4222 / Kasino.getBlackjackVoittoprosentti());
		}

		// System.out.println("valiaikaMultiplier: " + valiaikaMultiplier);

		keskimSaapumisaika = keskimSaapumisaika * valiaikaMultiplier;
		Negexp uusiGeneraattori = new Negexp(keskimSaapumisaika, Kasino.getSeed());
		generaattori = uusiGeneraattori;
	}
}
