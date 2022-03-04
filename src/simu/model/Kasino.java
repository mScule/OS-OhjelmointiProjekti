package simu.model;

import eduni.distributions.Negexp;
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
    private static double yllapitohinta = 0;
    private final static double minimiYllapitohinta = 100;
    private static double mainoskulut = 0;
    private static long seed = 1337;
    // Asiakkaiden ominaisuuksien jakauma.
    private static Normal asiakasOminNormal = new Normal(0, 0.5, seed);
    // Pelien jakauma.
    private static Uniform pelitUniform = new Uniform(0, 1, seed);
    // Sisäänkäynnin, uloskäynnin ja baarin palveluaikajakauma.
    public final static double defaultKeskimPalveluaika = 10;
    public final static Negexp defaultPalveluajatNegexp = new Negexp(defaultKeskimPalveluaika, seed);
    public final static double defaultKeskimSaapumisaika = 10;
    public final static Negexp defaultSaapumisajatNegexp = new Negexp(defaultKeskimSaapumisaika, seed);
    public final static double pelipoydanHinta = 100;
    public final static double baarinHinta = 300;
    public final static double sisaankaynninHinta = 1000;
    public final static double uloskaynninHinta = 1000;
    private static boolean vararikko = false;
    public final static double investmentInefficiencyRatio = 200;

    private static double keskimPalveluaika = 10;
    private static double keskimSaapumisvaliaika = 10;

    private Kasino() {
    }

    public static double getKeskimPalveluaika() {
        return keskimPalveluaika;
    }

    public static void setKeskimPalveluaika(double keskimPalveluaika) {
        Kasino.keskimPalveluaika = keskimPalveluaika;
    }

    public static double getKeskimSaapumisvaliaika() {
        return keskimSaapumisvaliaika;
    }

    public static void setKeskimSaapumisvaliaika(double keskimSaapumisvaliaika) {
        Kasino.keskimSaapumisvaliaika = keskimSaapumisvaliaika;
    }

    public static double getYllapitohinta() {
        return yllapitohinta;
    }

    public static long getSeed() {
        return seed;
    }

    public static void setSeed(long seed) {
        Kasino.seed = seed;
    }

    public static double getMainoskulut() {
        return mainoskulut;
    }

    public static void setMainoskulut(double mainoskulut) {
        Kasino.mainoskulut = mainoskulut;
    }

    public static double getMinimiyllapitohinta() {
        return minimiYllapitohinta;
    }

    public static double getKokoYllapitohinta() {
        return yllapitohinta + mainoskulut + minimiYllapitohinta;
    }

    public static void setYllapitohinta(double yllapitohinta) {
        Kasino.yllapitohinta = yllapitohinta;
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
        // TODO päivitä asiakkaiden keskim. mielialaa joka kerta, kun jonkin asiakkaan
        // mieliala muuttuu.
    }

    public static double getAsiakkaidenKeskimPaihtyneisyys() {
        return asiakkaidenKeskimPaihtyneisyys;
    }

    public static void setAsiakkaidenKeskimPaihtyneisyys(double asiakkaidenKeskimPaihtyneisyys) {
        // TODO päivitä asiakkaiden keskim. päihtyneisyys joka kerta, kun jonkin
        // asiakkaan päihtyneisyys muuttuu.
    }

    public static double getAsiakkaidenKeskimVarakkuus() {
        return asiakkaidenKeskimVarakkuus;
    }

    public static void setAsiakkaidenKeskimVarakkuus(double asiakkaidenKeskimVarakkuus) {
        // TODO päivitä asiakkaiden keskim. päihtyneisyys joka kerta, kun jonkin
        // asiakkaan päihtyneisyys muuttuu.
    }

    // TODO: käytä rahaa kasinon laajentamiseen?
    // TODO: käytä rahaa kasinon mainostamiseen?
}
