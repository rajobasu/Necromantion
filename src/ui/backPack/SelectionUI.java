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
import maps.Map;
import objects.Collectable;
import objects.GameObject;
import objects.entities.Player;
import objects.misc.BackPack;
import objects.playerResource.armours.Shield;
import objects.playerResource.food.Food;
import objects.playerResource.potions.Potion;
import objects.playerResource.weapons.Weapon;

public class SelectionUI implements ButtonListener, ButtonMaker {

	private Collectable object;

	private CustomButton btn_drop;
	private CustomButton btn_cancel;
	private CustomButton btn_use;

	private Canvas c;
	private Map map;

	public SelectionUI(Collectable object, Canvas c, Map map) {
		super();
		this.object = object;
		this.c = c;
		this.map = map;
	}

	@Override
	public void initializeButtons() {
		btn_cancel = new CustomButton(200, 200, 60, 20, c);
		btn_drop = new CustomButton(200, 230, 60, 20, c);
		btn_use = new CustomButton(200, 260, 60, 20, c);

		btn_cancel.addButtonListener(this);
		btn_drop.addButtonListener(this);
		btn_use.addButtonListener(this);

		btn_cancel.setText("Cancel");
		btn_drop.setText("Drop");

		if (object instanceof Weapon || object instanceof Shield) {
			btn_use.setText("equip");
		} else if (object instanceof Food) {
			btn_use.setText("Eat");
		} else if (object instanceof Potion) {
			btn_use.setText("Drink");
		}

		btn_cancel.setDefaultColor(Color.RED);
		btn_drop.setDefaultColor(Color.yellow);
		btn_use.setDefaultColor(Color.green);

		btn_cancel.setPressColor(Color.RED);
		btn_drop.setPressColor(Color.yellow);
		btn_use.setPressColor(Color.green);

		btn_cancel.setActionCommand("c");
		btn_drop.setActionCommand("d");
		btn_use.setActionCommand("u");

	}

	@Override
	public void renderButtons(Graphics g) {
		btn_cancel.render(g);
		btn_drop.render(g);
		btn_use.render(g);

	}

	@Override
	public void tickButtons() {
		btn_cancel.tick();
		btn_drop.tick();
		btn_use.tick();

	}

	@Override
	public void buttonClicked(ButtonEvent e) {

		BackPack bp = BackPack.INSTANCE();
		GameEngine.INSTANCE.setGameState(GameState.INVENTORY);
		
		if (e.getActionCommand().equals("c")) {

		} else if (e.getActionCommand().equals("u")) {
			
			if (object instanceof Weapon) {
				bp.addItem(bp.getWeapon());
				bp.removeItem(object);
				bp.setWeapon((Weapon) object);
			} else if (object instanceof Shield) {
				bp.addItem(bp.getShield());
				bp.removeItem(object);
				bp.setShield((Shield) object);
			} else if (object instanceof Food) {
				Player.getINSTANCE().eatFood((Food) object);
			} else if (object instanceof Potion) {
				//((Potion) object).drink(map);
				bp.removeItem(object);
			}

		} else {
			bp.removeItem(object);
			GameObject obj = (GameObject) object;
			Player player = Player.getINSTANCE();
			obj.setX(player.getX());
			obj.setY(player.getY());

			map.addCollectable((GameObject) object);

		}
		System.out.println(bp.getItems().size());
		btn_cancel.destroy();
		btn_drop.destroy();
		btn_use.destroy();
	}

	@Override
	public void destroyButtons() {
		// TODO Auto-generated method stub
		
	}

}
