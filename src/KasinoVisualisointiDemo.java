import javafx.scene.Scene;

import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import kasinoSimulaattori.view.KasinoVisualisointi;

// KasinoVisualisointi DEMO
public class KasinoVisualisointiDemo extends Application {
	
	private Stage stage;
	private BorderPane root;
	
	private static KasinoVisualisointi visualisointi;
	
	@Override
	public void start(Stage stage) throws Exception {
		root = new BorderPane();
		this.stage = stage;
		this.stage.setTitle("KasinoSimulaattori");
		
		visualisointi = new KasinoVisualisointi();
		
		root.setCenter(visualisointi.getCanvas());
		
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
		AsiakasSpawner spawneri = new AsiakasSpawner(visualisointi);
			visualisointi.start();
			spawneri.start();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}