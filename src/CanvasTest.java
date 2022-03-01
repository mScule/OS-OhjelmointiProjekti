import javafx.scene.Scene;

import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.KasinoVisualisointi;

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
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}