package objects.playerResource.food;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import framework.Constants;
import imageHandlers.Images;

public class Ration extends Food {

	

	public Ration(int x, int y) {
		super(x, y, FULL);
		image=Images.RATION;
	}

	@Override
	public String getInfo() {
		return "This is a ration.\n";
	}

	@Override
	public BufferedImage getImage() {
		// TODO Auto-generated method stub
		return imgLdr.getImage(image, 0);
	}

	
	@Override
	public void render(Graphics2D g) {
		int u=Constants.UNIT_DISTANCE;
		g.drawImage(getImage(),x*u, y*u, u, u, null);

	}

}
