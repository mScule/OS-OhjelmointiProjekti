package simu.model;
import java.util.Random;
import eduni.distributions.ContinuousGenerator;
import simu.framework.Kello;
import simu.framework.Tapahtuma;
import simu.framework.Tapahtumalista;

public class Uloskaynti extends Palvelupiste {

	public Uloskaynti(ContinuousGenerator generator, Tapahtumalista tapahtumalista) {
		super(generator, tapahtumalista);
	}
	
	@Override
	public void aloitaPalvelu() {
		super.aloitaPalvelu();
		double palveluaika = generator.sample();
		Asiakas a = jono.peek();
		
		// int status = new Random().nextInt(1, TapahtumanTyyppi.values().length);
		int status = super.getSample();
		TapahtumanTyyppi tyyppi = TapahtumanTyyppi.values()[status];

		a.setStatus(tyyppi);
		tapahtumalista.lisaa(new Tapahtuma(tyyppi,Kello.getInstance().getAika()+palveluaika, 3));
	}
}
