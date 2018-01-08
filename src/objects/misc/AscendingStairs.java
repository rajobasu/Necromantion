package objects.misc;

import java.awt.Graphics2D;

import framework.Constants;
import imageHandlers.Images;
import maps.Map;
import objects.Stair;

public class AscendingStairs extends Stair {

	public AscendingStairs(int x, int y, int imageCode) {
		super(x, y, Images.ASCENDING_STAIRS);
		image = Images.ASCENDING_STAIRS;
	}

	@Override
	public void tick(Map map) {
		/*
		 * if (gm == null) GameEngine.getInstance(this);
		 * 
		 * if (p == null) p = Player.getINSTANCE();
		 * 
		 * if (p.isColliding(x, y)) { gm.toPreviousLevel(); }
		 */
	}

	@Override
	public void render(Graphics2D g) {
		int u = Constants.UNIT_DISTANCE;
		g.drawImage(imgLdr.getImage(image, 0), x * u, y * u, u, u, null);
	}

	@Override
	public String getInfo() {
		return "This is the staircase to go to the previous floor, to a probably easier level, so as to give you some breathing space, or for any other reason.";
	}
}
