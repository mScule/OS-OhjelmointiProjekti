package simu.model;

import simu.framework.Kello;
import simu.framework.Trace;
import eduni.distributions.Normal;
import eduni.distributions.Uniform;

// TODO:
// Asiakas koodataan simulointimallin edellyttämällä tavalla (data!)
public class Asiakas {

	private Kello kello = Kello.getInstance();
	private double saapumisaika;
	private double poistumisaika;
	private int id;
	private static int i = 1;
	private static long sum = 0;
	private double[] ominaisuudet = new double[Ominaisuus.values().length];
	private Normal normal;
	private TapahtumanTyyppi status;

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
		normal = new Normal(0, 0.5, System.currentTimeMillis());
		id = i++;

		for (int i = 0; i < ominaisuudet.length; i++) {
			Double sample = -1d;
			// Limit a customer's starting trait to be between 0 and 1.
			while (sample < 0 || sample >= 1) {
				sample = 0.5 + normal.sample();
			}
			ominaisuudet[i] = sample;

		}

		saapumisaika = Kello.getInstance().getAika();
		Trace.out(Trace.Level.INFO, "Uusi asiakas nro " + id + " saapui klo " + saapumisaika);
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

		return output;
	}
}
