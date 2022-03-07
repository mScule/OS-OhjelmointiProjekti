package kasinoSimulaattori.view;

import javafx.scene.canvas.Canvas;

import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import kasinoSimulaattori.MainApp;
import kasinoSimulaattori.controller.IKontrolleriVtoM;
import kasinoSimulaattori.controller.KasinoKontrolleri;
import kasinoSimulaattori.simu.framework.Trace;
import kasinoSimulaattori.simu.framework.Trace.Level;

public class SimulaattoriGUIController {
	
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
	private Label aikaID;
	@FXML
	private Label paivaID;
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

	private MainApp mainApp;

	private static IKontrolleriVtoM kontrolleri;
	public KasinoVisualisointi visualisointi;

	public SimulaattoriGUIController() throws FileNotFoundException {
		visualisointi = new KasinoVisualisointi();
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}


	public void naytaTulokset() {
		double tulokset[] = kontrolleri.haeTulokset();
		aikaID.setText(Double.toString(tulokset[0]));
		paivaID.setText(Double.toString(tulokset[0]));
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
		kontrolleri = mainApp.getController();
		double aika = Double.parseDouble(aikaTF.getText());
		long viive = Long.parseLong(viiveTF.getText());
		double mainostus = Double.parseDouble(mainostusTF.getText());
		double max = Double.parseDouble(maxTF.getText());
		double min = Double.parseDouble(minTF.getText());
		double yllapito = Double.parseDouble(yllapitoTF.getText());
		double tasapeli = Double.parseDouble(tasapeliTF.getText());
		kontrolleri.kaynnistaSimulointi(aika, viive, mainostus, max, min, yllapito, tasapeli);
		System.out.println(aika + " " + viive);
		naytaTulokset();
		canvas.setCenter(visualisointi.getCanvas());
		visualisointi.start();
	}
	
	@FXML
	public void handleReset() {
		aikaID.setText("");
		paivaID.setText("");
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
	
	
}
