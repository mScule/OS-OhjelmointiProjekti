package simu.model;

import eduni.distributions.Normal;
import eduni.distributions.Uniform;

public final class Kasino {
    public final static int kasinonLahtoRahat = 1000000;
    // Mitä rahamäärää asiakkaan varakkuus kun "double = 1" merkitsee:
    public final static int asiakkaanVarakkuus1Double = 50000;
    private static double kasinonRahat = kasinonLahtoRahat;
    private static double asiakkaidenKeskimMieliala = 0;
    private static double asiakkaidenKeskimPaihtyneisyys = 0;
    private static double asiakkaidenKeskimVarakkuus = 0;
    // Asiakkaiden ominaisuuksien jakauma.
    private static Normal asiakasOminNormal = new Normal(0, 0.5, 1337);
    // Pelien jakauma.
    private static Uniform pelitUniform = new Uniform(0, 1, 1337);
    private static boolean vararikko = false;

    private Kasino() {
    }

    public static boolean isVararikko() {
        return vararikko;
    }

    public static void setVararikko(boolean vararikko) {
        Kasino.vararikko = vararikko;
    }

    public static Normal getAsiakasOminNormal() {
        return asiakasOminNormal;
    }

    public static Uniform getPelitUniform() {
        return pelitUniform;
    }

    public static double getKasinonRahat() {
        return kasinonRahat;
    }

    public static void loseMoney(double rahamaara) {
        kasinonRahat -= rahamaara;
    }

    public static void gainMoney(double rahamaara) {
        kasinonRahat += rahamaara;
    }

    public static double getKasinonVoitto() {
        return kasinonRahat - kasinonLahtoRahat;
    }

    public static double getAsiakkaidenKeskimMieliala() {
        return asiakkaidenKeskimMieliala;
    }

    public static void setAsiakkaidenKeskimMieliala(double asiakkaidenKeskimMieliala) {
        // TODO päivitä asiakkaiden keskim. mielialaa joka kerta, kun jonkin asiakkaan mieliala muuttuu.
    }

    public static double getAsiakkaidenKeskimPaihtyneisyys() {
        return asiakkaidenKeskimPaihtyneisyys;
    }

    public static void setAsiakkaidenKeskimPaihtyneisyys(double asiakkaidenKeskimPaihtyneisyys) {
        // TODO päivitä asiakkaiden keskim. päihtyneisyys joka kerta, kun jonkin asiakkaan päihtyneisyys muuttuu.
    }

    public static double getAsiakkaidenKeskimVarakkuus() {
        return asiakkaidenKeskimVarakkuus;
    }

    public static void setAsiakkaidenKeskimVarakkuus(double asiakkaidenKeskimVarakkuus) {
        // TODO päivitä asiakkaiden keskim. päihtyneisyys joka kerta, kun jonkin asiakkaan päihtyneisyys muuttuu.
    }

    // TODO: käytä rahaa kasinon ylläpitämiseen?
    // TODO: käytä rahaa kasinon laajentamiseen?
    // TODO: käytä rahaa kasinon mainostamiseen?
}
