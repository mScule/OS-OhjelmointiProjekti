package kasinoSimulaattori.view;

import kasinoSimulaattori.simu.model.KasinoTulokset;

/**
 * Rajapinta sovelluksen näkymälle
 */
public interface ISimulaattorinUI {
	
	/**
	 * @return Simulaation uusimman suoritetun tapahtuman ajankohta
	 */
	public double getAika();
	
	/**
	 * @return Viive tapahtumien välissä.
	 */
	public long getViive();
	
	/**
	 * Kontrolleri antaa käyttöliittymälle tuloksia, joita Moottori tuottaa 
	 * @param aika Aika jolloin simulaation ajo päättyy.
	 */
	public void setLoppuaika(double aika);
	
	/**
	 * @return Palauttaa viittauksen visualisointiin.
	 */
	public IVisualisointi getVisualisointi();
	
	/**
	 * @return palauttaa viittauksen käyttöliittymän kontrolleriin
	 */
	public SimulaattoriGUIController getGui();
	
	/**
	 * Tulosikkunan aukaiseva metodi.
	 * @param tulokset
	 */
	public void naytaTulokset(KasinoTulokset[] tulokset);
	
	/**
	 * Käynnistää visualisoinnin uudestaan.
	 */
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
