package ui.pages;

import java.awt.Graphics2D;

import framework.Constants;
import imageHandlers.ImageLoader;
import imageHandlers.Images;

public class GameEndPage extends WindowPage {

	public GameEndPage() {
	}

	@Override
	public void tick() {
//
	}

	@Override
	public void render(Graphics2D g) {
		g.drawImage(ImageLoader.INSTANCE().getImage(Images.GAME_END, 0), 0, 0,
				Constants.BREADTH * Constants.UNIT_DISTANCE, Constants.LENGTH * Constants.UNIT_DISTANCE, null);
	}

}
