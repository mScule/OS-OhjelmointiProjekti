package simu.model;

public final class Kasino {
    private final static int kasinonLahtoRahat = 1000000;
    private static int kasinonRahat;
    private static int kasinonTuotto;
    private static double asiakkaidenKeskimMieliala;
    private static double asiakkaidenKeskimPaihtyneisyys;
    private static double asiakkaidenKeskimVarakkuus;

    private Kasino() {
    }

    public static int getKasinonRahat() {
        return kasinonRahat;
    }

    public static void loseMoney(int rahamaara) {
        kasinonRahat -= rahamaara;
    }

    public static void gainMoney(int rahamaara) {
        kasinonRahat += rahamaara;
    }

    public static int getKasinonTuotto() {
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
