package framework;

public final class Constants {

	// these are for the whole window.
	public static final int LENGTH = 14;
	public static final int BREADTH = 24;
	//public static final int UI_UNIT_DISTANCE = 32;
	
	
	public static final int DODGED=0;
	public static final int BACKPACK_SIZE = 30;
	public static final int SPREAD_QUOTIENT = 3;
	public static final int MAX_FIRE_TURNS = 5;
	public static final int POTION_COUNT = 6;
	public static final int UNIT_DISTANCE = 32;

	public class Transparency{
		private Transparency(){}
		
		public static final int NONE=0;
		public static final int MEDIUM=1;
		public static final int FULL=2;
	}
	
	public static double ZOOM = 1.0;
	private static boolean[] potionColorIdentified = new boolean[POTION_COUNT];

	public static boolean hasBeenIdentified(int colour) {
		return potionColorIdentified[colour];
	}

	public static void identify(int colour) {
		potionColorIdentified[colour] = true;
	}

	private Constants() {}
}
