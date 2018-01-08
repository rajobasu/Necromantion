package objects.roomItems;

import java.awt.Graphics2D;

import framework.Constants;
import imageHandlers.Images;
import maps.Map;
import objects.Burnable;
import objects.GameObject;
import objects.Opaque;
import objects.Tile;
import objects.Walkable;
import objects.entities.Player;

public class Grass extends Tile implements Burnable, Walkable, Opaque {

	public Grass(int x, int y, int imageCode) {
		super(x, y, Images.GRASS);
		p = Player.getINSTANCE();
		image = Images.GRASS;
	}

	@Override
	public void tick(Map map) {
		for (GameObject ob : map.getEnemies()) {
			if (ob.isColliding(x, y)) {
				map.removeTile(this);
				map.addTile(new TrampledGrass(x, y, 0));
				return;
			}
		}

		if (p == null)
			p = Player.getINSTANCE();

		if (p.isColliding(x, y)) {
			map.removeTile(this);
			map.addTile(new TrampledGrass(x, y, 0));
			return;
		}

	}

	@Override
	public void render(Graphics2D g) {
		int u = Constants.UNIT_DISTANCE;
		g.drawImage(imgLdr.getImage(image, 0), x * u, y * u, u, u, null);
	}

	@Override
	public void updateGraphics() {

	}

	@Override
	public void burn(Map map) {
		map.removeTile(this);
		map.addTile(new TrampledGrass(x, y, 0));
	}

	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return "Grass... stay away from it when a fire is near.";
	}

}
