package factories;

import java.util.LinkedList;

import maps.Map;
import maps.MapManager;
import objects.entities.Enemy;
import objects.entities.Player;
import squidpony.squidmath.XoRoRNG;

/**
 * This factory is used to generate new Random Enemies.
 * 
 * @author rajob
 *
 */
public class EnemySpawner {

	private final int MAX_ENEMY_COUNT = 5;
	private final XoRoRNG rng = new XoRoRNG();
	private final int SPAWN_PROBABILITY = 5;// out of 100

	private Player player;

	public EnemySpawner() {
		player = Player.getINSTANCE();
	}

	/**
	 * create a random enemy and place it in a random location in the map. It
	 * will create a new enemy based on the {@code ENEMY_COUNT}
	 * 
	 * @param map
	 *            - The map where the enemy has to be placed.
	 */
	public void spawnEnemy(Map map) {
		if (player == null) player = Player.getINSTANCE();

		LinkedList<Enemy> enemies = map.getEnemies();

		if (enemies.size() > MAX_ENEMY_COUNT + MapManager.getCurrentLevel()) return;
		if (rng.nextInt(100) < SPAWN_PROBABILITY*map.getID()) {
			while (true) {
				int x = rng.nextInt(map.getBreadth());
				int y = rng.nextInt(map.getLength());
				if (map.isWalkable(x, y) && !player.isWithinRange(x, y, 10)) {
					boolean canPut = true;
					for (Enemy e : enemies) {
						if (e.isColliding(x, y)) {
							canPut = false;
							break;
						}
					}
					if (canPut) {
						map.addEnemy(RandomEnemyGenerator.getRandomEnemy(x, y , map.getID()));
						return;
					}
				}
			}
		}

	}

}
