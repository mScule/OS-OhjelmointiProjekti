import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CanvasTest extends Application {
	
	private Stage stage;
	private BorderPane root;
	private Canvas canvas;
	
	private GraphicsContext gc;
	
	private void drawInfo(int x, int y, int queueAmt, int serviceAmt) throws FileNotFoundException {
		Image
			queue   = new Image(new FileInputStream("images\\queue.png")),
			service = new Image(new FileInputStream("images\\service.png"));
		
		gc.drawImage(queue,   x * 128,      y * 128);
		gc.drawImage(service, x * 128 + 64, y * 128);
		
		gc.setStroke(Color.WHITE);
		
		gc.strokeText(queueAmt + ""  , x * 128     , y * 128);
		gc.strokeText(serviceAmt + "", x * 128 + 64, y * 128);
	}
	
	private void drawBackground(int x, int y) throws FileNotFoundException {
		Image service = new Image(new FileInputStream("images\\background.png"));
		gc.drawImage(service, x*128, y*128);
	}
	
	private void drawService(String image, int x, int y) throws FileNotFoundException {
		Image service = new Image(new FileInputStream("images\\" + image));
		gc.drawImage(service, x*128, y*128);
	}
	
	private void drawFloor(int x, int y) throws FileNotFoundException {
		Image floor = new Image(new FileInputStream("images\\floor.png"));
		
		x *= 128;
		y *= 128;
		
		gc.drawImage(floor, x     , y     );
		gc.drawImage(floor, x + 64, y     );
		gc.drawImage(floor, x     , y + 64);
		gc.drawImage(floor, x + 64, y + 64);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		root = new BorderPane();
		this.stage = stage;
		this.stage.setTitle("KasinoSimulaattori");
		
		canvas = new Canvas(512 + 256 + 128, 512 + 256);
		gc = canvas.getGraphicsContext2D();

		// Drawing background
		for(int x = 0; x < 7; x++) {
			for(int y = 0; y < 6; y++) {
				drawBackground(x,y);
			}
		}
		
		//_|___|_
		drawFloor(1,1); 
		drawFloor(5,1);
		
		//_|||||_
		drawFloor(1,2);
		drawFloor(2,2);
		drawFloor(3,2);
		drawFloor(4,2);
		drawFloor(5,2);
		
		//_|||||_
		drawFloor(1,3);
		drawFloor(2,3);
		drawFloor(3,3);
		drawFloor(4,3);
		drawFloor(5,3);
		
		//__|_|__
		drawFloor(2,4);

		drawFloor(4,4);
		
		//_|_____
		drawService("bar.png", 1,1);
		drawInfo(1,1, 0,0);

		//_____|_
		drawService("blackjack_table.png", 5,1);
		drawInfo(5,1, 0,0);
		
		//__|____
		drawService("enterance.png", 2,4);
		drawInfo(2,4, 0,0);
		
		//____|__
		drawService("exit.png", 4,4);
		drawInfo(4,4, 0,0);
		
		//_|____
		drawService("cage_enterance.png", 1,3);
		
		//____|_
		drawService("cage_exit.png", 5,3);
		
		//drawService("in_service")

		
		root.setCenter(canvas);
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
