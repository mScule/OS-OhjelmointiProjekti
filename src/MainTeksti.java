import simu.framework.*;
import simu.framework.Trace.Level;
import simu.model.OmaMoottori;

public class MainTeksti { // Tekstipohjainen

	public static void main(String[] args) {

		Trace.setTraceLevel(Level.INFO);
		Moottori m = new OmaMoottori(null);
		m.setYllapitoRahamaara(100);
		m.setMainostusRahamaara(0);
		m.setSimulointiaika(1000);
		m.run();
	}
}
