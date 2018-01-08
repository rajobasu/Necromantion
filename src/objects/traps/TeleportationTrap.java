package objects.traps;

import java.awt.Graphics2D;

import framework.Constants;
import imageHandlers.Images;
import maps.Map;

public class TeleportationTrap extends Trap {

	public TeleportationTrap(int x, int y, int imageCode) {
		super(x, y, imageCode);
	}

	@Override
	public void tick(Map map) {
		if (!activated)
			return;

		super.tick(map);
		if (shouldActivate(map)) {
			collidingWith(map).setLocation(map.getRandomFloor());
			System.out.println("You have been teleported ");
			deactivate();
		}
	}

	@Override
	public void render(Graphics2D g) {
		int u = Constants.UNIT_DISTANCE;
		if (!activated) {
			g.drawImage(imgLdr.getImage(Images.DEACTIVATED_TRAP, imageCode), x * u, y * u, u, u, null);
		} else if (detected) {
			g.drawImage(imgLdr.getImage(Images.TELEPORTATION_TRAP, imageCode), x * u, y * u, u, u, null);
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
			return "A teleportation trap! Be careful... it may lead you to safety or danger.";
		} else {
			return super.getInfo();
		}
	}
}
