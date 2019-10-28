package objects.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;

import framework.Constants;
import framework.Utilities;
import maps.Map;
import objects.GameObject;
import objects.entities.Player.SkillLevelImprovement;
import objects.roomItems.Door;
import objects.roomItems.Wall;

public abstract class Enemy extends Entity {

	public Enemy(int x, int y, int imageCode, int maxLife, int defenseStrength, int attackStrength) {
		super(x, y, imageCode, maxLife);
		this.defenseStrength = defenseStrength;
		this.attackStrength = attackStrength;
	}

	public void render(Graphics2D g) {
		int u = Constants.UNIT_DISTANCE;
		g.setColor(Color.RED);
		g.fillRect(x * u + 2, y * u, u - 2, 4);
		g.setColor(Color.green);
		int width = u - 3;
		g.fillRect(x * u + 2, y * u, (int) Math.floor(((double) life * width) / maxLife), 4);
	}

	/**
	 * This method encapsulates the AI according to which the enemy moves.
	 * 
	 * @param map
	 */
	public void move(Map map) {
		int dest_x = p.getX();
		int dest_y = p.getY();

		prevX = x;
		prevY = y;

		if (dest_x > x) {
			x++;
			if (!map.isWalkable(x, y) || checkEntityCollision(map.getEnemies())) {
				x--;
			}
		} else if (dest_x < x) {
			x--;
			if (!map.isWalkable(x, y) || checkEntityCollision(map.getEnemies())) {
				x++;
			}
		}
		if (dest_y > y) {
			y++;
			if (!map.isWalkable(x, y) || checkEntityCollision(map.getEnemies())) {
				y--;
			}
		} else if (dest_y < y) {
			y--;
			if (!map.isWalkable(x, y) || checkEntityCollision(map.getEnemies())) {
				y++;
			}
		}

	}

	/**
	 * Checks if the object on which the method is invoked is colliding with any of
	 * the objects in the List passed as a parameter
	 * 
	 * @param objects - List of the GameObjects
	 * @return - true is there is a collision, false otherwise
	 */

	public boolean checkCollision(LinkedList<GameObject> objects) {

		for (GameObject ob : objects) {
			if (ob.isColliding(x, y)) {

				if (ob instanceof Wall || ob instanceof Entity) {
					return true;
				}
				if (ob instanceof Door) {

					Door d = (Door) ob;
					if (d.isLocked()) {
						return true;
					}
				}

				break;
			}
		}

		return false;
	}

	/**
	 * Checks if the object on which the method is invoked is colliding with any of
	 * the Entities in the List passed as a parameter
	 * 
	 * @param objects - List of the Entities
	 * @return - true is there is a collision, false otherwise
	 */
	public boolean checkEntityCollision(LinkedList<Enemy> enemyList) {
		for (Enemy e : enemyList) {
			if (e.isColliding(x, y) && e != this) {
				return true;
			}
		}

		return false;
	}

	public void tick(Map map) {
		super.tick(map);
		if (life > 0)
			return;

		Player.getINSTANCE().updateSkillLevel(SkillLevelImprovement.ENEMY_KILLED);
		map.removeEnemy(this);
	}

	protected int attackEnemy(Entity p) {
		int x = Utilities.getDamage(attackStrength, p.getDefenseStrength(), 0);
		p.decreaseLife(Math.max(x, 0));

		return x;
	}

}
