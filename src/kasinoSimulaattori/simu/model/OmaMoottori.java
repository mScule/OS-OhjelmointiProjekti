package kasinoSimulaattori.simu.model;

import kasinoSimulaattori.controller.IKontrolleriMtoV;
import kasinoSimulaattori.eduni.distributions.Negexp;
import kasinoSimulaattori.eduni.distributions.Uniform;
import kasinoSimulaattori.simu.framework.Kello;
import kasinoSimulaattori.simu.framework.Moottori;
import kasinoSimulaattori.simu.framework.Saapumisprosessi;
import kasinoSimulaattori.simu.framework.Tapahtuma;
import kasinoSimulaattori.simu.framework.Trace;
import kasinoSimulaattori.simu.framework.Trace.Level;
import kasinoSimulaattori.simu.model.Asiakas.Ominaisuus;
import kasinoSimulaattori.util.Sijainti;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javafx.application.Platform;

/**
 * Moottori, joka pyörittää simulaatiota kolmivaiheisesti.
 * 
 * @author Jonathan Methuen, Vilhelm Niemi
 */
public class OmaMoottori extends Moottori implements IOmaMoottori {

	private Kello kello = Kello.getInstance();

	private Saapumisprosessi saapumisprosessi;

	private List<Asiakas> asiakkaatKasinolla = Collections.synchronizedList(new LinkedList<Asiakas>());

	private double[] tulokset = new double[IOmaMoottori.TULOSTEN_MAARA];

	private int saapuneidenAsiakkaidenMaara = 0, poistuneidenAsiakkaidenMaara = 0;

	private double poistuneidenAsiakKokMielentila;
	private double poistuneidenAsiakKokVarakkuus;
	private double poistuneidenAsiakKokUhkarohkeus;
	private double poistuneidenAsiakKokPaihtyneisyys;

	private double ajanjaksoltaKulutMaksettu = 0;

	private double poistumisajatSummattuna = 0.0;

	private Sijainti baariSijainti = new Sijainti(1 * 128, 1 * 128), blackjackSijainti = new Sijainti(5 * 128, 1 * 128),
			sisaankayntiSijainti = new Sijainti(2 * 128, 4 * 128), uloskayntiSijainti = new Sijainti(4 * 128, 4 * 128);

	private KasinoDAO kasinoDAO = KasinoDAO.getInstanssi();
	private boolean tietokantayhteys = false;

	/**
	 * OmaMoottori konstruktori metodi
	 * 
	 * @param kontrolleri viite kontrolleriin
	 */
	public OmaMoottori(IKontrolleriMtoV kontrolleri) {
		super(kontrolleri);

		if (kontrolleri != null) {
			testaaTietokantaYhteys();
		}

		Kasino.resetKasino();

		palvelupisteet = new HashMap<TapahtumanTyyppi, LinkedList<Palvelupiste>>();

		palvelupisteet.put(TapahtumanTyyppi.SISAANKAYNTI, new LinkedList<Palvelupiste>());
		palvelupisteet.put(TapahtumanTyyppi.ULOSKAYNTI, new LinkedList<Palvelupiste>());
		palvelupisteet.put(TapahtumanTyyppi.BAARI, new LinkedList<Palvelupiste>());
		palvelupisteet.put(TapahtumanTyyppi.PELI, new LinkedList<Palvelupiste>());

		palvelupisteet.get(TapahtumanTyyppi.SISAANKAYNTI)
				.add(new Sisaankaynti(Kasino.defaultPalveluajatNegexp, tapahtumalista, sisaankayntiSijainti,
						new Uniform(3, TapahtumanTyyppi.values().length, Kasino.getSeed())));
		palvelupisteet.get(TapahtumanTyyppi.ULOSKAYNTI).add(new Uloskaynti(Kasino.defaultPalveluajatNegexp,
				tapahtumalista, uloskayntiSijainti, new Uniform(1, 2, Kasino.getSeed())));
		palvelupisteet.get(TapahtumanTyyppi.BAARI).add(new Baari(Kasino.defaultPalveluajatNegexp, tapahtumalista,
				baariSijainti, new Uniform(2, TapahtumanTyyppi.values().length, Kasino.getSeed())));
		palvelupisteet.get(TapahtumanTyyppi.PELI).add(new Peli(Kasino.defaultPalveluajatNegexp, tapahtumalista,
				blackjackSijainti, new Uniform(2, TapahtumanTyyppi.values().length, Kasino.getSeed())));

		saapumisprosessi = new Saapumisprosessi(Kasino.defaultSaapumisajatNegexp, tapahtumalista,
				TapahtumanTyyppi.SISAANKAYNTI);
	}

	/**
	 * Aseta kasinon ylläpitoon käytettävä rahamäärä, eli joku positiivinen double
	 * luku. Se mitä isompi rahamäärä on, sitä lyhyemmät kasinon palvelupisteiden
	 * keskimääräiset palveluajat ovat.
	 * 
	 * @param rahamaara kasinon ylläpitoon käytettävä rahamäärä
	 */
	public void setYllapitoRahamaara(double rahamaara) {
		tarkistaDoubleLuku(rahamaara, "Valitse ylläpidon rahamääräksi joku positiivinen double luku.");

		Kasino.setYllapitohinta(rahamaara);

		double keskimPalveluaika = Kasino.defaultKeskimPalveluaika
				/ ((rahamaara / Kasino.investmentInefficiencyRatio) + 1);

		Kasino.setKeskimPalveluaika(keskimPalveluaika);

		Trace.out(Trace.Level.INFO, "keskimPalveluaika: " + keskimPalveluaika);

		for (Map.Entry<TapahtumanTyyppi, LinkedList<Palvelupiste>> pisteet : palvelupisteet.entrySet()) {
			for (Palvelupiste p : pisteet.getValue()) {
				p.setKeskimPalveluaika(keskimPalveluaika);
			}
		}
	}

	/**
	 * Aseta kasinon mainostukseen käytettävä rahamäärä, eli joku positiivinen
	 * double luku. Se mitä isompi rahamäärä on, sitä tiheämpään tahtiin kasinolle
	 * saapuu asiakkaita.
	 * 
	 * @param rahamaara kasinon mainostukseen käytettävä rahamäärä
	 */
	public void setMainostusRahamaara(double rahamaara) {
		tarkistaDoubleLuku(rahamaara, "Valitse mainostuksen rahamääräksi joku positiivinen double luku.");

		Kasino.setMainoskulut(rahamaara);

		double keskimSaapumisvaliaika = Kasino.defaultKeskimSaapumisaika
				/ ((rahamaara / Kasino.investmentInefficiencyRatio) + 1);

		Kasino.setKeskimSaapumisvaliaika(keskimSaapumisvaliaika);

		Trace.out(Trace.Level.INFO, "keskimSaapumisvaliaika: " + keskimSaapumisvaliaika);
	}

	/**
	 * Lisää kasinoon palvelupisteitä. Palvelupisteiden määrä ja tyypit kasvattavat
	 * kasinon kuluja.
	 * 
	 * @param palvelupisteTyyppi lisättävän palvelupisteen tyyppi
	 * @param maara              lisättävien palvelupisteiden määrä
	 */
	public void lisaaPalvelupisteita(TapahtumanTyyppi palvelupisteTyyppi, int maara) {

		switch (palvelupisteTyyppi) {
		case SISAANKAYNTI:
			tarkistaIntLuku(maara,
					"Valitse lisättävien sisäänkäyntien lukumääräksi nolla tai joku positiivinen int luku.");

			for (int i = 0; i < maara; i++) {

				palvelupisteet.get(TapahtumanTyyppi.SISAANKAYNTI)
						.add(new Sisaankaynti(new Negexp(Kasino.getKeskimPalveluaika(), Kasino.getSeed()),
								tapahtumalista, sisaankayntiSijainti,
								new Uniform(3, TapahtumanTyyppi.values().length, Kasino.getSeed())));
			}
			Kasino.setYllapitohinta(Kasino.getYllapitohinta() + (Kasino.sisaankaynninHinta * maara));
			break;

		case ULOSKAYNTI:
			tarkistaIntLuku(maara,
					"Valitse lisättävien uloskäyntien lukumääräksi nolla tai joku positiivinen int luku.");

			for (int i = 0; i < maara; i++) {

				palvelupisteet.get(TapahtumanTyyppi.ULOSKAYNTI)
						.add(new Uloskaynti(new Negexp(Kasino.getKeskimPalveluaika(), Kasino.getSeed()), tapahtumalista,
								uloskayntiSijainti, new Uniform(1, 2, Kasino.getSeed())));
			}
			Kasino.setYllapitohinta(Kasino.getYllapitohinta() + (Kasino.uloskaynninHinta * maara));
			break;

		case BAARI:
			tarkistaIntLuku(maara, "Valitse lisättävien baarien lukumääräksi nolla tai joku positiivinen int luku.");

			for (int i = 0; i < maara; i++) {
				palvelupisteet.get(TapahtumanTyyppi.BAARI)
						.add(new Baari(new Negexp(Kasino.getKeskimPalveluaika(), Kasino.getSeed()), tapahtumalista,
								baariSijainti, new Uniform(2, TapahtumanTyyppi.values().length, Kasino.getSeed())));
			}
			Kasino.setYllapitohinta(Kasino.getYllapitohinta() + (Kasino.baarinHinta * maara));
			break;

		case PELI:
			tarkistaIntLuku(maara,
					"Valitse lisättävien blackjack pöytien lukumääräksi nolla tai joku positiivinen int luku.");

			for (int i = 0; i < maara; i++) {
				palvelupisteet.get(TapahtumanTyyppi.PELI)
						.add(new Peli(new Negexp(Kasino.getKeskimPalveluaika(), Kasino.getSeed()), tapahtumalista,
								blackjackSijainti, new Uniform(2, TapahtumanTyyppi.values().length, Kasino.getSeed())));
			}
			Kasino.setYllapitohinta(Kasino.getYllapitohinta() + (Kasino.pelipoydanHinta * maara));
			break;

		default:
			System.err.println("Palvelupiste tyyppiä ei löytynyt.");
			break;
		}
	}

	/**
	 * Laske asiakkaiden keskimääräinen saapumiväliaika ja luo ensimmäisen asiakkaan
	 * saapuminen järjestelmään.
	 */
	@Override
	protected void alustukset() {
		saapumisprosessi.calculateKeskimSaapumisaika(); // Lasketaan asiakkaiden keskimääräinen saapumiväliaika
		saapumisprosessi.generoiSeuraava(); // Ensimmäinen saapuminen järjestelmään
	}

	/**
	 * Suorita vuorossa oleva tapahtuma ja päivitä simulaation visualisointia ja
	 * GUI:ta.
	 */
	@Override
	protected void suoritaTapahtuma(Tapahtuma t) { // B-vaiheen tapahtumat

		Asiakas a;

		// Kasino menettää ylläpidon hinnan verran rahaa joka 50 aikayksikössä
		double kulut = ((t.getAika() - ajanjaksoltaKulutMaksettu) / 50) * (Kasino.getKokoYllapitohinta());

		Trace.out(Trace.Level.INFO, "Kasino.loseMoney: " + kulut);

		Kasino.loseMoney(kulut);
		ajanjaksoltaKulutMaksettu = t.getAika();

		// Palvelupisteet haetaan tyypin mukaan
		TapahtumanTyyppi thisTyyppi = t.getTyypiLahtoSijainti();
		TapahtumanTyyppi seuraavanTapahtumanTyyppi = t.getTyyppiPaamaara();

		// Sijaintien alustus asiakkaan liikkumisanimaatiota varten
		Sijainti nykysijainti, loppusijainti;

		if (seuraavanTapahtumanTyyppi != TapahtumanTyyppi.SISAANKAYNTI) {
			// Haetaan nykyinen palvelupiste, josta haetaan asiakas
			Palvelupiste thisPalvelupiste = null;
			for (Palvelupiste p : palvelupisteet.get(thisTyyppi)) {
				if (t.getLahtoSijaintiID() == p.getId())
					thisPalvelupiste = p;
			}

			// Jos tapahtuma on palvelupisteestä siirtyminen toiseen paikkaan, poista
			// asiakas pelipisteen jonosta tapahtumaan liittyvän asiakkaan ID:n mukaan.
			// Muissa tapauksissa poista jonon ensimmäinen asiakas.
			if (thisTyyppi == TapahtumanTyyppi.PELI) {
				a = thisPalvelupiste.otaJonostaIDnMukaan(t.getPalveltavanAsiakkaanID());
			} else {
				a = thisPalvelupiste.otaJonosta();
			}

			nykysijainti = thisPalvelupiste.getSijainti();

			// Jos asiakas ei ole poistumassa, viedään asiakas haluttun tyyppiseen
			// pisteeseen jossa on lyhyin jono.
			// Jos asiakas poistuu, ei lisätä asiakasta enää uudelleen mihinkään jonoon ja
			// kasvatetaan kirjanpidossa poistuneiden asiakkaiden lukumäärää.
			if (seuraavanTapahtumanTyyppi != TapahtumanTyyppi.POISTUMINEN) {
				LinkedList<Palvelupiste> pisteet = palvelupisteet.get(seuraavanTapahtumanTyyppi);
				int lyhyinJono = pisteet.get(0).jono.size(), lyhyinIndex = 0;
				for (int i = 1; i < pisteet.size(); i++) {
					if (pisteet.get(i).jono.size() < lyhyinJono) {
						lyhyinJono = pisteet.get(i).jono.size();
						lyhyinIndex = i;
					}
				}
				pisteet.get(lyhyinIndex).lisaaJonoon(a);

				// Minne asiakas kävelee seuraavaksi
				loppusijainti = pisteet.get(lyhyinIndex).getSijainti();

			} else {
				// Asiakas poistuu kasinolta
				Trace.out(Trace.Level.INFO, "Asiakas " + a.getId() + " poistuu kasinolta.");
				Trace.out(Trace.Level.INFO, a);

				loppusijainti = uloskayntiSijainti;

				// Poista asiakas kasinolla oleskelevien asiakkaiden listasta.
				synchronized (asiakkaatKasinolla) {
					for (int i = 0; i < asiakkaatKasinolla.size(); i++) {
						if (asiakkaatKasinolla.get(i).getId() == a.getId()) {
							asiakkaatKasinolla.remove(i);
						}
					}
				}

				poistuneidenAsiakKokMielentila += a.getOminaisuus(Ominaisuus.MIELIALA);
				poistuneidenAsiakKokVarakkuus += a.getOminaisuus(Ominaisuus.VARAKKUUS);
				poistuneidenAsiakKokUhkarohkeus += a.getOminaisuus(Ominaisuus.UHKAROHKEUS);
				poistuneidenAsiakKokPaihtyneisyys += a.getOminaisuus(Ominaisuus.PAIHTYMYS);

				poistuneidenAsiakkaidenMaara++;
				poistumisajatSummattuna += a.getPoistumisaika();
			}
		} else {
			Asiakas uusiA = new Asiakas();
			Trace.out(Trace.Level.INFO, uusiA);
			synchronized (asiakkaatKasinolla) {
				asiakkaatKasinolla.add(uusiA);
			}

			nykysijainti = palvelupisteet.get(TapahtumanTyyppi.SISAANKAYNTI).get(0).getSijainti();

			// Laita asiakas sisäänkäyntiin, jossa on pienin jono
			LinkedList<Palvelupiste> pisteet = palvelupisteet.get(TapahtumanTyyppi.SISAANKAYNTI);
			int lyhyinJono = pisteet.get(0).jono.size(), lyhyinIndex = 0;
			for (int i = 1; i < pisteet.size(); i++) {
				if (pisteet.get(i).jono.size() < lyhyinJono) {
					lyhyinJono = pisteet.get(i).jono.size();
					lyhyinIndex = i;
				}
			}

			pisteet.get(lyhyinIndex).lisaaJonoon(uusiA);
			loppusijainti = pisteet.get(lyhyinIndex).getSijainti();

			saapuneidenAsiakkaidenMaara++;
			saapumisprosessi.generoiSeuraava();
		}

		// Luodaan mahdollinen asiakkaan liikkumisanimaatio
		if (nykysijainti != null && loppusijainti != null && nykysijainti.getX() != loppusijainti.getX()
				&& nykysijainti.getY() != loppusijainti.getY()) {
			Trace.out(Trace.Level.INFO, "Asiakas animaatio " + nykysijainti + " : " + loppusijainti);
			if (kontrolleri != null) {
				kontrolleri.visualisoiAsiakas(nykysijainti.getX(), nykysijainti.getY(), loppusijainti.getX(),
						loppusijainti.getY());
			}
		}

		// Päivitetään luvut visualisoinnissa

		int baariJono = 0, baariPalveltavat = 0, baariTyontekijat = palvelupisteet.get(TapahtumanTyyppi.BAARI).size(),
				blackjackJono = 0, blackjackPalveltavat = 0,
				blackjackTyontekijat = palvelupisteet.get(TapahtumanTyyppi.PELI).size(), sisaankayntiJono = 0,
				sisaankayntiPalveltavat = 0,
				sisaankayntiTyontekijat = palvelupisteet.get(TapahtumanTyyppi.SISAANKAYNTI).size(), uloskayntiJono = 0,
				uloskayntiPalveltavat = 0,
				uloskayntiTyontekijat = palvelupisteet.get(TapahtumanTyyppi.ULOSKAYNTI).size();

		for (Map.Entry<TapahtumanTyyppi, LinkedList<Palvelupiste>> pisteet : palvelupisteet.entrySet()) {

			for (Palvelupiste piste : pisteet.getValue()) {
				switch (pisteet.getKey()) {
				case SISAANKAYNTI:
					Sisaankaynti sisaankaynti = (Sisaankaynti) piste;
					sisaankayntiJono += sisaankaynti.jono.size();
					sisaankayntiPalveltavat += sisaankaynti.onVarattu() ? 1 : 0;
					break;

				case ULOSKAYNTI:
					Uloskaynti uloskaynti = (Uloskaynti) piste;
					uloskayntiJono += uloskaynti.jono.size();
					uloskayntiPalveltavat += uloskaynti.onVarattu() ? 1 : 0;
					break;

				case BAARI:
					Baari baari = (Baari) piste;
					baariJono += baari.jono.size();
					baariPalveltavat += baari.onVarattu() ? 1 : 0;
					break;

				case PELI:
					Peli peli = (Peli) piste;
					blackjackJono += peli.getJononpituus();
					blackjackPalveltavat += peli.getPelaajatPoydassa();
					break;

				default:
					break;
				}
			}
			if (kontrolleri != null) {
				kontrolleri.baariJonossa(baariJono);
				kontrolleri.baariPalveltavat(baariPalveltavat);
				kontrolleri.baariTyontekijat(baariTyontekijat);

				kontrolleri.blackjackJonossa(blackjackJono);
				kontrolleri.blackjackPalveltavat(blackjackPalveltavat);
				kontrolleri.blackjackTyontekijat(blackjackTyontekijat);

				kontrolleri.uloskayntiJonossa(uloskayntiJono);
				kontrolleri.uloskayntiPalveltavat(uloskayntiPalveltavat);
				kontrolleri.uloskayntiTyontekijat(uloskayntiTyontekijat);

				kontrolleri.sisaankayntiJonossa(sisaankayntiJono);
				kontrolleri.sisaankayntiPalveltavat(sisaankayntiPalveltavat);
				kontrolleri.sisaankayntiTyontekijat(sisaankayntiTyontekijat);
			}
		}

		// Päivitetään tulokset GUI:hin
		if (kontrolleri != null) {
			Platform.runLater(() -> {
				kontrolleri.setAika(kello.getAika() + "");
				kontrolleri.setRahat(getTulokset()[IOmaMoottori.TULOS_RAHA] + "");
				kontrolleri.setVoitot(getTulokset()[IOmaMoottori.TULOS_VOITTO] + "");
				kontrolleri.setSaapuneet(getTulokset()[IOmaMoottori.TULOS_SAAPUNEIDEN_ASIAKKAIDEN_MAARA] + "");
				kontrolleri.setPalvellut(getTulokset()[IOmaMoottori.TULOS_POISTUNEIDEN_ASIAKKAIDEN_MAARA] + "");
				kontrolleri.setAvgJono(getTulokset()[IOmaMoottori.TULOS_KESKIMAARAINEN_JONONPITUUS] + "");
				kontrolleri.setKokonaisoleskelu(getTulokset()[IOmaMoottori.TULOS_KOKONAISOLESKELUAIKA] + "");

				kontrolleri.setAvgOnnellisuus(getTulokset()[IOmaMoottori.TULOS_KESKIM_MIELENTILA] + "");
				kontrolleri.setAvgPaihtymys(getTulokset()[IOmaMoottori.TULOS_KESKIM_PAIHTYNEISYYS] + "");
				kontrolleri.setAvgVarallisuus(getTulokset()[IOmaMoottori.TULOS_KESKIM_VARAKKUUS] + "");
				kontrolleri.setAvgLapimeno(getTulokset()[IOmaMoottori.TULOS_KESKIMAARAINEN_LAPIMENOAIKA] + "");
			});
		}
	}

	/**
	 * Testaa tietokantayhteyden. Jos tietokantaan ei pystytä muodostamaan yhteyttä,
	 * niin metodi kysyy käyttäjältä halutaanko uusi tietokanta luoda.
	 */
	private void testaaTietokantaYhteys() {
		if (!kasinoDAO.yhteysOnnistuu()) {
			boolean yritetaanLuodaanTietokanta = kontrolleri.kyllaTaiEiDialogi("Tietokantaan yhdistäminen epäonnistui. "
					+ "Syynä voi olla, ettei tietokantaa ole tai kirjautuminen tietokantaan annetuilla kirjautumistiedoilla ei onnistunut. "
					+ "Yritetäänkö luoda uusi tietokanta?");

			if (yritetaanLuodaanTietokanta) {
				if (kasinoDAO.yritaLuodaTietokanta()) {
					kontrolleri.ilmoitusDialogi("Tietokannan luonti onnistui!");
					tietokantayhteys = true;
				} else
					kontrolleri.virheilmoitusDialogi(
							"Tietokannan luonti epäonnistui. Tarkista annetut tietokannan kirjautumistiedot.");
			}
		} else
			tietokantayhteys = true;

		if (!tietokantayhteys)
			kontrolleri.ilmoitusDialogi(
					"Tietokanta yhteyttä ei luotu joten simulaation ajoja ei tallenneta tietokantaan.");
	}

	/**
	 * Kutsuu kontrollerin visualisointi säikeen lopettavaa metodia ja välittää
	 * kontrolleriin "Simulaatio päättyi" viestin.
	 */
	@Override
	protected void lopetus() {
		if (kontrolleri != null) {
			kontrolleri.lopetaVisualisointi("Simulaatio päättyi");
		}
	}

	/**
	 * Lisää uusimmat tulokset tietokantaan
	 */
	@Override
	protected void tulokset() {
		KasinoTulokset uudetTulokset = new KasinoTulokset(getTulokset()[IOmaMoottori.TULOS_AIKA],
				Kasino.getMainoskulut(), Kasino.getMaxBet(), Kasino.getMinBet(), Kasino.getYllapitohinta(),
				Kasino.getBlackjackTasapeliprosentti(), Kasino.getBlackjackVoittoprosentti(),

				palvelupisteet.get(TapahtumanTyyppi.PELI).size(), palvelupisteet.get(TapahtumanTyyppi.BAARI).size(),
				palvelupisteet.get(TapahtumanTyyppi.SISAANKAYNTI).size(),
				palvelupisteet.get(TapahtumanTyyppi.ULOSKAYNTI).size(),

				Kasino.getKasinonRahat(), Kasino.getKasinonVoitto(),

				(int) getTulokset()[IOmaMoottori.TULOS_SAAPUNEIDEN_ASIAKKAIDEN_MAARA],
				(int) getTulokset()[IOmaMoottori.TULOS_POISTUNEIDEN_ASIAKKAIDEN_MAARA],

				getTulokset()[IOmaMoottori.TULOS_KESKIMAARAINEN_JONONPITUUS],
				getTulokset()[IOmaMoottori.TULOS_KOKONAISOLESKELUAIKA],

				getTulokset()[IOmaMoottori.TULOS_KESKIM_MIELENTILA],
				getTulokset()[IOmaMoottori.TULOS_KESKIM_PAIHTYNEISYYS],
				getTulokset()[IOmaMoottori.TULOS_KESKIM_VARAKKUUS],
				getTulokset()[IOmaMoottori.TULOS_KESKIMAARAINEN_LAPIMENOAIKA]);

		// DAO:n kanssa toimiminen
		if (tietokantayhteys) {
			if (kasinoDAO.lisaaTulokset(uudetTulokset)) {
				// Haetaan kaikki mitatut tulokset tietokannasta
				KasinoTulokset[] kaikkiTulokset = kasinoDAO.haeTulokset();

				if (kaikkiTulokset == null) {
					if (kontrolleri != null)
						kontrolleri.virheilmoitusDialogi("Tulosten hakeminen tietokannasta epäonnistui.");
				} else {
					// Infotasolla tulostetaan kaikki tulokset konsoliin
					int i = 1;
					for (KasinoTulokset t : kaikkiTulokset)
						Trace.out(Trace.Level.INFO, "Ajo (" + i++ + ")\n" + t + "\n");

					// Avataan uusimmat tulokset ikkunassa
					if (kontrolleri != null)
						kontrolleri.naytaTulokset(kaikkiTulokset);
				}
			} else {
				if (kontrolleri != null)
					kontrolleri.virheilmoitusDialogi("Tulosten lisäys tietokantaan epäonnistui.");
			}
		} else {
			KasinoTulokset[] uudetTuloksetTaulukossa = new KasinoTulokset[] { uudetTulokset };
			if (kontrolleri != null)
				kontrolleri.naytaTulokset(uudetTuloksetTaulukossa);
		}
	}

	// IOmaMoottori

	@Override
	public double[] getTulokset() {

		// Aika
		tulokset[IOmaMoottori.TULOS_AIKA] = kello.getAika();

		// Saapuneiden asiakkaiden määrä
		tulokset[IOmaMoottori.TULOS_SAAPUNEIDEN_ASIAKKAIDEN_MAARA] = saapuneidenAsiakkaidenMaara;

		// Poistuneiden asiakkaiden määrä
		tulokset[IOmaMoottori.TULOS_POISTUNEIDEN_ASIAKKAIDEN_MAARA] = poistuneidenAsiakkaidenMaara;

		// Keskimääräinen läpimenoaika
		double keskimaarainenLapimenoaika = 0.0;
		if (poistuneidenAsiakkaidenMaara != 0)
			keskimaarainenLapimenoaika = poistumisajatSummattuna / poistuneidenAsiakkaidenMaara;

		tulokset[IOmaMoottori.TULOS_KESKIMAARAINEN_LAPIMENOAIKA] = keskimaarainenLapimenoaika;

		// Kokonaisoleskeluaika
		double kokonaisoleskeluaika = 0.0;

		for (LinkedList<Palvelupiste> pisteet : palvelupisteet.values()) {
			for (Palvelupiste p : pisteet) {
				kokonaisoleskeluaika += p.getPalveluaika();
			}
		}

		tulokset[IOmaMoottori.TULOS_KOKONAISOLESKELUAIKA] = kokonaisoleskeluaika;

		double kokonaisjononpituus = 0.0;

		for (LinkedList<Palvelupiste> pisteet : palvelupisteet.values()) {
			for (Palvelupiste p : pisteet) {
				if (p instanceof Peli) {
					kokonaisjononpituus += ((Peli) p).getJononpituus();
				} else
					kokonaisjononpituus += p.getPalveluaika();
			}
		}

		// Keskimääräinen jononpituus
		double keskimaarainenjononpituus = kokonaisjononpituus / kello.getAika();
		tulokset[IOmaMoottori.TULOS_KESKIMAARAINEN_JONONPITUUS] = keskimaarainenjononpituus;

		// Raha
		tulokset[IOmaMoottori.TULOS_RAHA] = Kasino.getKasinonRahat();

		// Voitto
		tulokset[IOmaMoottori.TULOS_VOITTO] = Kasino.getKasinonVoitto();

		// Asiakkaiden keskimääräiset ominaisuudet

		double kokonaisMieliala = 0;
		double kokonaisVarakkuus = 0;
		double kokonaisUhkarohkeus = 0;
		double kokonaisPaihtymys = 0;

		synchronized (asiakkaatKasinolla) {
			if (asiakkaatKasinolla != null && asiakkaatKasinolla.size() != 0) {
				for (Asiakas asiakas : asiakkaatKasinolla) {
					kokonaisMieliala += asiakas.getOminaisuus(Ominaisuus.MIELIALA);
					kokonaisVarakkuus += asiakas.getOminaisuus(Ominaisuus.VARAKKUUS);
					kokonaisUhkarohkeus += asiakas.getOminaisuus(Ominaisuus.UHKAROHKEUS);
					kokonaisPaihtymys += asiakas.getOminaisuus(Ominaisuus.PAIHTYMYS);
				}
			}
		}

		tulokset[IOmaMoottori.TULOS_KESKIM_MIELENTILA] = (kokonaisMieliala + poistuneidenAsiakKokMielentila)
				/ saapuneidenAsiakkaidenMaara;
		tulokset[IOmaMoottori.TULOS_KESKIM_VARAKKUUS] = (kokonaisVarakkuus + poistuneidenAsiakKokVarakkuus)
				/ saapuneidenAsiakkaidenMaara;
		tulokset[IOmaMoottori.TULOS_KESKIM_UHKAROHKEUS] = (kokonaisUhkarohkeus + poistuneidenAsiakKokUhkarohkeus)
				/ saapuneidenAsiakkaidenMaara;
		tulokset[IOmaMoottori.TULOS_KESKIM_PAIHTYNEISYYS] = (kokonaisPaihtymys + poistuneidenAsiakKokPaihtyneisyys)
				/ saapuneidenAsiakkaidenMaara;

		return tulokset;
	}

	@Override
	public LinkedList<Palvelupiste> getPalvelupisteet(int palvelu) {
		switch (palvelu) {
		case IOmaMoottori.PALVELUTYYPPI_SISAANKAYNTI:
			LinkedList<Palvelupiste> sisaankaynnit = palvelupisteet.get(TapahtumanTyyppi.ULOSKAYNTI);
			return sisaankaynnit;
		case IOmaMoottori.PALVELUTYYPPI_ULOSKAYNTI:
			LinkedList<Palvelupiste> uloskaynnit = palvelupisteet.get(TapahtumanTyyppi.ULOSKAYNTI);
			return uloskaynnit;
		case IOmaMoottori.PALVELUTYYPPI_BAARI:
			LinkedList<Palvelupiste> baarit = palvelupisteet.get(TapahtumanTyyppi.BAARI);
			return baarit;
		case IOmaMoottori.PALVELUTYYPPI_PELI:
			LinkedList<Palvelupiste> pelit = palvelupisteet.get(TapahtumanTyyppi.PELI);
			return pelit;
		default:
			Trace.out(Level.ERR, "getPalvelupisteet() - Tuntematon palvelutyyppi.");
			return null;
		}
	}

	/**
	 * Hakee blackjack pöydän voittoprosentin.
	 * 
	 * @return Blackjack pöydän voittoprosentti
	 */
	public double getBlackjackVoittoprosentti() {
		return Kasino.getBlackjackVoittoprosentti();
	}

	/**
	 * Asettaa blackjack pöydän voittoprosentin.
	 * 
	 * @param blackjackVoittoprosentti blackjack pöydän uusi voittoprosentti
	 */
	public void setBlackjackVoittoprosentti(double blackjackVoittoprosentti) {
		tarkistaDoubleProsenttiluku(blackjackVoittoprosentti,
				"Valitse voittoprosentiksi joku double luku 0-1 väliltä.");

		Kasino.setBlackjackVoittoprosentti(blackjackVoittoprosentti);
	}

	/**
	 * Hakee blackjack pöydän tasapeliprosentin.
	 * 
	 * @return Blackjack pöydän tasapeliprosentti
	 */
	public double getBlackjackTasapeliprosentti() {
		return Kasino.getBlackjackTasapeliprosentti();
	}

	/**
	 * Asettaa blackjack pöydän tasapeliprosentin.
	 * 
	 * @param blackjackVoittoprosentti blackjack pöydän uusi tasapeliprosentti
	 */
	public void setBlackjackTasapeliprosentti(double blackjackTasapeliprosentti) {
		tarkistaDoubleProsenttiluku(blackjackTasapeliprosentti,
				"Valitse tasapeliprosentiksi joku double luku 0-1 väliltä.");

		Kasino.setBlackjackTasapeliprosentti(blackjackTasapeliprosentti);
	}

	/**
	 * Hakee blackjack pöydän minimipanoksen.
	 * 
	 * @return Blackjack pöydän minimipanos
	 */
	public double getMinBet() {
		return Kasino.getMinBet();
	}

	/**
	 * Asettaa blackjack pöydän minimipanoksen.
	 * 
	 * @param blackjackVoittoprosentti blackjack pöydän uusi minimipanos
	 */
	public void setMinBet(double minBet) {
		tarkistaDoubleLuku(minBet, "Valitse minimipanoksesksi joku positiivinen double luku.");
		Kasino.setMinBet(minBet);
	}

	/**
	 * Hakee blackjack pöydän maksimipanoksen.
	 * 
	 * @return Blackjack pöydän maksimipanos
	 */
	public double getMaxBet() {
		return Kasino.getMaxBet();
	}

	/**
	 * Asettaa blackjack pöydän maksimipanoksen.
	 * 
	 * @param blackjackVoittoprosentti blackjack pöydän uusi maksimipanos
	 */
	public void setMaxBet(double maxBet) {
		tarkistaDoubleLuku(maxBet, "Valitse maksimipanoksesksi joku positiivinen double luku.");
		Kasino.setMaxBet(maxBet);
	}

	/**
	 * Hakee onko kasinosimulaatio taukotilassa vai ei.
	 * 
	 * @return False, jos simulaatio ei ole taukotilassa ja true jos on
	 */
	public boolean getKasinoPause() {
		return Kasino.isPause();
	}

	/**
	 * Asettaa simulaation taukotilaan tai lopettaa simulaation taukotilan.
	 * 
	 * @param pause True asettaa simulaation taukotilaan ja false lopettaa
	 *              simulaation taukotilan.
	 */
	public void setKasinoPause(boolean pause) {
		Kasino.setPause(pause);
	}
}
