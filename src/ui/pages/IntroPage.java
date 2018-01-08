package ui.pages;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import framework.Constants;
import framework.Screen;
import framework.Utilities;

public class IntroPage extends WindowPage {

	private final String story;
	private KeyAdapter keyAdapter;

	public IntroPage() {
		story = "You are one of the best warriors of your country, so the king has sent you to retrieve the long lost Amulet of Yendor. "
				+ "Many have tried before, some have failed, some have died, some never heard of before. This is your chance of glory as"
				+ "whoever brings back the Amulet will be pronounced the next King .... ";

		keyAdapter = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					Screen.getCanvasReference().removeKeyListener(keyAdapter);
					Screen.startGame();
				}
			}
		};

		Screen.getCanvasReference().addKeyListener(keyAdapter);
	}

	@Override
	public void tick() {

	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, Constants.BREADTH * Constants.UNIT_DISTANCE, Constants.LENGTH * Constants.UNIT_DISTANCE);
		g.setColor(Color.LIGHT_GRAY);
		Font f = g.getFont();

		g.setFont(new Font("Consolas", Font.ITALIC, 30));

		// for debugging
		// g.drawString("Helllo Testing",0,10);

		Utilities.drawParagraph(30, 30, g, story, Constants.BREADTH * (Constants.UNIT_DISTANCE - 4));
		g.setFont(f);
	}

}
