package simu.framework;

import java.util.PriorityQueue;

public class Tapahtumalista {
	private PriorityQueue<Tapahtuma> lista = new PriorityQueue<Tapahtuma>();

	public Tapahtumalista() {

	}

	public Tapahtuma poista() {
		Trace.out(Trace.Level.INFO,
				"Tapahtumalistasta poistetaan " + lista.peek().getTyyppiPaamaara() + " " + lista.peek().getAika()
						+ ", asiakas " + lista.peek().getPalveltavanAsiakkaanID());
		return lista.remove();
	}

	public void lisaa(Tapahtuma t) {
		Trace.out(Trace.Level.INFO, "Tapahtumalistaan lisätään uusi " + t.getTyyppiPaamaara() + " " + t.getAika() +
				", asiakas " + t.getPalveltavanAsiakkaanID());
		lista.add(t);
	}

	public double getSeuraavanAika() {
		return lista.peek().getAika();
	}
}
