package simu.model;

import java.util.LinkedList;

import eduni.distributions.ContinuousGenerator;
import simu.framework.Kello;
import simu.framework.Tapahtuma;
import simu.framework.Tapahtumalista;
import simu.framework.Trace;

// TODO:
// Palvelupistekohtaiset toiminnallisuudet, laskutoimitukset (+ tarvittavat muuttujat), jonjen pituudet 
// ja raportointi koodattava
public class Palvelupiste {

	private LinkedList<Asiakas> jono = new LinkedList<Asiakas>(); // Tietorakennetoteutus
	
	private ContinuousGenerator generator;
	private Tapahtumalista tapahtumalista;
	private TapahtumanTyyppi skeduloitavanTapahtumanTyyppi; 
	
	//JonoStartegia strategia; //optio: asiakkaiden järjestys
	
	private boolean varattu = false;


	public Palvelupiste(ContinuousGenerator generator, Tapahtumalista tapahtumalista, TapahtumanTyyppi tyyppi){
		this.tapahtumalista = tapahtumalista;
		this.generator = generator;
		this.skeduloitavanTapahtumanTyyppi = tyyppi;
				
	}

	// Jonon 1. asiakas aina palvelussa baarissa ja vastaanotolla. 
	// TODO: Jos palvelupiste on pelipöytä, jonon 7 ensimmäistä ovat palvelussa.
	public void lisaaJonoon(Asiakas a){
		jono.add(a);
		
	}

	public Asiakas otaJonosta(){  // Poistetaan palvelussa ollut
		varattu = false;
		return jono.poll();
	}

	public void aloitaPalvelu(){  //Aloitetaan uusi palvelu, asiakas on jonossa palvelun aikana
		
		Trace.out(Trace.Level.INFO, "Aloitetaan uusi palvelu asiakkaalle " + jono.peek().getId());
		
		varattu = true;
		// TODO: Jos palvelupiste on blackjack pöytä, laske palveluaika jonon seitsemälle ensimmäiselle asiakkaalle
		// laskemalla asiakkaiden pelien määrät pelipöydässä.
		// Muuten laske asiakkaan palveluaika baarissa tai vastaanotossa asiakkaan ominaisuuksien ja jonkun 
		// satunnaisesti generoidun luvun avulla.
		// Laske ja päivitä myös asiakkaiden ominaisuudet.
		// Lisää erillinen Peli-yliluokka, jonka erilaiset pelien aliluokat perivät, jos erilaisia pelejä lisätään?
		// Luo kasinosta poistumistapahtuma asiakkaalle, jos hänen pelimerkit loppuvat.
		double palveluaika = generator.sample();
		tapahtumalista.lisaa(new Tapahtuma(skeduloitavanTapahtumanTyyppi,Kello.getInstance().getAika()+palveluaika));
	}


	public boolean onVarattu(){
		return varattu;
	}


	public boolean onJonossa(){
		return jono.size() != 0;
	}

}
