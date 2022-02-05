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

	private int getSample() {
		return (int) uniform.sample();
	}

	protected TapahtumanTyyppi arvoTapahtuma() {
		return TapahtumanTyyppi.values()[getSample()];
	}

	public Palvelupiste(ContinuousGenerator generator, Tapahtumalista tapahtumalista) {
		this.tapahtumalista = tapahtumalista;
		this.generator = generator;

		id = palveluid;
		palveluid++;
	}

	// Jonon 1. asiakas aina palvelussa baarissa ja vastaanotolla.
	public void lisaaJonoon(Asiakas a) {
		jono.add(a);
	}

	public Asiakas otaJonosta() { // Poistetaan palvelussa ollut
		varattu = false;

		return jono.poll();
	}

	public Asiakas otaJonostaIDnMukaan(int poistettavanAsiakkaanID) { // Etsitään asiakas tietyllä ID:llä ja poistetaan
																		// se jonosta.
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

		// varattu = true;
		// TODO: Jos palvelupiste on blackjack pöytä, laske palveluaika jonon
		// seitsemälle ensimmäiselle asiakkaalle laskemalla asiakkaiden pelien määrät
		// pelipöydässä.

		// TODO: Muuten laske asiakkaan palveluaika baarissa tai vastaanotossa asiakkaan
		// ominaisuuksien ja jonkun satunnaisesti generoidun luvun avulla.

		// TODO: Laske ja päivitä myös asiakkaiden ominaisuudet.

		// TODO: Luo kasinosta poistumistapahtuma asiakkaalle, jos hänen pelimerkit
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
