package simu.framework;

import eduni.distributions.*;
import simu.model.Kasino;
import simu.model.TapahtumanTyyppi;

public class Saapumisprosessi {

	private ContinuousGenerator generaattori;
	private Tapahtumalista tapahtumalista;
	private TapahtumanTyyppi tyyppi;

	public Saapumisprosessi(ContinuousGenerator g, Tapahtumalista tl, TapahtumanTyyppi tyyppi) {
		this.generaattori = g;
		this.tapahtumalista = tl;
		this.tyyppi = tyyppi;
	}

	public void generoiSeuraava() {
		Tapahtuma t = new Tapahtuma(tyyppi, Kello.getInstance().getAika() + generaattori.sample(), null, 0, 0);
		tapahtumalista.lisaa(t);
	}

	public void setKeskimSaapumisvaliaika(double keskimSaapumisaika){
		Negexp uusiGeneraattori = new Negexp(keskimSaapumisaika, Kasino.getSeed());
		generaattori = uusiGeneraattori;
	}
}
