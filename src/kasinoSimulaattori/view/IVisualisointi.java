package kasinoSimulaattori.view;

import javafx.scene.canvas.Canvas;

public interface IVisualisointi {
	
	// Yleiset
	public Canvas getCanvas();
	public void asiakkaanLiikeAnimaatio(int aloitusX, int aloitusY, int lopetusX, int lopetusY);
	
	// Baari
	public void setBaariJononPituus(int pituus);
	public void setBaariPalveltavienMaara(int maara);
	
	// Blackjack
	public void setBlackjackPalveltavienMaara(int maara);
	public void setBlackjackJononPituus(int pituus);
	
	// Sisäänkäynti
	public void setSisaankayntiPalveltavienMaara(int maara);
	public void setSisaankayntiJononPituus(int pituus);

	// Uloskäynti
	public void setUloskayntiPalveltavienMaara(int maara);
	public void setUloskayntiJononPituus(int pituus);
	
	// Animointi
	public void setAsiakasNopeus(double nopeus);
}
