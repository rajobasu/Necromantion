package objects.playerResource.weapons.tier1;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import framework.Constants;
import framework.engines.MessageEngine;
import imageHandlers.Images;
import objects.playerResource.weapons.Weapon;

public class ShortSword extends Weapon {

	public ShortSword(int x, int y, int level) {
		super(3 + 1 * level, 9 - level, level, x, y);
		image = Images.SHORT_SWORD;

	}

	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return "This is a shortSword " + getAdditionalInfo();
	}

	@Override
	public BufferedImage getImage() {

		return imgLdr.getImage(image, 0);
	}

	@Override
	public void render(Graphics2D g) {
		int u = Constants.UNIT_DISTANCE;
		g.drawImage(getImage(), x * u, y * u, u, u, null);

	}

	@Override
	public void upgrade() {
		minStrength--;
		MessageEngine.writeToScreen("ShortSword upgraded", Color.GREEN);
		averageDamage += 2;
	}

	@Override
	public void identify() {
		// TODO Auto-generated method stub

	}

}
