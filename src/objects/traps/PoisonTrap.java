package objects.traps;

import java.awt.Graphics2D;

import framework.Constants;
import imageHandlers.Images;
import maps.Map;
import objects.entities.Entity.Effect;

public class PoisonTrap extends Trap {

	public PoisonTrap(int x, int y, int imageCode) {
		super(x, y, imageCode);
	}

	@Override
	public void tick(Map map) {

		if (!activated)
			return;

		super.tick(map);
		if (shouldActivate(map)) {
			collidingWith(map).addEffect(Effect.POISONED, 4);
			System.out.println("Poison Trap activated");
			deactivate();
		}
	}

	@Override
	public void render(Graphics2D g) {
		int u = Constants.UNIT_DISTANCE;
		if (!activated) {
			g.drawImage(imgLdr.getImage(Images.DEACTIVATED_TRAP, imageCode), x * u, y * u, u, u, null);
		} else if (detected) {
			g.drawImage(imgLdr.getImage(Images.POISON_TRAP, imageCode), x * u, y * u, u, u, null);
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
			return "Be alert, the poison can hurt for long, causing long term damage";
		}
		return super.getInfo();
	}
}
