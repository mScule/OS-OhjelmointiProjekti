package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class VisualisointiNew implements IVisualisointi {
	
	private GraphicsContext gc;
	private Canvas canvas;
	
	private int asiakasLkm =0;
	
	public VisualisointiNew(Canvas canvas) {
		this.canvas = canvas;
		this.gc = canvas.getGraphicsContext2D();
		tyhjennaNaytto();
	}

	@Override
	public void tyhjennaNaytto() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
	}

	@Override
	public void uusiAsiakas() {
		asiakasLkm++;
		
		gc.setFill(Color.BLACK);
		gc.fillRect(100,80, 100, 20);
		gc.setFill(Color.RED);
		gc.setFont(new Font(20));
		gc.fillText("Asiakas " + asiakasLkm, 100, 100);
		
	}
	
}
