package kasinoSimulaattori.simu.model;

import kasinoSimulaattori.eduni.distributions.Negexp;
import kasinoSimulaattori.eduni.distributions.Normal;
import kasinoSimulaattori.eduni.distributions.Uniform;
import kasinoSimulaattori.simu.framework.Kello;

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
    public static Negexp defaultPalveluajatNegexp = new Negexp(defaultKeskimPalveluaika, seed);
    public final static double defaultKeskimSaapumisaika = 10;
    public static Negexp defaultSaapumisajatNegexp = new Negexp(defaultKeskimSaapumisaika, seed);
    public final static double pelipoydanHinta = 100;
    public final static double baarinHinta = 300;
    public final static double sisaankaynninHinta = 1000;
    public final static double uloskaynninHinta = 1000;
    private static boolean vararikko = false;
    public final static double investmentInefficiencyRatio = 200;

    private static double keskimPalveluaika = 10;
    private static double keskimSaapumisvaliaika = 10;
    private static double minBet = 100;
    private static double maxBet = 1000;
    private static double blackjackVoittoprosentti = 0.4222;
    private static double blackjackTasapeliprosentti = 0.0848;

    private static boolean pause;

    private Kasino() {

    }

    public static boolean isPause() {
        return pause;
    }

    public static void setPause(boolean pause) {
        Kasino.pause = pause;
    }

    public static void resetKasino() {
        // Resetoi tämä class:

        kasinonRahat = kasinonLahtoRahat;
        asiakkaidenKeskimMieliala = 0;
        asiakkaidenKeskimPaihtyneisyys = 0;
        asiakkaidenKeskimVarakkuus = 0;
        yllapitohinta = 0;
        mainoskulut = 0;
        seed = 1337;

        defaultSaapumisajatNegexp = new Negexp(defaultKeskimSaapumisaika, seed);
        // Asiakkaiden ominaisuuksien jakauma.
        asiakasOminNormal = new Normal(0, 0.5, seed);
        // Pelien jakauma.
        pelitUniform = new Uniform(0, 1, seed);
        // Sisäänkäynnin, uloskäynnin ja baarin palveluaikajakauma.
        defaultPalveluajatNegexp = new Negexp(defaultKeskimPalveluaika, seed);

        vararikko = false;

        keskimPalveluaika = 10;
        keskimSaapumisvaliaika = 10;
        minBet = 100;
        maxBet = 1000;
        blackjackVoittoprosentti = 0.4222;
        blackjackTasapeliprosentti = 0.0848;

        // Resetoi asiakkaat

        Asiakas.i = 1;
        Asiakas.sum = 0;

        Palvelupiste.palveluid = 0;
        
        // Resetoi kellonaika
        
        Kello.getInstance().setAika(0);
    }

    public static double getBlackjackVoittoprosentti() {
        return blackjackVoittoprosentti;
    }

    public static void setBlackjackVoittoprosentti(double blackjackVoittoprosentti) {
        Kasino.blackjackVoittoprosentti = blackjackVoittoprosentti;
    }

    public static double getBlackjackTasapeliprosentti() {
        return blackjackTasapeliprosentti;
    }

    public static void setBlackjackTasapeliprosentti(double blackjackTasapeliprosentti) {
        Kasino.blackjackTasapeliprosentti = blackjackTasapeliprosentti;
    }

    public static double getMinBet() {
        return minBet;
    }

    public static void setMinBet(double minBet) {
        Kasino.minBet = minBet;
    }

    public static double getMaxBet() {
        return maxBet;
    }

    public static void setMaxBet(double maxBet) {
        Kasino.maxBet = maxBet;
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
        // TODO päivitä asiakkaiden keskim. varakkuus joka kerta, kun jonkin
        // asiakkaan varakkuus muuttuu.
    }
}
