import kasinoSimulaattori.view.KasinoVisualisointi;

/**
 * Luokkaa käytetään kasinovisualisaattorin Liikkujen lisäämiseen demonstrointi mielessä. 
 * @author Vilhelm
 */
public class AsiakasSpawner extends Thread {
	private KasinoVisualisointi visulaattori;
	
	/**
	 * Asiakas spawnerin konstruktori.
	 * @param visulaattori Kasinon visualisaattori johon asiakkaita halutaan lisätä.
	 */
	public AsiakasSpawner(KasinoVisualisointi visulaattori) {
		this.visulaattori = visulaattori;
	}
	
	/**
	 * Luo asiakkaita tietyin aikavälein.
	 * @throws InterruptedException
	 */
	public void luoAsiakkaita() throws InterruptedException
	{
		while(true) {
		visulaattori.asiakkaanLiikeAnimaatio(1*128, 1*128, 5*128, 1*128);
		visulaattori.setBaariJononPituus(10);
		visulaattori.setUloskayntiJononPituus(10);
		Thread.sleep(1000);
		visulaattori.asiakkaanLiikeAnimaatio(1*128, 1*128, 4*128, 4*128);
		visulaattori.setBaariPalveltavienMaara(1);
		visulaattori.setUloskayntiJononPituus(10);
		Thread.sleep(1000);
		visulaattori.asiakkaanLiikeAnimaatio(5*128, 1*128, 4*128, 4*128);
		visulaattori.setBlackjackJononPituus(12);
		visulaattori.setBaariJononPituus(2);
		visulaattori.setUloskayntiJononPituus(5);
		visulaattori.setSisaankayntiPalveltavienMaara(2);
		visulaattori.setUloskayntiJononPituus(3);
		Thread.sleep(1000);
		visulaattori.asiakkaanLiikeAnimaatio(2*128, 4*128, 5*128, 1*128);
		visulaattori.setBlackjackPalveltavienMaara(4);
		visulaattori.setBlackjackJononPituus(3);
		Thread.sleep(1000);
		visulaattori.asiakkaanLiikeAnimaatio(2*128, 4*128, 4*128, 4*128);
		visulaattori.setBlackjackPalveltavienMaara(7);
		visulaattori.setSisaankayntiJononPituus(10);
		}
	}
	
	@Override 
	public void run() {
		try {
			luoAsiakkaita();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
