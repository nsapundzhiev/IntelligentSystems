package fmi.intelligent.systems.homeworks.second;

public class Pair {
	private int x;
	private int y;

	public Pair(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
