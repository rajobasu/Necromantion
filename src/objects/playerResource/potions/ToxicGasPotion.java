package objects.playerResource.potions;

import framework.Constants;
import imageHandlers.Images;
import maps.Map;
import objects.entities.Player;
import objects.gas.ToxicGas;

public class ToxicGasPotion extends Potion {

	public ToxicGasPotion(int x, int y) {
		super(x, y, 1);
		image = Images.POTION;
	}

	@Override
	public String getInfo() {
		if (!Constants.hasBeenIdentified(colour)) return super.getInfo();
		return "This is a potion of toxic gas. DOnt drink this! It might be a bad idea.... or maybe a good one ... ";
	}

	@Override
	public void use(Map map) {
		if (p == null) {
			p = Player.getINSTANCE();
		}

		ToxicGas tg = null;
		tg = new ToxicGas(p.getX(), p.getY(), Constants.SPREAD_QUOTIENT);
		map.addObject(tg);
	}

}
