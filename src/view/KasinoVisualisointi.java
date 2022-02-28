package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import controller.KasinoKontrolleri;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import simu.model.IOmaMoottori;

public class KasinoVisualisointi implements IKasinoVisualisointi {
	
	private Canvas kanvas;
	
	private GraphicsContext gc;
	
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
		kuvaPalvelussa;
	
	private KasinoKontrolleri kontrolleri;
	
	public KasinoVisualisointi(KasinoKontrolleri kontrolleri) throws FileNotFoundException {
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
		
		this.kontrolleri = kontrolleri;
		
		kanvas = new Canvas(512 + 256 + 128, 512 + 256);
		gc = kanvas.getGraphicsContext2D();
	}
	
	private void piirraInfo(int x, int y, int jononPituus, int palveltavienMaara) {
		
		gc.drawImage(kuvaJonossa,    x * 128,      y * 128);
		gc.drawImage(kuvaPalvelussa, x * 128 + 64, y * 128);
		
		gc.setStroke(Color.WHITE);
		
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
	
	public void paivita() { // WIP (eikai)
		
		// Tausta
		for(int x = 0; x < 7; x++) {
			for(int y = 0; y < 6; y++) {
				piirraTausta(x, y);
			}
		}
		
		// Lattia
		piirraLattia(1,1); 
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
		
		piirraLattia(2,4);
		piirraLattia(4,4);
		
		// Palvelupisteet
		
		// Baari
		piirraPalvelu(kuvaBar, 1,1);
		piirraInfo(1,1, 0,0);

		// Blackjack
		piirraPalvelu(kuvaBlackjack, 5,1);
		piirraInfo(5,1, 0,0);
		
		// Sisäänkäynti
		piirraPalvelu(kuvaSisaankaynti, 2,4);
		piirraPalvelu(kuvaHakkiSisaankaynti, 1,3);
		piirraInfo(2,4, 0,0);
		
		// Uloskäynti
		piirraPalvelu(kuvaUloskaynti, 4,4);
		piirraPalvelu(kuvaHakkiUloskaynti, 5,3);
		piirraInfo(4,4, 0,0);
	}
	
	public Canvas getKanvas() {
		return this.kanvas;
	}
}
