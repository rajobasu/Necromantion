package objects.misc;

import java.awt.Graphics2D;

import framework.Constants;
import imageHandlers.Images;
import maps.Map;
import objects.Stair;

public class DescendingStair extends Stair {

	public DescendingStair(int x, int y, int imageCode) {
		super(x, y, Images.DESCENDING_STAIRS);
		image = Images.DESCENDING_STAIRS;
	}

	@Override
	public void tick(Map map) {
		/*
		 * if (gm == null) GameEngine.getInstance(this);
		 * 
		 * if (p == null) p = Player.getINSTANCE();
		 * 
		 * if (p.isColliding(x, y)) { gm.toNextLevel(); }
		 */
	}

	@Override
	public void render(Graphics2D g) {
		int u = Constants.UNIT_DISTANCE;
		g.drawImage(imgLdr.getImage(image, 0), x * u, y * u, u, u, null);
	}

	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return "Go down the stairs to see what lies ahead... a brave soldier cannot just stay at the same floor forever, he will just die without new resources.";
	}

}
