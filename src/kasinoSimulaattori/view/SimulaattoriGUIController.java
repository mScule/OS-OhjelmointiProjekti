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
import kasinoSimulaattori.controller.IKontrolleriVtoM;
import kasinoSimulaattori.controller.KasinoKontrolleri;
import kasinoSimulaattori.simu.framework.Trace;
import kasinoSimulaattori.simu.framework.Trace.Level;

public class SimulaattoriGUIController extends Application implements ISimulaattorinUI {

	private Stage stage;
	private BorderPane root;

	@FXML
	private Button aloitusBtn;
	@FXML
	private Button pysäytysBtn;
	@FXML
	private Button uusikäynistysBtn;
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

	private IKontrolleriVtoM kontrolleri;
	private IVisualisointi view = null;
	private static KasinoVisualisointi visualisointi;

	@Override
	public void init() {

		Trace.setTraceLevel(Level.INFO);

		kontrolleri = new KasinoKontrolleri(this);

	}

	@Override
	public void start(Stage stage) {
		try {
			this.stage = stage;
			this.stage.setTitle("Kasino Simulaattori");

			rootLayout();

			setAlapaneelit();
			init();
			// naytaTulokset();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void rootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("MainLayout.fxml"));
			root = (BorderPane) loader.load();

			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setAlapaneelit() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("Layout.fxml"));
			alapaneelit = (AnchorPane) loader.load();
			root.setCenter(alapaneelit);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setCanvas() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("Layout.fxml"));
		canvas = (BorderPane) loader.load();
		canvas.setCenter(visualisointi.getKanvas());
	}

	public void naytaTulokset() {
		double tulokset[] = kontrolleri.haeTulokset();
		System.out.println(tulokset[0]);
		aikaID.setText(Double.toString(tulokset[0]));
	}

	@FXML
	public void handleStart() {
		System.out.println("TESTI");
		// aloitusBtn.setOnAction(new EventHandler<ActionEvent>() {
		// @Override
		// public void handle(ActionEvent event) {
		//kontrolleri = new KasinoKontrolleri(this);
		System.out.println("testttt");
		kontrolleri.kaynnistaSimulointi();
		naytaTulokset();
		aloitusBtn.setDisable(true);
		// }
		// });

	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public double getAika() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getViive() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setLoppuaika(double aika) {
		// TODO Auto-generated method stub

	}

	@Override
	public IVisualisointi getVisualisointi() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void paivita() {
		// TODO Kutsu IKasinoVisualisoinnin paivita metodia täällä

	}
}
