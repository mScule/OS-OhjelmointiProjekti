import javafx.scene.Scene;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.KasinoVisualisointi;

// KasinoVisualisointi DEMO
public class CanvasTest extends Application {
	
	private Stage stage;
	private BorderPane root;
	
	private static KasinoVisualisointi visualisointi;
	
	@Override
	public void start(Stage stage) throws Exception {
		root = new BorderPane();
		this.stage = stage;
		this.stage.setTitle("KasinoSimulaattori");
		
		visualisointi = new KasinoVisualisointi();
		visualisointi.piirraAsiakasLiike(5*128, 1*128, 4*128, 4*128);
		visualisointi.piirraAsiakasLiike(2*128, 4*128, 1*128, 1*128);
		visualisointi.piirraAsiakasLiike(5*128, 1*128, 1*128, 1*128);
		visualisointi.piirraAsiakasLiike(5*128, 1*128, 1*128, 1*128);
		visualisointi.piirraAsiakasLiike(1*128, 1*128, 4*128, 4*128);
		visualisointi.piirraAsiakasLiike(2*128, 4*128, 5*128, 1*128);
		
		root.setCenter(visualisointi.getKanvas());
		
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
		Platform.runLater(() -> visualisointi.setBaariJononPituus(10));
		Platform.runLater(() -> visualisointi.setBaariPalveltavienMaara(2));
		Platform.runLater(() -> visualisointi.setBaariJononPituus(8));
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}