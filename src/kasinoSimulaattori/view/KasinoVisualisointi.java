package kasinoSimulaattori.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.util.ArrayList;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import kasinoSimulaattori.util.AudioPlayer;
import kasinoSimulaattori.view.animated.Liikkuja;

/**
 * Kasinon visualisoinnista vastaava luokka
 * @author Vilhelm
 */
public class KasinoVisualisointi extends Thread implements IVisualisointi{
	
	// Canvas
	private Canvas kanvas;
	private GraphicsContext gc;
	
	// Liikkuvat spritet
	private ArrayList<Liikkuja> liikkujat;
	
	// Renderöintisilmukka
	private boolean visualisointiPaalla = true;
	private String lopetusViesti = "";
	
	private Image
	
		// Tausta spritet
		kuvaTausta,
		kuvaLattia,
		
		// Palvelupisteiden spritet
		kuvaBar,
		kuvaBlackjack,
		kuvaSisaankaynti,
		kuvaUloskaynti,
		kuvaHakkiSisaankaynti,
		kuvaHakkiUloskaynti,
		
		// Info palkkien ikonit
		kuvaJonossa,
		kuvaPalvelussa,
		kuvaTyontekija,
		
		// Liikkuvat spritet
		kuvaAsiakas;
	
	private int
		// Info palkkien kenttien luvut
		baariJono        = 0, baariPalveltavat        = 0, baariTyontekijat        = 0,
		blackjackJono    = 0, blackjackPalveltavat    = 0, blackjackTyontekijat    = 0,
		sisaankayntiJono = 0, sisaankayntiPalveltavat = 0, sisaankayntiTyontekijat = 0,
		uloskayntiJono   = 0, uloskayntiPalveltavat   = 0, uloskayntiTyontekijat   = 0;
	
	private double
		// Asiakkaiden animaatio
		asiakkaidenNopeus = 2,
	
		// Taustan animaatio
		taustaLiukuNopeus = 0,
	    taustaLiukuX = 0, taustaLiukuY = 0;
		
	/**
	 * Visualisaattorin konstruktori
	 * @throws FileNotFoundException Jos kuvia spriteille ei löydy, konstruktori heittää FileNotFound poikkeuksen.
	 */
	public KasinoVisualisointi() throws FileNotFoundException {
		
		// Tausta spritet
		kuvaTausta            = new Image(new FileInputStream("images\\background.png"     ));
		kuvaLattia            = new Image(new FileInputStream("images\\floor.png"          ));
		
		// Palvelupisteiden spritet
		kuvaBar               = new Image(new FileInputStream("images\\bar.png"            ));
		kuvaBlackjack         = new Image(new FileInputStream("images\\blackjack_table.png"));
		kuvaSisaankaynti      = new Image(new FileInputStream("images\\enterance.png"      ));
		kuvaUloskaynti        = new Image(new FileInputStream("images\\exit.png"           ));
		kuvaHakkiSisaankaynti = new Image(new FileInputStream("images\\cage_enterance.png" ));
		kuvaHakkiUloskaynti   = new Image(new FileInputStream("images\\cage_exit.png"      ));
		
		// Infopalkin spritet
		kuvaJonossa           = new Image(new FileInputStream("images\\queue.png"          ));
		kuvaPalvelussa        = new Image(new FileInputStream("images\\service.png"        ));
		kuvaTyontekija        = new Image(new FileInputStream("images\\employees.png"      ));
		
		// Liikkuvat spritet
		kuvaAsiakas           = new Image(new FileInputStream("images\\customer_style2.png"));
		
		// Liikkuvien spritejen säilön alustus
		liikkujat = new ArrayList<Liikkuja>();
		
		// Canvaksen alustus
		kanvas = new Canvas(512 + 256 + 128, 512 + 256);
		gc = kanvas.getGraphicsContext2D();
		gc.setStroke(Color.WHITE);
	}
	
	/**
	 * Piirtää infopalkin mikä sisältää kentät jononpituudelle, palveltavien määrälle, sekä työntekijöiden määrälle.
	 * @param x Sijainti x-akselilla pikseleissä.
	 * @param y Sijainti y-akselilla pikseleissä.
	 * @param jononPituus Luku jononpituudelle.
	 * @param palveltavienMaara Luku palveltavien määrälle.
	 * @param tyontekijoidenMaara Luku työntekijöiden määrälle.
	 */
	private void piirraInfoPalkki(int x, int y, int jononPituus, int palveltavienMaara, int tyontekijoidenMaara) {
		
		// Trimmaus
		int offsetKorkeus = -32, offsetLeveys = -16;
		
		// Harmaa läpikuultava tausta
		gc.setFill(new Color(0,0,0,0.5));
		gc.fillRect(x * 128 + offsetLeveys - 10, (y * 128) + offsetKorkeus - 16 - 4, 128 + 32 + 16, 64);
		
		// Punaiset reunukset
		gc.setStroke(Color.RED);
		gc.strokeRect(x * 128 + offsetLeveys - 10, (y * 128) + offsetKorkeus - 16 - 4, 128 + 32 + 16, 64);
		
		// Nollataan värit
		gc.setFill(Color.WHITE);
		gc.setStroke(Color.WHITE);
		
		// Piirretään ikonit
		gc.drawImage(kuvaJonossa   , (x * 128         ) + offsetLeveys, (y * 128) + offsetKorkeus);
		gc.drawImage(kuvaPalvelussa, (x * 128 + 64    ) + offsetLeveys, (y * 128) + offsetKorkeus);
		gc.drawImage(kuvaTyontekija, (x * 128 + 64 * 2) + offsetLeveys, (y * 128) + offsetKorkeus);
		
		// Piirretään annetut arvot
		gc.strokeText(jononPituus + ""        , (x * 128         ) + offsetLeveys, (y * 128) + offsetKorkeus);
		gc.strokeText(palveltavienMaara + ""  , (x * 128 + 64    ) + offsetLeveys, (y * 128) + offsetKorkeus);
		gc.strokeText(tyontekijoidenMaara + "", (x * 128 + 64 * 2) + offsetLeveys, (y * 128) + offsetKorkeus);
	}
	
	/**
	 * Piirtää yhden 128 * 128 pikseliä kokoisen tausta spriten annettuun sijaintiin.
	 * @param x Sijainti näytön vasemmasta yläkulmasta 128 pikselin sykäyksinä x-akselilla.
	 * @param y Sijainti näytön vasemmasta yläkulmasta 128 pikselin sykäyksinä y-akselilla.
	 */
	private void piirraTausta(int x, int y) {
		gc.drawImage(kuvaTausta, x * 128 + taustaLiukuX, y * 128 + taustaLiukuY);
	}
	
	/**
	 * Piirtää yhden annetun oletetusti palvelua kuvaavan spriten annettuun sijaintiin.
	 * @param kuva Piirrettävän palvelun kuva.
	 * @param x Sijainti näytön vasemmasta yläkulmasta 128 pikselin sykäyksinä x-akselilla.
	 * @param y Sijainti näytön vasemmasta yläkulmasta 128 pikselin sykäyksinä y-akselilla.
	 */
	private void piirraPalvelu(Image kuva, int x, int y) {
		gc.drawImage(kuva, x * 128, y * 128);
	}
	
	/**
	 * Piirtää yhden 128 * 128 pikseliä kokoisen lattia spriten annettuun sijaintiin.
	 * @param x Sijainti näytön vasemmasta yläkulmasta 128 pikselin sykäyksinä x-akselilla.
	 * @param y Sijainti näytön vasemmasta yläkulmasta 128 pikselin sykäyksinä y-akselilla.
	 */
	private void piirraLattia(int x, int y) {
		x *= 128;
		y *= 128;
		
		gc.drawImage(kuvaLattia, x     , y     );
		gc.drawImage(kuvaLattia, x + 64, y     );
		gc.drawImage(kuvaLattia, x     , y + 64);
		gc.drawImage(kuvaLattia, x + 64, y + 64);
	}
	
	/**
	 * Piirtää koko kanvaksen alueelle taustan tausta spriteistä.
	 */
	private void piirraKokoTausta() {
		for(int x = -1; x <= 7; x++)
			for(int y = -1; y <= 6; y++)
				piirraTausta(x, y);
	}
	
	/**
	 * Visualisaation ruudunpäivitysmetodi, joka kutsutaan 30 sekunnissa.
	 */
	private void paivita() {
		
		// Taustaliu'un animointi
		
		taustaLiukuX += taustaLiukuNopeus;
		taustaLiukuY += taustaLiukuNopeus;
		
		if(taustaLiukuY >= 128 && taustaLiukuX >= 128) {
			taustaLiukuX = 0;
			taustaLiukuY = 0;
		}
		
		// Tausta
		piirraKokoTausta();
		
		// Lattia
		for(int y = 1; y <= 4; y++)
			for(int x = 1; x <= 5; x++)
				piirraLattia(x,y);
		
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
		
		// Poista mahdollisesti kohteessa olevat asiakkaat
		for(int i = liikkujat.size() - 1; i >= 0; i--)
			if(liikkujat.get(i).getKohteessa())
				liikkujat.remove(i);
		
		// Infot
		piirraInfoPalkki(1,1, baariJono, baariPalveltavat, baariTyontekijat);                      // Baari
		piirraInfoPalkki(5,1, blackjackJono, blackjackPalveltavat, blackjackTyontekijat);          // Blackjack
		piirraInfoPalkki(2,4, sisaankayntiJono, sisaankayntiPalveltavat, sisaankayntiTyontekijat); // Sisäänkäynti
		piirraInfoPalkki(4,4, uloskayntiJono, uloskayntiPalveltavat, uloskayntiTyontekijat);       // Uloskäynti
	}
	
	// Thread
	
	/**
	 * Säikeen pää metodi jossa ruudunpäivitys tapahtuu.
	 */
	@Override
	public void run() {
		final double PAIVITYSVALI = 30;
		
		double fps = 0, viimeisinPaivitys = System.currentTimeMillis();
		int kierrosluku = 0;

		// Ruudunpäivityssilmukka
		while(true) {
			
			// Kuvanpäivitysten määrä viimeisimmästä päivityksestä.
			fps = System.currentTimeMillis() - viimeisinPaivitys;
			
			// Viimeisimmästä ruudunpäivityksestä on kulunut päivitysvälin verran aikaa
			// joten kuva piirretään uudestaan.
			if(fps >= PAIVITYSVALI) {
				viimeisinPaivitys = System.currentTimeMillis();
								
				paivita();
				
				kierrosluku++;
				
				if(!visualisointiPaalla && liikkujat.isEmpty()) {
					AudioPlayer.stopMusic(); 
					break;
				}
				
				// Ruudun liike päivitys
				gc.strokeText("Fps: " + fps + "\n" + "Frame: " + kierrosluku, 32, 32);
			}
		}
		
		// Lopetusnäkymä
		gc.strokeText(lopetusViesti, 32, 32);
	}
	
	// IVisualisointi
	
	@Override
	public void lopetaVisualisointi(String viesti) {
		lopetusViesti = viesti;
		visualisointiPaalla = false;
	}
	
	@Override
	public void asiakkaanLiikeAnimaatio(int aloitusX, int aloitusY, int lopetusX, int lopetusY) {
		liikkujat.add(new Liikkuja(kuvaAsiakas, gc, aloitusX, aloitusY, lopetusX, lopetusY, 32));
	}
	
	@Override
	public Canvas getCanvas() {
		return this.kanvas;
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
	public void setUloskayntiJononPituus(int pituus) {
		uloskayntiJono = pituus;
	}

	@Override
	public void setUloskayntiPalveltavienMaara(int maara) {
		uloskayntiPalveltavat = maara;
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
	
	@Override
	public double getAsiakasNopeus() {
		return asiakkaidenNopeus;
	}
	
	@Override
	public void setTaustaLiukuNopeus(double nopeus) {
		taustaLiukuNopeus = nopeus;
	}
	
	@Override
	public double getTaustaLiukuNopeus() {
		return taustaLiukuNopeus;
	}
}
