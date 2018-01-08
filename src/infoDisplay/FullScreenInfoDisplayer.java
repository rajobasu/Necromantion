package infoDisplay;

import java.awt.Color;
import java.awt.Graphics2D;

import Input.KeyInput;
import framework.Constants;
import framework.Utilities;
import framework.engines.GameEngine;
import objects.GameObject;

/**
 * This class displays the Object information on a full screen mode. It is
 * disposed of as soon as the user makes any key input.
 * 
 * @author Rajarshi
 *
 */

public class FullScreenInfoDisplayer extends ObjectInfoDisplayer {

	/**
	 * Instance of a KeyInput.
	 */
	private KeyInput input;

	public FullScreenInfoDisplayer(GameObject ob) {
		super(ob);
		input = GameEngine.INSTANCE.getKeyInput();
	}

	/**
	 * Checks if user has made any key inputs.If so, it sets {@code remove} to
	 * true.
	 */
	@Override
	public void tick() {
		if (remove)
			System.out.println("hell");
		super.tick();
		if (input.peekLatestAction() != null) {
			remove = true;
		}
	}

	/**
	 * Render the info as a full screen message.
	 * 
	 */
	@Override
	public void render(Graphics2D g) {
		g.setColor(Color.DARK_GRAY);
		g.fill(GameEngine.INSTANCE.getArea());
		g.setColor(Color.LIGHT_GRAY);
		
		Utilities.drawParagraph(20, 20, g, ob.getInfo(),
				GameEngine.INSTANCE.getBreadth() * Constants.UNIT_DISTANCE - 20);
	}

}
