package buttonMsc;

import java.awt.Graphics;

/**
 * The interface for specifying the correct usage of the CustomButton as it has
 * to be updated and rendered on the AWT Canvas.
 * 
 * @author Rajarshi
 * @see Button
 */
public interface ButtonMaker {

	/**
	 * Initializes the CustomButtons that are to be used.
	 */
	void initializeButtons();

	/**
	 * This method has to be called for rendering of the CustomButton.
	 * 
	 * @param g
	 *            - The graphics object for rendering on the Canvas
	 */
	void renderButtons(Graphics g);

	/**
	 * This method has to be called for updating the CustomButton.
	 */
	void tickButtons();

	/**
	 * This method has to be called for destroying the CustomButton. It removes
	 * the CustomButton from the list of MouseListeners of the Canvas.
	 */
	void destroyButtons();
}
