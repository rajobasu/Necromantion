package ui.backPack;

import java.awt.Canvas;
import java.awt.Graphics2D;

import framework.Constants;
import framework.engines.GameEngine;
import imageHandlers.ImageLoader;
import imageHandlers.Images;
import objects.Collectable;
import objects.ResourceUpdator;
import ui.MultipleInstanceException;
import ui.UINotCreatedException;

public class BackPackUI {

	private static int insCount;

	private AllItemShower ais;
	private ItemSelector is;
	private SpecificItemShower sis;
	private Canvas engine;
	private static UIState state;

	private static BackPackUI INSTANCE;

	public static BackPackUI INSTANCE() {
		return INSTANCE;
	}

	public BackPackUI(Canvas c) {
		insCount++;
		INSTANCE = this;

		if (insCount > 1) { throw new MultipleInstanceException(); }

		setUIState(UIState.ALL_ITEM_SHOWER);

		ais = new AllItemShower(c);
		engine = c;

		System.out.println("New BackPack UI created");
	}

	public void render(Graphics2D g) {
		int b=GameEngine.INSTANCE.getBreadth();
		int ud=Constants.UNIT_DISTANCE;
		
		int percentSize60_x = (b * ud * 60 - 20) / 100;
		int percentSize40_x = (b * ud * 40 - 20) / 100;
		
		
		g.drawImage(ImageLoader.INSTANCE().getImage(Images.INV_BACKGROUND, 0), 0, 0,
				percentSize60_x, GameEngine.INSTANCE.getLength() * Constants.UNIT_DISTANCE, null);

		g.drawImage(ImageLoader.INSTANCE().getImage(Images.INV_BACKGROUND, 0), b*ud-percentSize40_x, 0,
				percentSize40_x, GameEngine.INSTANCE.getLength() * Constants.UNIT_DISTANCE, null);

		
		
		
		switch (state) {
			case ALL_ITEM_SHOWER: {
				if (ais == null) {
					ais = new AllItemShower(engine);
				}
				if (sis != null) {
					sis.destroy();
					sis = null;
				}

				if (ais.isBlocked()) ais.unBlock();

				ais.renderButtons(g);

				break;
			}
			case ITEM_SELECTOR: {
				if (is == null) throw new UINotCreatedException();
				if (!sis.isBlocked()) sis.block();

				sis.render(g);
				is.renderButtons(g);

				break;
			}
			case SPECIFIC_ITEM_SELECTOR: {
				if (sis == null) throw new UINotCreatedException();
				if (!ais.isBlocked()) ais.block();

				ais.renderButtons(g);
				sis.render(g);
				break;
			}
			default:
				throw new IllegalStateException();
		}
	}

	public void tick() {
		switch (state) {
			case ALL_ITEM_SHOWER: {
				ais.tickButtons();

				break;
			}
			case ITEM_SELECTOR: {
				is.tickButtons();
				break;
			}
			case SPECIFIC_ITEM_SELECTOR: {
				sis.tick();
				if (ais != null) ais.tickButtons();
				break;
			}
			default:
				throw new IllegalStateException();
		}
	}

	void activateSpecificItemShower(Collectable item) {

	}

	void activateAllItemShower() {
		if (ais != null) ais.enableButtons();
		sis = null;
	}

	void activateItemSelector(ResourceUpdator ru) {

	}

	static void setUIState(UIState state) {
		System.out.println("BPUI State : " + state);
		BackPackUI.state = state;
	}

	public void destroy() {
		if (ais != null) ais.destroyButtons();
		ais = null;
		if (sis != null) sis.destroy();
		sis = null;
		if (is != null) is.destroyButtons();

		insCount--;
		INSTANCE = null;

		System.out.println("BackPack UI destroyed");
	}

	void setSpecificItemShower(SpecificItemShower s) {
		sis = s;
	}

	void setItemSelector(ItemSelector is) {
		this.is = is;
	}

	void backPackUpdated() {
		if (ais != null) ais.destroyButtons();
		ais = new AllItemShower(engine);
	}

}
