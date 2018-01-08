package framework.engines;

import java.awt.Graphics2D;

public abstract class Engine {
	/**
	 * stores the offsetX according to the grid co-ordiante. Needs to be
	 * multiplied by {@code Constants.UNIT_DISTANCE} to get the actual X offset.
	 */
	protected int offsetX;
	/**
	 * stores the offsetY according to the grid co-ordiante. Needs to be
	 * multiplied by {@code Constants.UNIT_DISTANCE} to get the actual Y offset.
	 */
	protected int offsetY;
	/**
	 * stores the length according to the grid co-ordiante. Needs to be
	 * multiplied by {@code Constants.UNIT_DISTANCE} to get the actual length.
	 */
	protected int length;
	/**
	 * stores the breadth according to the grid co-ordiante. Needs to be
	 * multiplied by {@code Constants.UNIT_DISTANCE} to get the actual breadth.
	 */
	protected int breadth;

	abstract void init();

	abstract void render(Graphics2D g);

	abstract void tick();

	abstract void debugPrint();

	/**
	 * 
	 * 
	 * @return the the offsetX according to the grid co-ordiante. Needs to be
	 *         multiplied by {@code Constants.UNIT_DISTANCE} to get the actual X
	 *         offset.
	 */
	public int getOffsetX() {
		return offsetX;
	}

	/**
	 * @return the offsetY according to the grid co-ordiante. Needs to be
	 *         multiplied by {@code Constants.UNIT_DISTANCE} to get the actual Y
	 *         offset.
	 */
	public int getOffsetY() {
		return offsetY;
	}

	/**
	 * @return the length according to the grid co-ordiante. Needs to be
	 *         multiplied by {@code Constants.UNIT_DISTANCE} to get the actual
	 *         length.
	 */
	public int getLength() {
		return length;
	}

	/**
	 * @return the the breadth according to the grid co-ordiante. Needs to be
	 *         multiplied by {@code Constants.UNIT_DISTANCE} to get the actual
	 *         breadth.
	 */
	public int getBreadth() {
		return breadth;
	}

	/**
	 * @param offsetX
	 *            the offsetX to set
	 */
	public void setOffsetX(int offsetX) {
		this.offsetX = offsetX;
	}

	/**
	 * @param offsetY
	 *            the offsetY to set
	 */
	public void setOffsetY(int offsetY) {
		this.offsetY = offsetY;
	}

	/**
	 * 
	 * @param length
	 *            the length to set
	 * 
	 */
	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * @param breadth
	 *            the breadth to set
	 */
	public void setBreadth(int breadth) {
		this.breadth = breadth;
	}

}
