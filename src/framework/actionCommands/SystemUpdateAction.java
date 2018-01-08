package framework.actionCommands;
/**
 * Denotes actions which update the game environment directly.
 * @author Rajarshi
 *
 */
public class SystemUpdateAction extends Action {
	public static final int TYPE_TO_PREVIOUS_LEVEL = 0;
	public static final int TYPE_TO_NEXT_LEVEL = 1;
	public static final int TYPE_ZOOM_IN = 2;
	public static final int TYPE_ZOOM_OUT = 3;

	private int type;

	public SystemUpdateAction(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}
}
