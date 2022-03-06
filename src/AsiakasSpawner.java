import kasinoSimulaattori.view.KasinoVisualisointi;

// KasinoVisualisointi DEMO
public class AsiakasSpawner extends Thread {
	private KasinoVisualisointi visulaattori;
	
	public AsiakasSpawner(KasinoVisualisointi visulaattori) {
		this.visulaattori = visulaattori;
	}
	
	public void luoAsiakkaita() throws InterruptedException
	{
		while(true) {
		visulaattori.piirraAsiakasLiike(1*128, 1*128, 5*128, 1*128);
		Thread.sleep(1000);
		visulaattori.piirraAsiakasLiike(1*128, 1*128, 4*128, 4*128);
		Thread.sleep(1000);
		visulaattori.piirraAsiakasLiike(5*128, 1*128, 4*128, 4*128);
		Thread.sleep(1000);
		visulaattori.piirraAsiakasLiike(2*128, 4*128, 5*128, 1*128);
		Thread.sleep(1000);
		visulaattori.piirraAsiakasLiike(2*128, 4*128, 4*128, 4*128);
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
