package objects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import maps.Map;

public interface Collectable {

	BufferedImage getImage();

	void render(Graphics2D g);

	void use(Map map);
}