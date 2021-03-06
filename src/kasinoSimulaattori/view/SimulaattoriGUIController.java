package kasinoSimulaattori.view;

import javafx.scene.canvas.Canvas;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import kasinoSimulaattori.MainApp;
import kasinoSimulaattori.controller.IKontrolleriVtoM;
import kasinoSimulaattori.simu.framework.Trace;
import kasinoSimulaattori.simu.framework.Trace.Level;
import kasinoSimulaattori.simu.model.Kasino;
import kasinoSimulaattori.util.AudioPlayer;

/**
 * Simulaattorin käyttöliittymän kontrolleri
 * @author Johanna Lavikainen
 */
public class SimulaattoriGUIController {

	private Stage stage;
	private BorderPane root;

	@FXML
	private Label aikaID;
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
	@FXML
	private Button startBTN;

	private MainApp mainApp;

	private static IKontrolleriVtoM kontrolleri;

	/**
	 * Parametrion tyhjä konstruktori
	 */
	public SimulaattoriGUIController() {}

	/**
	 * Asettaa viittauksen päänäkymään
	 * @param mainApp Päänäkymän viittaus
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	/**
	 * Asettaa viittauksen visualisointiin
	 * @param visualisaattori Visualisointi viittaus
	 */
	public void setVisualisaattori(Canvas visualisaattori) {
		canvas.setCenter(visualisaattori);
	}

	/**
	 * Näyttää tulokset
	 */
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

	/**
	 * Käsittelee käyttöliittymän alustamisen
	 */
	@FXML
	public void handleStart() {
		kontrolleri = mainApp.getController();
		if (!kontrolleri.simuloidaan()) {
			try {
				kontrolleri.resetVisualistointi();
				double aika = Double.parseDouble(aikaTF.getText());
				long viive = Long.parseLong(viiveTF.getText());
				double mainostus = Double.parseDouble(mainostusTF.getText());
				double max = Double.parseDouble(maxTF.getText());
				double min = Double.parseDouble(minTF.getText());
				double yllapito = Double.parseDouble(yllapitoTF.getText());
				double tasapeli = Double.parseDouble(tasapeliTF.getText());
				double voitto = Double.parseDouble(voittoTF.getText());
				int pelit = Integer.parseInt(pelitTF.getText());
				int baarit = Integer.parseInt(baaritTF.getText());
				int sisaankaynnit = Integer.parseInt(sisaankaynnitTF.getText());
				int uloskaynnit = Integer.parseInt(uloskaynnitTF.getText());

				kontrolleri.asetaSyotteetMoottoriin(aika, viive, mainostus, max, min, yllapito, tasapeli, voitto,
						pelit, baarit, sisaankaynnit, uloskaynnit);
			} catch (IllegalArgumentException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Syötteissä virheitä");
				alert.setContentText(e.getMessage());

				alert.showAndWait();
				kontrolleri.resetoiSimulointi();
				return;
			}

			try {
				AudioPlayer.playMusic();
			} catch (UnsupportedAudioFileException e) {
				System.err.println(e.getMessage());
			} catch (IOException e) {
				System.err.println(e.getMessage());
			} catch (LineUnavailableException e) {
				System.err.println(e.getMessage());
			}

			kontrolleri.kaynnistaSimulointi();

			startBTN.setText("PAUSE");
			naytaTulokset();
		} else {
			handlePause();
		}
	}

	/**
	 * Käsittelee simulaation hetkellisen keskeyttämisen
	 */
	@FXML
	public synchronized void handlePause() {
		if (!kontrolleri.getKasinoPause()) {
			kontrolleri.setKasinoPause(true);
			startBTN.setText("RESUME");
		} else {
			kontrolleri.setKasinoPause(false);
			kontrolleri.jatkaSimulointia();
			startBTN.setText("PAUSE");
		}
	}

	/**
	 * Käsittelee simulaation nopeuttamisnäppäimen painamisen
	 */
	@FXML
	public void handleNopeuta() {
		kontrolleri = mainApp.getController();
		kontrolleri.nopeuta();
	}

	/**
	 * Käsittelee simulaation hidastusnäppäimen painamisen
	 */
	@FXML
	public void handleHidasta() {
		kontrolleri = mainApp.getController();
		kontrolleri.hidasta();
	}

	/**
	 * Asettaa arvon tekstikenttään aika
	 */
	public void setAikaTF(String value) {
		aikaTF.setText(value);
	}

	/**
	 * Asettaa arvon tekstikenttään viive
	 */
	public void setViiveTF(String value) {
		viiveTF.setText(value);
	}

	/**
	 * Asettaa arvon tekstikenttään mainostus
	 */
	public void setMainostusTF(String value) {
		mainostusTF.setText(value);
	}

	/**
	 * Asettaa arvon tekstikenttään korkein panos
	 */
	public void setMaxTF(String value) {
		maxTF.setText(value);
	}

	/**
	 * Asettaa arvon tekstikenttään pienin panos
	 */
	public void setMinTF(String value) {
		minTF.setText(value);
	}

	/**
	 * Asettaa arvon tekstikenttään ylläpitokulut
	 */
	public void setYllapitoTF(String value) {
		yllapitoTF.setText(value);
	}

	/**
	 * Asettaa arvon tekstikenttään tasapeli
	 */
	public void setTasapeliTF(String value) {
		tasapeliTF.setText(value);
	}

	/**
	 * Asettaa arvon tekstikenttään voittoprosentti
	 */
	public void setVoittoTF(String value) {
		voittoTF.setText(value);
	}

	/**
	 * Asettaa arvon tekstikenttään pelien määrä
	 */
	public void setPelitTF(String value) {
		pelitTF.setText(value);
	}

	/**
	 * Asettaa arvon tekstikenttään baarien määrä
	 */
	public void setBaaritTF(String value) {
		baaritTF.setText(value);
	}

	/**
	 * Asettaa arvon tekstikenttään sisäänkäyntien määrä
	 */
	public void setSisaankaynnitTF(String value) {
		sisaankaynnitTF.setText(value);
	}

	/**
	 * Asettaa arvon tekstikenttään uloskäyntien määrä
	 */
	public void setUloskaynnitTF(String value) {
		uloskaynnitTF.setText(value);
	}

	// Setterit labelelille

	public void setAika(String value) {
		aikaID.setText(value);
	}

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

	/**
	 * uudelleen alustaa käynnistysnäppäimen
	 */
	public void resetStartButton() {
		startBTN.setText("START");
	}

	/**
	 * Avaa virheilmoitusdialogin
	 * @param viesti Näytettävä viesti
	 */
	public void virheilmoitusDialogi(String viesti) {
		Alert ilmoitus = new Alert(AlertType.ERROR);
		ilmoitus.setTitle("Virhe");
		ilmoitus.setHeaderText("Virhe");
		ilmoitus.setContentText(viesti);
		ilmoitus.show();
	}
}