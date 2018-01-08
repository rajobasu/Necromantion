package objects.playerResource.armours;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class BaseArmour extends Shield {

	public BaseArmour() {
		super(0, 0, 0);
		this.absorbDamage=5;
		this.minStrength=1;
		this.level=0;
	}

	@Override
	public BufferedImage getImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void identify() {
		// TODO Auto-generated method stub

	}

	@Override
	public void upgrade() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub

	}

}
