package objects.playerResource.potions;

import framework.Constants;
import maps.Map;
import objects.entities.Player;
import objects.entities.Entity.Effect;

public class PoisonPotion extends Potion {

	public PoisonPotion(int x, int y) {
		super(x, y, 6);
	}

	
	@Override
	public void use(Map map) {
		Player.getINSTANCE().addEffect(Effect.POISONED, 3);
	}

	@Override
	public String getInfo() {
		if (!Constants.hasBeenIdentified(colour))
			return super.getInfo();

		return "This is a potion of poisonous nature. Drinking this is weakening at best and deadly at worst.";
	}

}
