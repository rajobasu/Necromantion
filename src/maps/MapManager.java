package maps;

import java.util.LinkedList;

import objects.Stair;
import objects.entities.Player;

/**
 * Manages all the map levels and stores them. Also stores the refernce to the
 * currently active map. Also stores the current level.
 * 
 * @author Rajarshi
 * @see Map
 */
public class MapManager {

	private LinkedList<Map> maps;
	private static Map currentMap;
	private static int level;
	private int maxLevel;

	/**
	 * creates a new Map manager with a empty list of maps and default level of
	 * 1.
	 */
	public MapManager() {
		maps = new LinkedList<>();
		level = 1;
	}

	public void addNewMap(Map map) {
		maps.add(map);
		maxLevel++;
	}

	/**
	 * sets the current map to the given parameter.
	 * 
	 * @param map
	 *            - The current Map.
	 */
	public void setCurrentMap(Map map) {
		if (currentMap == null) {
			currentMap = map;
		}
	}

	/**
	 * checks if the level can be increased by checking if the player is
	 * standing over the correct Stair. If possible , then the current level map
	 * is set to the next one.
	 * 
	 * @see Stair
	 */
	public void increaseLevel() {
		currentMap = maps.get(level++);
		System.out.println("level increased");
		// Set the new Player Location on the Stair

		Player p = Player.getINSTANCE();
		Stair asc = currentMap.getAscendingStair();

		p.setX(asc.getX());
		p.setY(asc.getY());
	}

	/**
	 * checks if the level can be decreased by checking if the player is
	 * standing over the correct Stair.If possible , then the current level map
	 * is set to the previous one.
	 * 
	 * @see Stair
	 */
	public void decreaseLevel() {
		currentMap = maps.get(--level - 1);
		// Set the new Player Location on the Stair
		System.out.println("level decreased");
		Player p = Player.getINSTANCE();
		Stair asc = currentMap.getDescendingStair();

		p.setX(asc.getX());
		p.setY(asc.getY());

	}

	public static int getCurrentLevel() {
		return level;
	}

	public int getMaxLevel() {
		return maxLevel;
	}

	public static Map getCurrentMap() {
		return currentMap;
	}
}
