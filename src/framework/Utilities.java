package framework;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.text.AttributedString;
import java.util.LinkedList;

import framework.Constants.Transparency;
import framework.engines.GameEngine;
import imageHandlers.Images;
import maps.Location;
import maps.MapManager;
import objects.GameObject;
import objects.entities.Player;
import squidpony.squidmath.XoRoRNG;

public class Utilities {

	private Utilities() {
	}

	public static Point calculateOriginPoint(int currentX, int currentY, int width, int height) {
		int returnX = currentX - width / 2;
		int returnY = currentY - height / 2;

		return new Point(returnX, returnY);
	}

	public static void renderImage(Graphics2D g, BufferedImage image, int x, int y, int width, int height, int shift) {
		g.drawImage(image, x - shift, y - shift, width + shift, height + shift, null);
	}

	public static void renderObject(Graphics2D g, GameObject object) {

	}

	public static void renderObject(Graphics2D g, int x, int y, GameObject object) {

	}

	public static void renderObject(Graphics2D g, int x, int y, int width, int height, GameObject object) {

	}

	public static void renderObject(Graphics2D g, GameObject object, int width, int height) {

	}

	/**
	 * This returns the list of points through which a line joing two given
	 * points will pass through using the Bresenham line algorithm.
	 * 
	 * 
	 * @param x0
	 *            - The starting x co-ordinate
	 * @param y0
	 *            - The starting y co-ordinate
	 * @param x1
	 *            - The ending x co-ordinate
	 * @param y1
	 *            - The ending y co-ordiante
	 * @return {@code LinkedList<Point> } denoting the points through which the
	 *         line joing the two points will pass through.
	 */
	public static LinkedList<Point> findLine(int x0, int y0, int x1, int y1) {
		LinkedList<Point> line = new LinkedList<Point>();

		int dx = Math.abs(x1 - x0);
		int dy = Math.abs(y1 - y0);

		int sx = x0 < x1 ? 1 : -1;
		int sy = y0 < y1 ? 1 : -1;

		int err = dx - dy;
		int e2;

		while (true) {
			line.add(new Point(x0, y0));

			if (x0 == x1 && y0 == y1)
				break;

			e2 = 2 * err;
			if (e2 > -dy) {
				err = err - dy;
				x0 = x0 + sx;
			}

			if (e2 < dx) {
				err = err + dx;
				y0 = y0 + sy;
			}
		}

		if (line.size() > 0)
			line.removeFirst();
		if (line.size() > 0)
			line.removeLast();
		return line;
	}

	public static Images getTransparencyConstant(int transparency) {
		if (transparency == Transparency.FULL) {
			return null;
		} else if (transparency == Transparency.MEDIUM) {
			return Images.MEDIUM_TRANSPARENCY_MASK;
		} else {
			return Images.NO_TRANSPARENCY_MASK;
		}

	}

	/**
	 * renders the paragraph according to the specified width
	 * 
	 * @param x
	 *            - the starting x co-ordinate
	 * @param y
	 *            - the starting y co-ordiante
	 * @param g
	 *            - the graphics object
	 * @param paragraph
	 *            - the text that has to be drawn
	 * @param width
	 *            - the width of the line
	 * @return the last y co-ordinate on which the paragraph was drawn
	 */

	public static int drawParagraph(int x, int y, Graphics2D g, String paragraph, float width) {
		// g.setColor(Color.CYAN);
		String[] lines = paragraph.split("\n");
		int p = 0;
		// g.drawString("Helllo Testing",0,10);

		for (String line : lines) {
			AttributedString attrStrn = new AttributedString(line);
			attrStrn.addAttribute(TextAttribute.FONT, g.getFont());
			LineBreakMeasurer linebreaker = new LineBreakMeasurer(attrStrn.getIterator(), g.getFontRenderContext());

			while (linebreaker.getPosition() < line.length()) {
				TextLayout textLayout = linebreaker.nextLayout(width);
				// textLayout.se
				// textLayout.getS
				p = y;
				y += textLayout.getAscent();
				textLayout.draw(g, x, y);
				p = y;
				y += textLayout.getDescent() + textLayout.getLeading();
			}
		}

		return p;
	}

	public static int getDamage(int attackStrength, int defenseStrength, int xp) {

		XoRoRNG xrng = new XoRoRNG();
		int damage = xrng.nextInt(Math.max(0, attackStrength - defenseStrength));

		damage += xrng.nextInt(defenseStrength);
		damage += xp;

		return damage;
	}

	/**
	 * 
	 * @param location
	 *            - The location of the mouse on the screen
	 * 
	 * @return The location on the grid of the map.If the mouse pointer location
	 *         is outside of the game Engine window, then {@code null} is
	 *         returned.
	 */

	public static Location getMouseLocation(Location location) {
		if (location == null)
			return null;

		int mouseX = location.getX();
		int mouseY = location.getY();

		if (!(mouseX > GameEngine.INSTANCE.getOffsetX() && mouseX < Constants.UNIT_DISTANCE
				* (GameEngine.INSTANCE.getBreadth() + GameEngine.INSTANCE.getOffsetX()))) {
			return null;
		}

		if (!(mouseY > GameEngine.INSTANCE.getOffsetY() && mouseY < Constants.UNIT_DISTANCE
				* (GameEngine.INSTANCE.getLength() + GameEngine.INSTANCE.getOffsetY()))) {
			return null;
		}

		Player p = Player.getINSTANCE();

		int deltaX = mouseX - p.getActualX() + Camera.getOffsetX();
		int deltaY = mouseY - p.getActualY() + Camera.getOffsetY();

		int gridX = p.getX() + (int) (Math.floor(deltaX * 1.0 / (Constants.UNIT_DISTANCE * Constants.ZOOM)));
		int gridY = p.getY() + (int) (Math.floor(deltaY * 1.0 / (Constants.UNIT_DISTANCE * Constants.ZOOM)));

		Location locationReturn = new Location(gridX, gridY);

		if (MapManager.getCurrentMap() == null)
			return null;
		// if (!MapManager.getCurrentMap().isOutside(location))
		// return null;
		if (MapManager.getCurrentMap().getTile(locationReturn) == null)
			return null;
		if (!MapManager.getCurrentMap().isVisible(locationReturn.getX(), locationReturn.getY()))
			return null;

		return locationReturn;
	}

	/**
	 * This method is used to get a random boolean value.
	 * 
	 * @return The random boolean value.
	 */
	public static boolean randomBoolean() {
		double x = Math.random();
		return x < 0.5;
	}

	/**
	 * This method is used to get a random boolean value, only that is more
	 * likely to return true than false;
	 * 
	 * @return A boolean value.
	 */
	public static boolean randomBooleanTowardsTrue() {
		double x = Math.random();
		return x < 0.75;

	}

	/**
	 * This method is used to return a random boolean value, but with a greater
	 * probability of return a false. This method is the exact opposite of the
	 * {@code randomBooleanTowardsTrue()} canonically.
	 * 
	 * @return A boolean value.
	 */
	public static boolean randomBooleanTowardsFalse() {
		return !randomBooleanTowardsTrue();
	}
}
