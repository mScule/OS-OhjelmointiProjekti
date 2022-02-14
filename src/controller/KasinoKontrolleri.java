package controller;

import javafx.application.Platform;
import simu.framework.IMoottori;
import simu.model.OmaMoottori;
import view.ISimulaattorinUI;

public class KasinoKontrolleri implements IKontrolleriVtoM, IKontrolleriMtoV {

	private IMoottori moottori;
	private ISimulaattorinUI ui;
	
	public KasinoKontrolleri(ISimulaattorinUI ui) {
		this.ui = ui;
	}
	
	// IKontrolleriMtoV:
	
	@Override
	public void naytaLoppuaika(double aika) {
		Platform.runLater(() -> ui.setLoppuaika(aika));
	}

	@Override
	public void visualisoiAsiakas() {
		Platform.runLater(new Runnable() {
			public void run() {
				ui.getVisualisointi().uusiAsiakas();
			}
		});
	}

	// IKontrolleriVtoM:
	
	@Override
	public void kaynnistaSimulointi() {
		moottori = new OmaMoottori(this);
		moottori.setSimulointiaika(ui.getAika());
		
		moottori.setViive(ui.getViive());
		ui.getVisualisointi().tyhjennaNaytto();
		((Thread)moottori).start();
	}

	@Override
	public void nopeuta() {
		moottori.setViive((long)(moottori.getViive() * 0.9));
	}

	@Override
	public void hidasta() {
		moottori.setViive((long)(moottori.getViive() * 1.1));
	}
}
