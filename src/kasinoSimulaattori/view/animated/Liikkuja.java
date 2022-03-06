package kasinoSimulaattori.view.animated;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Liikkuja {
	private int
		aloitusX, aloitusY,
		lopetusX, lopetusY,
		lopetusEtaisyys;
	
	private double x, y;
	
	private GraphicsContext gc;
	private Image kuva;
	private boolean kohteessa;
	
	public Liikkuja(Image kuva, GraphicsContext gc, int aloitusX, int aloitusY, int lopetusX, int lopetusY, int lopetusEtaisyys) {

		this.kuva = kuva;
		this.gc = gc;
		
		this.aloitusX = aloitusX;
		this.aloitusY = aloitusY;
		this.lopetusX = lopetusX;
		this.lopetusY = lopetusY;
		
		x = aloitusX;
		y = aloitusY;
		
		this.lopetusEtaisyys = lopetusEtaisyys;
		kohteessa = false;
	}
	
	public void liikuta(double nopeus) {
		
		x += (lopetusX - aloitusX) * nopeus;
		y += (lopetusY - aloitusY) * nopeus;
		
		//System.out.println("x:" + x);
		//System.out.println("y:" + y);
		
		double
			etaisyysX = x < lopetusX ? lopetusX - x : x - lopetusX,
			etaisyysY = y < lopetusY ? lopetusY - y : y - lopetusY;
		
		gc.drawImage(kuva, (int)x, (int)y);
		
		if(etaisyysX <= lopetusEtaisyys && etaisyysY <= lopetusEtaisyys)
			kohteessa = true;
	}
	
	public boolean getKohteessa() {
		return kohteessa;
	}
}
