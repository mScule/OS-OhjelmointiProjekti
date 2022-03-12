package kasinoSimulaattori.simu.model;

import java.util.LinkedList;
import kasinoSimulaattori.simu.framework.IMoottori;

public interface IOmaMoottori extends IMoottori {

	public static final int TULOS_AIKA = 0,
			TULOS_SAAPUNEIDEN_ASIAKKAIDEN_MAARA = 1,
			TULOS_POISTUNEIDEN_ASIAKKAIDEN_MAARA = 2,
			TULOS_KESKIMAARAINEN_LAPIMENOAIKA = 3,
			TULOS_KOKONAISOLESKELUAIKA = 4,
			TULOS_KESKIMAARAINEN_JONONPITUUS = 5,
			TULOS_RAHA = 6,
			TULOS_VOITTO = 7,
			TULOS_KESKIM_MIELENTILA = 8,
			TULOS_KESKIM_VARAKKUUS = 9,
			TULOS_KESKIM_UHKAROHKEUS = 10,
			TULOS_KESKIM_PAIHTYNEISYYS = 11,
			TULOSTEN_MAARA = 12; // Käytetään vain arrayn luomisessa.

	public static final int PALVELUTYYPPI_SISAANKAYNTI = 0,
			PALVELUTYYPPI_ULOSKAYNTI = 1,
			PALVELUTYYPPI_BAARI = 2,
			PALVELUTYYPPI_PELI = 3,
			PALVELUJEN_MAARA = 4;

	/**
	 * Laskee ja palauttaa kasinon nykyhetkiset tulokset.
	 * 
	 * @return Double taulukko kasinon tuloksista.
	 */
	public double[] getTulokset();

	/**
	 * Hakee ja palauttaa kaikki olemassa olevat tietyn tyyppisen palvelupisteen
	 * oliot.
	 * 
	 * @param palvelu palvelupistetyypin enumin numeerinen arvo.
	 * @return Kaikki haetun tyyppiset palvelupisteet LinkedList listana tai
	 *         null, jos palvelupisteitä ei löyty.
	 */
	public LinkedList<Palvelupiste> getPalvelupisteet(int palvelu);

	public boolean simuloidaan();
}
