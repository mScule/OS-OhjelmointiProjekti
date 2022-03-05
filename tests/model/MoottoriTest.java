package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import simu.framework.Moottori;
import simu.framework.Trace;
import simu.framework.Trace.Level;
import simu.model.IOmaMoottori;
import simu.model.OmaMoottori;
import simu.model.TapahtumanTyyppi;

class MoottoriTest {

	private final double DELTA = 0.001;

	@Test
	@DisplayName("Test")
	void test() throws InterruptedException {
		Trace.setTraceLevel(Level.ERR);
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
		// System.out.println("m.getTulokset(): " + m.getTulokset()[IOmaMoottori.TULOS_KESKIM_PAIHTYNEISYYS]);
		assertEquals(0.5847673229652299, m.getTulokset()[IOmaMoottori.TULOS_KESKIM_PAIHTYNEISYYS], DELTA,
				("Eri tulos"));
	}

	@Test
	@DisplayName("Test2")
	void test2() throws InterruptedException {
		Trace.setTraceLevel(Level.ERR);
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
		// System.out.println("m.getTulokset(): " + m.getTulokset()[IOmaMoottori.TULOS_KESKIM_PAIHTYNEISYYS]);
		assertEquals(0.5847673229652299, m.getTulokset()[IOmaMoottori.TULOS_KESKIM_PAIHTYNEISYYS], DELTA,
				("Eri tulos"));
	}

}
