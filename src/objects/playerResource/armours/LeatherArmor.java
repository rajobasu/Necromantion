package objects.playerResource.armours;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import framework.Constants;
import imageHandlers.Images;

public class LeatherArmor extends Shield {

	public LeatherArmor(int x, int y, int imageCode) {
		super(x, y, imageCode);
		absorbDamage=10;
		level=2;
		image=Images.LEATHER_ARMOR;
		System.out.println("Leather Armour created");
	}

	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BufferedImage getImage() {
		// TODO Auto-generated method stub
		return imgLdr.getImage(image, 0);
	}

	@Override
	public void identify() {
		// TODO Auto-generated method stub

	}

	@Override
	public void upgrade() {
		level++;
		absorbDamage+=level+2;
	}

	@Override
	public void render(Graphics2D g) {
		int u=Constants.UNIT_DISTANCE;
		g.drawImage(getImage(), x*u, y*u, u, u, null);


	}

}
