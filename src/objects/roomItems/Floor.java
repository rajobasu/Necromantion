package objects.roomItems;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import framework.Constants;
import imageHandlers.Images;
import maps.Map;
import objects.Tile;
import objects.Walkable;

public class Floor extends Tile implements Walkable {

	public Floor(int x, int y, int imageCode) {
		super(x, y, Images.FLOOR);
		image = Images.FLOOR;
	}

	@Override
	public void render(Graphics2D g) {
		BufferedImage img = imgLdr.getImage(image, imageCode);
		g.drawImage(img, x * Constants.UNIT_DISTANCE, y * Constants.UNIT_DISTANCE, Constants.UNIT_DISTANCE,
				Constants.UNIT_DISTANCE, null);
	}

	@Override
	public void tick(Map map) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateGraphics() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return "Not much can be said about this floor... nothing more than what it seems like... or is there?!";
	}

}
