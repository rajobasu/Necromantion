package objects.roomItems;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import framework.Constants;
import imageHandlers.ImageLoader;
import imageHandlers.Images;
import maps.Map;
import objects.Opaque;
import objects.Tile;
import objects.Walkable;
import objects.entities.Player;

public class Door extends Tile implements Walkable, Opaque {

	private ImageLoader img;
	private boolean locked;
	private boolean open;
	private int keyCode;

	public Door(int x, int y, int imageCode, boolean locked, int keyCode) {
		super(x, y, Images.DOOR);
		open = false;
		this.locked = false;
		this.keyCode = keyCode;
		img = ImageLoader.INSTANCE();
		image = Images.DOOR;
	}

	public boolean isLocked() {
		return locked;
	}

	public boolean open(int keyCode) {
		if (this.keyCode == keyCode) {
			locked = false;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void tick(Map map) {
		if (p == null)
			p = Player.getINSTANCE();

		if (p.isColliding(x, y))
			open = true;
		else
			open = false;
	}

	@Override
	public void render(Graphics2D g) {
		int u = Constants.UNIT_DISTANCE;
		BufferedImage image;
		if (open) {

			image = img.getImage(this.image, 1);
		} else {
			image = img.getImage(this.image, 0);
		}
		g.drawImage(image, x * u, y * u, u, u, null);
	}

	@Override
	public void updateGraphics() {
		if (p == null)
			return;
		open = false;
		if (p.isColliding(x, y)) {
			open = true;
		}
	}

	@Override
	public String getInfo() {
		return "Push open this door to find what lies behind...";
	}
}
