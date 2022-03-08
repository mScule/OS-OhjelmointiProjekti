package kasinoSimulaattori.controller;

public interface IKontrolleriMtoV {
	
		// Visualisointi
		
		public void naytaLoppuaika(double aika);
		public void visualisoiAsiakas(int x1, int y1, int x2, int y2);
		public void lopetaVisualisointi(String viesti);
		
		// Baari
		public void baariPalveltavat(int maara);
		public void baariJonossa(int maara);
		public void baariTyontekijat(int maara);
		
		// Blackjack
		public void blackjackPalveltavat(int maara);
		public void blackjackJonossa(int maara);
		public void blackjackTyontekijat(int maara);
		
		// Sisäänkäynti
		public void sisaankayntiPalveltavat(int maara);
		public void sisaankayntiJonossa(int maara);
		public void sisaankayntiTyontekijat(int maara);
		
		// Uloskäynti
		public void uloskayntiPalveltavat(int maara);
		public void uloskayntiJonossa(int maara);
		public void uloskayntiTyontekijat(int maara);
		
		// UI
		public void setAika(String value);
		// public void setPaiva(String value);
		public void setRahat(String value);
		public void setVoitot(String value);
		public void setSaapuneet(String value);
		public void setPalvellut(String value);
		public void setAvgJono(String value);
		public void setKokonaisoleskelu(String value);
		public void setAvgOnnellisuus(String value);
		public void setAvgPaihtymys(String value);
		public void setAvgVarallisuus(String value);
		public void setAvgLapimeno(String value);
}
