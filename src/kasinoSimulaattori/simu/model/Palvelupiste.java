package kasinoSimulaattori.simu.model;

import java.util.LinkedList;

import kasinoSimulaattori.eduni.distributions.Negexp;
import kasinoSimulaattori.eduni.distributions.Uniform;
import kasinoSimulaattori.simu.framework.Tapahtumalista;
import kasinoSimulaattori.simu.framework.Trace;
import kasinoSimulaattori.util.Sijainti;

/**
 * Kasinon palvelupisteiden yliluokka.
 * 
 * @author Jonathan Methuen
 */
public class Palvelupiste implements IPalvelupiste {

	/**
	 * Numero, joka kasvaa koko ajan kun luokasta luodaan olioita. Käytetään
	 * jokaisen olion oman id arvon asettamiseen.
	 */
	public static int palveluid = 0;
	protected LinkedList<Asiakas> jono = new LinkedList<Asiakas>(); // Tietorakennetoteutus
	/**
	 * Palveulupisteen palveluaikojen arvontaan käytetty Negexp jakauma.
	 */
	protected Negexp negexpGenerator;
	protected Tapahtumalista tapahtumalista;
	/**
	 * Seuraavan tapahtuman arvontaan käytetty Uniform jakauma. Arvotaan joku muu
	 * tapahtuma, kuin SISÄÄNKÄYNTI tai POISTUMINEN.
	 */
	protected Uniform nextTapahtumaUniform;
	protected boolean varattu = false;
	private int id;
	private double palveluaika = 0;
	private int palvellutAsiakkaat = 0;
	private double[] tulokset = new double[IPalvelupiste.TULOSTEN_MAARA];
	private Sijainti sijainti;

	/**
	 * Palvelupisteen konstruktori
	 * 
	 * @param negexpGenerator      Palveulupisteen palveluaikojen arvontaan käytetty
	 *                             Negexp jakauma
	 * @param tapahtumalista       Viittaus simulaation tapahtumalistaan
	 * @param sijainti             Palvelupisteen sijainti x- ja y-akselilla
	 * @param nextTapahtumaUniform Seuraavan tapahtuman arvontaan käytetty Uniform
	 *                             jakauma
	 */
	public Palvelupiste(Negexp negexpGenerator, Tapahtumalista tapahtumalista, Sijainti sijainti,
			Uniform nextTapahtumaUniform) {
		this.tapahtumalista = tapahtumalista;
		this.negexpGenerator = negexpGenerator;
		this.sijainti = sijainti;
		this.nextTapahtumaUniform = nextTapahtumaUniform;

		id = palveluid;
		palveluid++;
	}

	/**
	 * Hakee palvelupisteen x- ja y-akselin sijainnin canvaksella
	 * 
	 * @return Palvelupisteen sijainti x- ja y-akselilla
	 */
	public Sijainti getSijainti() {
		return sijainti;
	}

	/**
	 * Asettaa palvelupisteelle uuden keskimääräisen palveluajan
	 * 
	 * @param uusiKeskimPalveluaika Uusi keskimääräinen palveluaika
	 */
	public void setKeskimPalveluaika(double uusiKeskimPalveluaika) {
		Negexp newGenerator = new Negexp(uusiKeskimPalveluaika, Kasino.getSeed());
		negexpGenerator = newGenerator;
	}

	/**
	 * Hakee Uniform jakaumasta seuraavaksi luotavaa tapahtumatyyppiä kuvaavan
	 * satunnaisen luvun.
	 * 
	 * @return Seuraavaksi luotavaa tapahtumatyyppiä kuvaava luku
	 */
	private int getSample() {
		return (int) nextTapahtumaUniform.sample();
	}

	/**
	 * Hakee seuraavaksi luotavan tapahtuman tyypin sitä kuvaavalla satunnaisella
	 * luvulla. Luku on arvottu Uniform jakaumalla.
	 * 
	 * @return Seuraavaksi luotavan tapahtuman tyyppi
	 */
	protected TapahtumanTyyppi arvoTapahtuma() {
		return TapahtumanTyyppi.values()[getSample()];
	}

	/**
	 * Kasvattaa palvelupisteen asiakkaiden palvelemiseen käytettyä aikaa
	 * 
	 * @param aika Kuinka paljon palvelupisteen asiakkaiden
	 *             palvelemiseen käytettyä aikaa kasvatetaan
	 */
	protected void lisaaPalveluAikaa(double aika) {
		palveluaika += aika;
	}

	/**
	 * Hakee palvelupisteen asiakkaiden palvelemiseen käytetyn ajan
	 * 
	 * @return Palvelupisteen asiakkaiden palvelemiseen käytetty aika
	 */
	public double getPalveluaika() {
		return palveluaika;
	}

	/**
	 * Lisää palvelupisteen palvelemien asiakkaiden määrää
	 * 
	 * @param aika Kuinka paljon palvelupisteen asiakkaiden
	 *             palvelemiseen käytettyä aikaa kasvatetaan
	 */
	protected void lisaaPalveltuAsiakas() {
		palvellutAsiakkaat++;
	}

	/**
	 * Hakee palvelupisteen palvelemien asiakkaiden määrän
	 * 
	 * @return Palvelupisteen palvelemien asiakkaiden määrä
	 */
	public int getPalvellutAsiakkaat() {
		return palvellutAsiakkaat;
	}

	/**
	 * Lisää asiakkaan palvelupisteen jonoon. Jonon 1. asiakas on aina palveltavana
	 * baari, sisäänkäynti tai uloskäynti palvelupisteillä.
	 * 
	 * @param a Palvelupisteen jonoon lisättävä asiakas
	 */
	public void lisaaJonoon(Asiakas a) {
		jono.add(a);
	}

	/**
	 * Poistaa asiakkaan palvelupisteen jonosta ja lisää palveltujen asiakkaiden
	 * määrää
	 * 
	 * @return Viittaus poistettavaan asiakkaaseen
	 */
	public Asiakas otaJonosta() { // Poistetaan palvelussa ollut
		lisaaPalveltuAsiakas();
		varattu = false;
		return jono.poll();
	}

	/**
	 * Etsii asiakkaan, jolla on tietty id ja poistaa hänet palvelupisteen jonosta
	 * 
	 * @param poistettavanAsiakkaanID Poistettavan asiakkaan id
	 * @return Viittaus poistettavaan asiakkaaseen tai null, jos asiakasta ei löydy
	 *         haettavalla id:llä
	 */
	public Asiakas otaJonostaIDnMukaan(int poistettavanAsiakkaanID) {
		for (int i = 0; i < jono.size(); i++) {
			if (poistettavanAsiakkaanID == jono.get(i).getId())
				return jono.remove(i);
		}
		System.err.println("Asiakasta " + poistettavanAsiakkaanID + "ei löytynyt jonosta.");
		return null;
	}

	/**
	 * Aloittaa palvelupisteen jonossa ensimmäisenä olevan asiakkaan palvelemisen
	 */
	public void aloitaPalvelu() {
		Trace.out(Trace.Level.INFO, "Aloitetaan uusi palvelu, asiakas " + jono.peek().getId() + " ["
				+ this.getClass().toString() + " " + getId() + " ]");
		// Printtaa asiakkaan tiedot
		Trace.out(Trace.Level.INFO, jono.peek());
	}

	/**
	 * Kertoo onko palvelupiste varattu vai ei.
	 * 
	 * @return False, jos palvelupiste ei ole varattu ja true jos on
	 */
	public boolean onVarattu() {
		return varattu;
	}

	/**
	 * Kertoo onko palvelupisteen jonossa asiakkaita vai ei
	 * 
	 * @return False, jos jonossa ei ole asiakkaita ja true jos on
	 */
	public boolean onJonossa() {
		return jono.size() != 0;
	}

	/**
	 * Hakee palvelupisteen id:n
	 * 
	 * @return Palvelupisteen id
	 */
	public int getId() {
		return id;
	}

	@Override
	public double[] getTulokset() {
		double onVarattu;

		if (onVarattu()) {
			onVarattu = 1;
		} else {
			onVarattu = 0;
		}

		// ID
		tulokset[IPalvelupiste.ID] = getId();

		// Varattu
		tulokset[IPalvelupiste.VARATTU] = onVarattu;

		// Saapuneiden asiakkaiden määrä
		tulokset[IPalvelupiste.PALVELUAIKA] = getPalveluaika();

		// Poistuneiden asiakkaiden määrä
		tulokset[IPalvelupiste.PALVELLUT_ASIAKKAAT] = getPalvellutAsiakkaat();

		return tulokset;
	}
}
