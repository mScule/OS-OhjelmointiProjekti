package simu.model;

import java.util.LinkedList;

import eduni.distributions.ContinuousGenerator;
import eduni.distributions.Uniform;
import simu.framework.Tapahtumalista;
import simu.framework.Trace;

// TODO:
// Palvelupistekohtaiset toiminnallisuudet, laskutoimitukset (+ tarvittavat muuttujat), jonjen pituudet 
// ja raportointi koodattava
public class Palvelupiste {

	protected LinkedList<Asiakas> jono = new LinkedList<Asiakas>(); // Tietorakennetoteutus

	protected ContinuousGenerator generator;
	protected Tapahtumalista tapahtumalista;
	protected Uniform uniform = new Uniform(2, TapahtumanTyyppi.values().length);

	// JonoStartegia strategia; //optio: asiakkaiden järjestys
	private static int palveluid = 0;
	private int id;
	protected boolean varattu = false;

	private double palveluaika = 0;
	private int palvellutAsiakkaat = 0;

	public Palvelupiste(ContinuousGenerator generator, Tapahtumalista tapahtumalista) {
		this.tapahtumalista = tapahtumalista;
		this.generator = generator;

		id = palveluid;
		palveluid++;
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
		System.out.println(jono.peek());

		// TODO: Käytä asiakkaan palveluajan laskemiseen jonkun satunnaisesti
		// generoidun luvun lisäksi asiakkaan ominaisuuksia.

		// TODO: Laske ja päivitä asiakkaiden ominaisuudet.

		// TODO: Luo asiakkaalle kasinosta poistumistapahtuma, jos hänen pelimerkit
		// loppuvat.
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
}
