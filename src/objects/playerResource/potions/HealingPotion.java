package objects.playerResource.potions;

import framework.Constants;
import maps.Map;
import objects.entities.Player;

public class HealingPotion extends Potion {

	public HealingPotion(int x, int y) {
		super(x, y, 0);
	}

	@Override
	public void use(Map map) {
		if (p == null) {
			p = Player.getINSTANCE();
		}
		p.increaseLife(p.getMaxLife() - p.getLife());
	}

	@Override
	public String getInfo() {

		if (!Constants.hasBeenIdentified(colour))
			return super.getInfo();

		return "One of the most useful potions, this is a potion with healing capabilities which will increase your health!";
	}

}
