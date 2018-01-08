package objects.traps;

import java.awt.Graphics2D;

import framework.Constants;
import imageHandlers.Images;
import maps.Map;
import objects.entities.Player;
import objects.misc.Fire;

public class FireTrap extends Trap {

	public FireTrap(int x, int y, int imageCode) {
		super(x, y, imageCode);
		// TODO Auto-generated constructor stub
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
			Fire tg = new Fire(x, y, Constants.MAX_FIRE_TURNS);

			map.addObject(tg);
		}

	}

	@Override
	public void render(Graphics2D g) {
		int u = Constants.UNIT_DISTANCE;
		if (!activated) {
			g.drawImage(imgLdr.getImage(Images.DEACTIVATED_TRAP, imageCode), x * u, y * u, u, u, null);
		} else if (detected) {
			g.drawImage(imgLdr.getImage(Images.FIRE_TRAP, imageCode), x * u, y * u, u, u, null);
		} else {
			g.drawImage(imgLdr.getImage(Images.FLOOR, imageCode), x * u, y * u, u, u, null);
		}

	}

	@Override
	public void updateGraphics() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getInfo() {
		if (detected) {
			return "A fiery trap...step on it and you will burn...";
		}
		return super.getInfo();
	}
}
