package simu.model;

import java.util.LinkedList;

import eduni.distributions.ContinuousGenerator;
import eduni.distributions.Negexp;
import eduni.distributions.Uniform;
import simu.framework.Tapahtumalista;
import simu.framework.Trace;

// TODO:
// Palvelupistekohtaiset toiminnallisuudet, laskutoimitukset (+ tarvittavat muuttujat), jonjen pituudet 
// ja raportointi koodattava
public class Palvelupiste implements IPalvelupiste {

	protected LinkedList<Asiakas> jono = new LinkedList<Asiakas>(); // Tietorakennetoteutus

	protected Negexp negexpGenerator;
	protected Tapahtumalista tapahtumalista;
	// Arvo joku muu tapahtuma, kuin SISÄÄNKÄYNTI tai POISTUMINEN.
	protected Uniform uniform = new Uniform(2, TapahtumanTyyppi.values().length, Kasino.getSeed());

	// JonoStartegia strategia; //optio: asiakkaiden järjestys
	public static int palveluid = 0;
	private int id;
	protected boolean varattu = false;

	private double palveluaika = 0;
	private int palvellutAsiakkaat = 0;

	private double[] tulokset = new double[IPalvelupiste.TULOSTEN_MAARA];

	public Palvelupiste(Negexp negexpGenerator, Tapahtumalista tapahtumalista) {
		this.tapahtumalista = tapahtumalista;
		this.negexpGenerator = negexpGenerator;

		id = palveluid;
		palveluid++;
	}

	public void setKeskimPalveluaika(double uusiKeskimPalveluaika){
		Negexp newGenerator = new Negexp(uusiKeskimPalveluaika, Kasino.getSeed());
		negexpGenerator = newGenerator;
	}

	private int getSample() {
		return (int) uniform.sample();
	}

	protected TapahtumanTyyppi arvoTapahtuma() {
		return TapahtumanTyyppi.values()[getSample()];
	}

	protected void lisaaPalveluAikaa(double aika) {
		palveluaika += aika;
	}

	public double getPalveluaika() {
		return palveluaika;
	}

	protected void lisaaPalveltuAsiakas() {
		palvellutAsiakkaat++;
	}

	public int getPalvellutAsiakkaat() {
		return palvellutAsiakkaat;
	}

	// Jonon 1. asiakas aina palvelussa baarissa tai vastaanotolla.
	public void lisaaJonoon(Asiakas a) {
		jono.add(a);
	}

	public Asiakas otaJonosta() { // Poistetaan palvelussa ollut
		lisaaPalveltuAsiakas();
		varattu = false;

		return jono.poll();
	}

	// Etsitään asiakas tietyllä ID:llä ja poistetaan se jonosta.
	public Asiakas otaJonostaIDnMukaan(int poistettavanAsiakkaanID) {
		for (int i = 0; i < jono.size(); i++) {
			if (poistettavanAsiakkaanID == jono.get(i).getId())
				return jono.remove(i);
		}
		System.err.println("Asiakasta " + poistettavanAsiakkaanID + "ei löytynyt jonosta.");
		return null;
	}

	public void aloitaPalvelu() { // Aloitetaan uusi palvelu, asiakas on jonossa palvelun aikana

		Trace.out(Trace.Level.INFO, "Aloitetaan uusi palvelu, asiakas " + jono.peek().getId() + " ["
				+ this.getClass().toString() + " " + getId() + " ]");
		// Printtaa asiakkaan tiedot
		Trace.out(Trace.Level.INFO,jono.peek());

		// TODO: Käytä asiakkaan palveluajan laskemiseen jonkun satunnaisesti
		// generoidun luvun lisäksi asiakkaan ominaisuuksia.

		// TODO: Laske ja päivitä asiakkaiden ominaisuudet.
	}

	public void poistu() {
		otaJonosta();
	}

	public boolean onVarattu() {
		return varattu;
	}

	public boolean onJonossa() {
		return jono.size() != 0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public double[] getTulokset() {
		double onVarattu;

		if (onVarattu()) {
			onVarattu = 1;
		} else {
			onVarattu = 0;
		}

		// ID
		tulokset[IPalvelupiste.ID] = getId();

		// Varattu
		tulokset[IPalvelupiste.VARATTU] = onVarattu;

		// Saapuneiden asiakkaiden määrä
		tulokset[IPalvelupiste.PALVELUAIKA] = getPalveluaika();

		// Poistuneiden asiakkaiden määrä
		tulokset[IPalvelupiste.PALVELLUT_ASIAKKAAT] = getPalvellutAsiakkaat();

		return tulokset;
	}
}
