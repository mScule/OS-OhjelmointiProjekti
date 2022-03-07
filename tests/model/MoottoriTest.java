package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;

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
import kasinoSimulaattori.simu.model.Kasino;
import kasinoSimulaattori.simu.model.OmaMoottori;
import kasinoSimulaattori.simu.model.Palvelupiste;
import kasinoSimulaattori.simu.model.Peli;
import kasinoSimulaattori.simu.model.TapahtumanTyyppi;

class MoottoriTest {

	private final double DELTA = 0.0000000000000001;
	Moottori m;

	@BeforeAll
	static void setTraceLevel() {
		Trace.setTraceLevel(Level.ERR);
	}

	@BeforeEach
	public void nollaaKello() {
		Kello.getInstance().setAika(0);
		m = new OmaMoottori(null);
	}

	@ParameterizedTest(name = "Tuleeko samat lopputulokset samoilla lähtöarvoilla")
	@CsvSource({ "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11" })
	void testaaSamatLoppuarvot(int luku) throws InterruptedException {
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
	@DisplayName("Testaa setSimulointiaika() negatiivinen argumentti")
	void testaaSetSimulointiaika() {
		IllegalArgumentException poikkeus = assertThrows(IllegalArgumentException.class,
				() -> m.setSimulointiaika(-1), "Negatiivinen argumentti ei tuottanut poikkeusta.");
		assertEquals("Valitse joku positiivinen double luku.", poikkeus.getMessage(), "Väärä virheilmoitus");
	}

	@Test
	@DisplayName("Testaa setMainostusRahamaara() negatiivinen argumentti")
	void testaaSetMainostusRahamaara() {
		IllegalArgumentException poikkeus = assertThrows(IllegalArgumentException.class,
				() -> m.setMainostusRahamaara(-1), "Negatiivinen argumentti ei tuottanut poikkeusta.");
		assertEquals("Valitse joku positiivinen double luku.", poikkeus.getMessage(), "Väärä virheilmoitus");
	}

	@Test
	@DisplayName("Testaa setMainostusRahamaara() toimiva argumentti")
	void testaaSetMainostusRahamaara2() {
		int i = 1;
		m.setMainostusRahamaara(i);
		assertEquals(i, Kasino.getMainoskulut());
	}

	@Test
	@DisplayName("Testaa setYllapitoRahamaara() negatiivinen argumentti")
	void testaaSetYllapitoRahamaara() {
		IllegalArgumentException poikkeus = assertThrows(IllegalArgumentException.class,
				() -> m.setYllapitoRahamaara(-1), "Negatiivinen argumentti ei tuottanut poikkeusta.");
		assertEquals("Valitse joku positiivinen double luku.", poikkeus.getMessage(), "Väärä virheilmoitus");
	}

	@Test
	@DisplayName("Testaa setYllapitoRahamaara() toimiva argumentti")
	void testaaSetYllapitoRahamaara2() {
		int i = 1;
		m.setYllapitoRahamaara(i);
		assertEquals(i, Kasino.getYllapitohinta());
	}

	@Test
	@DisplayName("Testaa setBlackjackVoittoprosentti() negatiivinen argumentti")
	void testaaSetBlackjackVoittoprosentti() {
		IllegalArgumentException poikkeus = assertThrows(IllegalArgumentException.class,
				() -> m.setBlackjackVoittoprosentti(-1), "Negatiivinen argumentti ei tuottanut poikkeusta.");
		assertEquals("Valitse joku double luku 0-1 väliltä.", poikkeus.getMessage(), "Väärä virheilmoitus");
	}

	@Test
	@DisplayName("Testaa setBlackjackVoittoprosentti() liian iso argumentti")
	void testaaSetBlackjackVoittoprosentti2() {
		IllegalArgumentException poikkeus = assertThrows(IllegalArgumentException.class,
				() -> m.setBlackjackVoittoprosentti(1.1), "Liian iso argumentti ei tuottanut poikkeusta.");
		assertEquals("Valitse joku double luku 0-1 väliltä.", poikkeus.getMessage(), "Väärä virheilmoitus");
	}

	@Test
	@DisplayName("Testaa setBlackjackVoittoprosentti() toimiva argumentti")
	void testaaSetBlackjackVoittoprosentti3() {
		int i = 1;
		m.setBlackjackVoittoprosentti(i);
		assertEquals(i, Kasino.getBlackjackVoittoprosentti());
	}

	@Test
	@DisplayName("Testaa setBlackjackTasapeliprosentti() negatiivinen argumentti")
	void testaaSetBlackjackTasapeliprosentti() {
		IllegalArgumentException poikkeus = assertThrows(IllegalArgumentException.class,
				() -> m.setBlackjackTasapeliprosentti(-1), "Negatiivinen argumentti ei tuottanut poikkeusta.");
		assertEquals("Valitse joku double luku 0-1 väliltä.", poikkeus.getMessage(), "Väärä virheilmoitus");
	}

	@Test
	@DisplayName("Testaa setBlackjackTasapeliprosentti() liian iso argumentti")
	void testaaSetBlackjackTasapeliprosentti2() {
		IllegalArgumentException poikkeus = assertThrows(IllegalArgumentException.class,
				() -> m.setBlackjackTasapeliprosentti(1.1), "Liian iso argumentti ei tuottanut poikkeusta.");
		assertEquals("Valitse joku double luku 0-1 väliltä.", poikkeus.getMessage(), "Väärä virheilmoitus");
	}

	@Test
	@DisplayName("Testaa setBlackjackTasapeliprosentti() toimiva argumentti")
	void testaaSetBlackjackTasapeliprosentti3() {
		int i = 1;
		m.setBlackjackTasapeliprosentti(i);
		assertEquals(i, Kasino.getBlackjackTasapeliprosentti());
	}

	@Test
	@DisplayName("Testaa setMaxBet() negatiivinen argumentti")
	void testaaSetMaxBet() {
		IllegalArgumentException poikkeus = assertThrows(IllegalArgumentException.class,
				() -> m.setMaxBet(-1), "Negatiivinen argumentti ei tuottanut poikkeusta.");
		assertEquals("Valitse joku positiivinen int luku.", poikkeus.getMessage(), "Väärä virheilmoitus");
	}

	@Test
	@DisplayName("Testaa setMaxBet() toimiva argumentti")
	void testaaSetMaxBet2() {
		int i = 2000;
		m.setMaxBet(2000);
		assertEquals(i, Kasino.getMaxBet());
	}

	@Test
	@DisplayName("Testaa setMinBet() negatiivinen argumentti")
	void testaaSetMinBet() {
		IllegalArgumentException poikkeus = assertThrows(IllegalArgumentException.class,
				() -> m.setMinBet(-1), "Negatiivinen argumentti ei tuottanut poikkeusta.");
		assertEquals("Valitse joku positiivinen int luku.", poikkeus.getMessage(), "Väärä virheilmoitus");
	}

	@Test
	@DisplayName("Testaa setMinBet() toimiva argumentti")
	void testaaSetMinBet2() {
		int i = 2000;
		m.setMinBet(2000);
		assertEquals(i, Kasino.getMinBet());
	}

	@Test
	@DisplayName("Testaa lisaaPalvelupisteita() negatiivinen argumentti")
	void testaaLisaaPalvelupisteita() {
		IllegalArgumentException poikkeus = assertThrows(IllegalArgumentException.class,
				() -> m.lisaaPalvelupisteita(TapahtumanTyyppi.PELI, -1),
				"Negatiivinen argumentti ei tuottanut poikkeusta.");
		assertEquals("Valitse joku positiivinen int luku.", poikkeus.getMessage(), "Väärä virheilmoitus");
	}

	@Test
	@DisplayName("Testaa lisaaPalvelupisteita() oikea määrä ja oikeat tyypit")
	void testaaLisaaPalvelupisteita2() {
		m.lisaaPalvelupisteita(TapahtumanTyyppi.PELI, 10);

		LinkedList<Palvelupiste> pelit = m.getPalvelupisteet(3);

		assertEquals(pelit.size(), 11, "Väärä määrä palvelupisteitä.");

		for (int i = 0; i < pelit.size(); i++) {
			assertEquals(pelit.get(i).getClass(), Peli.class, "Väärä palvelupiste tyyppi.");
		}
	}
}
