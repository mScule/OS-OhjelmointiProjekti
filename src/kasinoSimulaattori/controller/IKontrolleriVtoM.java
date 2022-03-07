package kasinoSimulaattori.controller;

import java.util.LinkedList;

import kasinoSimulaattori.simu.model.Palvelupiste;

public interface IKontrolleriVtoM {
	
		// Rajapinta, joka tarjotaan  käyttöliittymälle:
	
		public void kaynnistaSimulointi(double aika, long viive, double mainostus, double max, double min, double yllapito, double tasapeli);
		public void nopeuta();
		public void hidasta();
		
		public double[] haeTulokset();
		public LinkedList<Palvelupiste> haePalvelupisteet(int palvelu);
}
