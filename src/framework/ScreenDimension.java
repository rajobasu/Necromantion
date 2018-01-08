package framework;

public class ScreenDimension {

	private int startingX;
	private int endingX;

	private int startingY;
	private int endingY;

	public ScreenDimension(int startingX, int endingX, int startingY, int endingY) {
		super();
		this.startingX = startingX;
		this.endingX = endingX;
		this.startingY = startingY;
		this.endingY = endingY;
	}

	public int getStartingX() {
		return startingX;
	}

	public int getEndingX() {
		return endingX;
	}

	public int getStartingY() {
		return startingY;
	}

	public int getEndingY() {
		return endingY;
	}
}
