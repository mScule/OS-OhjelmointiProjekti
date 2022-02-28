package view;

import javafx.scene.canvas.Canvas;
import java.io.IOException;

import controller.IKontrolleriVtoM;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SimulaattoriGUInew extends Application implements ISimulaattorinUI{
	
	private Stage stage;
	private BorderPane root;
	
	@FXML private Canvas canvas;
	@FXML private Button aloitusBtn;
	@FXML private Button pysäytysBtn;
	@FXML private Button uusikäynistysBtn;
	
	private IKontrolleriVtoM kontrolleri;
	private IVisualisointi view = null;
	
	@Override
	public void start(Stage stage){
		try {
			this.stage = stage;
			this.stage.setTitle("Kasino Simulaattori");
			rootLayout();
			setAlapaneelit();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}			
	
	public void rootLayout() {
		try {
			FXMLLoader loader= new FXMLLoader();
			loader.setLocation(getClass().getResource("MainLayout.fxml"));
			root = (BorderPane) loader.load();
			
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setAlapaneelit() {
		try {
			FXMLLoader loader= new FXMLLoader();
			loader.setLocation(getClass().getResource("Alapaneelit.fxml"));
			AnchorPane alapaneelit = (AnchorPane) loader.load();
			
			root.setCenter(alapaneelit);
			
			
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void handelStart() {
		if (view == null){
			view = new VisualisointiNew(canvas);
			view.tyhjennaNaytto();
		}
		aloitusBtn.setDisable(true);
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


}
