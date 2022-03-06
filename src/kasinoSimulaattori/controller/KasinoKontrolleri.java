package kasinoSimulaattori.controller;

import java.util.LinkedList;

import javafx.application.Platform;
import kasinoSimulaattori.simu.model.IOmaMoottori;
import kasinoSimulaattori.simu.model.OmaMoottori;
import kasinoSimulaattori.simu.model.Palvelupiste;
import kasinoSimulaattori.view.ISimulaattorinUI;

public class KasinoKontrolleri implements IKontrolleriVtoM, IKontrolleriMtoV {

	private IOmaMoottori moottori;
	private ISimulaattorinUI ui;
	
	public KasinoKontrolleri(ISimulaattorinUI ui) {
		this.ui = ui;
	}
	
	// IKontrolleriMtoV:
	
	@Override
	public void naytaLoppuaika(double aika) {
		Platform.runLater(() -> ui.setLoppuaika(aika));
	}

	@Override
	public void visualisoiAsiakas(int x1, int y1, int x2, int y2) {
		Platform.runLater(() -> 
			ui.getVisualisointi().asiakkaanLiikeAnimaatio(x1, y1, x2, y2)
		);
	}
	
	// Baari
	@Override
	public void baariPalveltavat(int maara) {
		this.ui.getVisualisointi().setBaariPalveltavienMaara(maara);
	}
	@Override
	public void baariJonossa(int maara) {
		this.ui.getVisualisointi().setBaariJononPituus(maara);
	}
	
	// Blackjack
	@Override
	public void blackjackPalveltavat(int maara) {
		this.ui.getVisualisointi().setBlackjackPalveltavienMaara(maara);
	}
	@Override
	public void blackjackJonossa(int maara) {
		this.ui.getVisualisointi().setBlackjackJononPituus(maara);
	}
	
	// Sisäänkäynti
	@Override
	public void sisaankayntiPalveltavat(int maara) {
		this.ui.getVisualisointi().setSisaankayntiPalveltavienMaara(maara);
	}
	@Override
	public void sisaankayntiJonossa(int maara) {
		this.ui.getVisualisointi().setSisaankayntiJononPituus(maara);
	}
	
	// Uloskäynti
	@Override
	public void uloskayntiPalveltavat(int maara) {
		this.ui.getVisualisointi().setUloskayntiPalveltavienMaara(maara);
	}
	@Override
	public void uloskayntiJonossa(int maara) {
		this.ui.getVisualisointi().setUloskayntiJononPituus(maara);
	}

	// IKontrolleriVtoM:
	
	@Override
	public void kaynnistaSimulointi() {
		moottori = new OmaMoottori(this);
		moottori.setSimulointiaika(ui.getAika());
		
		moottori.setViive(ui.getViive());
		((Thread)moottori).start();
	}

	@Override
	public void nopeuta() {
		moottori.setViive((long)(moottori.getViive() * 0.9));
	}

	@Override
	public void hidasta() {
		moottori.setViive((long)(moottori.getViive() * 1.1));
	}

	@Override
	public double[] haeTulokset() {
		return moottori.getTulokset();
	}

	@Override
	public LinkedList<Palvelupiste> haePalvelupisteet(int palvelu) {
		return moottori.getPalvelupisteet(palvelu);
	}
}
