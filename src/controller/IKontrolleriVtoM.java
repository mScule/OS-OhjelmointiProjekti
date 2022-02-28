package controller;

import simu.model.Palvelupiste;

public interface IKontrolleriVtoM {
	
		// Rajapinta, joka tarjotaan  käyttöliittymälle:
	
		public void kaynnistaSimulointi();
		public void nopeuta();
		public void hidasta();
		
		public double[] haeTulokset();
		public Palvelupiste[] haePalvelupisteet(int palvelu);
}
