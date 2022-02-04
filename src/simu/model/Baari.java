package simu.model;

import eduni.distributions.ContinuousGenerator;
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

		
		// Arvotaan tapahtumantyyppi (Muu kuin sisäänkäynti)
		TapahtumanTyyppi tyyppi = arvoTapahtuma();

		a.setStatus(tyyppi);
		tapahtumalista.lisaa(new Tapahtuma(tyyppi, Kello.getInstance().getAika() + palveluaika, TapahtumanTyyppi.BAARI, getId()));
	}

}
