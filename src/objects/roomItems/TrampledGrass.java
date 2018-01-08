package objects.roomItems;

import java.awt.Graphics2D;

import framework.Constants;
import imageHandlers.Images;
import maps.Map;
import objects.Burnable;
import objects.Tile;
import objects.Walkable;

public class TrampledGrass extends Tile implements Burnable, Walkable {

	public TrampledGrass(int x, int y, int imageCode) {
		super(x, y, Images.TRAMPLED_GRASS);
		image = Images.TRAMPLED_GRASS;
	}

	@Override
	public void tick(Map map) {

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

	public void burn(Map map) {
		map.removeTile(this);
		map.addTile(new BurntGrass(x, y, 0));
	}

	@Override
	public String getInfo() {
		return "Some trampled grass... maybe someone has been here before... maybe you...maybe someone else... be on the watch..";
	}

}
