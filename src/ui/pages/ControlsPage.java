package ui.pages;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import buttonMsc.ButtonEvent;
import buttonMsc.ButtonListener;
import buttonMsc.ButtonMaker;
import buttonMsc.CustomButton;
import framework.Constants;
import framework.Screen;
import imageHandlers.ImageLoader;
import imageHandlers.Images;
import ui.TextDisplayer;

public class ControlsPage extends WindowPage implements ButtonListener, ButtonMaker {

	private CustomButton back;
	private Canvas canvas;

	private TextDisplayer td;

	public ControlsPage(Canvas ca) {
		this.canvas = ca;
		td = new TextDisplayer(15, 15,
				"Controls Are: \n"
				+ "      Move : arrow keys \n"
				+ "      Attack : a \n"
				+ "      Collect Item : c \n"
				+ "      backpack : use mouse \n"
				+ "      next level : u \n"
				+ "      previous level : d",
				(Constants.BREADTH - 5) * Constants.UNIT_DISTANCE);
		td.setSize(30);
		initializeButtons();
	}

	@Override
	public void tick() {
		
		tickButtons();
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Constants.BREADTH * Constants.UNIT_DISTANCE, Constants.LENGTH * Constants.UNIT_DISTANCE);
		int bcnt = Constants.BREADTH * Constants.UNIT_DISTANCE / Constants.UNIT_DISTANCE + 2;
		int lcnt = Constants.LENGTH * Constants.UNIT_DISTANCE / Constants.UNIT_DISTANCE + 2;

		for (int i = 0; i < bcnt; i++) {
			for (int j = 0; j < lcnt; j++) {
				g.drawImage(ImageLoader.INSTANCE().getImage(Images.FLOOR, 0), i * Constants.UNIT_DISTANCE,
						j * Constants.UNIT_DISTANCE, Constants.UNIT_DISTANCE, Constants.UNIT_DISTANCE, null);
			}
		}
		renderButtons(g);
		td.displayText(g);
	}

	@Override
	public void initializeButtons() {
		back = new CustomButton((Constants.BREADTH - 3) * Constants.UNIT_DISTANCE,
				(Constants.LENGTH - 1) * Constants.UNIT_DISTANCE, 3 * Constants.UNIT_DISTANCE,
				1 * Constants.UNIT_DISTANCE, canvas);
		
		back.setText("Back");
		back.setDefaultColor(new Color(0, 255, 191));
		back.setHoverColor(new Color(0, 191, 255));
		back.addButtonListener(this);

	}

	@Override
	public void renderButtons(Graphics g) {
		back.render(g);

	}

	@Override
	public void tickButtons() {
		back.tick();

	}

	@Override
	public void destroyButtons() {
		back.destroy();

	}

	@Override
	public void buttonClicked(ButtonEvent e) {
		Screen.setCurrentPage(new MenuPage(canvas));
	}

}
