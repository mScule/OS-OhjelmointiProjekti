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

	public void calculateKeskimSaapumisaika() {
		// Pienempi, mitä parempi voittoprosentti asiakkailla on blackjackissä 
		double valiaikaMultiplierWinPer = 1;

		for (int i = 0; i < 6; i++) {
			// Trace.out(Trace.Level.INFO,"valiaikaMultiplier: " + valiaikaMultiplierWinPer);
			// Trace.out(Trace.Level.INFO,
			// 		"(0.42 / Kasino.getBlackjackVoittoprosentti(): " + (0.42 / Kasino.getBlackjackVoittoprosentti()));
			valiaikaMultiplierWinPer = valiaikaMultiplierWinPer * (0.4222 / Kasino.getBlackjackVoittoprosentti());
		}

		double valiaikaMultiplierMinBet = 1;

		for (int i = 0; i < 1; i++) {
			Trace.out(Trace.Level.INFO,"valiaikaMultiplierMinBet: " + valiaikaMultiplierMinBet);
			Trace.out(Trace.Level.INFO,
					"(100 / Kasino.getMinBet()): " + (100 / Kasino.getMinBet()));
			valiaikaMultiplierMinBet = valiaikaMultiplierMinBet * ((double)Kasino.getMinBet() / 100);
		}

		// Trace.out(Trace.Level.INFO,"valiaikaMultiplier: " + valiaikaMultiplier);

		double keskimSaapumisaika = Kasino.getKeskimSaapumisvaliaika();

		keskimSaapumisaika *= valiaikaMultiplierWinPer * valiaikaMultiplierMinBet;
		Trace.out(Trace.Level.INFO,"keskimSaapumisaika: " + keskimSaapumisaika);
		Negexp uusiGeneraattori = new Negexp(keskimSaapumisaika, Kasino.getSeed());
		generaattori = uusiGeneraattori;
	}
}
