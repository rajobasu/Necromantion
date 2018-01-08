package maps;

/**
 * Encapsulates the location on the Map grid.
 * 
 * @author Rajarshi
 *
 */
public class Location {

	private int x;
	private int y;

	/**
	 * @param x
	 * @param y
	 */
	public Location(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	@Override
	public String toString() {
		return x + " " + y;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Location) {
			if (x == ((Location) obj).x && y == ((Location) obj).y)
				return true;
			else
				return false;
		} else
			return false;

	}

}
