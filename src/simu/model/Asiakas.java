package simu.model;

import simu.framework.Kello;
import simu.framework.Trace;
import eduni.distributions.Normal;

// Asiakas koodataan simulointimallin edellyttämällä tavalla (data!)
public class Asiakas implements IAsiakas {

	private Kello kello = Kello.getInstance();
	private double saapumisaika;
	private double poistumisaika;
	private int id;
	private static int i = 1;
	private static long sum = 0;
	private double[] ominaisuudet = new double[Ominaisuus.values().length];
	private double asiakkaanLahtoVarat;
	private Normal normal;
	private TapahtumanTyyppi status;
	private double[] tulokset = new double[IAsiakas.TULOSTEN_MAARA];

	public TapahtumanTyyppi getStatus() {
		return status;
	}

	public void setStatus(TapahtumanTyyppi status) {
		this.status = status;
	}

	public double getOminaisuudet(Ominaisuus ominaisuus) {
		return ominaisuudet[ominaisuus.ordinal()];
	}

	public enum Ominaisuus {
		MIELIALA, VARAKKUUS, UHKAROHKEUS, PAIHTYMYS
	};

	public Asiakas() {
		normal = Kasino.getAsiakasOminNormal();
		id = i++;

		for (int i = 0; i < ominaisuudet.length; i++) {

			Double sample = -1d;
			if (i != Ominaisuus.VARAKKUUS.ordinal()) {
				// Limit a customer's starting trait to be between 0 and 1.
				while (sample < 0 || sample >= 1) {
					sample = 0.5 + normal.sample();
				}
			} else {
				// TODO lower varakkuus based on min bet
				while (sample < 0) {
					sample = (0.5 + normal.sample()) / (200 / (double)Kasino.getMinBet());
				}
			}
			ominaisuudet[i] = sample;

		}

		asiakkaanLahtoVarat = getOminaisuudet(Ominaisuus.VARAKKUUS);
		saapumisaika = Kello.getInstance().getAika();
		Trace.out(Trace.Level.INFO, "Uusi asiakas nro " + id + " saapui klo " + saapumisaika);
	}

	public double getAsiakkaanLahtoVarallisuus() {
		return asiakkaanLahtoVarat;
	}

	public double getAsiakkaanVoitto() {
		return getOminaisuudet(Ominaisuus.VARAKKUUS) - asiakkaanLahtoVarat;
	}

	public void setOminaisuus(Ominaisuus ominaisuus, Double arvo) {
		ominaisuudet[ominaisuus.ordinal()] = arvo;
	}

	public double getPoistumisaika() {
		return kello.getAika() - getSaapumisaika();
	}

	public double getSaapumisaika() {
		return saapumisaika;
	}

	public void setSaapumisaika(double saapumisaika) {
		this.saapumisaika = saapumisaika;
	}

	public int getId() {
		return id;
	}

	public void raportti() {
		Trace.out(Trace.Level.INFO, "\nAsiakas " + id + " valmis! ");
		Trace.out(Trace.Level.INFO, "Asiakas " + id + " saapui: " + saapumisaika);
		Trace.out(Trace.Level.INFO, "Asiakas " + id + " poistui: " + poistumisaika);
		Trace.out(Trace.Level.INFO, "Asiakas " + id + " viipyi: " + (poistumisaika - saapumisaika));
		sum += (poistumisaika - saapumisaika);
		double keskiarvo = sum / id;
		System.out.println("Asiakkaiden läpimenoaikojen keskiarvo tähän asti " + keskiarvo);
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
