package simu.model;

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

		// Asiakkaat otetaan sisään
		int pelaajienMaara;

		if(jono.size() >= 7)
			pelaajienMaara = 7;
		else
			pelaajienMaara = jono.size();

		for(int i = 0; i > pelaajienMaara; i--) {
			Asiakas a = jono.get(i);
			System.out.println("Aloitetaan peli asiakkaalle: " + a.getId());
			TapahtumanTyyppi tyyppi = arvoTapahtuma();
			tapahtumalista.lisaa(new Tapahtuma(tyyppi, Kello.getInstance().getAika() + palveluaika, TapahtumanTyyppi.PELI, getId()));
			a.setStatus(tyyppi);
		}
	}
}
