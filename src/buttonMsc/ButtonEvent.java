package buttonMsc;

import objects.Collectable;

/**
 * This is an event which is generated when the CustomButton is clicked.
 * 
 * @see CustomButton
 * 
 * @author Rajarshi
 *
 */
public class ButtonEvent {
	private ButtonID ID;
	private Collectable object;
	private String actionCommand;

	/**
	 * Constructor for the Event, generated from the ID, the associated object,
	 * if any, and the actionCommand for that specific button.
	 * 
	 * @param ID - Specifies the Mouse Button ID.
	 * @param object - The associated object with the Button if any.
	 * @param actionCommand - The action Command of the Button.
	 * 
	 * @see ButtonID
	 * @see CustomButton
	 */

	ButtonEvent(ButtonID ID, Collectable object, String actionCommand) {
		super();
		this.ID = ID;
		this.object = object;
		this.actionCommand = actionCommand;

	}

	public Collectable getObject() {
		return object;
	}

	public String getActionCommand() {
		return actionCommand;
	}

	public ButtonID getID() {
		return ID;
	}

}
