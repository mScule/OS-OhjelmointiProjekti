package kasinoSimulaattori.simu.model;

import kasinoSimulaattori.eduni.distributions.Negexp;
import kasinoSimulaattori.eduni.distributions.Normal;
import kasinoSimulaattori.eduni.distributions.Uniform;
import kasinoSimulaattori.simu.framework.Kello;

/**
 * Luokka, joka säilöö ja määrittelee kasinon erilaisia parametrejä.
 * 
 * @author Jonathan Methuen
 */
public final class Kasino {
    /**
     * Kasinon lähtörahamäärä, jonka arvo on 1000000
     */
    public final static int kasinonLahtoRahat = 1000000;
    /**
     * Kertoo sen, mitä rahamäärää se tarkoittaa, kun asiakkaan varakkuus double = 1
     */
    public final static int asiakkaanVarakkuus1Double = 50000;
    /**
     * Kasinon palveluaikojen keskimääräinen oletus palveluaika
     */
    public final static double defaultKeskimPalveluaika = 10;

    /**
     * Kasinolle saapuvien asiakkaiden keskimääräinen oletusväliaika
     */
    public final static double defaultKeskimSaapumisaika = 10;
    /**
     * Yhden blackjack pöydän lisääminen kasinolle kasvattaa kasinon kuluja sadalla.
     */
    public final static double pelipoydanHinta = 100;
    /**
     * Yhden baarin lisääminen kasinolle kasvattaa kasinon kuluja kolmellasadalla.
     */
    public final static double baarinHinta = 300;
    /**
     * Yhden sisäänkäynnin lisääminen kasinolle kasvattaa kasinon kuluja tuhannella.
     */
    public final static double sisaankaynninHinta = 1000;
    /**
     * Yhden uloskaynnin lisääminen kasinolle kasvattaa kasinon kuluja tuhannella.
     */
    public final static double uloskaynninHinta = 1000;
    /**
     * Tämän muutujan arvo heikentää kasinoon käytettävän rahamäärän hyötyjä. Isompi
     * arvo heikentää hyötyjä enemmän.
     */
    public final static double investmentInefficiencyRatio = 200;
    /**
     * Kasinon minimiylläpitohinta on 100
     */
    private final static double minimiYllapitohinta = 100;

    private static long seed = 1337;
    /**
     * Sisäänkäynnin, uloskäynnin ja baarin palveluaikojen oletusjakauma
     */
    public static Negexp defaultPalveluajatNegexp = new Negexp(defaultKeskimPalveluaika, seed);
    /**
     * Kasinolle saapuvien asiakkaiden saapumisväliaikojen oletusjakauma
     */
    public static Negexp defaultSaapumisajatNegexp = new Negexp(defaultKeskimSaapumisaika, seed);

    // Asiakkaiden ominaisuuksien jakauma.
    private static Normal asiakasOminNormal = new Normal(0, 0.5, seed);
    // Pelien jakauma
    private static Uniform pelitUniform = new Uniform(0, 1, seed);

    private static double kasinonRahat = kasinonLahtoRahat;
    private static double yllapitohinta = minimiYllapitohinta;
    private static double mainoskulut = 0;
    private static double keskimPalveluaika = 10;
    private static double keskimSaapumisvaliaika = 10;
    private static double minBet = 100;
    private static double maxBet = 1000;
    private static double blackjackVoittoprosentti = 0.4222;
    private static double blackjackTasapeliprosentti = 0.0848;

    private static boolean pause;
    private static boolean vararikko = false;

    private Kasino() {
    }

    /**
     * Hakee onko kasinosimulaatio taukotilassa vai ei.
     * 
     * @return False, jos simulaatio ei ole taukotilassa ja true jos on
     */
    public static boolean isPause() {
        return pause;
    }

    /**
     * Asettaa simulaation taukotilaan tai lopettaa simulaation taukotilan.
     * 
     * @param pause True asettaa simulaation taukotilaan ja false lopettaa
     *              simulaation taukotilan.
     */
    public static void setPause(boolean pause) {
        Kasino.pause = pause;
    }

    /**
     * Palauttaa kasinon takaisin lähtöarvoihin
     */
    public static void resetKasino() {

        // Palauta tämä luokka oletusarvoihinsa

        kasinonRahat = kasinonLahtoRahat;
        yllapitohinta = minimiYllapitohinta;
        mainoskulut = 0;
        seed = 1337;

        defaultSaapumisajatNegexp = new Negexp(defaultKeskimSaapumisaika, seed);
        asiakasOminNormal = new Normal(0, 0.5, seed);
        pelitUniform = new Uniform(0, 1, seed);
        defaultPalveluajatNegexp = new Negexp(defaultKeskimPalveluaika, seed);

        vararikko = false;

        keskimPalveluaika = defaultKeskimPalveluaika;
        keskimSaapumisvaliaika = 10;
        minBet = 100;
        maxBet = 1000;
        blackjackVoittoprosentti = 0.4222;
        blackjackTasapeliprosentti = 0.0848;

        // Palauta asiakkaat oletusarvoihinsa

        Asiakas.i = 1;

        Palvelupiste.palveluid = 0;

        // Nolla kellonaika

        Kello.getInstance().setAika(0);
    }

    /**
     * Hakee kasinon blackjack pelin asiakkaiden voittoprosentin
     * 
     * @return Kasinon blackjack pelin asiakkaiden voittoprosentti
     */
    public static double getBlackjackVoittoprosentti() {
        return blackjackVoittoprosentti;
    }

    /**
     * Asettaa kasinon blackjack pelin asiakkaiden voittoprosentin
     * 
     * @param blackjackVoittoprosentti Asetettava kasinon blackjack pelin
     *                                 asiakkaiden voittoprosentti
     */
    public static void setBlackjackVoittoprosentti(double blackjackVoittoprosentti) {
        Kasino.blackjackVoittoprosentti = blackjackVoittoprosentti;
    }

    /**
     * Hakee kasinon blackjack pelin tasapeliprosentin
     * 
     * @return Kasinon blackjack pelin tasapeliprosentin
     */
    public static double getBlackjackTasapeliprosentti() {
        return blackjackTasapeliprosentti;
    }

    /**
     * Asettaa kasinon blackjack pelin tasapeliprosentin
     * 
     * @param blackjackVoittoprosentti Asetettava kasinon blackjack pelin
     *                                 tasapeliprosentin
     */
    public static void setBlackjackTasapeliprosentti(double blackjackTasapeliprosentti) {
        Kasino.blackjackTasapeliprosentti = blackjackTasapeliprosentti;
    }

    /**
     * Hakee kasinon blackjack pelin minimipanoksen
     * 
     * @return Kasinon blackjack pelin minimipanos
     */
    public static double getMinBet() {
        return minBet;
    }

    /**
     * Asettaa kasinon blackjack pelin minimipanoksen
     * 
     * @param minBet Asetettava kasinon blackjack pelin minimipanos
     */
    public static void setMinBet(double minBet) {
        Kasino.minBet = minBet;
    }

    /**
     * Hakee kasinon blackjack pelin maksimipanoksen
     * 
     * @return Kasinon blackjack pelin maksimipanos
     */
    public static double getMaxBet() {
        return maxBet;
    }

    /**
     * Asettaa kasinon blackjack pelin maksimipanoksen
     * 
     * @param minBet Asetettava kasinon blackjack pelin maksimipanos
     */
    public static void setMaxBet(double maxBet) {
        Kasino.maxBet = maxBet;
    }

    /**
     * Hakee kasinon palveluaikojen keskimääräisen pituuden
     * 
     * @return Kasinon palveluaikojen keskimääräinen pituus
     */
    public static double getKeskimPalveluaika() {
        return keskimPalveluaika;
    }

    /**
     * Asettaa kasinon palveluaikojen keskimääräisen pituuden
     * 
     * @param keskimPalveluaika Asetettava palveluaikojen keskimääräinen pituus
     */
    public static void setKeskimPalveluaika(double keskimPalveluaika) {
        Kasino.keskimPalveluaika = keskimPalveluaika;
    }

    /**
     * Hakee asiakkaiden kasinoon saapumisväliaikojen keskimääräisen pituuden
     * 
     * @return Asiakkaiden kasinoon saapumisväliaikojen keskimääräinen pituus
     */
    public static double getKeskimSaapumisvaliaika() {
        return keskimSaapumisvaliaika;
    }

    /**
     * Asettaa asiakkaiden kasinoon saapumisväliaikojen keskimääräisen pituuden
     * 
     * @param keskimSaapumisvaliaika Asetettava asiakkaiden kasinoon
     *                               saapumisväliaikojen keskimääräinen pituus
     */
    public static void setKeskimSaapumisvaliaika(double keskimSaapumisvaliaika) {
        Kasino.keskimSaapumisvaliaika = keskimSaapumisvaliaika;
    }

    /**
     * Hakee kasinon ylläpitohinnan
     * 
     * @return Kasinon ylläpitohinta
     */
    public static double getYllapitohinta() {
        return yllapitohinta;
    }

    /**
     * Asettaa kasinon ylläpitohinnan
     * 
     * @param yllapitohinta Asetettava kasinon ylläpitohinta
     */
    public static void setYllapitohinta(double yllapitohinta) {
        Kasino.yllapitohinta = yllapitohinta;
    }

    /**
     * Hakee kasinon jakaumien seedin
     * 
     * @return Kasinon jakaumien seed
     */
    public static long getSeed() {
        return seed;
    }

    /**
     * Asettaa kasinon jakaumien seedin
     * 
     * @param seed Asetettava kasinon jakaumien seed
     */
    public static void setSeed(long seed) {
        Kasino.seed = seed;
    }

    /**
     * Hakee kasinon mainoskulut
     * 
     * @return Kasinon mainoskulut
     */
    public static double getMainoskulut() {
        return mainoskulut;
    }

    /**
     * Asettaa kasinon mainoskulut
     * 
     * @param mainoskulut Asetettava kasinon mainoskulut
     */
    public static void setMainoskulut(double mainoskulut) {
        Kasino.mainoskulut = mainoskulut;
    }

    /**
     * Hakee kasinon minimi ylläpitohinnan
     * 
     * @return Kasinon minimi ylläpitohinta
     */
    public static double getMinimiyllapitohinta() {
        return minimiYllapitohinta;
    }

    /**
     * Hakee kasinon ylläpidon kulut kokonaisuudessaan
     * 
     * @return Kasinon ylläpidon kulut kokonaisuudessaan
     */
    public static double getKokoYllapitohinta() {
        return yllapitohinta + mainoskulut + minimiYllapitohinta;
    }

    /**
     * Hakee onko kasino vararikossa
     * 
     * @return True, jos kasino on vararikossa ja false jos ei ole
     */
    public static boolean isVararikko() {
        return vararikko;
    }

    /**
     * Asettaa kasinon vararikkoon tai poistaa kasinon vararikosta
     * 
     * @param vararikko Asetetaan true, jos kasino laitetaan vararikkoon ja false
     *                  jos poistetaan kasino vararikosta
     */
    public static void setVararikko(boolean vararikko) {
        Kasino.vararikko = vararikko;
    }

    /**
     * Hakee kasinon asiakkaiden ominaisuuksien lähtöarvojen määrittelemiseen
     * käytettävän normaalijakauman
     * 
     * @return Asiakkaiden ominaisuuksien lähtöarvojen määrittelemiseen
     *         käytettävä normaalijakauma
     */
    public static Normal getAsiakasOminNormal() {
        return asiakasOminNormal;
    }

    /**
     * Hakee kasinon pelien tulosten määrittelemisessä käytettävän tasaisen jakauman
     * 
     * @return Kasinon pelien tulosten määrittelemisessä käytettävä tasainen jakauma
     */
    public static Uniform getPelitUniform() {
        return pelitUniform;
    }

    /**
     * Hakee kasinon tämänhetkisen rahamäärän
     * 
     * @return Kasinon tämänhetkinen rahamäärä
     */
    public static double getKasinonRahat() {
        return kasinonRahat;
    }

    /**
     * Vähentää kasinolta parametrin arvon verran rahaa
     * 
     * @param rahamaara Kasinon rahoista vähennettävän rahamäärän suuruus
     */
    public static void loseMoney(double rahamaara) {
        kasinonRahat -= rahamaara;
    }

    /**
     * Lisää kasinolle parametrin arvon verran rahaa
     * 
     * @param rahamaara Kasinon rahoihin lisättävän rahamäärän suuruus
     */
    public static void gainMoney(double rahamaara) {
        kasinonRahat += rahamaara;
    }

    /**
     * Laskee ja palauttaa kasinon voittojen rahamäärän
     * 
     * @return Kasinon voittojen rahamäärä
     */
    public static double getKasinonVoitto() {
        return kasinonRahat - kasinonLahtoRahat;
    }
}
