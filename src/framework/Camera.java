package framework;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import framework.engines.GameEngine;
import objects.GameObject;
import objects.entities.Entity;

public class Camera {

	private static int offsetX;
	private static int offsetY;
	private static Entity reference;

	private static Point centerOfScreen;

	public static void test(Graphics2D g) {
		Color c = g.getColor();
		g.setColor(Color.RED);

		g.draw(getViewPort());
		g.setColor(c);
	}

	public static Rectangle getViewPort() {
		return new Rectangle(offsetX, offsetY, GameEngine.INSTANCE.getBreadth() * Constants.UNIT_DISTANCE,
				GameEngine.INSTANCE.getLength() * Constants.UNIT_DISTANCE);
	}

	public static void initialize(Entity reference) {
		Camera.reference = reference;

		int x = Constants.UNIT_DISTANCE * GameEngine.INSTANCE.getBreadth() / 2;
		int y = Constants.UNIT_DISTANCE * GameEngine.INSTANCE.getLength() / 2;

		centerOfScreen = new Point(x, y);

		// System.out.println(centerOfScreen);
		offsetX = (int) (reference.getX() * Constants.UNIT_DISTANCE - centerOfScreen.getX());
		offsetY = (int) (reference.getY() * Constants.UNIT_DISTANCE - centerOfScreen.getY());

		// System.out.println(offsetX);
		// System.out.println(offsetY);

	}

	public static void update() {
		offsetX = (int) (reference.getActualX() * Constants.ZOOM - centerOfScreen.getX());
		offsetY = (int) (reference.getActualY() * Constants.ZOOM - centerOfScreen.getY());

		// if (offsetX < 0)
		// offsetX = 0;
		// if (offsetY < 0)
		// offsetY = 0;
		//
		// if (offsetX+Constants.BREADTH> currentMap.getBreadth())
		// offsetX = currentMap.getBreadth()-Constants.BREADTH;
		// if (offsetY + Constants.LENGTH > currentMap.getLength())
		// offsetY = currentMap.getLength() - Constants.LENGTH;
	}

	public static int getOffsetX() {
		return offsetX;
	}

	public static int getOffsetY() {
		return offsetY;
	}

	public static boolean isInView(GameObject object) {

		Rectangle veiwport = getViewPort();

		Rectangle objectArea = new Rectangle((int) (object.getX() * Constants.UNIT_DISTANCE * Constants.ZOOM),
				(int) (object.getY() * Constants.UNIT_DISTANCE*Constants.ZOOM), (int) (Constants.UNIT_DISTANCE * Constants.ZOOM),
				(int) (Constants.UNIT_DISTANCE * Constants.ZOOM));

		return veiwport.contains(objectArea);
	}

	public static boolean isInVeiw(int x, int y) {
		Rectangle veiwport = getViewPort();
		Rectangle objectArea = new Rectangle((int) (x* Constants.UNIT_DISTANCE * Constants.ZOOM),
				(int) (y * Constants.UNIT_DISTANCE*Constants.ZOOM), (int) (Constants.UNIT_DISTANCE * Constants.ZOOM),
				(int) (Constants.UNIT_DISTANCE * Constants.ZOOM));
		return veiwport.contains(objectArea);

	}

	private Camera() {
	}

	public static ScreenDimension getViewportDimension() {
		int stX = offsetX / Constants.UNIT_DISTANCE;
		int stY = offsetY / Constants.UNIT_DISTANCE;

		int endX = (GameEngine.INSTANCE.getBreadth() * Constants.UNIT_DISTANCE + offsetX) / Constants.UNIT_DISTANCE + 1;
		int endY = (GameEngine.INSTANCE.getLength() * Constants.UNIT_DISTANCE + offsetY) / Constants.UNIT_DISTANCE + 1;

		return new ScreenDimension(stX, endX, stY, endY);
	}

}
