package simu.model;

import java.util.Random;

import eduni.distributions.ContinuousGenerator;
import eduni.distributions.Uniform;
import simu.framework.Kello;
import simu.framework.Tapahtuma;
import simu.framework.Tapahtumalista;

public class Baari extends Palvelupiste {

	public Baari(ContinuousGenerator generator, Tapahtumalista tapahtumalista) {
		super(generator, tapahtumalista);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void aloitaPalvelu() {
		super.aloitaPalvelu();
		double palveluaika = generator.sample();
		Asiakas a = jono.peek();

		// int status = new Random().nextInt(0, TapahtumanTyyppi.values().length);
		int status = (int) super.uniform.sample();
		TapahtumanTyyppi tyyppi = TapahtumanTyyppi.values()[status];

		a.setStatus(tyyppi);
		tapahtumalista.lisaa(new Tapahtuma(tyyppi, Kello.getInstance().getAika() + palveluaika, getId()));
	}

}
