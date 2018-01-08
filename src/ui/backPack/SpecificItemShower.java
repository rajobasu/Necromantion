package ui.backPack;

import java.awt.Canvas;
import java.awt.Graphics2D;

import framework.Constants;
import framework.engines.GameEngine;
import objects.Collectable;
import objects.GameObject;
import ui.BlockableUI;
import ui.TextDisplayer;

public class SpecificItemShower implements BlockableUI {

	private TextDisplayer td;
	private ActionDisplayer ad;

	private final int ud;
	private final int l;
	private final int b;
	private final boolean isEquippedItem;

	private final int percentSize50_y;
	private final int percentSize10_y;
	private final int percentSize70_x;
	private final int percentSize60_x;

	public SpecificItemShower(Collectable c, Canvas e, boolean equippedItem) {
		ud = Constants.UNIT_DISTANCE;
		b = GameEngine.INSTANCE.getBreadth();
		l = GameEngine.INSTANCE.getLength();

		percentSize70_x = (b * ud * 70 - 20) / 100;
		percentSize60_x = (b * ud * 60 - 20) / 100;
		percentSize50_y = (l * ud * 50 - 20) / 100;
		percentSize10_y = (l * ud * 10 - 20) / 100;

		isEquippedItem = equippedItem;

		td = new TextDisplayer(percentSize60_x + 30, percentSize10_y, ((GameObject) c).getInfo(),
				b * ud - percentSize60_x - 40);
		ad = new ActionDisplayer(percentSize70_x, percentSize50_y + 10, e, c, isEquippedItem);
		//MessageEngine.writeToScreen(" "+equippedItem+"  "+isEquippedItem);
	}

	public void render(Graphics2D g) {
		td.displayText(g);
		ad.renderButtons(g);
	}

	public void tick() {
		ad.tickButtons();
	}

	public void destroy() {
		ad.destroyButtons();
	}

	@Override
	public void block() {
		ad.block();
	}

	@Override
	public void unBlock() {
		ad.unBlock();
	}

	@Override
	public boolean isBlocked() {

		return ad.isBlocked();
	}

}
