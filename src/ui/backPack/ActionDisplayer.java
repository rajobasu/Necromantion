package ui.backPack;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

import buttonMsc.ButtonEvent;
import buttonMsc.ButtonListener;
import buttonMsc.ButtonMaker;
import buttonMsc.CustomButton;
import framework.GameState;
import framework.engines.GameEngine;
import maps.MapManager;
import objects.Collectable;
import objects.GameObject;
import objects.ResourceUpdator;
import objects.entities.Player;
import objects.misc.BackPack;
import objects.playerResource.armours.BaseArmour;
import objects.playerResource.armours.Shield;
import objects.playerResource.food.Food;
import objects.playerResource.holographs.Holograph;
import objects.playerResource.potions.Potion;
import objects.playerResource.selectionCriterias.Identifyable;
import objects.playerResource.weapons.BaseWeapon;
import objects.playerResource.weapons.Weapon;
import ui.BlockableUI;

public class ActionDisplayer implements ButtonListener, ButtonMaker, BlockableUI {

	private CustomButton btn_drop;
	private CustomButton btn_cancel;
	private CustomButton btn_use;
	private int startingY;
	private int startingX;
	private Canvas canvasObject;
	private Collectable object;
	private BackPack bp;
	private boolean isEquippedItem;

	public ActionDisplayer(int startingX, int startingY, Canvas canvas, Collectable object, boolean isEquippedItem) {
		super();
		this.startingY = startingY;
		this.startingX = startingX;
		canvasObject = canvas;
		System.out.println("x ~~> " + startingX);
		System.out.println("Y ~~> " + startingY);
		this.object = object;
		this.isEquippedItem = isEquippedItem;
		bp = BackPack.INSTANCE();

		initializeButtons();

	}

	@Override
	public void initializeButtons() {
		if (!isEquippedItem)
			btn_drop = new CustomButton(startingX, startingY, 100, 30, canvasObject);
		btn_cancel = new CustomButton(startingX, startingY + 40, 100, 30, canvasObject);
		btn_use = new CustomButton(startingX, startingY + 80, 100, 30, canvasObject);

		btn_cancel.addButtonListener(this);
		if (!isEquippedItem)
			btn_drop.addButtonListener(this);
		btn_use.addButtonListener(this);

		btn_cancel.setText("Cancel");
		if (!isEquippedItem)
			btn_drop.setText("Drop");

		if (object instanceof Weapon || object instanceof Shield) {
			// MessageEngine.writeToScreen(" "+isEquippedItem);
			if (isEquippedItem) {
				btn_use.setText("unequip");
			} else {
				btn_use.setText("equip");
			}
		} else if (object instanceof Food) {
			btn_use.setText("Eat");
		} else if (object instanceof Potion) {
			btn_use.setText("Drink");
		} else if (object instanceof Holograph) {
			btn_use.setText("Read");
		}

		btn_cancel.setDefaultColor(Color.RED);
		if (!isEquippedItem)
			btn_drop.setDefaultColor(Color.YELLOW);
		btn_use.setDefaultColor(Color.GREEN);

		btn_cancel.setPressColor(Color.RED);
		if (!isEquippedItem)
			btn_drop.setPressColor(Color.yellow);
		btn_use.setPressColor(Color.green);

		btn_cancel.setActionCommand("c");
		if (!isEquippedItem)
			btn_drop.setActionCommand("d");
		btn_use.setActionCommand("u");
	}

	@Override
	public void renderButtons(Graphics g) {
		btn_cancel.render(g);
		if (!isEquippedItem)
			btn_drop.render(g);
		btn_use.render(g);
	}

	@Override
	public void tickButtons() {
		btn_cancel.tick();
		if (!isEquippedItem)
			btn_drop.tick();
		btn_use.tick();
	}

	@Override
	public void buttonClicked(ButtonEvent e) {
		destroyButtons();
		if (e.getActionCommand().equalsIgnoreCase("c")) {

			BackPackUI.setUIState(UIState.ALL_ITEM_SHOWER);
			BackPackUI.INSTANCE().activateAllItemShower();

		} else if (e.getActionCommand().equalsIgnoreCase("d")) {
			bp.removeItem(object);
			GameObject obj = (GameObject) object;
			Player player = Player.getINSTANCE();
			obj.setX(player.getX());
			obj.setY(player.getY());

			MapManager.getCurrentMap().addCollectable((GameObject) object);

			GameEngine.INSTANCE.setGameState(GameState.LEVELMAP);
		} else {
			// using the itemselected
			if (object instanceof Identifyable) {
				((Identifyable) object).identify();

			}
			if (!(object instanceof ResourceUpdator)) {

				if (object instanceof Shield) {
					if (!(bp.getShield() instanceof BaseArmour))
						bp.addItem(bp.getShield());
					if (isEquippedItem)
						bp.setShield(null);
					if (!isEquippedItem)
						bp.setShield((Shield) object);
				} else if (object instanceof Weapon) {
					if (!(bp.getWeapon() instanceof BaseWeapon))
						bp.addItem(bp.getWeapon());
					if (isEquippedItem)
						bp.setWeapon(null);

					if (!isEquippedItem)
						bp.setWeapon((Weapon) object);
				} else {

					object.use(MapManager.getCurrentMap());

				}

				if (!isEquippedItem)
					bp.removeItem(object);
				GameEngine.INSTANCE.setGameState(GameState.LEVELMAP);

			} else {
				BackPackUI.INSTANCE().setItemSelector(new ItemSelector(canvasObject,
						((ResourceUpdator) object).getSelectionCriteria(), (ResourceUpdator) object));
				BackPackUI.setUIState(UIState.ITEM_SELECTOR);
			}

		}
	}

	@Override
	public void destroyButtons() {
		btn_cancel.destroy();
		if (!isEquippedItem)
			btn_drop.destroy();
		btn_use.destroy();
	}

	@Override
	public void block() {
		btn_cancel.disable();
		if (!isEquippedItem)
			btn_drop.disable();
		btn_use.disable();
	}

	@Override
	public void unBlock() {
		btn_cancel.enable();
		if (!isEquippedItem)
			btn_drop.enable();
		btn_use.enable();

	}

	@Override
	public boolean isBlocked() {

		return btn_cancel.isEnabled();
	}
}
