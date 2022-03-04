import simu.framework.*;
import simu.framework.Trace.Level;
import simu.model.OmaMoottori;
import simu.model.TapahtumanTyyppi;

public class MainTeksti { // Tekstipohjainen

	public static void main(String[] args) {

		Trace.setTraceLevel(Level.INFO);
		Moottori m = new OmaMoottori(null);
		m.setYllapitoRahamaara(1000);
		m.lisaaPalvelupisteita(TapahtumanTyyppi.BAARI, 1);
		m.lisaaPalvelupisteita(TapahtumanTyyppi.PELI, 4);
		m.lisaaPalvelupisteita(TapahtumanTyyppi.ULOSKAYNTI, 1);
		m.lisaaPalvelupisteita(TapahtumanTyyppi.SISAANKAYNTI, 1);
		m.setBlackjackTasapeliprosentti(0.08);
		m.setBlackjackVoittoprosentti(0.42);
		m.setMaxBet(10000);
		m.setMinBet(30);
		//Pitää tällä hetkellä säätää viimeisenä, koska ainoa metodi millä säädetään saapumisväliaikoja
		m.setMainostusRahamaara(1000);
		m.setSimulointiaika(1000);
		m.run();
	}
}
