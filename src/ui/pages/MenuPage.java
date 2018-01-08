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

public class MenuPage extends WindowPage implements ButtonMaker {

//	private CustomButton about;
	private CustomButton play;
	private CustomButton controls;
	private CustomButton credits;

	private Canvas canvas;

	public MenuPage(Canvas canvas) {
		this.canvas = canvas;
		initializeButtons();
	}

	@Override
	public void initializeButtons() {
		int u = Constants.UNIT_DISTANCE;
		int b = Constants.BREADTH;
		// int l=Constants.LENGTH;

		int offsetX = b * u / 2 - 70;
		int offsetY = 50;

//		about = new CustomButton(offsetX, offsetY, 180, 40, canvas);
		offsetY += 50;
		play = new CustomButton(offsetX, offsetY, 180, 40, canvas);
		offsetY += 50;
		controls = new CustomButton(offsetX, offsetY, 180, 40, canvas);
		offsetY += 50;
		credits = new CustomButton(offsetX, offsetY, 180, 40, canvas);
		offsetY += 50;

//		about.setText("about");
		play.setText("Play");
		controls.setText("Controls");
		credits.setText("Credits");

//		about.setDefaultColor(new Color(0, 255, 191));
//		about.setHoverColor(new Color(0, 191, 255));

		play.setDefaultColor(Color.GREEN);
		play.setHoverColor(new Color(191, 255, 0));

		controls.setDefaultColor(new Color(255, 191, 0));
		controls.setHoverColor(new Color(255, 128, 0));

		credits.setDefaultColor(new Color(255, 179, 255));
		credits.setHoverColor(new Color(255, 128, 255));

//		about.addButtonListener(new ButtonListener() {
//
//			@Override
//			public void buttonClicked(ButtonEvent e) {
//
//			}
//		});

		play.addButtonListener(new ButtonListener() {

			@Override
			public void buttonClicked(ButtonEvent e) {
				destroyButtons();
				Screen.setCurrentPage(new IntroPage());
			}
		});

		controls.addButtonListener(new ButtonListener() {

			@Override
			public void buttonClicked(ButtonEvent e) {
				destroyButtons();
				Screen.setCurrentPage(new ControlsPage(canvas));
			}
		});

		credits.addButtonListener(new ButtonListener() {

			@Override
			public void buttonClicked(ButtonEvent e) {

			}
		});

	}

	@Override
	public void renderButtons(Graphics g) {
//		about.render(g);
		play.render(g);
		credits.render(g);
		controls.render(g);

	}

	@Override
	public void tickButtons() {
//		about.tick();
		play.tick();
		controls.tick();
		credits.tick();
	}

	@Override
	public void destroyButtons() {
//		about.destroy();
		play.destroy();
		controls.destroy();
		credits.destroy();
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

	}

}
