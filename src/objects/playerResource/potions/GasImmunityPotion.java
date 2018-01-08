package objects.playerResource.potions;

import framework.Constants;
import maps.Map;
import objects.entities.Entity.Effect;
import objects.entities.Player;

public class GasImmunityPotion extends Potion {

	public GasImmunityPotion(int x, int y) {
		super(x, y, 5);

	}

	@Override
	public void use(Map map) {
		Player.getINSTANCE().addEffect(Effect.GAS_IMMUNITY, 10);
	}

	@Override
	public String getInfo() {
		if (!Constants.hasBeenIdentified(colour))
			return super.getInfo();

		return "This is a potion which makes you immune to all gases!";
	}

}
