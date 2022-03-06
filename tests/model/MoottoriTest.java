package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import kasinoSimulaattori.simu.framework.Kello;
import kasinoSimulaattori.simu.framework.Moottori;
import kasinoSimulaattori.simu.framework.Trace;
import kasinoSimulaattori.simu.framework.Trace.Level;
import kasinoSimulaattori.simu.model.IOmaMoottori;
import kasinoSimulaattori.simu.model.OmaMoottori;
import kasinoSimulaattori.simu.model.TapahtumanTyyppi;

class MoottoriTest {

	private final double DELTA = 0.0000000000000001;
	Moottori m;

	@BeforeAll
	static void setTraceLevel(){
		Trace.setTraceLevel(Level.ERR);
	}

	@BeforeEach
	public void nollaaKello() {
		Kello.getInstance().setAika(0);
		m = new OmaMoottori(null);
	}

	@ParameterizedTest(name = "Tuleeko sama tulos samoilla lähtöarvoilla")
	@CsvSource({ "0","1","2","3","4","5","6","7","8","9","10","11" })
	void SamatLoppuarvotTesti(int luku) throws InterruptedException {
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

		double tulos1 = m.getTulokset()[luku];

		Kello.getInstance().setAika(0);

		Moottori m2 = new OmaMoottori(null);
		m2.setBlackjackTasapeliprosentti(0.08);
		m2.setMainostusRahamaara(4000);
		m2.setBlackjackVoittoprosentti(0.42);
		m2.setMaxBet(10000);
		m2.setMinBet(200);
		m2.setYllapitoRahamaara(1000);
		m2.lisaaPalvelupisteita(TapahtumanTyyppi.BAARI, 1);
		m2.lisaaPalvelupisteita(TapahtumanTyyppi.PELI, 4);
		m2.lisaaPalvelupisteita(TapahtumanTyyppi.ULOSKAYNTI, 1);
		m2.lisaaPalvelupisteita(TapahtumanTyyppi.SISAANKAYNTI, 1);
		m2.setSimulointiaika(100);
		m2.start();
		m2.join();

		double tulos2 = m2.getTulokset()[luku];
		
		System.out.println("m.getTulokset()[" + luku + "]: " + tulos1);
		System.out.println("m2.getTulokset()[" + luku + "]: " + tulos2);

		assertEquals(tulos1, tulos2, DELTA, ("Eri tulos"));
	}

	@Test
	@DisplayName("setBlackjackTasapeliprosentti")
	void setBlackjackTasapeliprosenttiLegalArgument(){
		// IllegalArgumentException poikkeus = assertThrows(IllegalArgumentException)
	}

}
