package objects.traps;

import java.awt.Graphics2D;

import framework.Constants;
import imageHandlers.Images;
import maps.Map;
import objects.gas.ToxicGas;

public class ToxicGasTrap extends Trap {

	public ToxicGasTrap(int x, int y, int imageCode) {
		super(x, y, imageCode);
	}

	@Override
	public void tick(Map map) {
		if (!activated)
			return;

		super.tick(map);
		if (shouldActivate(map)) {
			deactivate();
			ToxicGas tg = null;
			tg = new ToxicGas(x, y, Constants.SPREAD_QUOTIENT);
			map.addObject(tg);

		}
	}

	@Override
	public void render(Graphics2D g) {
		int u = Constants.UNIT_DISTANCE;
		if (!activated) {
			g.drawImage(imgLdr.getImage(Images.DEACTIVATED_TRAP, imageCode), x * u, y * u, u, u, null);
		} else if (detected) {
			g.drawImage(imgLdr.getImage(Images.TOXIC_GAS_TRAP, imageCode), x * u, y * u, u, u, null);
		} else {
			g.drawImage(imgLdr.getImage(Images.FLOOR, imageCode), x * u, y * u, u, u, null);
		}
	}

	@Override
	public void updateGraphics() {

	}

	@Override
	public String getInfo() {
		if (detected) {
			return "This is a ToxicGas trap. Step away, or you will be immersed in a cloud of poisonous gas";
		} else {
			return super.getInfo();
		}

	}
}
