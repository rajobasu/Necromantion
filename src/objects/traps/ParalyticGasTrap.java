package objects.traps;

import java.awt.Graphics2D;

import framework.Constants;
import imageHandlers.Images;
import maps.Map;
import objects.entities.Player;
import objects.gas.ParalysisGas;

public class ParalyticGasTrap extends Trap {

	public ParalyticGasTrap(int x, int y, int imageCode) {
		super(x, y, imageCode);
		image = Images.PARALYTIC_GAS_TRAP;
	}

	@Override
	public void tick(Map map) {
		if (!activated)
			return;

		if (p == null)
			p = Player.getINSTANCE();

		if (p.isWithinRange(x, y, 3)) {
			detected = true;
		}

		if (shouldActivate(map)) {
			deactivate();
			ParalysisGas tg = null;
			tg = new ParalysisGas(x, y, Constants.SPREAD_QUOTIENT);
			map.addObject(tg);

		}
	}

	@Override
	public void render(Graphics2D g) {
		int u = Constants.UNIT_DISTANCE;
		if (!activated) {
			g.drawImage(imgLdr.getImage(Images.DEACTIVATED_TRAP, imageCode), x * u, y * u, u, u, null);
		} else if (detected) {
			g.drawImage(imgLdr.getImage(image, imageCode), x * u, y * u, u, u, null);
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
			return "A gas to paralyse you... beware !";
		}
		return super.getInfo();
	}

}
