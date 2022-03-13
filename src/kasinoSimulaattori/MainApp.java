package kasinoSimulaattori;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import javafx.application.Application;
import javafx.application.Platform;

import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import javafx.stage.Stage;

import kasinoSimulaattori.controller.IKontrolleriVtoM;
import kasinoSimulaattori.controller.KasinoKontrolleri;

import kasinoSimulaattori.simu.framework.Trace;
import kasinoSimulaattori.simu.framework.Trace.Level;
import kasinoSimulaattori.simu.model.KasinoTulokset;

import kasinoSimulaattori.view.ISimulaattorinUI;
import kasinoSimulaattori.view.IVisualisointi;
import kasinoSimulaattori.view.KasinoVisualisointi;
import kasinoSimulaattori.view.SimulaattoriGUIController;
import kasinoSimulaattori.view.TuloksetGUIController;

public class MainApp extends Application implements ISimulaattorinUI {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private IKontrolleriVtoM kontrolleri;
    private SimulaattoriGUIController gui;
    private KasinoVisualisointi visualisointi;
    
    public MainApp() throws FileNotFoundException {
        kontrolleri   = new KasinoKontrolleri(this);
    	gui           = new SimulaattoriGUIController();
    	visualisointi = new KasinoVisualisointi();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Kasino simulaattori");
        this.primaryStage.setResizable(false);
        Trace.setTraceLevel(Level.ERR);
    	
        initMainLayout();
        showAlapaneelit();
        
        gui.setVisualisaattori(visualisointi.getCanvas());
        visualisointi.start();
        
        // Valmiit arvot
        Platform.runLater(() -> {
        	gui.setMainostusTF("1");
        	gui.setMaxTF("10000");
        	gui.setMinTF("200");
        	gui.setYllapitoTF("50");
        	gui.setTasapeliTF("0.08");
        	gui.setVoittoTF("0.44");
        	gui.setPelitTF("0");
        	gui.setBaaritTF("0");
        	gui.setSisaankaynnitTF("0");
        	gui.setUloskaynnitTF("0");
        	gui.setAikaTF("1000");
        	gui.setViiveTF("100");
        });
    }
    
    public IKontrolleriVtoM getController() {
    	return kontrolleri;
    }
    
    /**
     * Initializes the root layout and tries to load the last opened
     * person file.
     */
    private void initMainLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/MainLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            // Give the controller access to the main app.
            gui = loader.getController();
            gui.setMainApp(this);
            
            primaryStage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
    private void showAlapaneelit() {
        try {
            // Load layout.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Layout.fxml"));
            AnchorPane alapaneelit = (AnchorPane) loader.load();
            
            // Set layout into the center of root layout.
            rootLayout.setCenter(alapaneelit);

            // Give the controller access to the main app.
            gui = loader.getController();
            gui.setMainApp(this);
            
            // Asetetaan canvas
            gui.setVisualisaattori(visualisointi.getCanvas());
        } catch (IOException e) {
            System.err.println(e.getMessage());
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
		return visualisointi;
	}

	@Override
	public SimulaattoriGUIController getGui() {
		return gui;
	}

	@Override
	public void naytaTulokset(KasinoTulokset[] tulokset) {
		try {
	        FXMLLoader loader = new FXMLLoader();
	        
	        loader.setLocation(MainApp.class.getResource("view/TuloksetGUI.fxml"));
			Scene scene = new Scene(loader.load());
			
			TuloksetGUIController tuloksetKontrolleri = loader.getController();
	        
	        Stage tuloksetStage = new Stage();
	        tuloksetStage.setScene(scene);
	        tuloksetStage.setResizable(false);
	        tuloksetStage.setTitle("Ajot");
	        tuloksetKontrolleri.avaa(tulokset);
	        tuloksetStage.show();
		} catch (IOException e) {
			System.err.println("Virhe tulosten näyttämisessä");
		}
	}

    public void resetVisualisointi(){
        try {
			visualisointi = new KasinoVisualisointi();
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		}
        gui.setVisualisaattori(visualisointi.getCanvas());
        visualisointi.start();
    }
    
    @Override
    public void virheilmoitusDialogi(String viesti) {
    	gui.virheilmoitusDialogi(viesti);
    }
    
    @Override
    public void ilmoitusDialogi(String viesti) {
    	Alert ilmoitus = new Alert(AlertType.INFORMATION);
    	ilmoitus.setTitle("Ilmoitus");
    	ilmoitus.setHeaderText("Ilmoitus");
    	ilmoitus.setContentText(viesti);
    	ilmoitus.show();
    }
    
    @Override
    public boolean kyllaTaiEiDialogi(String viesti) {
    	Alert ilmoitus = new Alert(AlertType.CONFIRMATION);
    	ilmoitus.setTitle("Varmistus");
    	ilmoitus.setHeaderText("Varmistus:");
    	ilmoitus.setContentText(viesti);
    	
    	ButtonType
    		kyllaButton = new ButtonType("Kylla"),
    		eiButton    = new ButtonType("Ei");
    	
    	ilmoitus.getButtonTypes().setAll(kyllaButton, eiButton);
    	
    	Optional<ButtonType> vastaus = ilmoitus.showAndWait();

    	if (vastaus.get() == kyllaButton)
    		return true;
    	else if (vastaus.get() == eiButton)
    		return false;
    	else
    		return false;
    }
}
