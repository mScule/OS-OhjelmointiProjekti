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
import kasinoSimulaattori.simu.model.Kasino;
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
	@FXML
	private Button startBTN;

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
		if (isInputValid()) {
			kontrolleri = mainApp.getController();
			if (!kontrolleri.simuloidaan()) {
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
				kontrolleri.kaynnistaSimulointi(aika, viive, mainostus, max, min, yllapito, tasapeli, voitto, pelit,
						baarit,
						sisaankaynnit, uloskaynnit);
				startBTN.setText("PAUSE");
				naytaTulokset();
			} else {
				handlePause();
			}
		}
	}

	private boolean isInputValid() {
		String errorMessage = "";

		if (aikaTF.getText() == null || aikaTF.getText().length() == 0) {
			errorMessage += "Syötä simulaation aika.\n";
		} else {
			try {
				Double.parseDouble(aikaTF.getText());
			} catch (NumberFormatException e) {
				errorMessage += "Syötteen pitää olla numero!\n";
			}
		}
		if (viiveTF.getText() == null || viiveTF.getText().length() == 0) {
			errorMessage += "Syötä viive.\n";
		} else {
			try {
				Long.parseLong(viiveTF.getText());
			} catch (NumberFormatException e) {
				errorMessage += "Syötteen pitää olla numero!\n";
			}
		}

		if (mainostusTF.getText() == null || mainostusTF.getText().length() == 0) {
			errorMessage += "Syötä mainostuksen määrä.\n";
		} else {
			try {
				Double.parseDouble(mainostusTF.getText());
			} catch (NumberFormatException e) {
				errorMessage += "Syötteen pitää olla numero!\n";
			}
		}

		if (maxTF.getText() == null || maxTF.getText().length() == 0) {
			errorMessage += "Syötä maksimipanos\n";
		} else {
			try {
				Integer.parseInt(maxTF.getText());
			} catch (NumberFormatException e) {
				errorMessage += "Syötteen pitää olla numero!\n";
			}
		}

		if (minTF.getText() == null || minTF.getText().length() == 0) {
			errorMessage += "Syötä minimipanos.\n";
		} else {
			try {
				Integer.parseInt(minTF.getText());
			} catch (NumberFormatException e) {
				errorMessage += "Syötteen pitää olla numero!\n";
			}
		}

		if (yllapitoTF.getText() == null || yllapitoTF.getText().length() == 0) {
			errorMessage += "Syötä ylläpidon määrä.\n";
		} else {
			try {
				Double.parseDouble(yllapitoTF.getText());
			} catch (NumberFormatException e) {
				errorMessage += "Syötteen pitää olla numero!\n";
			}
		}

		if (tasapeliTF.getText() == null || tasapeliTF.getText().length() == 0) {
			errorMessage += "Syötä tasapeli prosentti.\n";
		} else {
			try {
				Double.parseDouble(tasapeliTF.getText());
			} catch (NumberFormatException e) {
				errorMessage += "Syötteen pitää olla numero!\n";
			}
		}

		if (voittoTF.getText() == null || voittoTF.getText().length() == 0) {
			errorMessage += "Syötä voittoprosentti.\n";
		} else {
			try {
				Double.parseDouble(voittoTF.getText());
			} catch (NumberFormatException e) {
				errorMessage += "Syötteen pitää olla numero!\n";
			}
		}

		if (pelitTF.getText() == null || pelitTF.getText().length() == 0) {
			errorMessage += "Syötä pelien määrä.\n";
		} else {
			try {
				Integer.parseInt(pelitTF.getText());
			} catch (NumberFormatException e) {
				errorMessage += "Syötteen pitää olla numero!\n";
			}
		}

		if (baaritTF.getText() == null || baaritTF.getText().length() == 0) {
			errorMessage += "Syötä baarien määrä.\n";
		} else {
			try {
				Integer.parseInt(baaritTF.getText());
			} catch (NumberFormatException e) {
				errorMessage += "Syötteen pitää olla numero!\n";
			}
		}

		if (sisaankaynnitTF.getText() == null || sisaankaynnitTF.getText().length() == 0) {
			errorMessage += "Syötä sisäänkäyntien määrä.\n";
		} else {
			try {
				Integer.parseInt(sisaankaynnitTF.getText());
			} catch (NumberFormatException e) {
				errorMessage += "Syötteen pitää olla numero!\n";
			}
		}

		if (uloskaynnitTF.getText() == null || uloskaynnitTF.getText().length() == 0) {
			errorMessage += "Syötä uloskäyntien määrä.\n";
		} else {
			try {
				Integer.parseInt(uloskaynnitTF.getText());
			} catch (NumberFormatException e) {
				errorMessage += "Syötteen pitää olla numero!\n";
			}
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			// Show the error message.
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Syötteissä virheitä");
			alert.setContentText(errorMessage);

			alert.showAndWait();

			return false;
		}
	}

	@FXML
	public synchronized void handlePause() {
		if (!Kasino.isPause()) {
			Kasino.setPause(true);
			startBTN.setText("RESUME");
		} else {
			Kasino.setPause(false);
			kontrolleri.continueSim();
			startBTN.setText("PAUSE");
		}
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
	
	public void resetStartButton() {
		startBTN.setText("START");
	}
}