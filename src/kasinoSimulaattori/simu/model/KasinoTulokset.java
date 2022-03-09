package kasinoSimulaattori.simu.model;

public class KasinoTulokset {
	private double
		aika,
		mainostus,
		maksimiPanos,
		minimiPanos,
		yllapito,
		tasapeliProsentti,
		voittoProsentti,
		rahat,
		voitot,
		keskJonotusaika,
		kokonaisoleskeluaika,
		keskOnnellisuus,
		keskPaihtymys,
		keskVarallisuus,
		keskLapimenoaika;
	
	private int
		blackjackPoydat,
		baarit,
		sisaankaynnit,
		uloskaynnit,
		saapuneetAsiakkaat,
		palvellutAsiakkaat;
	
	public KasinoTulokset(
		double aika,
		double mainostus,
		double maksimiPanos,
		double minimiPanos,
		double yllapito,
		double tasapeliProsentti,
		double voittoProsentti,
		
		int blackjackPoydat,
		int baarit,
		int sisaankaynnit,
		int uloskaynnit,
		
		double rahat,
		double voitot,
		
		int saapuneetAsiakkaat,
		int palvellutAsiakkaat,
		
		double keskJonotusaika,
		double kokonaisoleskeluaika,
		
		double keskOnnellisuus,
		double keskPaihtymys,
		double keskVarallisuus,
		double keskLapimenoaika
	) {
		this.aika              = aika;
		this.mainostus         = mainostus;
		this.maksimiPanos      = maksimiPanos;
		this.yllapito          = yllapito;
		this.tasapeliProsentti = tasapeliProsentti;
		this.voittoProsentti   = voittoProsentti;
		
		this.blackjackPoydat = blackjackPoydat;
		this.baarit          = baarit;
		this.sisaankaynnit   = sisaankaynnit;
		this.uloskaynnit     = uloskaynnit;
		
		this.rahat  = rahat;
		this.voitot = voitot;
		
		this.saapuneetAsiakkaat = saapuneetAsiakkaat;
		this.palvellutAsiakkaat = palvellutAsiakkaat;
		
		this.keskJonotusaika      = keskJonotusaika;
		this.kokonaisoleskeluaika = kokonaisoleskeluaika;
		
		this.keskOnnellisuus  = keskOnnellisuus;
		this.keskPaihtymys    = keskPaihtymys;
		this.keskVarallisuus  = keskVarallisuus;
		this.keskLapimenoaika = keskLapimenoaika;
	}
	
	public double getAika()              { return aika;              }
	public double getMainostus()         { return mainostus;         }
	public double getMaksimiPanos()      { return maksimiPanos;      }
	public double getMinimiPanos()       { return minimiPanos;       }
	public double getYllapito()          { return yllapito;          }
	public double getTasapeliProsentti() { return tasapeliProsentti; }
	public double getVoittoProsentti()   { return voittoProsentti;   }
	
	public int getBlackjackPoydat() { return blackjackPoydat;   }
	public int getBaarit()          { return baarit;            }
	public int getSisaankaynnit()   { return sisaankaynnit;     }
	public int getUloskaynnit()     { return uloskaynnit;       }
	
	public double getRahat()  { return rahat;  }
	public double getVoitot() { return voitot; }
	
	public int getSaapuneetAsiakkaat() { return saapuneetAsiakkaat; }
	public int getPalvellutAsiakkaat() { return palvellutAsiakkaat; }
	
	public double getKeskJonotusaika()      { return keskJonotusaika;      }
	public double getKokonaisoleskeluaika() { return kokonaisoleskeluaika; }
	
	public double getKeskOnnellisuus()  { return keskOnnellisuus;  }
	public double getKeskPaihtymys()    { return keskPaihtymys;    }
	public double getKeskVarallisuus()  { return keskVarallisuus;  }
	public double getKeskLapimenoaika() { return keskLapimenoaika; }
	
	@Override
	public String toString() {
		return
			"Aika: "               + aika              + "\n" +
			"Mainostus: "          + mainostus         + "\n" +
			"Maksimi panos: "      + maksimiPanos      + "\n" +
			"Minimi panos: "       + minimiPanos       + "\n" +
			"Ylläpito: "           + yllapito          + "\n" +
			"Tasapeli prosentti: " + tasapeliProsentti + "\n" +
			"Voitto prosentti: "   + voittoProsentti   + "\n" +
			
			"Blackjack pöydät: " + blackjackPoydat   + "\n" +
			"Baarit: "           + baarit            + "\n" + 
			"Sisäänkäynnit: "    + sisaankaynnit     + "\n" +
			"Uloskäynnit: "      + uloskaynnit       + "\n" +
			
			"Rahat: "  + rahat  + "\n" +
			"Voitot: " + voitot + "\n" +
			
			"Saapuneet asiakkaat: " + saapuneetAsiakkaat + "\n" +
			"Palvellut asiakkaat: " + palvellutAsiakkaat + "\n" +
			
			"Keskimääräinen jonotusaika: " + keskJonotusaika      + "\n" +
			"Kokonaisoleskeluaika: "       + kokonaisoleskeluaika + "\n" +
			
			"Keskimääräinen onnellisuus: "  + keskOnnellisuus  + "\n" +
			"Keskimääräinen päihtymys: "    + keskPaihtymys    + "\n" +
			"Keskimääräinen varallisuus: "  + keskVarallisuus  + "\n" +
			"Keskimääräinen läpimenoaika: " + keskLapimenoaika + "\n";
	}
}
