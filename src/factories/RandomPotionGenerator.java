package factories;

import objects.playerResource.potions.FirePotion;
import objects.playerResource.potions.GasImmunityPotion;
import objects.playerResource.potions.HealingPotion;
import objects.playerResource.potions.PoisonPotion;
import objects.playerResource.potions.Potion;
import objects.playerResource.potions.StrengthPotion;
import objects.playerResource.potions.ToxicGasPotion;
import squidpony.squidmath.XoRoRNG;
/**
 * This factory is used to create new Random Potions. 
 * @author rajob
 *
 */
public class RandomPotionGenerator {

	private static XoRoRNG rng;
	static {
		rng = new XoRoRNG();
	}

	private RandomPotionGenerator() {
		
	}
	/**
	 * generates a random potion
	 * @param x - The x co-ordinate where the potion has to be placed.
	 * @param y - The y co-ordiante where the potion has to be placed. 
	 * @return a random potion.
	 */
	public static Potion getRandomPotion(int x, int y) {
		final int POTION_COUNT = 5;
		switch (rng.nextInt(POTION_COUNT)) {
			case 0:
				return new HealingPotion(x, y);
			case 1:
				return new StrengthPotion(x, y);
			case 2:
				return new FirePotion(x, y);
			case 3:
				return new ToxicGasPotion(x, y);
			case 4:
				return new GasImmunityPotion(x, y);
			case 5:
				return new PoisonPotion(x, y);
		}

		return null;
	}

}
