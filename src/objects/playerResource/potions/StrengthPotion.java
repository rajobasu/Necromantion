package objects.playerResource.potions;

import framework.Constants;
import imageHandlers.Images;
import maps.Map;
import objects.entities.Player;

public class StrengthPotion extends Potion {

	public StrengthPotion(int x, int y) {
		super(x, y, 2);
		image = Images.POTION;
	}

	@Override
	public void use(Map map) {
		if (p == null) p = Player.getINSTANCE();
		p.increaseStrength();
	}

	@Override
	public String getInfo() {
		if (!Constants.hasBeenIdentified(colour)) return super.getInfo();

		return "A strength potion! Drink this to revigorate yourself and increase your strength.";
	}
}
