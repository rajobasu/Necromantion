package objects.playerResource.weapons;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * This class is just as a Dummy class mainly used if the player is not equipped
 * with any weapon. This is the most basic level of "Weapon" that he can have.
 * 
 * @author rajarshibasu
 *
 */
public class BaseWeapon extends Weapon {

	public BaseWeapon() {
		super(5, 1, 0, 0, 0);
		// TODO Auto-generated constructor stub
	}

	@Override
	public BufferedImage getImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void identify() {
	}

	@Override
	public void upgrade() {
	}

	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub

	}

}
