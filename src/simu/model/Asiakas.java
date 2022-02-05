package simu.model;

import java.util.HashMap;
import java.util.Map;

import simu.framework.Kello;
import simu.framework.Trace;
import eduni.distributions.LogNormal;
import eduni.distributions.Uniform;

// TODO:
// Asiakas koodataan simulointimallin edellyttämällä tavalla (data!)
public class Asiakas {
	private double saapumisaika;
	private double poistumisaika;
	private int id;
	private static int i = 1;
	private static long sum = 0;
	private HashMap<Ominaisuus, Double> ominaisuudet = new HashMap<Ominaisuus, Double>();
	private Uniform uniform;
	private TapahtumanTyyppi status;
	private boolean palveltavana = false;

	public boolean isPalveltavana() {
		return palveltavana;
	}

	public void setPalveltavana(boolean palveltavana) {
		this.palveltavana = palveltavana;
	}

	public TapahtumanTyyppi getStatus() {
		return status;
	}

	public void setStatus(TapahtumanTyyppi status) {
		this.status = status;
	}

	public Double getOminaisuudet(Ominaisuus ominaisuus) {
		return ominaisuudet.get(ominaisuus);
	}

	// TODO: Lisää asiakkaan ominaisuudet.
	public enum Ominaisuus {
		MIELIALA, VARAKKUUS, UHKAROHKEUS, PAIHTYMYS
	};

	public Asiakas() {
		uniform = new Uniform(0.0000001, 1, System.currentTimeMillis());
		id = i++;

		for (int i = 0; i < Ominaisuus.values().length; i++) {
			Ominaisuus ominaisuus = Ominaisuus.values()[i];
			double randomLuku = uniform.sample();
			// System.out.println("randomLuku: " + randomLuku);
			ominaisuudet.put(ominaisuus, randomLuku);
		}

		saapumisaika = Kello.getInstance().getAika();
		Trace.out(Trace.Level.INFO, "Uusi asiakas nro " + id + " saapui klo " + saapumisaika);
	}
	
	public void setOminaisuus(Ominaisuus ominaisuus, Double arvo) {
		ominaisuudet.put(ominaisuus, arvo);
	}

	public double getPoistumisaika() {
		return poistumisaika;
	}

	public void setPoistumisaika(double poistumisaika) {
		this.poistumisaika = poistumisaika;
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
		output += "Päihtymys: " + ominaisuudet.get(Ominaisuus.PAIHTYMYS) + "\n";
		output += "Varakkuus: " + ominaisuudet.get(Ominaisuus.VARAKKUUS) + "\n";
		output += "Uhkarohkeus: " + ominaisuudet.get(Ominaisuus.UHKAROHKEUS) + "\n";
		output += "Mieliala: " + ominaisuudet.get(Ominaisuus.MIELIALA) + "\n";

		return output;
	}
}
