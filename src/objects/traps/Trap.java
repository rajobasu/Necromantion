package objects.traps;

import framework.Utilities;
import imageHandlers.Images;
import maps.Map;
import objects.Tile;
import objects.Walkable;
import objects.entities.Enemy;
import objects.entities.Entity;
import objects.entities.Player;

public abstract class Trap extends Tile implements Walkable {

	protected boolean activated;
	protected boolean detected;

	@Override
	public void tick(Map map) {
		if (p == null)
			p = Player.getINSTANCE();

		if (p.isWithinRange(x, y, 3) && Utilities.randomBooleanTowardsFalse()) {
			detected = true;
		}

	}

	public Trap(int x, int y, int imageCode) {
		super(x, y, Images.FLOOR);
		activated = true;
		detected = false;
	}

	/**
	 * Deactivates the trap.
	 */
	public void deactivate() {
		activated = false;
	}

	/**
	 * Checks if this trap should be activated by checking if any {@code Entity}
	 * is currently stepping upon this trap.
	 * 
	 * @param map
	 *            The current Map.
	 * @return Whether this {@code Trap} should be activated.
	 */
	protected boolean shouldActivate(Map map) {
		if (p.isColliding(x, y))
			return true;
		for (Enemy e : map.getEnemies()) {
			if (e.isColliding(x, y))
				return true;
		}

		return false;
	}

	/**
	 * Finds the {@code Entity} that is colliding with this element in the
	 * current map, null if nothing collides.
	 * 
	 * @param map
	 *            The current map
	 * @return The {@code Entity} which is colliding with this current element,
	 *         null if none are.
	 */
	protected Entity collidingWith(Map map) {
		if (p.isColliding(x, y))
			return p;
		for (Enemy e : map.getEnemies()) {
			if (e.isColliding(x, y))
				return e;
		}

		return null;

	}

	@Override
	public String getInfo() {
		return "This seems to be some kind of a trap.";
	}
}
