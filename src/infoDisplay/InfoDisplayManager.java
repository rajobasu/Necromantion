package infoDisplay;

import java.awt.Graphics2D;
import java.util.LinkedList;

/**
 * Manages the list of Info Display Areas, and where on the screen to render.
 * 
 * @author Rajarshi
 *
 */
public class InfoDisplayManager {

	private final int DISPLAY_LIMIT;
	private LinkedList<ObjectInfoDisplayer> displays;

	public InfoDisplayManager() {
		DISPLAY_LIMIT = 1;
		displays = new LinkedList<>();
	}

	/**
	 * Checks which displays to end and removes them from the list of active
	 * displays.
	 */
	public void tick() {
		LinkedList<ObjectInfoDisplayer> toRemove = new LinkedList<>();
		for (ObjectInfoDisplayer ob : displays) {
			if (ob.remove)
				toRemove.add(ob);
			else
				ob.tick();
		}

		for (ObjectInfoDisplayer ob : toRemove)
			displays.remove(ob);
	}

	/**
	 * renders the displays.
	 * 
	 * @param g
	 *            - The graphics context.
	 */
	public void render(Graphics2D g) {
		for (ObjectInfoDisplayer ob : displays) {
			ob.render(g);
		}
	}

	/**
	 * checks if the number of displays already present are within the limit,
	 * and if so, then adds the current object.
	 * 
	 * @param ob
	 */
	public void addDisplayObject(ObjectInfoDisplayer ob) {
		if (displays.size() >= DISPLAY_LIMIT) return;

		displays.add(ob);
	}
}
