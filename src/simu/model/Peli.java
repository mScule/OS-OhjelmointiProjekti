package simu.model;

import java.util.Random;

import eduni.distributions.ContinuousGenerator;
import simu.framework.Kello;
import simu.framework.Tapahtuma;
import simu.framework.Tapahtumalista;

public class Peli extends Palvelupiste {

	public Peli(ContinuousGenerator generator, Tapahtumalista tapahtumalista) {
		super(generator, tapahtumalista);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void aloitaPalvelu() {
		super.aloitaPalvelu();
		double palveluaika = generator.sample();
		Asiakas a = jono.peek();

		int status = super.getSample();
		// int status = new Random().nextInt(0, TapahtumanTyyppi.values().length);
		TapahtumanTyyppi tyyppi = TapahtumanTyyppi.values()[status];

		a.setStatus(tyyppi);
		tapahtumalista.lisaa(new Tapahtuma(tyyppi, Kello.getInstance().getAika() + palveluaika, 2));
	}
}
