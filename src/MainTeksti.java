import simu.framework.*;
import simu.framework.Trace.Level;
import simu.model.OmaMoottori;

public class MainTeksti { // Tekstipohjainen

	public static void main(String[] args) {

		Trace.setTraceLevel(Level.INFO);
		Moottori m = new OmaMoottori(null);
		m.setYllapitoRahamaara(3000);
		m.setSimulointiaika(200);
		m.run();
	}
}
