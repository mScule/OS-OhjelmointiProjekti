package kasinoSimulaattori.controller;

import java.util.LinkedList;

import kasinoSimulaattori.simu.model.Palvelupiste;

public interface IKontrolleriVtoM {

	// Rajapinta, joka tarjotaan käyttöliittymälle:

	/**
	 * Simuloinnin moottorin instanssi luodaan ja alustetaan.
	 * @param aika Simulaation kesto.
	 * @param viive Tapahtumalistan tapahtumien aikaväli simuloinnin aikana.
	 * @param mainostus Mainostukseen käytetyt rahat.
	 * @param max Asiakkaan mahdollinen maksimi panos.
	 * @param min Asiakkaan mahdollinen minimi panos.
	 * @param yllapito Ylläpitoon käytettävät kulut.
	 * @param tasapeli Tasapeliprosentti blackjackissa.
	 * @param voitto Voittoprosentti blackjackissa.
	 * @param pelit Blackjackpöytien määrä.
	 * @param baarit Baaritiskien määrä.
	 * @param sisaankaynnit Sisäänkäyntien määrä.
	 * @param uloskaynnit Uloskäyntien määrä.
	 */
	public void asetaSyotteetMoottoriin(double aika, long viive, double mainostus, int max, int min, 
			double yllapito, double tasapeli, double voitto, int pelit, int baarit, 
			int sisaankaynnit, int uloskaynnit);
	/**
	 * Käynnistää moottorin.
	 */
	public void kaynnistaSimulointi();

	/**
	 * Pienentää viivettä tapahtumien simuloinnin välissä 10 prosentilla.
	 */
	public void nopeuta();

	/**
	 * Suurentaa viivettä tapahtumien simuloinnin välissä 10 prosentilla.
	 */
	public void hidasta();

	/**
	 * Palauttaa moottorin mittaamat suorituskykysuureet double taulukkona johon voi viitata IOmaMoottori
	 * rajapinnasta saatavilla "TULOS_" alkuisilla vakioilla.
	 */
	public double[] haeTulokset();

	/**
	 * Palauttaa palvelupisteet taulukon halutusta palvelupiste ryhmästä.
	 * @param palvelu IOmaMoottorista saatava "PALVELUTYYPPI_" alkuinen vakio.
	 */
	public LinkedList<Palvelupiste> haePalvelupisteet(int palvelu);
	
	/**
	 * Jatkaa simulointia paussin jälkeen.
	 */
	public void jatkaSimulointia();

	/**
	 * @return false Jos simulaatio on päättynyt. true Jos simulaatio on käynnissä, tai paussilla.
	 */
	public boolean simuloidaan();

	/**
	 * Käynnistää visualisoinnin uudestaan.
	 */
	public void resetVisualistointi();
	/**
	 * Luo uuden tyhjän instannsin moottorista.
	 */
	public void resetoiSimulointi();
	/**
	 * Hakee onko simulaatio tauolla vai ei.
	 */
	public boolean getKasinoPause();
	/**
	 * Asettaa simulaation tauolle tai pois tauolta.
	 */
	public void setKasinoPause(boolean pause);
}
