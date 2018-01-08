package maps;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import factories.EnemySpawner;
import framework.Camera;
import framework.Constants;
import framework.Utilities;
import imageHandlers.ImageLoader;
import imageHandlers.Images;
import objects.GameObject;
import objects.Opaque;
import objects.Spreadable;
import objects.Stair;
import objects.Tile;
import objects.Walkable;
import objects.entities.Enemy;
import objects.entities.Entity;
import objects.entities.Player;
import objects.roomItems.Floor;

/**
 * This represents a Map which is a level in the game. It uses LinkedLists of
 * all the different objects and 2D arrays for storing the tiles and the
 * visibility map.
 * 
 * @author Rajarshi
 *
 */
public class Map {
	private LinkedList<GameObject> objects;
	private LinkedList<Tile> tiles;
	private LinkedList<GameObject> collectables;
	private LinkedList<Enemy> enemies;

	private Tile[][] tileMap;

	private int[][] visibilityMap;

	private int length;
	private int breadth;
	private final int ID;

	private Entity p;

	private Stair ascendingStair;
	private Stair descendingStair;

	private final EnemySpawner es;

	private ArrayList<Floor> walkableTiles;

	/**
	 * This creates a new map in which objects can be added. The length and
	 * breadth are used in maintaining the the tile maps and visibility arrays.
	 * The initial visibility of all the tiles are set to NONE. It also creates
	 * a new enemy spawner.
	 * 
	 * @param ID
	 *            - The level of the map.
	 * @param breadth
	 *            -The breadth of the Map.
	 * @param length
	 *            - The length of the map.
	 * 
	 * @see Constants.Transparency
	 */
	public Map(int ID, int breadth, int length) {

		this.ID = ID;
		this.length = length;
		this.breadth = breadth;

		objects = new LinkedList<>();
		collectables = new LinkedList<>();
		tiles = new LinkedList<>();
		enemies = new LinkedList<>();

		tileMap = new Tile[breadth + 10][length + 10];
		visibilityMap = new int[breadth + 10][length + 10];

		for (int i = 0; i < breadth + 10; i++) {
			for (int j = 0; j < length + 10; j++) {
				visibilityMap[i][j] = Constants.Transparency.NONE;
			}
		}
		es = new EnemySpawner();

		walkableTiles = new ArrayList<>();

	}

	/**
	 * This method updates all the game objects and entities except the player
	 * and also rendomly spawns enemies. This only updates the object state and
	 * not the graphcis. This method is called only when the player makes an
	 * action.
	 * 
	 * @see EnemySpawner
	 */
	public void tick() {
		for (Enemy ob : getEnemies()) {
			ob.tick(this);
		}
		for (Tile t : getTiles()) {
			t.tick(this);
		}

		// For all the objects , if they are spreadable, they must first spread
		// as else they would act on
		// objects that may have moved after updation.
		for (GameObject obj : getObjects()) {
			if (obj instanceof Spreadable) {
				Spreadable sprdbl = (Spreadable) obj;
				if (sprdbl.canSpread())
					sprdbl.spread(this);
			}
		}
		// after spreading only shoud they be allowed to cause damage further.
		for (GameObject obj : getObjects()) {
			obj.tick(this);
		}

		es.spawnEnemy(this);

		// System.out.println("Object list at 4 4 :
		// "+objectMap[4][4].toString());

	}

	/**
	 * Updates the player specifically.
	 */
	public void tickPlayer() {

		p.tick(this);
	}

	/**
	 * This updates the graphics and animation of all the game objects. This
	 * method is invoked multiple times per second to maintain the smooth
	 * animation.
	 */
	public void updateGraphics() {
		for (GameObject ob : getObjects()) {
			ob.updateGraphics();
		}
		p.updateGraphics();
		for (Enemy e : getEnemies()) {
			e.updateGraphics();
		}
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getBreadth() {
		return breadth;
	}

	public void setBreadth(int breadth) {
		this.breadth = breadth;
	}

	/**
	 * Draws the map including all the objects and the tiles. Also gives a
	 * shadow effect based on areas that are visited and those that are in the
	 * line of sight
	 * 
	 * @param g
	 *            - The graphics context used for drawing.
	 */
	public void render(Graphics2D g) {
		updateVisibility();

		renderBackground(g);
		renderForeground(g);

		renderShadows(g);
	}

	/**
	 * Updates the visibility of all the tiles of the map.If the tile is in the
	 * line of sight, than visibility is full.If not, then , if it has been
	 * visited earlier, then visibility is medium, else none.
	 * 
	 * To find the line of sight, it uses Bresenham's line algorithm.
	 */
	public void updateVisibility() {
		for (int i = 0; i < visibilityMap.length; i++) {
			for (int j = 0; j < visibilityMap[i].length; j++) {
				if (Camera.isInVeiw(i, j)) {
					// Point is in the frame.

					LinkedList<Point> points = Utilities.findLine(p.getX(), p.getY(), i, j);

					boolean isVisible = true;

					for (Point point : points) {
						int x = point.x;
						int y = point.y;

						if ((tileMap[x][y] instanceof Opaque)) {
							isVisible = false;
							break;
						}

					}

					if (isVisible) {
						visibilityMap[i][j] = Constants.Transparency.FULL;
					} else {
						if (visibilityMap[i][j] == Constants.Transparency.FULL) {

							visibilityMap[i][j] = Constants.Transparency.MEDIUM;
						}
					}

				}
			}
		}
	}

	/**
	 * This method acutually casts the shadows and draws the transparency
	 * layers.To blur out the shadow effect, tiles adjacent to no visibility
	 * areas are masked with a gradient to make the transition smooth.
	 * 
	 * @param g
	 *            - The graphics context.
	 */
	public void renderShadows(Graphics2D g) {
		for (int i = 0; i < visibilityMap.length; i++) {
			for (int j = 0; j < visibilityMap[i].length; j++) {
				if (Camera.isInVeiw(i, j)) {
					// System.out.println(visibilityMap[i][j]+" "+i+ " "+j);

					// In all drawings an xtra padding is given

					Images image = Utilities.getTransparencyConstant(visibilityMap[i][j]);
					if (image != null) {
						if (image == Images.NO_TRANSPARENCY_MASK) {
							Color c = g.getColor();
							g.setColor(Color.BLACK);
							g.fillRect(i * Constants.UNIT_DISTANCE - 2, j * Constants.UNIT_DISTANCE - 2,
									Constants.UNIT_DISTANCE + 4, Constants.UNIT_DISTANCE + 4);
							g.setColor(c);
						} else {
							Utilities.renderImage(g, ImageLoader.INSTANCE().getImage(image, 0),
									i * Constants.UNIT_DISTANCE, j * Constants.UNIT_DISTANCE, Constants.UNIT_DISTANCE,
									Constants.UNIT_DISTANCE, 0);
						}
					}
				}
			}
		}

		// cast semi shadows for blocks adjacent to shadowed regions.
		int u = Constants.UNIT_DISTANCE;
		for (int i = 0; i < visibilityMap.length; i++) {
			for (int j = 0; j < visibilityMap[i].length; j++) {
				if (Camera.isInVeiw(i, j)) {
					if (visibilityMap[i][j] != Constants.Transparency.NONE) {

						// BE CAREFUL :: i actually is the y co-ordinate and j
						// is
						// the x-co-ordinate.

						Images image = null;
						if (i - 1 < 0 || visibilityMap[i - 1][j] == Constants.Transparency.NONE) {

							image = Images.LEFT_SHADOW_TRANSPARENCY_MASK;
						}
						if (image != null)
							Utilities.renderImage(g, ImageLoader.INSTANCE().getImage(image, 0), i * u, j * u, u, u, 0);
						image = null;

						if (i + 1 > visibilityMap.length || visibilityMap[i + 1][j] == Constants.Transparency.NONE) {
							image = Images.RIGHT_SHADOW_TRANSPARENCY_MASK;

						}
						if (image != null)
							Utilities.renderImage(g, ImageLoader.INSTANCE().getImage(image, 0), i * u, j * u, u, u, 0);
						image = null;
						if (j - 1 < 0 || visibilityMap[i][j - 1] == Constants.Transparency.NONE) {
							image = Images.UP_SHADOW_TRANSPARENCY_MASK;
						}
						if (image != null)
							Utilities.renderImage(g, ImageLoader.INSTANCE().getImage(image, 0), i * u, j * u, u, u, 0);

						image = null;
						if (j + 1 > visibilityMap[i].length || visibilityMap[i][j + 1] == Constants.Transparency.NONE) {
							image = Images.BOTTOM_SHADOW_TRANSPARENCY_MASK;
						}
						if (image != null)
							Utilities.renderImage(g, ImageLoader.INSTANCE().getImage(image, 0), i * u, j * u, u, u, 0);

					}

				}
			}
		}
	}

	/**
	 * Renders the foreground objects including the player, enemies and objects,
	 * if they are within the Camera's viewport.
	 * 
	 * @param g
	 *            - The graphics context.
	 */
	public void renderForeground(Graphics2D g) {

		p.render(g);
		for (GameObject obj : getEnemies()) {

			if (Camera.isInView(obj) && visibilityMap[obj.getX()][obj.getY()] == Constants.Transparency.FULL) {// &&
				obj.render(g);
			}
		}
		for (GameObject obj : getObjects()) {
			if (Camera.isInView(obj) && visibilityMap[obj.getX()][obj.getY()] == Constants.Transparency.FULL) {// &&
				obj.render(g);
			}
		}

	}

	/**
	 * Renders the background objects including the collectable items and tiles,
	 * if they are within the Camera's viewport.
	 * 
	 * @param g
	 *            - The graphics context.
	 */
	public void renderBackground(Graphics2D g) {
		for (GameObject ob : getTiles()) {
			if (Camera.isInView(ob)) {
				ob.render(g);
				// if (p.isWithinRange(ob.getX(), ob.getY(), 4)) {
				// ob.render(g);
				// ((Tile) ob).traverse();
				// } else {
				//
				// ((Tile) ob).renderDarkerShade(g);
				// }

			}
		}

		for (GameObject ob : getCollectables()) {
			if (Camera.isInView(ob) && visibilityMap[ob.getX()][ob.getY()] == Constants.Transparency.FULL) {// &&
				ob.render(g);
			}
		}
	}

	/*
	 * Getters setters and other add,remove functions
	 * 
	 * 
	 */
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addObject(GameObject ob) {
		objects.add(ob);
	}

	public void removeEnemy(Enemy ob) {
		enemies.remove(ob);
	}

	public void addEnemy(Enemy ob) {
		enemies.add(ob);
	}

	public void removeObject(GameObject ob) {
		objects.remove(ob);
	}

	public void addTile(Tile ob) {
		tiles.add(ob);
		tileMap[ob.getX()][ob.getY()] = ob;

		if (ob instanceof Floor)
			addWalkableTile(ob);
	}

	public void removeTile(Tile ob) {
		tiles.remove(ob);
		tileMap[ob.getX()][ob.getY()] = null;

		if (ob instanceof Floor)
			removeWalkableTile(ob);

	}

	public void addCollectable(GameObject ob) {
		collectables.add(ob);
	}

	public void removeCollectable(GameObject ob) {
		collectables.remove(ob);
	}

	public int getID() {
		return ID;
	}

	@SuppressWarnings("unchecked")
	public LinkedList<Tile> getTiles() {
		return (LinkedList<Tile>) tiles.clone();
	}

	@SuppressWarnings("unchecked")
	public LinkedList<GameObject> getObjects() {
		return (LinkedList<GameObject>) objects.clone();
	}

	@SuppressWarnings("unchecked")
	public LinkedList<GameObject> getCollectables() {
		return (LinkedList<GameObject>) collectables.clone();
	}

	@SuppressWarnings("unchecked")
	public LinkedList<Enemy> getEnemies() {
		return (LinkedList<Enemy>) enemies.clone();
	}

	public boolean isWalkable(int x, int y) {
		return tileMap[x][y] instanceof Walkable;
	}

	public Tile getTile(int x, int y) {

		return tileMap[x][y];
	}

	public void initPlayer() {
		p = Player.getINSTANCE();
	}

	public Stair getAscendingStair() {
		return ascendingStair;
	}

	public void setAscendingStair(Stair ascendingStair) {
		this.ascendingStair = ascendingStair;
	}

	public Stair getDescendingStair() {
		return descendingStair;
	}

	public void setDescendingStair(Stair descendingStair) {
		this.descendingStair = descendingStair;
	}

	public boolean isVisible(int x, int y) {
		return visibilityMap[x][y] == Constants.Transparency.FULL;
	}

	public boolean isWithin(Location location) {
		return (location.getX() >= 0 && location.getX() < breadth)
				&& (location.getY() >= 0 && location.getY() < length);
	}

	public boolean isOutside(Location location) {
		return !isWithin(location);
	}

	public Tile getTile(Location latestLocation) {
		if (isOutside(latestLocation))
			return null;
		return getTile(latestLocation.getX(), latestLocation.getY());
	}

	private void addWalkableTile(Tile t) {
		if (t instanceof Floor) {
			walkableTiles.add((Floor) t);
		}
	}

	private void removeWalkableTile(Tile t) {
		if (t instanceof Floor) {
			walkableTiles.remove((Floor) t);
		}
	}

	/**
	 * This method returns a random floor location that may or may not be
	 * occupied.
	 * 
	 * @return a Location
	 */
	private Location getRandomFloor_HELPER() {
		return walkableTiles.get(new Random().nextInt(walkableTiles.size())).getLocation();
	}

	/**
	 * This method returns a random floor location that is not occupied.
	 * 
	 * @return a Location
	 */
	public Location getRandomFloor() {
		while (true) {
			Location randomLocation = getRandomFloor_HELPER();
			if (Player.getINSTANCE().getLocation().equals(randomLocation))
				continue;
			for (Enemy enemy : enemies) {
				if (enemy.getLocation().equals(randomLocation))
					continue;
			}

			return randomLocation;
		}
	}
}
