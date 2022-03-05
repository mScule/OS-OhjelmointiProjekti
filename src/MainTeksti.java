import simu.framework.*;
import simu.framework.Trace.Level;
import simu.model.IOmaMoottori;
import simu.model.OmaMoottori;
import simu.model.TapahtumanTyyppi;

public class MainTeksti { // Tekstipohjainen

	public static void main(String[] args) throws InterruptedException {

		Trace.setTraceLevel(Level.INFO);
		Moottori m = new OmaMoottori(null);
		m.setBlackjackTasapeliprosentti(0.08);
		m.setMainostusRahamaara(4000);
		m.setBlackjackVoittoprosentti(0.42);
		m.setMaxBet(10000);
		m.setMinBet(200);
		m.setYllapitoRahamaara(1000);
		m.lisaaPalvelupisteita(TapahtumanTyyppi.BAARI, 1);
		m.lisaaPalvelupisteita(TapahtumanTyyppi.PELI, 4);
		m.lisaaPalvelupisteita(TapahtumanTyyppi.ULOSKAYNTI, 1);
		m.lisaaPalvelupisteita(TapahtumanTyyppi.SISAANKAYNTI, 1);
		m.setSimulointiaika(100);
		m.start();
		m.join();
		System.out.println("m.getTulokset(): " + m.getTulokset()[IOmaMoottori.TULOS_KESKIM_PAIHTYNEISYYS]);
	}
}
