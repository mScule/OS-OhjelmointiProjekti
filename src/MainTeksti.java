import simu.framework.*;
import simu.framework.Trace.Level;
import simu.model.OmaMoottori;
import simu.model.TapahtumanTyyppi;

public class MainTeksti { // Tekstipohjainen

	public static void main(String[] args) {

		Trace.setTraceLevel(Level.INFO);
		Moottori m = new OmaMoottori(null);
		m.setYllapitoRahamaara(1000);
		m.setMainostusRahamaara(1000);
		m.lisaaPalvelupisteita(TapahtumanTyyppi.BAARI, 1);
		m.lisaaPalvelupisteita(TapahtumanTyyppi.PELI, 4);
		m.lisaaPalvelupisteita(TapahtumanTyyppi.ULOSKAYNTI, 1);
		m.lisaaPalvelupisteita(TapahtumanTyyppi.SISAANKAYNTI, 1);
		m.setSimulointiaika(1000);
		m.run();
	}
}
