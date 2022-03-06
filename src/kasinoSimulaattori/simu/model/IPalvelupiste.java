package kasinoSimulaattori.simu.model;

public interface IPalvelupiste {
	public static final int ID = 0,
			VARATTU = 1,
			PALVELUAIKA = 2,
			PALVELLUT_ASIAKKAAT = 3,
			TULOSTEN_MAARA = 16;

	public double[] getTulokset();
}
