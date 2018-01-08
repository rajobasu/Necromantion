package framework.engines;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import buttonMsc.ButtonEvent;
import buttonMsc.ButtonListener;
import buttonMsc.ButtonMaker;
import buttonMsc.CustomButton;
import framework.Constants;
import framework.GameState;
import framework.Screen;
import imageHandlers.ImageLoader;
import imageHandlers.Images;
import maps.MapManager;
import objects.entities.Player;

public class HUDEngine extends Engine implements ButtonMaker, ButtonListener {

	private Player player;
	private ImageLoader imgLdr;

	private static CustomButton bp;

	public HUDEngine() {

	}

	@Override
	public void init() {
		player = Player.getINSTANCE();
		imgLdr = ImageLoader.INSTANCE();

		initializeButtons();
		bp.enable();
	}

	@Override
	public void render(Graphics2D g) {

		g.translate(offsetX, offsetY);
		////////////////////////////////
		int u = Constants.UNIT_DISTANCE;

		g.drawImage(imgLdr.getImage(Images.HUD_BACKGROUND, 0), 0, 0, breadth * u, length * u, null);

		g.setColor(Color.RED);
		g.drawImage(imgLdr.getImage(Images.HEART, 0), 2, 2, u / 2 + 4, u / 2 + 4, null);
		int width = u * 5 - 3;
		g.fillRect(2 + u / 2 + 2 + 4, 2 + u / 4, width, 8);
		g.setColor(Color.green);
		g.fillRect(2 + u / 2 + 2 + 4, 2 + u / 4,
				(int) Math.floor(((double) player.getLife() / player.getMaxLife()) * width), 8);

		g.setColor(Color.CYAN);
		g.drawString("XP       : " + player.getXP(), 2, 2 + u / 2 + 18);
		g.drawString("Strength : " + player.getStrength(), 2, 2 + u / 2 + 35);
		Color c = g.getColor();
		g.setColor(new Color(50 + 10, 100, 200));
		g.drawString("LEVEL    : " + MapManager.getCurrentLevel(), 2, 2 + u / 2 + 52);
		g.setColor(c);
		/////////////////////////////
		g.translate(-offsetX, -offsetY);
		renderButtons(g);
		if (player.getLife() == 0) {
			MessageEngine.writeToScreen("Game Over", Color.MAGENTA);
			Screen.endGame();
		}
	}

	@Override
	public void initializeButtons() {
		bp = new CustomButton(offsetX + (breadth - 2) * Constants.UNIT_DISTANCE, /* Screen X*/
				offsetY + (length - 2) * Constants.UNIT_DISTANCE, /* Screen Y*/
				2 * Constants.UNIT_DISTANCE, /* Width */
				2 * Constants.UNIT_DISTANCE, /* Height */
				Screen.getCanvasReference(), imgLdr.getImage(Images.BACKPACK, 0), null);

		bp.addButtonListener(this);
	}

	@Override
	public void tick() {
		tickButtons();

	}

	@Override
	public void debugPrint() {
	}

	@Override
	public void buttonClicked(ButtonEvent e) {
		GameEngine.INSTANCE.setGameState(GameState.INVENTORY);
	}

	@Override
	public void renderButtons(Graphics g) {
		bp.render(g);

	}

	@Override
	public void tickButtons() {
		bp.tick();

	}

	@Override
	public void destroyButtons() {
		bp.destroy();
	}

	public static void enableUI() {
		bp.enable();
	}

	public static void disableUI() {
		bp.disable();
	}

}
