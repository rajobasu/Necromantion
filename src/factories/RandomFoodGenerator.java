package factories;

import objects.playerResource.food.Food;
import objects.playerResource.food.Ration;
import squidpony.squidmath.XoRoRNG;

public class RandomFoodGenerator {

	private static XoRoRNG rng;
	static {
		rng = new XoRoRNG();
	}
	
	private RandomFoodGenerator() {
		// TODO Auto-generated constructor stub
	}

	public static Food getRandomFood(int x, int y) {
		final int FOOD_COUNT = 1;
		switch (rng.nextInt(FOOD_COUNT)) {
			case 0:
				return new Ration(x, y);

		}
		
		return null;
	}
}
