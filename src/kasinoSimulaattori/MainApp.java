package kasinoSimulaattori;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import kasinoSimulaattori.controller.KasinoKontrolleri;
import kasinoSimulaattori.view.SimulaattoriGUIController;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private KasinoKontrolleri kontrolleri;
    private SimulaattoriGUIController controller = new SimulaattoriGUIController();
	
    public MainApp() {
	}

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Kasino simulaattori");
        try {
			controller.init();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        initMainLayout();
        showAlapaneelit();
    }
    
    /**
     * Initializes the root layout and tries to load the last opened
     * person file.
     */
    public void initMainLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class
                    .getResource("view/MainLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            // Give the controller access to the main app.
            SimulaattoriGUIController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showAlapaneelit() {
        try {
            // Load layout.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Layout.fxml"));
            AnchorPane alapaneelit = (AnchorPane) loader.load();
            
            // Set layout into the center of root layout.
            rootLayout.setCenter(alapaneelit);

            // Give the controller access to the main app.
            SimulaattoriGUIController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
  

    
	/**
	 * Returns the main stage.
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

    public static void main(String[] args) {
        launch(args);
    }

	public void kaynnistaSimulointi() {
		kontrolleri.kaynnistaSimulointi();
		
	}
}
