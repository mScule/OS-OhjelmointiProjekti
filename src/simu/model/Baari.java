package simu.model;

import eduni.distributions.ContinuousGenerator;
import eduni.distributions.Normal;
import simu.framework.Kello;
import simu.framework.Tapahtuma;
import simu.framework.Tapahtumalista;
import simu.model.Asiakas.Ominaisuus;

public class Baari extends Palvelupiste {

	public Baari(ContinuousGenerator generator, Tapahtumalista tapahtumalista) {
		super(generator, tapahtumalista);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void aloitaPalvelu() {
		varattu = true;
		super.aloitaPalvelu();
		double palveluaika = generator.sample();
		Asiakas a = jono.peek();

		// a.setOminaisuus(Ominaisuus.PAIHTYMYS, a.getOminaisuudet(Ominaisuus.PAIHTYMYS) + new Normal(0.5, 0.5).sample());
		
		// Arvotaan tapahtumantyyppi (Muu kuin sisäänkäynti)
		TapahtumanTyyppi tyyppi = arvoTapahtuma();

		a.setStatus(tyyppi);
		tapahtumalista.lisaa(new Tapahtuma(tyyppi, Kello.getInstance().getAika() + palveluaika, TapahtumanTyyppi.BAARI, getId(), a.getId()));
	}

}
