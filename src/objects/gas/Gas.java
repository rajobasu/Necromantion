package objects.gas;

import maps.Map;
import objects.GameObject;
import objects.Spreadable;
import objects.TurnBased;
import objects.entities.Player;
import objects.roomItems.Door;
import objects.roomItems.Wall;

public abstract class Gas extends GameObject implements TurnBased, Spreadable {

	protected int turnsLeft;
	protected int spreadQuotient;

	public Gas(int x, int y, int turnsLeft, int spreadQ) {
		super(x, y, 0);
		this.turnsLeft = turnsLeft;
		p = Player.getINSTANCE();
		spreadQuotient = spreadQ;
	}

	/**
	 * Spread the gas to all the neighboring cells as long as the
	 * {@code spreadQuotient} is positive.
	 */
	public void spread(Map map) {
		for (GameObject ob : map.getTiles()) {
			if (ob.isNearby(x, y)) {
				if (ob instanceof Wall || (ob instanceof Door && !((Door) ob).isLocked())) {
					continue;
				}
				// found ~~> is there any gas already there
				boolean found = false;
				int xx = ob.getX();
				int yy = ob.getY();
				for (GameObject o : map.getObjects()) {
					if (o instanceof Gas && o.isColliding(xx, yy)) {
						found = true;
						break;
					}
				}
				if (!found) {
					map.addObject(getNewInstance(ob.getX(), ob.getY(), spreadQuotient - 1));
				}
			}
		}
		spreadQuotient = 0;
	}

	/**
	 * checks if the {@code Gas} can spread further.
	 */
	public boolean canSpread() {
		return spreadQuotient > 0;
	}

	public void identify() {
	}

	protected abstract Gas getNewInstance(int x, int y, int sp);
}
