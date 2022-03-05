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
			// // System.out.println("valiaikaMultiplier: " + valiaikaMultiplierWinPer);
			// // System.out.println(
			// 		"(0.42 / Kasino.getBlackjackVoittoprosentti(): " + (0.42 / Kasino.getBlackjackVoittoprosentti()));
			valiaikaMultiplierWinPer = valiaikaMultiplierWinPer * (0.4222 / Kasino.getBlackjackVoittoprosentti());
		}

		double valiaikaMultiplierMinBet = 1;

		for (int i = 0; i < 1; i++) {
			// System.out.println("valiaikaMultiplierMinBet: " + valiaikaMultiplierMinBet);
			// System.out.println(
			//		"(100 / Kasino.getMinBet()): " + (100 / Kasino.getMinBet()));
			valiaikaMultiplierMinBet = valiaikaMultiplierMinBet * ((double)Kasino.getMinBet() / 100);
		}

		// // System.out.println("valiaikaMultiplier: " + valiaikaMultiplier);

		double keskimSaapumisaika = Kasino.getKeskimSaapumisvaliaika();

		keskimSaapumisaika *= valiaikaMultiplierWinPer * valiaikaMultiplierMinBet;
		// System.out.println("keskimSaapumisaika: " + keskimSaapumisaika);
		Negexp uusiGeneraattori = new Negexp(keskimSaapumisaika, Kasino.getSeed());
		generaattori = uusiGeneraattori;
	}
}
