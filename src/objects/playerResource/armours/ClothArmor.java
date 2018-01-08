package objects.playerResource.armours;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import framework.Constants;
import imageHandlers.Images;

public class ClothArmor extends Shield {

	public ClothArmor(int x, int y, int imageCode) {
		super(x, y, imageCode);
		
		absorbDamage=3;
		level=0;
		image=Images.CLOTH_ARMOR;
	}

	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return "This is a simple cloth armour";
	}

	

	@Override
	public BufferedImage getImage() {
		// TODO Auto-generated method stub
		return imgLdr.getImage(image, 0);
	}

	

	@Override
	public void render(Graphics2D g) {
		int u=Constants.UNIT_DISTANCE;
		g.drawImage(getImage(), x*u, y*u, u, u, null);

	}

	@Override
	public void upgrade() {
		level++;
		absorbDamage+=level;
	}

	@Override
	public void identify() {
		// TODO Auto-generated method stub
		
	}

}
