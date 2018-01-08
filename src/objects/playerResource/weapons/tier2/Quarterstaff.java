package objects.playerResource.weapons.tier2;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import framework.Constants;
import framework.engines.MessageEngine;
import imageHandlers.Images;
import objects.playerResource.weapons.Weapon;

public class Quarterstaff extends Weapon {

	public Quarterstaff(int x, int y, int level) {
		super(5 + level * 4, 12, level, x, y);
		image = Images.QUARTERSTAFF;
	}

	@Override
	public String getInfo() {
		return "This is a QuarterStaff of level 5. "+getAdditionalInfo();
	}

	@Override
	public BufferedImage getImage() {
		// TODO Auto-generated method stub
		return imgLdr.getImage(image, 0);
	}

	@Override
	public void upgrade() {
		minStrength--;
		MessageEngine.writeToScreen("Weapon upgraded", Color.GREEN);
		averageDamage += 4;
	}

	@Override
	public void render(Graphics2D g) {
		int u = Constants.UNIT_DISTANCE;
		g.drawImage(getImage(), x * u, y * u, u, u, null);
	}

	@Override
	public void identify() {

	}

}
