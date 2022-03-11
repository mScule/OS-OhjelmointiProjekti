package kasinoSimulaattori.controller;

import java.util.LinkedList;

import kasinoSimulaattori.simu.model.Palvelupiste;

public interface IKontrolleriVtoM {

	// Rajapinta, joka tarjotaan käyttöliittymälle:

	public void kaynnistaSimulointi(double aika, long viive, double mainostus, int max, int min, 
			double yllapito, double tasapeli, double voitto, int pelit, int baarit, 
			int sisaankaynnit, int uloskaynnit);

	public void nopeuta();

	public void hidasta();

	public double[] haeTulokset();

	public LinkedList<Palvelupiste> haePalvelupisteet(int palvelu);
	
	public void continueSim();

	public boolean simuloidaan();

	public void resetVisualistointi();
}
