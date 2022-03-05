package controller;

import java.util.LinkedList;

import simu.model.Palvelupiste;

public interface IKontrolleriVtoM {
	
		// Rajapinta, joka tarjotaan  käyttöliittymälle:
	
		public void kaynnistaSimulointi();
		public void nopeuta();
		public void hidasta();
		
		public double[] haeTulokset();
		public LinkedList<Palvelupiste> haePalvelupisteet(int palvelu);
}
