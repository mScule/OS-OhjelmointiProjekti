package simu.model;

public interface IOmaMoottori {

	public static final int
		TULOS_AIKA                           = 0,
		TULOS_SAAPUNEIDEN_ASIAKKAIDEN_MAARA  = 1,
		TULOS_POISTUNEIDEN_ASIAKKAIDEN_MAARA = 2,
		TULOS_KESKIMAARAINEN_LAPIMENOAIKA    = 3,
		TULOS_KOKONAISOLESKELUAIKA           = 4,
		TULOS_KESKIMAARAINEN_JONONPITUUS     = 5,
		TULOS_RAHA                           = 6,
		TULOSTEN_MAARA                       = 7;
	
	public double[] getTulokset();
	
	public static final int
		PALVELUTYYPPI_SISAANKAYNTI = 0,
		PALVELUTYYPPI_ULOSKAYNTI   = 1,
		PALVELUTYYPPI_BAARI        = 2,
		PALVELUTYYPPI_PELI         = 3,
		PALVELUJEN_MAARA           = 4;
	
	public Palvelupiste[] getPalvelupisteet(int palvelu);
}
