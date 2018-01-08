package factories;

import objects.playerResource.holographs.Holograph;
import objects.playerResource.holographs.IdentifyHolograph;
import objects.playerResource.holographs.TeleportationHolograph;
import objects.playerResource.holographs.UpgradeHolograph;
import squidpony.squidmath.XoRoRNG;

public class RandomHolographGenerator {

	private static XoRoRNG rng;
	static {
		rng = new XoRoRNG();
	}

	private RandomHolographGenerator() {
	}

	public static Holograph getRandomHolograph(int x, int y) {
		final int HOLOGRAPH_COUNT = 2;
		switch (rng.nextInt(HOLOGRAPH_COUNT)) {
		case 0:
			return new UpgradeHolograph(x, y, 0);
		case 1:
			return new IdentifyHolograph(x, y, 1);
		case 2:
			return new TeleportationHolograph(x, y, 2);
		}

		return null;
	}

}
