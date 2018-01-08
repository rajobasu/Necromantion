package objects.playerResource.food;

import maps.Map;
import objects.Collectable;
import objects.GameObject;
import objects.entities.Player;

public abstract class Food extends GameObject implements Collectable {

	private int value;
	protected static final int FULL = -1;

	public Food(int x, int y, int value) {
		super(x, y, 0);
		this.value = value;
	}

	public int getValue() {
		if (value == FULL)
			return Player.STARVE_LIMIT;

		return value;
	}

	public void updateGraphics() {
	}

	@Override
	public void tick(Map map) {
		// TODO Auto-generated method stub

	}

	public void use(Map map) {
		Player.getINSTANCE().eatFood(this);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Food";
	}

	@Override
	public String getInfo() {
		return "A ration to satisfy your hunger for the time being.";
	}
}
