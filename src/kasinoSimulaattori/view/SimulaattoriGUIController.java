package kasinoSimulaattori.view;

import javafx.scene.canvas.Canvas;
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
	private IVisualisointi view = null;
	private static KasinoVisualisointi visualisointi;
	
	public SimulaattoriGUIController() {
		
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
	public void handleStart(ActionEvent e) {
		System.out.println("TESTI");
		kontrolleri = mainApp.getController();
		kontrolleri.kaynnistaSimulointi();
		naytaTulokset();
	}


}
