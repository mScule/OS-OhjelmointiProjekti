package kasinoSimulaattori.view;

import javafx.scene.canvas.Canvas;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import kasinoSimulaattori.MainApp;
import kasinoSimulaattori.controller.IKontrolleriVtoM;
import kasinoSimulaattori.util.AudioPlayer;

public class SimulaattoriGUIController {

	private Stage stage;
	private BorderPane root;

	@FXML
	private Label aikaID;
	// @FXML
	// private Label paivaID;
	@FXML
	private Label rahatID;
	@FXML
	private Label voitotID;
	@FXML
	private Label saapuneetID;
	@FXML
	private Label palvellutID;
	@FXML
	private Label avgJonoID;
	@FXML
	private Label kokonaisoleskeluID;
	@FXML
	private Label avgOnnellisuusID;
	@FXML
	private Label avgPaihtymysID;
	@FXML
	private Label avgVarallisuusID;
	@FXML
	private Label avgLapimenoID;
	@FXML
	private BorderPane canvas;
	@FXML
	private AnchorPane alapaneelit;
	@FXML
	private TextField aikaTF;
	@FXML
	private TextField viiveTF;
	@FXML
	private TextField mainostusTF;
	@FXML
	private TextField maxTF;
	@FXML
	private TextField minTF;
	@FXML
	private TextField yllapitoTF;
	@FXML
	private TextField tasapeliTF;
	@FXML
	private TextField voittoTF;
	@FXML
	private TextField pelitTF;
	@FXML
	private TextField baaritTF;
	@FXML
	private TextField sisaankaynnitTF;
	@FXML
	private TextField uloskaynnitTF;

	private MainApp mainApp;

	private static IKontrolleriVtoM kontrolleri;

	public SimulaattoriGUIController() {
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public void setVisualisaattori(Canvas visualisaattori) {
		canvas.setCenter(visualisaattori);
	}

	public void naytaTulokset() {
		double tulokset[] = kontrolleri.haeTulokset();
		System.out.println(tulokset[0]);
		aikaID.setText(Double.toString(tulokset[0]));
		// paivaID.setText(Double.toString(tulokset[0]));
		rahatID.setText(Double.toString(tulokset[6]));
		voitotID.setText(Double.toString(tulokset[7]));

		saapuneetID.setText(Double.toString(tulokset[1]));
		palvellutID.setText(Double.toString(tulokset[2]));
		avgJonoID.setText(Double.toString(tulokset[5]));

		kokonaisoleskeluID.setText(Double.toString(tulokset[4]));
		avgOnnellisuusID.setText(Double.toString(tulokset[8]));
		avgPaihtymysID.setText(Double.toString(tulokset[11]));
		avgVarallisuusID.setText(Double.toString(tulokset[9]));
		avgLapimenoID.setText(Double.toString(tulokset[3]));
	}

	@FXML
	public void handleStart() {
		System.out.println("TESTI");
		kontrolleri = mainApp.getController();
		double aika = Double.parseDouble(aikaTF.getText());
		long viive = Long.parseLong(viiveTF.getText());
		double mainostus = Double.parseDouble(mainostusTF.getText());
		int max = Integer.parseInt(maxTF.getText());
		int min = Integer.parseInt(minTF.getText());
		double yllapito = Double.parseDouble(yllapitoTF.getText());
		double tasapeli = Double.parseDouble(tasapeliTF.getText());
		double voitto = Double.parseDouble(voittoTF.getText());
		int pelit = Integer.parseInt(pelitTF.getText());
		int baarit = Integer.parseInt(baaritTF.getText());
		int sisaankaynnit = Integer.parseInt(sisaankaynnitTF.getText());
		int uloskaynnit = Integer.parseInt(uloskaynnitTF.getText());
		try {
			AudioPlayer.playMusic();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		kontrolleri.kaynnistaSimulointi(aika, viive, mainostus, max, min, yllapito, tasapeli, voitto, pelit, baarit,
				sisaankaynnit, uloskaynnit);
		System.out.println(aika + " " + viive);
		naytaTulokset();
	}

	@FXML
	public void handleReset() {
		aikaID.setText("");
		// paivaID.setText("");
		rahatID.setText("");
		voitotID.setText("");
		saapuneetID.setText("");
		palvellutID.setText("");
		avgJonoID.setText("");
		kokonaisoleskeluID.setText("");
		avgOnnellisuusID.setText("");
		avgPaihtymysID.setText("");
		avgVarallisuusID.setText("");
		avgLapimenoID.setText("");
		/*
		 * kontrolleri = mainApp.getController(); kontrolleri.kaynnistaSimulointi();
		 * naytaTulokset(); canvas.setCenter(visualisointi.getCanvas());
		 * visualisointi.start();
		 */
	}

	@FXML
	public void handlePause() {

	}

	@FXML
	public void handleNopeuta() {
		kontrolleri = mainApp.getController();
		kontrolleri.nopeuta();
	}

	@FXML
	public void handleHidasta() {
		kontrolleri = mainApp.getController();
		kontrolleri.hidasta();
	}

	// Setterit tekstikentille
	public void setAikaTF(String value) {
		aikaTF.setText(value);
	}

	public void setViiveTF(String value) {
		viiveTF.setText(value);
	}

	public void setMainostusTF(String value) {
		mainostusTF.setText(value);
	}

	public void setMaxTF(String value) {
		maxTF.setText(value);
	}

	public void setMinTF(String value) {
		minTF.setText(value);
	}

	public void setYllapitoTF(String value) {
		yllapitoTF.setText(value);
	}

	public void setTasapeliTF(String value) {
		tasapeliTF.setText(value);
	}

	public void setVoittoTF(String value) {
		voittoTF.setText(value);
	}

	public void setPelitTF(String value) {
		pelitTF.setText(value);
	}

	public void setBaaritTF(String value) {
		baaritTF.setText(value);
	}

	public void setSisaankaynnitTF(String value) {
		sisaankaynnitTF.setText(value);
	}

	public void setUloskaynnitTF(String value) {
		uloskaynnitTF.setText(value);
	}

	// Setterit labelelille
	public void setAika(String value) {
		aikaID.setText(value);
	}

	// public void setPaiva(String value) { paivaID.setText(value); }
	public void setRahat(String value) {
		rahatID.setText(value);
	}

	public void setVoitot(String value) {
		voitotID.setText(value);
	}

	public void setSaapuneet(String value) {
		saapuneetID.setText(value);
	}

	public void setPalvellut(String value) {
		palvellutID.setText(value);
	}

	public void setAvgJono(String value) {
		avgJonoID.setText(value);
	}

	public void setKokonaisoleskelu(String value) {
		kokonaisoleskeluID.setText(value);
	}

	public void setAvgOnnellisuus(String value) {
		avgOnnellisuusID.setText(value);
	}

	public void setAvgPaihtymys(String value) {
		avgPaihtymysID.setText(value);
	}

	public void setAvgVarallisuus(String value) {
		avgVarallisuusID.setText(value);
	}

	public void setAvgLapimeno(String value) {
		avgLapimenoID.setText(value);
	}
}