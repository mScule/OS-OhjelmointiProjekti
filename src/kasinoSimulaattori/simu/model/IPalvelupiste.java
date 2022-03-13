package kasinoSimulaattori.simu.model;

/**
 * Palvelupiste luokan rajapinta. Käytetään
 * datan hakemiseen Palvelupiste luokasta.
 * 
 * @author Jonathan Methuen
 */
public interface IPalvelupiste {
	/**
	 * Taulukossa palvelupisteen tietyntyyppisen datan indeksiä vastaava muuttumaton
	 * kokonaisluku
	 */
	public static final int ID = 0,
			VARATTU = 1,
			PALVELUAIKA = 2,
			PALVELLUT_ASIAKKAAT = 3,
			TULOSTEN_MAARA = 16;

	/**
	 * Hakee palvelupistekohtaista dataa
	 * 
	 * @return Double array palvelupisteen datasta
	 */
	public double[] getTulokset();
}
