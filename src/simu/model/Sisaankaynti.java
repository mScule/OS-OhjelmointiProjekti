package simu.model;
import eduni.distributions.ContinuousGenerator;
import simu.framework.Kello;
import simu.framework.Tapahtuma;
import simu.framework.Tapahtumalista;

public class Sisaankaynti extends Palvelupiste {

	public Sisaankaynti(ContinuousGenerator generator, Tapahtumalista tapahtumalista) {
		super(generator, tapahtumalista);
	}
	
	@Override
	public void aloitaPalvelu() {
		varattu = true;
		super.aloitaPalvelu();
		double palveluaika = generator.sample();
		Asiakas a = jono.peek();
		
		// int status = new Random().nextInt(1, TapahtumanTyyppi.values().length);
		TapahtumanTyyppi tyyppi = arvoTapahtuma();

		a.setStatus(tyyppi);
		
		tapahtumalista.lisaa(new Tapahtuma(tyyppi,Kello.getInstance().getAika()+palveluaika, TapahtumanTyyppi.SISAANKAYNTI, getId(), a.getId()));
	}
}
