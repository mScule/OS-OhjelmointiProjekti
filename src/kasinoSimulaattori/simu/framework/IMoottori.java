package kasinoSimulaattori.simu.framework;

public interface IMoottori { // UUSI
		
	// Kontrolleri käyttää tätä rajapintaa
	
	public void setSimulointiaika(double aika);
	public void setViive(long aika);
	public long getViive();
	public void setMainostusRahamaara(double mainostus);
	public void setMaxBet(int max);
	public void setMinBet(int min);
	public void setYllapitoRahamaara(double yllapito);
	public void setBlackjackTasapeliprosentti(double tasapeli);
}
