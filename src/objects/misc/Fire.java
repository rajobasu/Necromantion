package objects.misc;

import java.awt.Graphics2D;
import java.util.LinkedList;

import animations.AnimationType;
import animations.FireAnimation;
import framework.Constants;
import imageHandlers.Images;
import maps.Map;
import objects.Burnable;
import objects.GameObject;
import objects.Spreadable;
import objects.TurnBased;
import objects.entities.Enemy;
import objects.entities.Player;
import objects.roomItems.WaterFloor;

public class Fire extends GameObject implements Spreadable, TurnBased {

	private int damage;
	private int turnsLeft;
	private FireAnimation animation;

	public Fire(int x, int y, int turnsLeft) {
		super(x, y, 0);
		damage = 3;
		this.turnsLeft = turnsLeft;
		image = Images.FIRE;

		animation = new FireAnimation(AnimationType.LOOP_BACK_WHEN_END);
	}

	@Override
	public void tick(Map map) {
		if (turnsLeft <= 0) {
			map.removeObject(this);
			return;
		}
		decreaseTurnsLeft();

		if (p == null) {
			p = Player.getINSTANCE();
		}

		// spread(map); the objects are made to spread from the map itself.
		boolean hasFuel = false;

		if (isColliding(p.getX(), p.getY())) {
			p.decreaseLife(damage);
		}

		if (p.isColliding(x, y)) {
			hasFuel = true;
		}

		LinkedList<Enemy> objects = map.getEnemies();

		for (GameObject ob : objects) {
			if (ob instanceof Enemy && ob.isColliding(x, y)) {
				hasFuel = true;
				((Enemy) ob).decreaseLife(damage);
				break;
			}
		}

		if (map.getTile(x, y) instanceof Burnable) {
			hasFuel = true;
			((Burnable) map.getTile(x, y)).burn(map);
		}

		if (!hasFuel) {
			map.removeObject(this);
		} else {
			turnsLeft++;
		}

	}

	@Override
	public void render(Graphics2D g) {
		int u = Constants.UNIT_DISTANCE;
		g.drawImage(imgLdr.getImage(image, animation.getSceneIndex()), x * u, y * u, u, u, null);

	}

	@Override
	public void updateGraphics() {
		animation.updateAnimation();

	}

	@Override
	public void spread(Map map) {

		LinkedList<GameObject> objects = map.getObjects();
		for (GameObject ob : map.getTiles()) {
			if (ob instanceof Burnable) {
				if (ob.isNearby(x, y) && !ob.isColliding(x, y)) {
					boolean alp = false;// if fire is already present
					for (GameObject obj : objects) {
						if (obj instanceof Fire && obj.isColliding(ob.getX(), ob.getY())) {
							alp = true;
							break;
						}
					}

					if (!alp && !(map.getTile(ob.getX(), ob.getY()) instanceof WaterFloor))
						map.addObject(new Fire(ob.getX(), ob.getY(), turnsLeft));
				}
			}
		}

		for (GameObject ob : map.getEnemies()) {
			if (ob instanceof Burnable) {
				if (ob.isNearby(x, y) && !ob.isColliding(x, y)) {
					boolean alp = false;// if fire is already present
					for (GameObject obj : objects) {
						if (obj instanceof Fire && obj.isColliding(ob.getX(), ob.getY())) {
							alp = true;
							break;
						}
					}

					if (!alp && !(map.getTile(ob.getX(), ob.getY()) instanceof WaterFloor))
						map.addObject(new Fire(ob.getX(), ob.getY(), turnsLeft));
				}
			}
		}

		GameObject ob = Player.getINSTANCE();
		if (ob instanceof Burnable) {
			if (ob.isNearby(x, y) && !ob.isColliding(x, y)) {
				boolean alp = false;// if fire is already present
				for (GameObject obj : objects) {
					if (obj instanceof Fire && obj.isColliding(ob.getX(), ob.getY())) {
						alp = true;
						break;
					}
				}

				if (!alp && !(map.getTile(ob.getX(), ob.getY()) instanceof WaterFloor))
					map.addObject(new Fire(ob.getX(), ob.getY(), turnsLeft - 1));
			}
		}

	}

	@Override
	public void decreaseTurnsLeft() {
		turnsLeft--;
	}

	/**
	 * This method should not be called.
	 */
	@Override
	public boolean canSpread() {
		return false;
	}

}
