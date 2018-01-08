package objects;

import java.awt.Graphics2D;

import imageHandlers.ImageLoader;
import imageHandlers.Images;
import maps.Location;
import maps.Map;
import objects.entities.Player;

/**
 * This is the main superclass for all the game objects.
 * 
 * @author rajarshibasu
 *
 */
public abstract class GameObject {

	protected int x;
	protected int y;
	protected ImageLoader imgLdr;
	protected Images image;
	protected int imageCode;
	protected Player p;

	private final int renderingPrecedence;

	public class RenderingPrecedence {
		private RenderingPrecedence() {
		}

		public static final int BACKGROUND_LAYER_1 = 1;
		public static final int BACKGROUND_LAYER_2 = 2;
		public static final int BACKGROUND_LAYER_3 = 3;

		public static final int FOREGROUND_LAYER_1 = 4;
		public static final int FOREGROUND_LAYER_2 = 5;
		public static final int FOREGROUND_LAYER_3 = 6;

	}

	/**
	 * This method returns the name of the object.
	 * 
	 * @return - A string
	 */
	public String getName() {
		return this.getClass().getSimpleName();
	}

	/**
	 * This method returns some essential information about the object.
	 * 
	 * @return - A string
	 */
	public String getInfo() {
		return "No Information is currently available";
	}

	/**
	 * Create a new {@code GameObject} at the specified location on the map.
	 * 
	 * 
	 * @param x
	 *            The x co-ordiante of the object to be created.
	 * @param y
	 *            The y co-ordinate of the object to be created.
	 * @param image
	 *            Not required.. kept for legacy reasons.
	 */
	public GameObject(int x, int y, int image) {
		super();
		this.x = x;
		this.y = y;
		imgLdr = ImageLoader.INSTANCE();

		renderingPrecedence = 1;
	}

	/**
	 * Create a new {@code GameOject} at the given location on the map.
	 * 
	 * @param x
	 *            The x co-ordinate of the object to be created.
	 * @param y
	 *            The y co-ordinate of the object to be created.
	 */
	public GameObject(int x, int y) {
		this(x, y, 0);
	}

	/**
	 * Create a new {@code GameObject} at the given location on the map with the
	 * specified {@code RenderingPrecedence}.
	 * 
	 * @param x
	 *            The x co-ordinate of the object to be created.
	 * @param y
	 *            The y co-ordinate of the object to be created.
	 * @param imageCode
	 *            Not reaquired.. kept for legacy reasons.
	 * @param renderingPrecedence
	 *            The specified {@code RenderingPrecedence}.
	 */
	public GameObject(int x, int y, int imageCode, int renderingPrecedence) {
		super();
		this.x = x;
		this.y = y;
		this.imageCode = imageCode;
		imgLdr = ImageLoader.INSTANCE();
		this.renderingPrecedence = renderingPrecedence;
	}

	/**
	 * This method is called each time the user gives a input that updates the
	 * game environment.Use this method to update the state of the object,
	 * things that need to be updated each time the player does something.
	 * However, since this is not called periodically, dont put updates that
	 * depend on time, example graphics.
	 * 
	 * @param map
	 *            The current Map object on which this {@code GameObject} is.
	 */
	public abstract void tick(Map map);

	/**
	 * Draw the object on the screen.
	 * 
	 * @param g
	 *            - The {@code Graphics2D} object for drawing on the
	 *            {@code Canvas}.
	 */
	public abstract void render(Graphics2D g);

	/**
	 * This method is called periodically according to the fps of the game.
	 * Update the graphics and the animations here.
	 */
	public abstract void updateGraphics();

	/**
	 * This method checks if this object is adjacent to the given location
	 * according to Manhatten distance.
	 * 
	 * @param x
	 *            The x co-ordinate.
	 * @param y
	 *            The y co-ordinate.
	 * @return whether this object is adjacent to the given location.
	 */
	public boolean isNearby(int x, int y) {
		return Math.abs(this.x - x) < 2 && Math.abs(this.y - y) < 2;
	}

	public boolean isColliding(int x, int y) {
		return this.x == x && this.y == y;
	}

	public boolean isWithinRange(int x, int y, int range) {
		int dx = Math.abs(this.x - x);
		int dy = Math.abs(this.y - y);

		if (dx * dx + dy * dy <= range * range) {
			return true;
		}
		return false;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getImageCode() {
		return imageCode;
	}

	public void setImageCode(int imageCode) {
		this.imageCode = imageCode;
	}

	public boolean hasOngoingExclusiveAnimations() {
		return false;
	}

	public int getRenderingPrecedence() {
		return renderingPrecedence;
	}

	@Override
	public String toString() {
		return this.getClass().getName();
	}

	public Location getLocation() {
		return new Location(x, y);
	}

	public void setLocation(Location location) {
		setX(location.getX());
		setY(location.getY());
	}
}