package kasinoSimulaattori;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import kasinoSimulaattori.controller.IKontrolleriVtoM;
import kasinoSimulaattori.controller.KasinoKontrolleri;

import kasinoSimulaattori.simu.framework.Trace;
import kasinoSimulaattori.simu.framework.Trace.Level;
import kasinoSimulaattori.simu.model.KasinoDAO;
import kasinoSimulaattori.simu.model.KasinoTulokset;

import kasinoSimulaattori.view.ISimulaattorinUI;
import kasinoSimulaattori.view.IVisualisointi;
import kasinoSimulaattori.view.KasinoVisualisointi;
import kasinoSimulaattori.view.SimulaattoriGUIController;
import kasinoSimulaattori.view.TuloksetGUIController;

/**
 * Simulaattorin päänäkymä
 */
public class MainApp extends Application implements ISimulaattorinUI {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private IKontrolleriVtoM kontrolleri;
    private SimulaattoriGUIController gui;
    private KasinoVisualisointi visualisointi;

    /**
     * Alustaa päänäkymän.
     */
    private void initMainLayout() {
        try {
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/MainLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            gui = loader.getController();
            gui.setMainApp(this);
            
            primaryStage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
    /**
     * Liittää alapaneelit näkymään.
     */
    private void showAlapaneelit() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Layout.fxml"));
            AnchorPane alapaneelit = (AnchorPane) loader.load();
            rootLayout.setCenter(alapaneelit);
            gui = loader.getController();
            gui.setMainApp(this);
            
            // Asetetaan canvas
            gui.setVisualisaattori(visualisointi.getCanvas());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
    /**
     * Kirjautumis dialogi mikä kysyy käyttäjänimen ja salasanan
     * @param kayttaja Käyttäjä nimi
     * @param salasana Salasana
     */
    private void kirjautumisDialogi() {
    	Stage ikkuna = new Stage();
    	ikkuna.setWidth(330);
    	BorderPane kirjautumisDialogi = new BorderPane();
    	
    	TextField kayttajaTF     = new TextField();
    	PasswordField salasanaPF = new PasswordField();
    	
    	Label
    		kayttajaLB = new Label(),
    		salasanaLB = new Label();
    	
    	kayttajaLB.setText(" Käyttäjätunnus");
    	salasanaLB.setText(" Salasana");
    	
    	HBox
			kayttajaRivi = new HBox(),
			salasanaRivi = new HBox();
    	kayttajaRivi.setPadding(new Insets(8));
    	salasanaRivi.setPadding(new Insets(8));
    	
    	kayttajaRivi.getChildren().addAll(kayttajaTF, kayttajaLB);
    	salasanaRivi.getChildren().addAll(salasanaPF, salasanaLB);
    	
    	Button kirjauduButton = new Button();
    	kirjauduButton.setText("Aseta tietokannan kirjautumistiedot");
    	kirjauduButton.setPadding(new Insets(8));
    	kirjauduButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				KasinoDAO.getInstanssi().setKayttaja(kayttajaTF.getText());
				KasinoDAO.getInstanssi().setSalasana(salasanaPF.getText());
				
				System.out.println(kayttajaTF.getText());
				System.out.println(salasanaPF.getText());
				ikkuna.close();
			}
    	});
    	
    	VBox rivit = new VBox();
    	rivit.setPadding(new Insets(8,8,8,8));
    	rivit.getChildren().addAll(kayttajaRivi, salasanaRivi, kirjauduButton);
    	
    	kirjautumisDialogi.setCenter(rivit);
    	
    	Scene scene = new Scene(kirjautumisDialogi);
    	
    	ikkuna.setTitle("Tietokantaan kirjautuminen");
    	ikkuna.setScene(scene);
    	ikkuna.showAndWait();
    }
    
    /**
     * Päämetodi.
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * Näkymän parametriton konstruktori.
     * @throws FileNotFoundException
     */
    public MainApp() throws FileNotFoundException {
        kontrolleri   = new KasinoKontrolleri(this);
    	gui           = new SimulaattoriGUIController();
    	visualisointi = new KasinoVisualisointi();
    }

    /**
     * Kutsutaan ohjelman alussa
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
    	
    	kirjautumisDialogi();
    	
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
    
    /**
     * @return Palauttaa viitteen kontrolleriin.
     */
    public IKontrolleriVtoM getController() {
    	return kontrolleri;
    }
    
	/**
	 * @return Palauttaa viitteen pääikkunaan.
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
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

	@Override
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
