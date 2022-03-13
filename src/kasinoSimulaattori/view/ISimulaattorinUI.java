package kasinoSimulaattori.view;

import kasinoSimulaattori.simu.model.KasinoTulokset;

public interface ISimulaattorinUI {
	
	// Kontrolleri tarvitsee syötteitä, jotka se välittää Moottorille
	public double getAika();
	public long getViive();
	
	//Kontrolleri antaa käyttöliittymälle tuloksia, joita Moottori tuottaa 
	public void setLoppuaika(double aika);
	
	// Kontrolleri tarvitsee  
	public IVisualisointi getVisualisointi();
	
	public SimulaattoriGUIController getGui();
	
	public void naytaTulokset(KasinoTulokset[] tulokset);
    public void resetVisualisointi();
    
	/**
	 * Näyttää virhe ilmoituksen ikkunassa.
	 * @param viesti Virheilmoitus
	 */
    public void virheilmoitusDialogi(String viesti);
    
    /**
     * Näyttää neutraalin ilmoitusdialogin ikkunassa.
     * @param viesti Näytettävä viesti
     */
    public void ilmoitusDialogi(String viesti);
    
	/**
	 * Näyttää kysymyksen ikkunassa,
	 * johon käyttäjä voi vastata kyllä tai ei.
	 * @param viesti Viesti joka näytetään käyttäjälle.
	 * @return true Jos käyttäjä valitsi kyllä. false Jos käyttäjä valitsi ei.
	 */
	public boolean kyllaTaiEiDialogi(String viesti);
}
