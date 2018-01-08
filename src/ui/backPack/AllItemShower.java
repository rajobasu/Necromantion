package ui.backPack;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import buttonMsc.ButtonEvent;
import buttonMsc.ButtonListener;
import buttonMsc.ButtonMaker;
import buttonMsc.CustomButton;
import framework.Constants;
import framework.GameState;
import framework.engines.GameEngine;
import imageHandlers.ImageLoader;
import imageHandlers.Images;
import objects.Collectable;
import objects.misc.BackPack;
import objects.playerResource.armours.BaseArmour;
import objects.playerResource.weapons.BaseWeapon;
import ui.BlockableUI;

public class AllItemShower implements ButtonListener, ButtonMaker, BlockableUI {

	private LinkedList<CustomButton> buttons;
	private BackPack bp;
	private Canvas c;
	private CustomButton weapon;
	private CustomButton shield;
	private CustomButton btn_cancel;

	public AllItemShower(Canvas c) {
		bp = BackPack.INSTANCE();
		buttons = new LinkedList<>();
		this.c = c;
		initializeButtons();

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

		if (bp.getWeapon() instanceof BaseWeapon) {
			weapon = new CustomButton();
		} else {
			weapon = new CustomButton(offsetx, offsety, percentSize10_x, percentSize10_y, c, bp.getWeapon().getImage(),
					bp.getWeapon());
		}
		if (bp.getShield() instanceof BaseArmour) {
			shield = new CustomButton();
		} else {
			shield = new CustomButton(offsetx + percentSize10_x + spaceX, offsety, percentSize10_x, percentSize10_y, c,
					bp.getShield().getImage(), bp.getShield());
		}
		btn_cancel = new CustomButton(offsetx + 3 * (percentSize10_x + spaceX), offsety, percentSize10_x,
				percentSize10_y, c);

		weapon.setActionCommand("EQUIPPED");
		shield.setActionCommand("EQUIPPED");

		weapon.addButtonListener(this);
		shield.addButtonListener(this);

		BufferedImage img;
		btn_cancel.setImage(img = ImageLoader.INSTANCE().getImage(Images.BACK_BUTTON, 0));
		btn_cancel.setDarkImage(img);

		// offsety = percentSize15_y;

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

		btn_cancel.setDefaultColor(Color.orange);
		btn_cancel.addButtonListener(this);
		btn_cancel.setActionCommand("cancel");
		btn_cancel.setText("Back");

	}

	@Override
	public void buttonClicked(ButtonEvent e) {


		if (e.getActionCommand().equalsIgnoreCase("EQUIPPED")) {
			disableButtons();
			BackPackUI.INSTANCE().setSpecificItemShower(new SpecificItemShower(e.getObject(), c, true));
			// MessageEngine.writeToScreen("Equipped item selected");
			BackPackUI.setUIState(UIState.SPECIFIC_ITEM_SELECTOR);

		} else if (e.getActionCommand().equalsIgnoreCase("cancel")) {
			GameEngine.INSTANCE.setGameState(GameState.LEVELMAP);
		} else {
			disableButtons();
			BackPackUI.INSTANCE().setSpecificItemShower(new SpecificItemShower(e.getObject(), c, false));
			BackPackUI.setUIState(UIState.SPECIFIC_ITEM_SELECTOR);
		}
	}

	@Override
	public void renderButtons(Graphics g) {
		weapon.render(g);
		shield.render(g);
		btn_cancel.render(g);
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
		btn_cancel.tick();

	}

	public void block() {
		btn_cancel.disable();
		for (CustomButton button : buttons) {
			button.disable();
		}
		weapon.disable();
		shield.disable();

	}

	public void unBlock() {
		btn_cancel.enable();
		for (CustomButton button : buttons) {
			button.enable();
		}
		weapon.enable();
		shield.enable();
	}

	public void destroyButtons() {
		weapon.destroy();
		shield.destroy();
		btn_cancel.destroy();
		for (CustomButton button : buttons) {
			button.destroy();
		}
	}

	public void disableButtons() {
		block();
	}

	public void enableButtons() {
		unBlock();
	}

	public boolean isBlocked() {
		return !(btn_cancel.isEnabled());
	}

}
