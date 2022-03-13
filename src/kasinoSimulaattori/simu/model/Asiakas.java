package kasinoSimulaattori.simu.model;

import kasinoSimulaattori.eduni.distributions.Normal;
import kasinoSimulaattori.simu.framework.Kello;
import kasinoSimulaattori.simu.framework.Trace;

/**
 * Kasinon asiakasluokka.
 * 
 * @author Jonathan Methuen, Vilhelm Niemi
 */
public class Asiakas implements IAsiakas {

	private Kello kello = Kello.getInstance();
	private double saapumisaika;
	private int id;
	/**
	 * Numero, joka kasvaa koko ajan kun luokasta luodaan olioita. Käytetään
	 * jokaisen olion oman id arvon asettamiseen.
	 */
	public static int i = 1;
	private double[] ominaisuudet = new double[Ominaisuus.values().length];
	private double asiakkaanLahtoVarat;
	/**
	 * Asiakkaan ominaisuuksien normaalijakauma
	 */
	private Normal normal;
	private TapahtumanTyyppi status;
	private double[] tulokset = new double[IAsiakas.TULOSTEN_MAARA];

	/**
	 * Asiakkaan konstruktori. Arpoo asiakkaan ominaisuuksien arvot.
	 */
	public Asiakas() {
		normal = Kasino.getAsiakasOminNormal();
		id = i++;
		for (int i = 0; i < ominaisuudet.length; i++) {
			Double sample = -1d;
			if (i != Ominaisuus.VARAKKUUS.ordinal()) {
				// Rajoita asiakkaan lähtöominaisuus 0-1 väliin.
				while (sample < 0 || sample >= 1) {
					sample = 0.5 + normal.sample();
				}
			} else {
				while (sample < 0) {
					sample = (0.5 + normal.sample()) / (200 / (double) Kasino.getMinBet());
				}
			}
			ominaisuudet[i] = sample;
		}
		asiakkaanLahtoVarat = getOminaisuus(Ominaisuus.VARAKKUUS);
		saapumisaika = Kello.getInstance().getAika();
		Trace.out(Trace.Level.INFO, "Uusi asiakas nro " + id + " saapui klo " + saapumisaika);
	}

	/**
	 * Hakee mihin palvelupisteelle asiakas on seuraavaksi menossa.
	 * 
	 * @return Palvelupisteen tyyppi, jonne asiakas on seuraavaksi menossa
	 */
	public TapahtumanTyyppi getStatus() {
		return status;
	}

	/**
	 * Asettaa mihin palvelupisteelle asiakas on seuraavaksi menossa.
	 * 
	 * @param status palvelupisteen tyyppi, jonne asiakas on seuraavaksi menossa
	 */
	public void setStatus(TapahtumanTyyppi status) {
		this.status = status;
	}

	/**
	 * Asiakkaan ominaisuustyypit
	 */
	public enum Ominaisuus {
		MIELIALA, VARAKKUUS, UHKAROHKEUS, PAIHTYMYS
	};

	/**
	 * Asettaa asiakkaan tietyn ominaisuuden arvon.
	 * 
	 * @param ominaisuus asiakkaan ominaisuus, jonka arvo asetetaan
	 * @param arvo       asetettava arvo
	 */
	public void setOminaisuus(Ominaisuus ominaisuus, Double arvo) {
		ominaisuudet[ominaisuus.ordinal()] = arvo;
	}

	/**
	 * Hakee asiakkaan tietyn ominaisuuden arvo.
	 * 
	 * @return Haettavan asiakkaan ominaisuuden arvo
	 */
	public double getOminaisuus(Ominaisuus ominaisuus) {
		return ominaisuudet[ominaisuus.ordinal()];
	}

	/**
	 * Hakee asiakkaan lähtövarallisuuden.
	 * 
	 * @return Asiakkaan lähtövarallisuus
	 */
	public double getAsiakkaanLahtoVarallisuus() {
		return asiakkaanLahtoVarat;
	}

	/**
	 * Hakee asiakkaan varakkuuden muutoksen verrattuna asiakkaan lähtövarakkuuteen.
	 * 
	 * @return Asiakkaan varakkuuden muutos verrattuna asiakkaan lähtövarakkuuteen.
	 */
	public double getAsiakkaanVoitto() {
		return getOminaisuus(Ominaisuus.VARAKKUUS) - asiakkaanLahtoVarat;
	}

	/**
	 * Hakee asiakkaan saapumisajan.
	 * 
	 * @return Asiakkaan saapumisaika
	 */
	public double getSaapumisaika() {
		return saapumisaika;
	}

	/**
	 * Laskee ja palauttaa asiakkaan viettämän ajan kasinolla.
	 * 
	 * @return Asiakkaan viettämä aika kasinolla
	 */
	public double getPoistumisaika() {
		return kello.getAika() - getSaapumisaika();
	}

	/**
	 * Asettaa asiakkaan saapumisajan.
	 * 
	 * @param saapumisaika Asetettava asiakkaan saapumisaika
	 */
	public void setSaapumisaika(double saapumisaika) {
		this.saapumisaika = saapumisaika;
	}

	/**
	 * Hakee asiakkaan id:n.
	 * 
	 * @return Asiakkaan id
	 */
	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		String output = "";
		output += "ASIAKAS [" + getId() + "]\n";
		output += "Läpimenoaika: " + getPoistumisaika() + "\n";
		output += "Päihtymys: " + ominaisuudet[Ominaisuus.PAIHTYMYS.ordinal()] + "\n";
		output += "Varakkuus: " + ominaisuudet[Ominaisuus.VARAKKUUS.ordinal()] + "\n";
		output += "Uhkarohkeus: " + ominaisuudet[Ominaisuus.UHKAROHKEUS.ordinal()] + "\n";
		output += "Mieliala: " + ominaisuudet[Ominaisuus.MIELIALA.ordinal()] + "\n";
		output += "Rahat: " + (ominaisuudet[Ominaisuus.VARAKKUUS.ordinal()] * Kasino.asiakkaanVarakkuus1Double) + "\n";

		return output;
	}

	@Override
	public double[] getTulokset() {
		double asiakkaanVarakkuus = ominaisuudet[(Ominaisuus.VARAKKUUS).ordinal()];

		// ID
		tulokset[IAsiakas.ID] = getId();
		// SAAPUMISAIKA
		tulokset[IAsiakas.SAAPUMISAIKA] = getSaapumisaika();
		// STATUS
		tulokset[IAsiakas.STATUS] = getStatus().ordinal();
		// MIELENTILA
		tulokset[IAsiakas.MIELENTILA] = ominaisuudet[(Ominaisuus.MIELIALA).ordinal()];
		// VARAKKUUS
		tulokset[IAsiakas.VARAKKUUS] = asiakkaanVarakkuus;
		// UHKAROHKEUS
		tulokset[IAsiakas.UHKAROHKEUS] = ominaisuudet[(Ominaisuus.UHKAROHKEUS).ordinal()];
		// PAIHTYMYS
		tulokset[IAsiakas.PAIHTYMYS] = ominaisuudet[(Ominaisuus.PAIHTYMYS).ordinal()];
		// RAHAT
		tulokset[IAsiakas.RAHAT] = asiakkaanVarakkuus * Kasino.asiakkaanVarakkuus1Double;

		return tulokset;
	}
}
