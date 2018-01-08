package framework.actionCommands;

public class MovementAction extends PlayerAction {

	public static final int MOVE_N = 0;
	public static final int MOVE_NE = 1;
	public static final int MOVE_E = 2;
	public static final int MOVE_SE = 3;
	public static final int MOVE_S = 4;
	public static final int MOVE_SW = 5;
	public static final int MOVE_W = 6;
	public static final int MOVE_NW = 8;

	private int deltaX;
	private int deltaY;

	public MovementAction(int moveDirection) {
		super();

		switch (moveDirection) {
			case MOVE_N: {
				deltaY = -1;
				break;
			}
			case MOVE_NE: {
				deltaX = 1;
				deltaY = -1;

				break;
			}
			case MOVE_E: {
				deltaX = 1;
				break;
			}
			case MOVE_SE: {
				deltaX = 1;
				deltaY = 1;
				break;
			}
			case MOVE_S: {
				deltaY = 1;
				break;
			}
			case MOVE_SW: {
				deltaY = 1;
				deltaX = -1;
				break;
			}
			case MOVE_W: {
				deltaX = -1;
				break;
			}
			case MOVE_NW: {
				deltaX = -1;
				deltaY = -1;
				break;
			}
		}
	}

	public int getDeltaX() {
		return deltaX;
	}

	public int getDeltaY() {
		return deltaY;
	}

}
