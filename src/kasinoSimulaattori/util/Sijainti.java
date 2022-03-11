package kasinoSimulaattori.util;

/**
 * Luokka pitää sisällään tiedot x ja y koordinaateista.
 * Käytetään visualisoinnissa sijaintien käsittelyyn.
 * @author Vilhelm
 */
public class Sijainti {
	private int x, y;
	
	/**
	 * Sijainti olion konstruktori.
	 * @param x X-koordinaatti
	 * @param y Y-koordinaatti
	 */
	public Sijainti(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * @return Palauttaa sijainnin x-koordinaatin.
	 */
	public int getX() { return x; }
	
	/**
	 * @return Palauttaa sijainnin y-koordinaatin.
	 */
	public int getY() { return y; }
	
	/**
	 * @return Sijannin merkkijonoesitys.
	 */
	@Override
	public String toString() {
		return x + ", " + y;
	}
}
