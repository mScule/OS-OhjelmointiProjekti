package kasinoSimulaattori.util;

public class Sijainti {
	private int x, y;
	
	public Sijainti(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() { return x; }
	public int getY() { return y; }
	
	@Override
	public String toString() {
		return x + ", " + y;
	}
}
