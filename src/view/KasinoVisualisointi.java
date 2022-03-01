package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import controller.KasinoKontrolleri;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Timer;

import simu.model.IOmaMoottori;

import view.animated.Liikkuja;

public class KasinoVisualisointi extends Thread implements IKasinoVisualisointi {
	
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
	
	//private KasinoKontrolleri kontrolleri;
	private int
		testiLuku = 0;
	
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
		
		//this.kontrolleri = kontrolleri;
		
		kanvas = new Canvas(512 + 256 + 128, 512 + 256);
		gc = kanvas.getGraphicsContext2D();
		gc.setStroke(Color.WHITE);
		
		this.start();
	}
	
	private void piirraInfo(int x, int y, int jononPituus, int palveltavienMaara) {
		
		gc.drawImage(kuvaJonossa,    x * 128,      y * 128);
		gc.drawImage(kuvaPalvelussa, x * 128 + 64, y * 128);
		
		gc.strokeText(jononPituus + ""  , x * 128     , y * 128);
		gc.strokeText(palveltavienMaara + "", x * 128 + 64, y * 128);
	}
	
	private void piirraTausta(int x, int y) {
		gc.drawImage(kuvaTausta, x*128, y*128);
	}
	
	private void piirraPalvelu(Image kuva, int x, int y) {
		gc.drawImage(kuva, x*128, y*128);
	}
	
	private void piirraLattia(int x, int y) {
		x *= 128;
		y *= 128;
		
		gc.drawImage(kuvaLattia, x     , y     );
		gc.drawImage(kuvaLattia, x + 64, y     );
		gc.drawImage(kuvaLattia, x     , y + 64);
		gc.drawImage(kuvaLattia, x + 64, y + 64);
	}
	
	public void piirraAsiakasLiike(int aloitusX, int aloitusY, int lopetusX, int lopetusY) {
		liikkujat.add(new Liikkuja(kuvaAsiakas, gc, aloitusX, aloitusY, lopetusX, lopetusY, 32));
	}
	
	public void paivita() { // WIP (eikai)
		
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
		piirraInfo(1,1, testiLuku,testiLuku);

		// Blackjack
		piirraPalvelu(kuvaBlackjack, 5,1);
		piirraInfo(5,1, testiLuku,testiLuku);
		
		// Sisäänkäynti
		piirraPalvelu(kuvaSisaankaynti, 2,4);
		piirraPalvelu(kuvaHakkiSisaankaynti, 1,4);
		piirraInfo(2,4, testiLuku,testiLuku);
		
		// Uloskäynti
		piirraPalvelu(kuvaUloskaynti, 4,4);
		piirraPalvelu(kuvaHakkiUloskaynti, 5,4);
		piirraInfo(4,4, testiLuku,testiLuku);
		
		// Asiakkaiden liikkeet
		for(Liikkuja l : liikkujat)
			l.liikuta(0.01);
		
		// Poista asiakkaat jotka kohteessa
		for(int i = liikkujat.size() - 1; i >= 0; i--)
			if(liikkujat.get(i).getKohteessa())
				liikkujat.remove(i);
	}
	
	public Canvas getKanvas() {
		return this.kanvas;
	}
	
	@Override
	public void run() {
		double alkuAika = System.currentTimeMillis();
		double paivitysVali = 1000/33.33334; // 30 fps

		while(true) {
			double fps = System.currentTimeMillis() - alkuAika;
			if(fps >= paivitysVali) {
				alkuAika = System.currentTimeMillis();
				paivita();
			}
		}
	}
}
