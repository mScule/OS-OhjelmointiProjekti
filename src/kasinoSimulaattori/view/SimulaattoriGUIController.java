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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import kasinoSimulaattori.MainApp;
import kasinoSimulaattori.controller.IKontrolleriVtoM;
import kasinoSimulaattori.controller.KasinoKontrolleri;
import kasinoSimulaattori.simu.framework.Trace;
import kasinoSimulaattori.simu.framework.Trace.Level;

public class SimulaattoriGUIController {

	private Stage stage;
	private BorderPane root;

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

	public void setCanvas() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("Layout.fxml"));
		canvas = (BorderPane) loader.load();
		canvas.setCenter(visualisointi.getCanvas());
	}

	public void naytaTulokset() {
		double tulokset[] = kontrolleri.haeTulokset();
		System.out.println(tulokset[0]);
		aikaID.setText(Double.toString(tulokset[0]));
	}

	@FXML
	public void handleStart() {
		System.out.println("TESTI");
		kontrolleri = mainApp.getController();
		kontrolleri.kaynnistaSimulointi();
		naytaTulokset();
		canvas.setCenter(visualisointi.getCanvas());
		visualisointi.start();
	}
}
