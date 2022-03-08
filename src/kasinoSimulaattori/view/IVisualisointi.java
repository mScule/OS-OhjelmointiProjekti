package kasinoSimulaattori.view;

import javafx.scene.canvas.Canvas;

public interface IVisualisointi {
	
	// Yleiset
	public Canvas getCanvas();
	public void asiakkaanLiikeAnimaatio(int aloitusX, int aloitusY, int lopetusX, int lopetusY);
	public void lopetaVisualisointi(String viesti);
	
	// Baari
	public void setBaariJononPituus(int pituus);
	public void setBaariPalveltavienMaara(int maara);
	public void setBaariTyontekijoidenMaara(int maara);
	
	// Blackjack
	public void setBlackjackPalveltavienMaara(int maara);
	public void setBlackjackJononPituus(int pituus);
	public void setBlackjackTyontekijoidenMaara(int maara);
	
	// Sisäänkäynti
	public void setSisaankayntiPalveltavienMaara(int maara);
	public void setSisaankayntiJononPituus(int pituus);
	public void setSisaankayntiTyontekijoidenMaara(int maara);

	// Uloskäynti
	public void setUloskayntiPalveltavienMaara(int maara);
	public void setUloskayntiJononPituus(int pituus);
	public void setUloskayntiTyontekijoidenMaara(int maara);
	
	// Animointi
	public void setAsiakasNopeus(double nopeus);
	public double getAsiakasNopeus();
	public void setTaustaLiukuNopeus(double nopeus);
	public double getTaustaLiukuNopeus();
}
