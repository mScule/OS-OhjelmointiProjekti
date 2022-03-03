import simu.framework.*;
import simu.framework.Trace.Level;
import simu.model.OmaMoottori;

public class MainTeksti { // Tekstipohjainen

	public static void main(String[] args) {

		Trace.setTraceLevel(Level.INFO);
		Moottori m = new OmaMoottori(null);
		m.setSimulointiaika(300);
		m.run();
	}
}
