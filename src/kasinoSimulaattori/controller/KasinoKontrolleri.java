package kasinoSimulaattori.controller;

import java.util.LinkedList;

import javafx.application.Platform;
import kasinoSimulaattori.simu.model.IOmaMoottori;
import kasinoSimulaattori.simu.model.KasinoTulokset;
import kasinoSimulaattori.simu.model.OmaMoottori;
import kasinoSimulaattori.simu.model.Palvelupiste;
import kasinoSimulaattori.simu.model.TapahtumanTyyppi;
import kasinoSimulaattori.view.ISimulaattorinUI;

/**
 * Kontrolleri välittämään tietoa näkymästä malliin ja toisinpäin.
 * @author Vilhelm Niemi
 */
public class KasinoKontrolleri implements IKontrolleriVtoM, IKontrolleriMtoV {

	private IOmaMoottori moottori;
	private ISimulaattorinUI ui;

	/**
	 * Kasinokontrollerin konstruktori metodi.
	 * @param ui viite näkymään.
	 */
	public KasinoKontrolleri(ISimulaattorinUI ui) {
		this.ui = ui;
	}

	// IKontrolleriMtoV:

	// Visualisointi
	
	@Override
	public void lopetaVisualisointi(String viesti) {
		Platform.runLater(() -> ui.getGui().resetStartButton());
		ui.getVisualisointi().lopetaVisualisointi(viesti);
	}

	@Override
	public void naytaLoppuaika(double aika) {
		Platform.runLater(() -> ui.setLoppuaika(aika));
	}

	// Animointi
	
	@Override
	public void visualisoiAsiakas(int x1, int y1, int x2, int y2) {
		Platform.runLater(() -> ui.getVisualisointi().asiakkaanLiikeAnimaatio(x1, y1, x2, y2));
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

	@Override
	public void baariTyontekijat(int maara) {
		this.ui.getVisualisointi().setBaariTyontekijoidenMaara(maara);
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

	@Override
	public void blackjackTyontekijat(int maara) {
		this.ui.getVisualisointi().setBlackjackTyontekijoidenMaara(maara);
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

	@Override
	public void sisaankayntiTyontekijat(int maara) {
		this.ui.getVisualisointi().setSisaankayntiTyontekijoidenMaara(maara);
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

	@Override
	public void uloskayntiTyontekijat(int maara) {
		this.ui.getVisualisointi().setUloskayntiTyontekijoidenMaara(maara);
	}

	// UI

	@Override
	public void setAika(String value) {
		this.ui.getGui().setAika(value);
	}

	@Override
	public void setRahat(String value) {
		this.ui.getGui().setRahat(value);
	}

	@Override
	public void setVoitot(String value) {
		this.ui.getGui().setVoitot(value);
	}

	@Override
	public void setSaapuneet(String value) {
		this.ui.getGui().setSaapuneet(value);
	}

	@Override
	public void setPalvellut(String value) {
		this.ui.getGui().setPalvellut(value);
	}

	@Override
	public void setAvgJono(String value) {
		this.ui.getGui().setAvgJono(value);
	}

	@Override
	public void setKokonaisoleskelu(String value) {
		this.ui.getGui().setKokonaisoleskelu(value);
	}

	@Override
	public void setAvgOnnellisuus(String value) {
		this.ui.getGui().setAvgOnnellisuus(value);
	}

	@Override
	public void setAvgPaihtymys(String value) {
		this.ui.getGui().setAvgPaihtymys(value);
	}

	@Override
	public void setAvgVarallisuus(String value) {
		this.ui.getGui().setAvgVarallisuus(value);
	}

	@Override
	public void setAvgLapimeno(String value) {
		this.ui.getGui().setAvgLapimeno(value);
	}

	@Override
	public void naytaTulokset(KasinoTulokset[] tulokset) {
		Platform.runLater(() -> ui.naytaTulokset(tulokset));
	}
	
	@Override
	public void virheilmoitusDialogi(String viesti) {
		Platform.runLater(() -> ui.virheilmoitusDialogi(viesti));
	}
	
	@Override
	public void ilmoitusDialogi(String viesti) {
		Platform.runLater(() -> ui.ilmoitusDialogi(viesti));
	}
	
	@Override
	public boolean kyllaTaiEiDialogi(String viesti) {
		return ui.kyllaTaiEiDialogi(viesti);
	}
	
	// IKontrolleriVtoM:

	@Override
	public void asetaSyotteetMoottoriin(double aika, long viive, double mainostus, double max, double min, double yllapito,
			double tasapeli, double voitto, int pelit, int baarit, int sisaankaynnit, int uloskaynnit) {
		moottori = new OmaMoottori(this);
		moottori.setSimulointiaika(aika);
		moottori.setViive(viive);
		moottori.setMainostusRahamaara(mainostus);
		moottori.setMaxBet(max);
		moottori.setMinBet(min);
		moottori.setYllapitoRahamaara(yllapito);
		moottori.setBlackjackTasapeliprosentti(tasapeli);
		moottori.setBlackjackVoittoprosentti(voitto);
		moottori.lisaaPalvelupisteita(TapahtumanTyyppi.PELI, pelit);
		moottori.lisaaPalvelupisteita(TapahtumanTyyppi.BAARI, baarit);
		moottori.lisaaPalvelupisteita(TapahtumanTyyppi.SISAANKAYNTI, sisaankaynnit);
		moottori.lisaaPalvelupisteita(TapahtumanTyyppi.ULOSKAYNTI, uloskaynnit);
	}

	@Override
	public void kaynnistaSimulointi(){
		((Thread) moottori).start();
	}

	public void resetoiSimulointi(){
		moottori = new OmaMoottori(this);
	}

	@Override
	public void nopeuta() {
		moottori.setViive((long) (moottori.getViive() * 0.9));
		ui.getVisualisointi().setAsiakasNopeus(ui.getVisualisointi().getAsiakasNopeus() * 1.1);
		ui.getVisualisointi().setTaustaLiukuNopeus(ui.getVisualisointi().getTaustaLiukuNopeus() * 1.1);
	}

	@Override
	public void hidasta() {
		moottori.setViive((long) (moottori.getViive() * 1.1));
		ui.getVisualisointi().setAsiakasNopeus(ui.getVisualisointi().getAsiakasNopeus() * 0.9);
		ui.getVisualisointi().setTaustaLiukuNopeus(ui.getVisualisointi().getTaustaLiukuNopeus() * 0.9);
	}

	@Override
	public double[] haeTulokset() {
		return moottori.getTulokset();
	}

	@Override
	public LinkedList<Palvelupiste> haePalvelupisteet(int palvelu) {
		return moottori.getPalvelupisteet(palvelu);
	}

	@Override
	public void jatkaSimulointia() {
		moottori.notifyThis();
	}

	@Override
	public boolean simuloidaan() {
		if (moottori == null)
			return false;
		return moottori.simuloidaan();
	}

	@Override
	public void resetVisualistointi() {
		Platform.runLater(() -> ui.resetVisualisointi());
	}

	@Override
	public boolean getKasinoPause() {
		return moottori.getKasinoPause();
	}

	@Override
	public void setKasinoPause(boolean pause) {
		moottori.setKasinoPause(pause);
	}
}
