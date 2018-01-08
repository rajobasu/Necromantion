package objects.playerResource.potions;

import framework.Constants;
import imageHandlers.Images;
import maps.Map;
import objects.entities.Player;
import objects.misc.Fire;

public class FirePotion extends Potion {

	public FirePotion(int x, int y) {
		super(x, y, 3);
		image = Images.POTION;
	}

	@Override
	public String getInfo() {
		if (!Constants.hasBeenIdentified(colour))
			return super.getInfo();
		return "A rather ingenious potion, useful in some cases, this will start a fire.";
	}

	@Override
	public void use(Map map) {
		if (p == null)
			p = Player.getINSTANCE();

		map.addObject(new Fire(p.getX(), p.getY(), Constants.MAX_FIRE_TURNS));
	}

}
