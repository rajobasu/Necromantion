package ui.backPack;

import java.awt.Canvas;
import java.awt.Color;
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
import objects.misc.BackPack;

public class BackPackUI2 implements ButtonListener, ButtonMaker {
	private LinkedList<CustomButton> buttons;
	private BackPack bp;
	private Canvas c;
	private CustomButton weapon;
	private CustomButton shield;
	private CustomButton btn_cancel;

	public BackPackUI2(Canvas c) {
		bp = BackPack.INSTANCE();
		buttons = new LinkedList<>();
		this.c = c;
	}

	@Override
	public void initializeButtons() {
		int offsetX = 9;
		int offsetY = 5;
		int ud = Constants.UNIT_DISTANCE;

		btn_cancel = new CustomButton(9 * ud, 0 * ud, 4 * (ud - 2), 2 * (ud - 2), c);
		weapon = new CustomButton(9 * ud, 2 * ud, 2 * (ud - 2), 2 * (ud - 2), c, bp.getWeapon().getImage(),
				bp.getWeapon());
		shield = new CustomButton(11 * ud, 2 * ud, 2 * (ud - 2), 2 * (ud - 2), c, bp.getShield().getImage(),
				bp.getShield());

		int ctr = 0, ctr2 = 0;
		for (Collectable cc : bp.getItems()) {
			CustomButton b = null;
			buttons.add(b = new CustomButton((offsetX + ctr2 * 2) * ud, (offsetY + ctr * 2) * ud, 2 * (ud - 2),
					2 * (ud - 2), c, cc.getImage(), cc));
			if (++ctr2 > 3) {
				ctr2 = 0;
				ctr++;
			}
			b.addButtonListener(this);
		}
		btn_cancel.setDefaultColor(Color.orange);
		btn_cancel.addButtonListener(this);
		btn_cancel.setActionCommand("cancel");
		btn_cancel.setText("Back");
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

	@Override
	public void buttonClicked(ButtonEvent e) {
		
	
		//TODO//GameEngine.initBackPackUI();
		Collectable cc = e.getObject();
		if (e.getActionCommand().equals("cancel")) {
			GameEngine.INSTANCE.setGameState(GameState.LEVELMAP);
		} else {
			GameEngine.INSTANCE.setGameState(GameState.LEVELMAP);
		}
		btn_cancel.destroy();
		weapon.destroy();
		shield.destroy();
		for (CustomButton b : buttons) {
			b.destroy();
		}
		if (!(e.getActionCommand().equalsIgnoreCase("cancel")))
			bp.setSelected(cc);

	}

	@Override
	public void destroyButtons() {
		// TODO Auto-generated method stub
		
	}

}
