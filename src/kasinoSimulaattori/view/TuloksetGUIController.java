package kasinoSimulaattori.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import kasinoSimulaattori.simu.framework.Trace;
import kasinoSimulaattori.simu.model.KasinoTulokset;

/**
 * Kontrolleri simulaatioajojen tuloksia näyttävälle ikkunalle.
 * @author Vilhelm
 */
public class TuloksetGUIController {
	
	private KasinoTulokset[] tulokset = null;
	private int naytettava;
	
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
	@FXML
	private Label tuloksetTaulukkoIndeksi;
	
	/**
	 * Valitsee mahdollisen seuraavan tuloksen annetusta tulostaulukosta, ja näyttää sen tiedot ikkunassa.
	 */
	@FXML
	private void seuraava() {
		naytettava++;
		
		if(naytettava >= tulokset.length)
			naytettava = tulokset.length - 1;
		
		nayta(tulokset[naytettava]);
		
		tuloksetTaulukkoIndeksi(naytettava);
	}
	
	/**
	 * Valitsee mahdollisen edellisen tuloksen annetusta tulostaulukosta, ja näyttää sen tiedot ikkunassa.
	 */
	@FXML
	private void edellinen() {
		naytettava--;
		
		if(naytettava < 0)
			naytettava = 0;
		
		nayta(tulokset[naytettava]);
		
		tuloksetTaulukkoIndeksi(naytettava);
	}
	
	/**
	 * Näyttää näytettävän ajon indeksin tyylitellysti.
	 * @param indeksi Ajon indeksi taulukossa.
	 */
	private void tuloksetTaulukkoIndeksi(int indeksi) {
		String tuloste = "Ajo " + (indeksi + 1) + "/" + tulokset.length;
		tuloksetTaulukkoIndeksi.setText(tuloste);
	}

	/**
	 * Näyttää annetun tuloksen tiedot ikkunassa.
	 * @param tulokset Annetut tulokset.
	 */
	private void nayta(KasinoTulokset tulokset) {
		ajo.setText((naytettava + 1) + "");
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
	
	/**
	 * Avaa annetun tulostaulukon ikkunaan.
	 * @param tulokset Annettu tulostaulukko.
	 */
	public void avaa(KasinoTulokset[] tulokset) {
		if(tulokset.length <= 0)
			System.err.println("Ei tuloksia");
		else {
			this.tulokset = tulokset;
			naytettava = tulokset.length - 1;
			
			nayta(tulokset[naytettava]);
			tuloksetTaulukkoIndeksi(naytettava);
		}
	}
}
