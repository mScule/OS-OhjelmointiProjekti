package kasinoSimulaattori.view.animated;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Sprite joka liikkuu aloitussijainnista lopetussijaintiin.
 * @author Vilhelm
 */
public class Liikkuja {
	private int
		aloitusX, aloitusY,
		lopetusX, lopetusY,
		lopetusEtaisyys;
	
	private double x, y;
	
	private GraphicsContext gc;
	private Image kuva;
	private boolean kohteessa;
	
	/**
	 * Liikkujan konstruktori
	 * @param kuva Sprite joka piirrettään.
	 * @param gc Canvaksen GraphicalContext johon liikkuja halutaan piirtää.
	 * @param aloitusX Aloitussijainnin x-koordinaatti.
	 * @param aloitusY Aloitussijainnin y-koordinaatti.
	 * @param lopetusX Lopetussijainnin x-koordinaatti.
	 * @param lopetusY Lopetussijainnin y-koordinaatti.
	 * @param lopetusEtaisyys Lopetusetäisyyden testialue lopetuskoordinaateista.
	 */
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
	
	/**
	 * Liikuttaa liikkujan lähemmäksi määränpäätään annetulla nopeudella.
	 * @param nopeus Matka jonka liikkuja liikkuu yhden metodikutsun aikana.
	 */
	public void liikuta(double nopeus) {
		
		x += (lopetusX - aloitusX) * nopeus;
		y += (lopetusY - aloitusY) * nopeus;
		
		double
			etaisyysX = x < lopetusX ? lopetusX - x : x - lopetusX,
			etaisyysY = y < lopetusY ? lopetusY - y : y - lopetusY;
		
		gc.drawImage(kuva, (int)x, (int)y);
		
		if(etaisyysX <= lopetusEtaisyys && etaisyysY <= lopetusEtaisyys)
			kohteessa = true;
	}
	
	/**
	 * @return true Jos liikkuja on kohteessa. false Muissa tapauksissa.
	 */
	public boolean getKohteessa() {
		return kohteessa;
	}
}
