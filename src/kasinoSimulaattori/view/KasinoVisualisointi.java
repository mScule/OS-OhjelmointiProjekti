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
	
	private boolean visualisointiPaalla = true;
	private String lopetusViesti = "";
	
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
		kuvaAsiakas,
		kuvaTyontekija;
	
	private int
		baariJono        = 0, baariPalveltavat        = 0, baariTyontekijat        = 0,
		blackjackJono    = 0, blackjackPalveltavat    = 0, blackjackTyontekijat    = 0,
		sisaankayntiJono = 0, sisaankayntiPalveltavat = 0, sisaankayntiTyontekijat = 0,
		uloskayntiJono   = 0, uloskayntiPalveltavat   = 0, uloskayntiTyontekijat   = 0,
		
	    ruudunLiike = 0;
	
	private double
		asiakkaidenNopeus = 2,
	
	    taustaLiukuX = 0, taustaLiukuY = 0;
		
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
		kuvaAsiakas           = new Image(new FileInputStream("images\\customer_style2.png"));
		kuvaTyontekija        = new Image(new FileInputStream("images\\employees.png"      ));
		
		liikkujat = new ArrayList<Liikkuja>();
		
		kanvas = new Canvas(512 + 256 + 128, 512 + 256);
		gc = kanvas.getGraphicsContext2D();
		gc.setStroke(Color.WHITE);
	}
	
	private void piirraInfo(int x, int y, int jononPituus, int palveltavienMaara, int tyontekijoidenMaara) {
		int offsetKorkeus = -32, offsetLeveys = -16;
		
		// Harmaa läpikuultava tausta

		gc.setFill(new Color(0,0,0,0.5));
		gc.setStroke(Color.RED);

		gc.fillRect(x * 128 + offsetLeveys - 10, (y * 128) + offsetKorkeus - 16 - 4, 128 + 32 + 16, 64);
		gc.strokeRect(x * 128 + offsetLeveys - 10, (y * 128) + offsetKorkeus - 16 - 4, 128 + 32 + 16, 64);
		
		gc.setFill(Color.WHITE);
		gc.setStroke(Color.WHITE);
		
		gc.drawImage(kuvaJonossa   , (x * 128         ) + offsetLeveys, (y * 128) + offsetKorkeus);
		gc.drawImage(kuvaPalvelussa, (x * 128 + 64    ) + offsetLeveys, (y * 128) + offsetKorkeus);
		gc.drawImage(kuvaTyontekija, (x * 128 + 64 * 2) + offsetLeveys, (y * 128) + offsetKorkeus);
		
		gc.strokeText(jononPituus + ""        , (x * 128         ) + offsetLeveys, (y * 128) + offsetKorkeus);
		gc.strokeText(palveltavienMaara + ""  , (x * 128 + 64    ) + offsetLeveys, (y * 128) + offsetKorkeus);
		gc.strokeText(tyontekijoidenMaara + "", (x * 128 + 64 * 2) + offsetLeveys, (y * 128) + offsetKorkeus);
	}
	
	private void piirraTausta(int x, int y) {
		gc.drawImage(kuvaTausta, x * 128 + taustaLiukuX, y * 128 + taustaLiukuY);
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
	public void lopetaVisualisointi(String viesti) {
		lopetusViesti = viesti;
		visualisointiPaalla = false;
	}
	
	@Override
	public void asiakkaanLiikeAnimaatio(int aloitusX, int aloitusY, int lopetusX, int lopetusY) {
		liikkujat.add(new Liikkuja(kuvaAsiakas, gc, aloitusX, aloitusY, lopetusX, lopetusY, 32));
	}
	
	private void piirraKokoTausta() {
		for(int x = -1; x <= 7; x++)
			for(int y = -1; y <= 6; y++)
				piirraTausta(x, y);
	}
	
	private void paivita() {
		
		// Tausta
		piirraKokoTausta();
		
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
		piirraLattia(3,4);
		piirraLattia(4,4);
		piirraLattia(5,4);
		
		// Palvelupisteet
		
		// Baari
		piirraPalvelu(kuvaBar, 1,1);

		// Blackjack
		piirraPalvelu(kuvaBlackjack, 5,1);
		
		// Sisäänkäynti
		piirraPalvelu(kuvaSisaankaynti, 2,4);
		piirraPalvelu(kuvaHakkiSisaankaynti, 1,4);
		
		// Uloskäynti
		piirraPalvelu(kuvaUloskaynti, 4,4);
		piirraPalvelu(kuvaHakkiUloskaynti, 5,4);

		// Asiakkaat
		
		// Asiakkaiden liikkeet
		for(Liikkuja l : liikkujat)
			l.liikuta(0.01 * asiakkaidenNopeus);
		
		// Poista asiakkaat jotka kohteessa
		for(int i = liikkujat.size() - 1; i >= 0; i--)
			if(liikkujat.get(i).getKohteessa())
				liikkujat.remove(i);
		
		// Infot
		
		piirraInfo(1,1, baariJono, baariPalveltavat, baariTyontekijat);                      // Baari
		piirraInfo(5,1, blackjackJono, blackjackPalveltavat, blackjackTyontekijat);          // Blackjack
		piirraInfo(2,4, sisaankayntiJono, sisaankayntiPalveltavat, sisaankayntiTyontekijat); // Sisäänkäynti
		piirraInfo(4,4, uloskayntiJono, uloskayntiPalveltavat, uloskayntiTyontekijat);       // Uloskäynti
	}
	
	public Canvas getCanvas() {
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
				
				taustaLiukuX++;
				taustaLiukuY++;
				
				if(taustaLiukuY >= 128 && taustaLiukuX >= 128)
				{
					taustaLiukuX = 0;
					taustaLiukuY = 0;
				}
				
				paivita();
				
				ruudunLiike++;
				
				if(!visualisointiPaalla && liikkujat.isEmpty())
					break;
				
				// Ruudun liike päivitys
				gc.strokeText("Fps: " + fps + "\n" + "Frame: " + ruudunLiike, 32, 32);
			}
		}
		
		// Lopetus näkymä
		gc.strokeText(lopetusViesti, 32, 32);
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
	@Override
	public void setBaariTyontekijoidenMaara(int maara) {
		baariTyontekijat = maara;
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
	@Override
	public void setBlackjackTyontekijoidenMaara(int maara) {
		blackjackTyontekijat = maara;
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
	@Override
	public void setSisaankayntiTyontekijoidenMaara(int maara) {
		sisaankayntiTyontekijat = maara;
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
	@Override
	public void setUloskayntiTyontekijoidenMaara(int maara) {
		uloskayntiTyontekijat = maara;
	}
	
	// Animointi
	@Override
	public void setAsiakasNopeus(double nopeus) {
		asiakkaidenNopeus = nopeus;
	}
}
