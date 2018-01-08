package ui.backPack;

import java.awt.Canvas;
import java.awt.Graphics;
import java.util.LinkedList;

import buttonMsc.ButtonEvent;
import buttonMsc.ButtonListener;
import buttonMsc.ButtonMaker;
import buttonMsc.CustomButton;
import framework.Constants;
import framework.GameState;
import framework.engines.GameEngine;
import objects.Collectable;
import objects.ResourceUpdator;
import objects.misc.BackPack;

public class ItemSelector implements ButtonListener, ButtonMaker {

	private ResourceUpdator associatedObject;

	private LinkedList<CustomButton> buttons;
	private BackPack bp;
	private Canvas c;
	private CustomButton weapon;
	private CustomButton shield;

	ItemSelector(Canvas c, Class<?> selector, ResourceUpdator associatedObject) {
		bp = BackPack.INSTANCE();
		this.c = c;
		this.associatedObject = associatedObject;
		buttons = new LinkedList<>();

		initializeButtons();
		discardFalseItems(selector);

	}

	private <T> void discardFalseItems(Class<T> selector) {
		if (selector == null) return;
		for (CustomButton button : buttons) {
			if (!selector.isInstance(button.getObject())) {
				button.disable();
			}
		}
	}

	@Override
	public void initializeButtons() {
		int offsetx = 15;
		int offsety = 35;
		int spaceX = 5;
		// int spaceY = 5;
		int ctrx = 1;
		int ctry = 1;

		int ud = Constants.UNIT_DISTANCE;
		int l = GameEngine.INSTANCE.getLength();
		int b = GameEngine.INSTANCE.getBreadth();

		// int percentSize15_x = (b * ud * 15 - 30) / 100;
		int percentSize15_y = (l * ud * 15 - 30) / 100;
		int percentSize10_x = (b * ud - 30) / 10;
		int percentSize10_y = (l * ud - 30) / 10;
		int percentSize60_x = (b * ud * 60 - 30) / 100;
		// int percentSize30_y = (l * ud * 30) / 100;

		if (bp.getWeapon() == null) {
			weapon = new CustomButton();
		} else {
			weapon = new CustomButton(offsetx, offsety, percentSize10_x, percentSize10_y, c, bp.getWeapon().getImage(),
					bp.getWeapon());
		}
		if (bp.getShield() == null) {
			shield = new CustomButton();
		} else {
			shield = new CustomButton(offsetx + percentSize10_x + spaceX, offsety, percentSize10_x, percentSize10_y, c,
					bp.getShield().getImage(), bp.getShield());
		}

		for (Collectable cc : bp.getItems()) {
			CustomButton button = null;
			buttons.add(button = new CustomButton((offsetx + percentSize10_x) * ctrx++ - percentSize10_x,
					offsety + (5 + percentSize15_y) * ctry, percentSize10_x, percentSize10_y, c, cc.getImage(), cc));

			if ((offsetx + percentSize10_x) * ctrx > percentSize60_x) {
				ctrx = 1;
				ctry++;
			}
			button.addButtonListener(this);
			// button.setBackgroundImage(ImageLoader.INSTANCE().getImage(Images.INVENTORY_SLOT,
			// 0));
		}

	}

	@Override
	public void buttonClicked(ButtonEvent e) {

		Collectable c = e.getObject();
		associatedObject.updateResource(c);

		GameEngine.INSTANCE.setGameState(GameState.LEVELMAP);
		bp.removeItem((Collectable) associatedObject);
	}

	@Override
	public void renderButtons(Graphics g) {
		weapon.render(g);
		shield.render(g);

		for (CustomButton b : buttons) {
			b.render(g);
		}
	}

	@Override
	public void tickButtons() {
		weapon.tick();
		shield.tick();
		for (CustomButton b : buttons) {
			b.tick();
		}

	}

	public void block() {

		for (CustomButton button : buttons) {
			button.disable();
		}

	}

	public void unBlock() {

		for (CustomButton button : buttons) {
			button.enable();
		}
	}

	public void destroyButtons() {
		weapon.destroy();
		shield.destroy();

		for (CustomButton button : buttons) {
			button.destroy();
		}
	}

}
