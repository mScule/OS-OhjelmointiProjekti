package kasinoSimulaattori.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import kasinoSimulaattori.view.animated.Liikkuja;

public class KasinoVisualisointi extends Thread implements IVisualisointi{
	
	private Canvas kanvas;
	
	private GraphicsContext gc;
	
	private ArrayList<Liikkuja> liikkujat;
	
	private Image
		kuvaTausta,
		kuvaLattia,
		kuvaBar,
		kuvaBlackjack,
		kuvaSisaankaynti,
		kuvaUloskaynti,
		kuvaHakkiSisaankaynti,
		kuvaHakkiUloskaynti,
		kuvaJonossa,
		kuvaPalvelussa,
		kuvaAsiakas;
	
	private int
		baariJono        = 0, baariPalveltavat        = 0,
		blackjackJono    = 0, blackjackPalveltavat    = 0,
		sisaankayntiJono = 0, sisaankayntiPalveltavat = 0,
		uloskayntiJono   = 0, uloskayntiPalveltavat   = 0,
		
	    ruudunLiike = 0;
		
	public KasinoVisualisointi() throws FileNotFoundException {
		kuvaTausta            = new Image(new FileInputStream("images\\background.png"     ));
		kuvaLattia            = new Image(new FileInputStream("images\\floor.png"          ));
		kuvaBar               = new Image(new FileInputStream("images\\bar.png"            ));
		kuvaBlackjack         = new Image(new FileInputStream("images\\blackjack_table.png"));
		kuvaSisaankaynti      = new Image(new FileInputStream("images\\enterance.png"      ));
		kuvaUloskaynti        = new Image(new FileInputStream("images\\exit.png"           ));
		kuvaHakkiSisaankaynti = new Image(new FileInputStream("images\\cage_enterance.png" ));
		kuvaHakkiUloskaynti   = new Image(new FileInputStream("images\\cage_exit.png"      ));
		kuvaJonossa           = new Image(new FileInputStream("images\\queue.png"          ));
		kuvaPalvelussa        = new Image(new FileInputStream("images\\service.png"        ));
		kuvaAsiakas           = new Image(new FileInputStream("images\\customer.png"       ));
		
		liikkujat = new ArrayList<Liikkuja>();
		
		kanvas = new Canvas(512 + 256 + 128, 512 + 256);
		gc = kanvas.getGraphicsContext2D();
		gc.setStroke(Color.WHITE);
	}
	
	private void piirraInfo(int x, int y, int jononPituus, int palveltavienMaara) {
		gc.drawImage(kuvaJonossa   , x * 128     , y * 128);
		gc.drawImage(kuvaPalvelussa, x * 128 + 64, y * 128);
		
		gc.strokeText(jononPituus + ""      , x * 128     , y * 128);
		gc.strokeText(palveltavienMaara + "", x * 128 + 64, y * 128);
	}
	
	private void piirraTausta(int x, int y) {
		gc.drawImage(kuvaTausta, x * 128, y * 128);
	}
	
	private void piirraPalvelu(Image kuva, int x, int y) {
		gc.drawImage(kuva, x * 128, y * 128);
	}
	
	private void piirraLattia(int x, int y) {
		x *= 128;
		y *= 128;
		
		gc.drawImage(kuvaLattia, x     , y     );
		gc.drawImage(kuvaLattia, x + 64, y     );
		gc.drawImage(kuvaLattia, x     , y + 64);
		gc.drawImage(kuvaLattia, x + 64, y + 64);
	}
	
	@Override
	public void asiakkaanLiikeAnimaatio(int aloitusX, int aloitusY, int lopetusX, int lopetusY) {
		liikkujat.add(new Liikkuja(kuvaAsiakas, gc, aloitusX, aloitusY, lopetusX, lopetusY, 32));
	}
	
	private void paivita() {
		
		// Tausta
		for(int x = 0; x < 7; x++)
			for(int y = 0; y < 6; y++)
				piirraTausta(x, y);
		
		// Lattia
		piirraLattia(1,1);
		piirraLattia(2,1);
		piirraLattia(3,1);
		piirraLattia(4,1);
		piirraLattia(5,1);
	
		piirraLattia(1,2);
		piirraLattia(2,2);
		piirraLattia(3,2);
		piirraLattia(4,2);
		piirraLattia(5,2);
		
		piirraLattia(1,3);
		piirraLattia(2,3);
		piirraLattia(3,3);
		piirraLattia(4,3);
		piirraLattia(5,3);
		
		piirraLattia(1,4);
		piirraLattia(2,4);

		piirraLattia(4,4);
		piirraLattia(5,4);
		
		// Palvelupisteet
		
		// Baari
		piirraPalvelu(kuvaBar, 1,1);
		piirraInfo(1,1, baariJono, baariPalveltavat);

		// Blackjack
		piirraPalvelu(kuvaBlackjack, 5,1);
		piirraInfo(5,1, blackjackJono, blackjackPalveltavat);
		
		// Sisäänkäynti
		piirraPalvelu(kuvaSisaankaynti, 2,4);
		piirraPalvelu(kuvaHakkiSisaankaynti, 1,4);
		piirraInfo(2,4, sisaankayntiJono, sisaankayntiPalveltavat);
		
		// Uloskäynti
		piirraPalvelu(kuvaUloskaynti, 4,4);
		piirraPalvelu(kuvaHakkiUloskaynti, 5,4);
		piirraInfo(4,4, uloskayntiJono, uloskayntiPalveltavat);
		
		// Asiakkaiden liikkeet
		for(Liikkuja l : liikkujat)
			l.liikuta(0.01);
		
		// Poista asiakkaat jotka kohteessa
		for(int i = liikkujat.size() - 1; i >= 0; i--)
			if(liikkujat.get(i).getKohteessa())
				liikkujat.remove(i);
	}
	
	synchronized public Canvas getCanvas() {
		return this.kanvas;
	}
	
	@Override
	synchronized public void run() {
		double alkuAika = System.currentTimeMillis();
		double paivitysVali = 1000/33.33334; // 30 fps

		while(true) {
			double fps = System.currentTimeMillis() - alkuAika;
			if(fps >= paivitysVali) {
				alkuAika = System.currentTimeMillis();
				paivita();
				
				// Ruudun liike päivitys
				gc.strokeText("Fps: " + fps + "\n" + "Frame: " + ruudunLiike, 32, 32);
				ruudunLiike++;
			}
		}
	}

	// Baari
	@Override
	public void setBaariJononPituus(int pituus) {
		baariJono = pituus;
	}
	@Override
	public void setBaariPalveltavienMaara(int maara) {
		baariPalveltavat = maara;
	}

	// Blackjack
	@Override
	public void setBlackjackJononPituus(int pituus) {
		blackjackJono = pituus;
	}
	@Override
	public void setBlackjackPalveltavienMaara(int maara) {
		blackjackPalveltavat = maara;
	}

	// Sisäänkäynti
	@Override
	public void setSisaankayntiJononPituus(int pituus) {
		sisaankayntiJono = pituus;
	}
	@Override
	public void setSisaankayntiPalveltavienMaara(int maara) {
		sisaankayntiPalveltavat = maara;
	}

	// Uloskäynti
	@Override
	public void setUloskayntiPalveltavienMaara(int maara) {
		uloskayntiPalveltavat = maara;
	}
	@Override
	public void setUloskayntiJononPituus(int pituus) {
		uloskayntiJono = pituus;
	}
}
