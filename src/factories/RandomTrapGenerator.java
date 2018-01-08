package factories;

import objects.traps.FireTrap;
import objects.traps.PoisonTrap;
import objects.traps.SpikeTrap;
import objects.traps.TeleportationTrap;
import objects.traps.ToxicGasTrap;
import objects.traps.Trap;
import squidpony.squidmath.XoRoRNG;

public class RandomTrapGenerator {

	private static XoRoRNG rng;

	static {
		rng = new XoRoRNG();
	}

	private RandomTrapGenerator() {

	}

	public static Trap getRandomTrap(int x, int y) {

		final int TRAP_COUNT = 5;
		switch (rng.nextInt(TRAP_COUNT)) {
			case 0:
				return new FireTrap(x, y, 0);
			case 1:
				return new ToxicGasTrap(x, y, 0);
			case 2:
				return new TeleportationTrap(x, y, 0);
			// case 3:
			// return new ParalyticGasTrap(x, y, 0);
			case 3:
				return new SpikeTrap(x, y, 0);
			case 4:
				return new PoisonTrap(x, y, 0);
		}

		return null;
	}

}
