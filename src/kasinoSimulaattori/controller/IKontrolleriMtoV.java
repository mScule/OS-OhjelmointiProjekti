package kasinoSimulaattori.controller;

import kasinoSimulaattori.simu.model.KasinoTulokset;

/**
 * Kontrolleri rajapinta välittämään tietoa mallista näkymään.
 * @author Vilhelm Niemi
 */

public interface IKontrolleriMtoV {

	// Visualisointi

	/**
	 * Asettaa näkymään luvun joka kertoo käyttäjälle mikä simulaation loppuaika
	 * oli.
	 * 
	 * @param aika loppuaika.
	 */
	public void naytaLoppuaika(double aika);

	/**
	 * Luo animaation asiakkaasta joka siirtyy sijainnista toiseen ja katoaa.
	 * @param x1 ensimmäisen sijainnin koordinaatti x-akselilla.
	 * @param y1 ensimmäisen sijainnin koordinaatti y-akselilla.
	 * @param x2 toisen sijainnin koordinaatti x-akselilla.
	 * @param y2 toisen sijainnin koordinaatti y-akselilla.
	 */
	public void visualisoiAsiakas(int x1, int y1, int x2, int y2);

	/**
	 * Päättää visualisointi säikeen toiminnan.
	 * 
	 * @param viesti Viesti mikä lisätään viimeisen ruudunpäivityksen yhteydessä
	 *               visualisoinnin vasempaan ylänurkkaan.
	 */
	public void lopetaVisualisointi(String viesti);

	// Baari
	
	/**
	 * Asettaa näkymään luvun joka kertoo käyttäjälle kuinka montaa asiakasta baarien palveltavana on.
	 * @param maara Asiakkaiden määrä.
	 */
	public void baariPalveltavat(int maara);

	/**
	 * Asettaa näkymään luvun joka kertoo käyttäjälle kuinka monta asiakasta baarien jonoissa on.
	 * @param maara Asiakkaiden määrä.
	 */
	public void baariJonossa(int maara);

	/**
	 * Asettaa näkymään luvun joka kertoo käyttäjälle kuinka monta työntekijää baareilla on.
	 * @param maara Työntekijöiden/palvelupisteiden määrä.
	 */
	public void baariTyontekijat(int maara);

	// Blackjack
	
	/**
	 * Asettaa näkymään luvun joka kertoo käyttäjälle kuinka montaa asiakasta blackjackpöytien palveltavana on.
	 * @param maara Asiakkaiden määrä.
	 */
	public void blackjackPalveltavat(int maara);
	
	/**
	 * Asettaa näkymään luvun joka kertoo käyttäjälle kuinka monta asiakasta blackjackpöytien jonoissa on.
	 * @param maara Asiakkaiden määrä.
	 */
	public void blackjackJonossa(int maara);

	/**
	 * Asettaa näkymään luvun joka kertoo käyttäjälle kuinka monta työntekijää blackjackpöydillä on.
	 * @param maara Työntekijöiden/palvelupisteiden määrä.
	 */
	public void blackjackTyontekijat(int maara);

	// Sisäänkäynti
	
	/**
	 * Asettaa näkymään luvun joka kertoo käyttäjälle kuinka montaa asiakasta sisäänkäyntien palveltavana on.
	 * @param maara Asiakkaiden määrä.
	 */
	public void sisaankayntiPalveltavat(int maara);

	/**
	 * Asettaa näkymään luvun joka kertoo käyttäjälle kuinka monta asiakasta sisäänkäyntien jonoissa on.
	 * @param maara Asiakkaiden määrä.
	 */
	public void sisaankayntiJonossa(int maara);
	
	/**
	 * Asettaa näkymään luvun joka kertoo käyttäjälle kuinka monta työntekijää sisäänkäynneillä on.
	 * @param maara Työntekijöiden/palvelupisteiden määrä.
	 */
	public void sisaankayntiTyontekijat(int maara);

	// Uloskäynti
	
	/**
	 * Asettaa näkymään luvun joka kertoo käyttäjälle kuinka montaa asiakasta uloskäyntien palveltavana on.
	 * @param maara Asiakkaiden määrä.
	 */
	public void uloskayntiPalveltavat(int maara);

	/**
	 * Asettaa näkymään luvun joka kertoo käyttäjälle kuinka monta asiakasta uloskäyntien jonoissa on.
	 * @param maara Asiakkaiden määrä.
	 */
	public void uloskayntiJonossa(int maara);
	
	/**
	 * Asettaa näkymään luvun joka kertoo käyttäjälle kuinka monta työntekijää uloskäynneillä on.
	 * @param maara Työntekijöiden/palvelupisteiden määrä.
	 */
	public void uloskayntiTyontekijat(int maara);

	// UI
	
	/**
	 * Asettaa näkymään luvun joka kertoo käyttäjälle missä ajan vaiheessa simulointi on.
	 * @param value Aika.
	 */
	public void setAika(String value);

	/**
	 * Asettaa näkymään luvun joka kertoo käyttäjälle kuinka paljon rahaa kasinolla on.
	 * @param value Kasinon rahat.
	 */
	public void setRahat(String value);
	
	/**
	 * Asettaa näkymään luvun joka kertoo käyttäjälle kuinka paljon voittoja/tappioita kasino on simulaation aikana tehnyt.
	 * @param value Voitot/tappiot.
	 */
	public void setVoitot(String value);
	
	/**
	 * Asettaa näkymään luvun joka kertoo käyttäjälle kuinka monta asiakasta on saapunut kasinolle.
	 * @param value Saapuneet asiakkaat.
	 */
	public void setSaapuneet(String value);

	/**
	 * Asettaa näkymään luvun joka kertoo käyttäjälle kuinka monta asiakasta on poistunut kasinolta.
	 * @param value Poistuneet asiakkaat.
	 */
	public void setPalvellut(String value);

	/**
	 * Asettaa näkymään luvun joka kertoo käyttäjälle mikä keskimääräinen jononpituus on.
	 * @param value Keskimääräinen jononpituus.
	 */
	public void setAvgJono(String value);

	/**
	 * Asettaa näkymään luvun joka kertoo käyttäjälle mikä on asiakkaiden yhteenlaskettu kokonaisoleskeluaika.
	 * @param value Kokonaisoleskeluaika.
	 */
	public void setKokonaisoleskelu(String value);

	/**
	 * Asettaa näkymään luvun joka kertoo käyttäjälle mikä asiakkaiden keskimääräinen onnellisuus on.
	 * @param value Keskiverto onnellisuus.
	 */
	public void setAvgOnnellisuus(String value);

	/**
	 * Asettaa näkymään luvun joka kertoo käyttäjälle mikä asiakkaiden keskimääräinen päihtymys on.
	 * @param value Keskiverto päihtymys.
	 */
	public void setAvgPaihtymys(String value);

	/**
	 * Asettaa näkymään luvun joka kertoo käyttäjälle mikä asiakkaiden keskimääräinen varallisuus on.
	 * @param value Keskiverto varallisuus.
	 */
	public void setAvgVarallisuus(String value);

	/**
	 * Asettaa näkymään luvun joka kertoo käyttäjälle mikä asiakkaiden määräinen läpimenoaika on.
	 * @param value Keskiverto läpimenoaika.
	 */
	public void setAvgLapimeno(String value);

	/**
	 * Avaa ikkunan jossa näytetään viimeisimmän ajon suorituskykysuureet.
	 * @param tulokset kasinon tulokset double taulukossa.
	 */
	public void naytaTulokset(KasinoTulokset[] tulokset);
	
	/**
	 * Näyttää virhe ilmoituksen ikkunassa.
	 * @param viesti Virheilmoitus
	 */
	public void virheilmoitusDialogi(String viesti);
}
