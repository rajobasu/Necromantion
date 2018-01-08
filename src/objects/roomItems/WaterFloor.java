package objects.roomItems;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import framework.Constants;
import imageHandlers.Images;
import maps.Map;
import objects.Tile;
import objects.Walkable;

public class WaterFloor extends Tile implements Walkable  {

	public WaterFloor(int x, int y) {
		super(x, y, Images.WATER_FLOOR);
		image = Images.WATER_FLOOR;
	}

	@Override
	public void tick(Map map) {
	}

	@Override
	public void render(Graphics2D g) {
		BufferedImage img = imgLdr.getImage(image, imageCode);
		g.drawImage(img, x * Constants.UNIT_DISTANCE, y * Constants.UNIT_DISTANCE, Constants.UNIT_DISTANCE,
				Constants.UNIT_DISTANCE, null);
	}

	@Override
	public void updateGraphics() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return "A wet floor... try stepping on it when burning.... It may just help...";
	}

}
