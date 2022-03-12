package kasinoSimulaattori.simu.framework;

import java.util.LinkedList;
import java.util.Map;

import kasinoSimulaattori.controller.IKontrolleriMtoV;
import kasinoSimulaattori.simu.framework.Trace.Level;
import kasinoSimulaattori.simu.model.Kasino;
import kasinoSimulaattori.simu.model.Palvelupiste;
import kasinoSimulaattori.simu.model.TapahtumanTyyppi;

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

	public boolean simuloidaan() {
		if (Kasino.isVararikko()) {
			Trace.out(Trace.Level.INFO, "Kasino meni vararikkoon!!!");
			return false;
		}
		return kello.getAika() < simulointiaika;
	}

	private void viive() {
		Trace.out(Trace.Level.INFO, "Viive: " + viive);
		try {
			sleep(viive);
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		}
	}

	// IMoottori

	@Override
	public void setSimulointiaika(double aika) {
		tarkistaDoubleLuku(aika);
		simulointiaika = aika;
	}

	@Override
	public void setViive(long viive) {
		tarkistaLongLuku(viive);
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
			if (Kasino.isPause()) {
				pause();
			}

			viive();

			Trace.out(Trace.Level.INFO, "\nA-vaihe: kello on " + nykyaika());
			kello.setAika(nykyaika());

			Trace.out(Trace.Level.INFO, "\nB-vaihe:");
			suoritaBTapahtumat();

			Trace.out(Trace.Level.INFO, "\nC-vaihe:");
			yritaCTapahtumat();

		}
		tulokset();
		lopetus();
	}

	public void tarkistaDoubleProsenttiluku(double luku) {
		if (!(luku <= 1) || !(luku > 0)) {
			throw new IllegalArgumentException("Valitse joku double luku 0-1 väliltä.");
		}
	}

	public void tarkistaDoubleLuku(double luku) {
		if (luku < 0)
			throw new IllegalArgumentException("Valitse joku positiivinen double luku.");
	}

	public void tarkistaIntLuku(int luku) {
		if (luku < 0) {
			throw new IllegalArgumentException("Valitse joku positiivinen int luku.");
		}
	}

	public void tarkistaLongLuku(long luku) {
		if (luku < 0) {
			throw new IllegalArgumentException("Valitse joku positiivinen long luku.");
		}
	}

	public synchronized void pause() {
		try {
			wait();
		} catch (InterruptedException e) {
			Trace.out(Level.ERR, e.getMessage());
		}
	}

	public synchronized void notifyThis() {
		notify();
	}

	// Abstraktit metodit
	
	protected abstract void alustukset(); // Määritellään simu.model-pakkauksessa Moottorin aliluokassa
	
	protected abstract void lopetus();

	protected abstract void suoritaTapahtuma(Tapahtuma t); // Määritellään simu.model-pakkauksessa Moottorin aliluokassa

	protected abstract void tulokset(); // Määritellään simu.model-pakkauksessa Moottorin aliluokassa

	public abstract void setYllapitoRahamaara(double rahamaara);

	public abstract void setMainostusRahamaara(double rahamaara);

	public abstract void lisaaPalvelupisteita(TapahtumanTyyppi palvelupisteTyyppi, int maara);

	public abstract double getBlackjackVoittoprosentti();

	public abstract void setBlackjackVoittoprosentti(double blackjackVoittoprosentti);

	public abstract double getBlackjackTasapeliprosentti();

	public abstract void setBlackjackTasapeliprosentti(double blackjackTasapeliprosentti);

	public abstract double getMinBet();

	public abstract void setMinBet(double minBet);

	public abstract double getMaxBet();

	public abstract void setMaxBet(double maxBet);

	public abstract double[] getTulokset();

	public abstract LinkedList<Palvelupiste> getPalvelupisteet(int palvelu);

	public abstract boolean getKasinoPause();

	public abstract void setKasinoPause(boolean pause);
}