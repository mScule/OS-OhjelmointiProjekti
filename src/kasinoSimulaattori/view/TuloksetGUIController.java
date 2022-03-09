package kasinoSimulaattori.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import kasinoSimulaattori.simu.model.KasinoTulokset;

public class TuloksetGUIController {
	
	@FXML
	private Label aika;
	@FXML
	private Label ajo;
	@FXML
	private Label baariTyontekijat;
	@FXML
	private Label blackjackTyontekijat;
	@FXML
	private Label keskLapimenoaika;
	@FXML
	private Label keskOleskeluaika;
	@FXML
	private Label keskOnnellisuus;
	@FXML
	private Label keskPaihtymys;
	@FXML
	private Label keskVarallisuus;
	@FXML
	private Label kokonaisoleskeluaika;
	@FXML
	private Label mainostus;
	@FXML
	private Label maksimipanos;
	@FXML
	private Label minimipanos;
	@FXML
	private Label palvellutAsiakkaat;
	@FXML
	private Label raha;
	@FXML
	private Label saapuneetAsiakkaat;
	@FXML
	private Label sisaankayntiTyontekijat;
	@FXML
	private Label tasapeliprosentti;
	@FXML
	private Label uloskayntiTyontekijat;
	@FXML
	private Label voitot;
	@FXML
	private Label voittoprosentti;
	@FXML
	private Label yllapito;
	@FXML
	private Label keskJonotusaika;

	public void nayta(KasinoTulokset tulokset) {
		aika.setText(tulokset.getAika() + "");
		mainostus.setText(tulokset.getMainostus() + "");
		maksimipanos.setText(tulokset.getMaksimiPanos() + "");
		minimipanos.setText(tulokset.getMinimiPanos() + "");
		yllapito.setText(tulokset.getYllapito() + "");
		tasapeliprosentti.setText(tulokset.getTasapeliProsentti() + "");
		voittoprosentti.setText(tulokset.getVoittoProsentti() + "");
		raha.setText(tulokset.getRahat() + "");
		voitot.setText(tulokset.getVoitot() + "");
		keskJonotusaika.setText(tulokset.getKeskJonotusaika() + "");
		kokonaisoleskeluaika.setText(tulokset.getKokonaisoleskeluaika() + "");
		keskOnnellisuus.setText(tulokset.getKeskOnnellisuus() + "");
		keskPaihtymys.setText(tulokset.getKeskPaihtymys() + "");
		keskVarallisuus.setText(tulokset.getKeskVarallisuus() + "");
		keskLapimenoaika.setText(tulokset.getKeskLapimenoaika() + "");
		blackjackTyontekijat.setText(tulokset.getBlackjackPoydat() + "");
		baariTyontekijat.setText(tulokset.getBaarit() + "");
		sisaankayntiTyontekijat.setText(tulokset.getSisaankaynnit() + "");
		uloskayntiTyontekijat.setText(tulokset.getUloskaynnit() + "");
		saapuneetAsiakkaat.setText(tulokset.getSaapuneetAsiakkaat() + "");
		palvellutAsiakkaat.setText(tulokset.getPalvellutAsiakkaat() + "");
	}
}
