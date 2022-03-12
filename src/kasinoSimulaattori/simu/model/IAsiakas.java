package kasinoSimulaattori.simu.model;

/**
 * Asiakas.java luokan rajapinta. Käytetään asiakkaan datan hakemiseen.
 * 
 * @author Jonathan Methuen
 */
public interface IAsiakas {
    /**
     * Taulukossa asiakkaan tietyn tyyppisen datan indeksiä vastaava muuttumaton
     * kokonaisluku.
     */
    public static final int ID = 0,
            SAAPUMISAIKA = 1,
            STATUS = 2,
            MIELENTILA = 3,
            VARAKKUUS = 4,
            UHKAROHKEUS = 5,
            PAIHTYMYS = 6,
            RAHAT = 7,
            TULOSTEN_MAARA = 8;

    /**
     * Hae asiakkaaseen liittyvä data.
     */
    public double[] getTulokset();
}
