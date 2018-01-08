package framework.actionCommands;

import java.awt.event.KeyEvent;

/**
 * This action is generated when an unexpected key is pressed. This contains the
 * KeyEvent upon receiving which this action was created.
 * 
 * @author Rajarshi
 *
 */

public class UnknownAction extends Action {
	/**
	 * The KeyEvent that generated this action.
	 */
	private KeyEvent keyEvent;

	public UnknownAction(KeyEvent keyEvent) {
		this.keyEvent = keyEvent;
	}

	/**
	 * @return the keyEvent
	 */
	public KeyEvent getKeyEvent() {
		return keyEvent;
	}
	
}
