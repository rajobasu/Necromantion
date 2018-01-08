package factories;

import objects.playerResource.armours.ClothArmor;
import objects.playerResource.armours.LeatherArmor;
import objects.playerResource.armours.Shield;
import squidpony.squidmath.XoRoRNG;

/**
 * This factory generates a new Random armour.
 * 
 * @author Rajarshi Basu
 *
 */
public class RandomArmourGenerator {

	private static XoRoRNG rng;
	static {
		rng = new XoRoRNG();
	}

	/**
	 * This constructor has been marked as private so as to prevent the creation
	 * of any object. This is in view of the factory pattern maintained.
	 */
	private RandomArmourGenerator() {

	}

	/**
	 * Creates a new random Armour with the location as given in parameter, and
	 * returns it.
	 * 
	 * @param x
	 *            The X coordinate of the location where the object is to be
	 *            placed.
	 * @param y
	 *            The Y coordinate of the location where the object is to be
	 *            placed.
	 * @return The random piece of armor that is created at the specified
	 *         location.
	 */
	public static Shield getRandomArmour(int x, int y) {
		final int ARMOUR_COUNT = 2;
		switch (rng.nextInt(ARMOUR_COUNT)) {
		case 0:
			return new ClothArmor(x, y, 0);
		case 1:
			return new LeatherArmor(x, y, 0);

		}

		return null;
	}

}
