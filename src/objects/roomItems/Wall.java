package objects.roomItems;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import framework.Constants;
import imageHandlers.Images;
import maps.Map;
import objects.Opaque;
import objects.Tile;

public class Wall extends Tile implements Opaque {

	public Wall(int x, int y, int imageCode) {
		super(x, y, Images.WALL);
		image = Images.WALL;
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(Color.BLUE);
		// g.fill(getBounds());
		BufferedImage img = imgLdr.getImage(image, imageCode);
		g.drawImage(img, x * Constants.UNIT_DISTANCE, y * Constants.UNIT_DISTANCE, Constants.UNIT_DISTANCE,
				Constants.UNIT_DISTANCE, null);
	}

	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return new Rectangle(x * Constants.UNIT_DISTANCE, y * Constants.UNIT_DISTANCE, Constants.UNIT_DISTANCE,
				Constants.UNIT_DISTANCE);
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
		return "Dont worry... nothing ordinary gets past these solid rock walls.";
	}

}
