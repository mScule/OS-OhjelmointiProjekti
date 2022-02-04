package simu.framework;

import simu.model.TapahtumanTyyppi;

public class Tapahtuma implements Comparable<Tapahtuma> {
	
	private TapahtumanTyyppi tyyppi, lahtoSijaintiTyypi;
	private int lahtoSijaintiID;

	private double aika;
	
	public Tapahtuma(TapahtumanTyyppi tyyppi, double aika, TapahtumanTyyppi lahtoSijaintiTyypi, int lahtoSijaintiID){
		this.tyyppi = tyyppi;
		this.aika = aika;
		this.lahtoSijaintiTyypi = lahtoSijaintiTyypi;
		this.lahtoSijaintiID = lahtoSijaintiID;
	}
	
	public void setTyyppi(TapahtumanTyyppi tyyppi) {
		this.tyyppi = tyyppi;
	}
	public TapahtumanTyyppi getTyyppi() {
		return tyyppi;
	}
	public void setAika(double aika) {
		this.aika = aika;
	}
	public double getAika() {
		return aika;
	}
	
	public void setlahtoSijaintiTyypi(TapahtumanTyyppi sijainti) {
		lahtoSijaintiTyypi = sijainti;
	}
	
	public TapahtumanTyyppi getlahtoSijaintiTyypi() {
		return lahtoSijaintiTyypi;
	}

	public int getLahtoSijaintiID() {
		return lahtoSijaintiID;
	}

	@Override
	public int compareTo(Tapahtuma arg) {
		if (this.aika < arg.aika) return -1;
		else if (this.aika > arg.aika) return 1;
		return 0;
	}
	
	
	

}
