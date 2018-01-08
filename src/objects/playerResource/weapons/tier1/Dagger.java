package objects.playerResource.weapons.tier1;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import framework.Constants;
import framework.engines.MessageEngine;
import imageHandlers.Images;
import objects.playerResource.weapons.Weapon;

public class Dagger extends Weapon {

	public Dagger(int x, int y, int level) {
		super(3 + 1 * level, 9 - level, level, x, y);
		image = Images.DAGGER;
	}

	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return "This is a Dagger. A short piece of metal, this cant normally cause much damage. But it is faster, so use it wisely. \n "
				+ getAdditionalInfo();
	}

	@Override
	public BufferedImage getImage() {
		return imgLdr.getImage(image, 0);
	}

	@Override
	public void identify() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void upgrade() {
		minStrength--;
		MessageEngine.writeToScreen("Dagger upgraded", Color.GREEN);
		averageDamage += 2;
	}

	@Override
	public void render(Graphics2D g) {
		int u = Constants.UNIT_DISTANCE;
		g.drawImage(getImage(), x * u, y * u, u, u, null);
	}

}
