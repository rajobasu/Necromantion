package infoDisplay;

import java.awt.Graphics2D;

import objects.GameObject;

/**
 * Displays the information associated with the given object. It will render the
 * information on a full screen mode.
 * 
 * @author Rajarshi
 *
 */
abstract class ObjectInfoDisplayer {

	protected GameObject ob;
	/**
	 * Indicates to the Manager class that this object needs to be removed.
	 * @see InfoDisplayManager
	 */
	protected boolean remove;

	public ObjectInfoDisplayer(GameObject ob) {
		this.ob = ob;
	}

	/**
	 * Called each time the game environment is updated.
	 */
	public void tick() {

	}

	/**
	 * Called to render the Display message on the screen.
	 * 
	 * @param g-
	 *            The graphics context.
	 */
	public abstract void render(Graphics2D g);

}
