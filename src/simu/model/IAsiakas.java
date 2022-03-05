package simu.model;

public interface IAsiakas {
    public static final int ID = 0,
            SAAPUMISAIKA = 1,
            STATUS = 2,
            MIELENTILA = 3,
            VARAKKUUS = 4,
            UHKAROHKEUS = 5,
            PAIHTYMYS = 6,
            RAHAT = 7,
            TULOSTEN_MAARA = 8;

    public double[] getTulokset();
}
