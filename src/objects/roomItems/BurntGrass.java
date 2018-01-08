package objects.roomItems;

import java.awt.Graphics2D;

import framework.Constants;
import imageHandlers.Images;
import maps.Map;
import objects.Tile;
import objects.Walkable;

public class BurntGrass extends Tile implements Walkable {

	public BurntGrass(int x, int y, int imageCode) {
		super(x, y, Images.BURNT_GRASS);
		image = Images.BURNT_GRASS;
	}

	@Override
	public void tick(Map map) {
		// TODO Auto-generated method stub
	}

	@Override
	public void render(Graphics2D g) {
		int u = Constants.UNIT_DISTANCE;
		g.drawImage(imgLdr.getImage(image, 0), x * u, y * u, u, u, null);

	}

	@Override
	public void updateGraphics() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return "This burnt grass is of no apparent value... ";
	}

}
