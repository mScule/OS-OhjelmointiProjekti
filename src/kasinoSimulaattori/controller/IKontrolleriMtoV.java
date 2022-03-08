package kasinoSimulaattori.controller;

public interface IKontrolleriMtoV {
	
		// Rajapinta, joka tarjotaan moottorille:
		
		public void naytaLoppuaika(double aika);
		public void visualisoiAsiakas(int x1, int y1, int x2, int y2);
		
		// Baari
		public void baariPalveltavat(int maara);
		public void baariJonossa(int maara);
		
		// Blackjack
		public void blackjackPalveltavat(int maara);
		public void blackjackJonossa(int maara);
		
		// Sisäänkäynti
		public void sisaankayntiPalveltavat(int maara);
		public void sisaankayntiJonossa(int maara);
		
		// Uloskäynti
		public void uloskayntiPalveltavat(int maara);
		public void uloskayntiJonossa(int maara);
}
