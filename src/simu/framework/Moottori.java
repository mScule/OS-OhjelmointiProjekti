package simu.framework;

import simu.model.Kasino;
import simu.model.Palvelupiste;
import simu.model.TapahtumanTyyppi;

import controller.IKontrolleriMtoV;

import java.util.LinkedList;
import java.util.Map;

public abstract class Moottori extends Thread implements IMoottori {

	private double simulointiaika = 0;
	private long viive = 0;

	private Kello kello;

	protected Tapahtumalista tapahtumalista;
	protected Map<TapahtumanTyyppi, LinkedList<Palvelupiste>> palvelupisteet;

	protected IKontrolleriMtoV kontrolleri;

	public Moottori(IKontrolleriMtoV kontrolleri) {

		kello = Kello.getInstance(); // Otetaan kello muuttujaan yksinkertaistamaan koodia

		tapahtumalista = new Tapahtumalista();

		this.kontrolleri = kontrolleri;
	}

	// Privaatit metodit

	private void suoritaBTapahtumat() {
		while (tapahtumalista.getSeuraavanAika() == kello.getAika()) {
			suoritaTapahtuma(tapahtumalista.poista());
		}
	}

	private void yritaCTapahtumat() {
		for (Map.Entry<TapahtumanTyyppi, LinkedList<Palvelupiste>> pisteet : palvelupisteet.entrySet()) {
			for (Palvelupiste p : pisteet.getValue()) {
				if (!p.onVarattu() && p.onJonossa()) {
					p.aloitaPalvelu();
				}
			}
		}
	}

	private double nykyaika() {
		return tapahtumalista.getSeuraavanAika();
	}

	private boolean simuloidaan() {
		if (Kasino.isVararikko()) {
			Trace.out(Trace.Level.INFO,"Kasino meni vararikkoon!!!");
			return false;
		}
		return kello.getAika() < simulointiaika;
	}

	private void viive() {
		Trace.out(Trace.Level.INFO, "Viive: " + viive);
		try {
			sleep(viive);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// IMoottori

	@Override
	public void setSimulointiaika(double aika) {
		simulointiaika = aika;
	}

	@Override
	public void setViive(long viive) {
		this.viive = viive;
	}

	@Override
	public long getViive() {
		return viive;
	}

	// Thread

	@Override
	public void run() {
		alustukset(); // luodaan mm. ensimmäinen tapahtuma
		while (simuloidaan()) {
			viive();

			Trace.out(Trace.Level.INFO, "\nA-vaihe: kello on " + nykyaika());
			kello.setAika(nykyaika());

			Trace.out(Trace.Level.INFO, "\nB-vaihe:");
			suoritaBTapahtumat();

			Trace.out(Trace.Level.INFO, "\nC-vaihe:");
			yritaCTapahtumat();

		}
		tulokset();
	}

	// Abstraktit metodit

	protected abstract void alustukset(); // Määritellään simu.model-pakkauksessa Moottorin aliluokassa

	protected abstract void suoritaTapahtuma(Tapahtuma t); // Määritellään simu.model-pakkauksessa Moottorin aliluokassa

	protected abstract void tulokset(); // Määritellään simu.model-pakkauksessa Moottorin aliluokassa

	public abstract void setYllapitoRahamaara(double rahamaara);

	public abstract void setMainostusRahamaara(double rahamaara);

	public abstract void lisaaPalvelupisteita(TapahtumanTyyppi palvelupisteTyyppi, int maara);

	public abstract double getBlackjackVoittoprosentti();

	public abstract void setBlackjackVoittoprosentti(double blackjackVoittoprosentti);

	public abstract double getBlackjackTasapeliprosentti();

	public abstract void setBlackjackTasapeliprosentti(double blackjackTasapeliprosentti);

	public abstract int getMinBet();

	public abstract void setMinBet(int minBet);

	public abstract int getMaxBet();

	public abstract void setMaxBet(int maxBet);

	public abstract double[] getTulokset();
}