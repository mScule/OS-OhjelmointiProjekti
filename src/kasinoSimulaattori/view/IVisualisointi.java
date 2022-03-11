package kasinoSimulaattori.view;

import javafx.scene.canvas.Canvas;

public interface IVisualisointi {
	
	// Yleiset
	
	/**
	 * Palauttaa viitteen canvakseen, jossa visualisointi tapahtuu.
	 */
	public Canvas getCanvas();
	
	/**
	 * Luo asiakkaan liike animaation sijainnista aloitusX, aloitusY sijaintiin lopetusX, lopetusY.
	 * @param aloitusX Aloitus sijainnin x-akselin koordinaatti.
	 * @param aloitusY Aloitus sijainnin y-akselin koordinaatti.
	 * @param lopetusX Lopetus sijainnin x-akselin koordinaatti.
	 * @param lopetusY Lopetus sijainnin y-akselin koordinaatti.
	 */
	public void asiakkaanLiikeAnimaatio(int aloitusX, int aloitusY, int lopetusX, int lopetusY);
	
	/**
	 * Kertoo säikeen kuvan renderöintisilmukalle että visualisointi lopetetaan.
	 * @param viesti Viesti joka näytetään visualisoinnin vasemmassa ylänurkassa visualisoinnin päätteeksi.
	 */
	public void lopetaVisualisointi(String viesti);
	
	// Baari
	
	/**
	 * Asettaa jononpituuden baarin infopalkkiin.
	 * @param pituus Jonon pituus.
	 */
	public void setBaariJononPituus(int pituus);
	
	/**
	 * Asettaa palveltavienmäärän baarin infopalkkiin.
	 * @param maara Palveltavien määrä.
	 */
	public void setBaariPalveltavienMaara(int maara);
	
	/**
	 * Asettaa työntekijöiden määrän baarin infopalkkiin.
	 * @param maara työntekijöiden määrä.
	 */
	public void setBaariTyontekijoidenMaara(int maara);
	
	// Blackjack

	/**
	 * Asettaa jononpituuden blackjackpöydän infopalkkiin.
	 * @param pituus Jonon pituus.
	 */
	public void setBlackjackJononPituus(int pituus);
	
	/**
	 * Asettaa palveltavienmäärän blackjackpöydän infopalkkiin.
	 * @param maara Palveltavien määrä.
	 */
	public void setBlackjackPalveltavienMaara(int maara);
	
	/**
	 * Asettaa työntekijöiden määrän blackjackpöydän infopalkkiin.
	 * @param maara työntekijöiden määrä.
	 */
	public void setBlackjackTyontekijoidenMaara(int maara);
	
	// Sisäänkäynti
	
	/**
	 * Asettaa jononpituuden sisäänkäynnin infopalkkiin.
	 * @param pituus Jonon pituus.
	 */
	public void setSisaankayntiJononPituus(int pituus);
	
	/**
	 * Asettaa palveltavienmäärän sisäänkäynnin infopalkkiin.
	 * @param maara Palveltavien määrä.
	 */
	public void setSisaankayntiPalveltavienMaara(int maara);
	
	/**
	 * Asettaa työntekijöiden määrän sisäänkäynnin infopalkkiin.
	 * @param maara työntekijöiden määrä.
	 */
	public void setSisaankayntiTyontekijoidenMaara(int maara);

	// Uloskäynti
	
	/**
	 * Asettaa jononpituuden uloskäynnin infopalkkiin.
	 * @param pituus Jonon pituus.
	 */
	public void setUloskayntiJononPituus(int pituus);
	
	/**
	 * Asettaa palveltavienmäärän uloskäynnin infopalkkiin.
	 * @param maara Palveltavien määrä.
	 */
	public void setUloskayntiPalveltavienMaara(int maara);
	
	/**
	 * Asettaa työntekijöiden määrän uloskäynnin infopalkkiin.
	 * @param maara työntekijöiden määrä.
	 */
	public void setUloskayntiTyontekijoidenMaara(int maara);
	
	// Animointi
	
	/**
	 * Asettaa asiakkaan nopeuden.
	 * @param nopeus Asiakkaan uusi nopeus.
	 */
	public void setAsiakasNopeus(double nopeus);
	
	/**
	 * Hakee asiakkaan nykyisen nopeuden.
	 * @return Asiakkaan nykyinen nopeus.
	 */
	public double getAsiakasNopeus();
	
	/**
	 * Asettaa taustan liukuanimaation nopeuden.
	 * @param nopeus Liu'un uusi nopeus.
	 */
	public void setTaustaLiukuNopeus(double nopeus);
	
	/**
	 * Hakee taustan liukuanimaation nopeuden.
	 * @return Taustan liukuanimaation nykyinen nopeus.
	 */
	public double getTaustaLiukuNopeus();
}
