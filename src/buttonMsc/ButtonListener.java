package buttonMsc;

/**
 * This is an interface to get notified when a CustomButton is clicked
 * 
 * @author Rajarshi
 * @see Button
 */
public interface ButtonListener {

	/**
	 * This method is called whenever the CustomButton to which it was added as
	 * a Listener is clicked.
	 * 
	 * @param e
	 *            - The ButtonEvent associated with the Button Click.
	 * @see Button
	 */
	void buttonClicked(ButtonEvent e);

}
