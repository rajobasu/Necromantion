package framework;

import java.awt.Canvas;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;

import Input.KeyInput;
import framework.engines.GameEngine;
import framework.engines.HUDEngine;
import framework.engines.MessageEngine;
import ui.pages.GameEndPage;
import ui.pages.MenuPage;
import ui.pages.WindowPage;

public class Screen extends Canvas {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Canvas INSTANCE;

	private GameEngine gameEngine;
	private HUDEngine hud;
	private MessageEngine msgEngine;

	private KeyInput keyInput;

	private static boolean gameStarted;
	private static boolean gameEnded;

	private static WindowPage currentPage;
	private static WindowPage gameEndPage;

	public static Canvas getCanvasReference() {
		return INSTANCE;
	}

	public Screen() {
		this.requestFocus();
		addKeyListener(keyInput = new KeyInput());
		gameStarted = false;
		INSTANCE = this;
		currentPage = new MenuPage(this);

	}

	public void tick() {

		if (gameStarted) {
			gameEngine.tick();
			hud.tick();
			msgEngine.tick();
		} else if (gameEnded) {
			gameEndPage.tick();
		} else {
			currentPage.tick();
		}
	}

	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(2);
			return;
		}

		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		g.setFont(new Font("Consolas", Font.BOLD, 20));

		// RenderingHints.
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		if (gameStarted) {

			hud.render(g);
			msgEngine.render(g);
			gameEngine.render(g);// this should be the last to render as it
									// scales the graphics
		} else if (gameEnded) {
			gameEndPage.render(g);
		} else {
			currentPage.render(g);
		}
		g.dispose();
		bs.show();
	}

	public void createEngines() {
		gameEngine = new GameEngine(keyInput);

		GameEngine.INSTANCE.setOffsetX(0);
		GameEngine.INSTANCE.setOffsetY(0);

		GameEngine.INSTANCE.setBreadth(Constants.BREADTH - 8);
		GameEngine.INSTANCE.setLength(Constants.LENGTH);

		hud = new HUDEngine();
		
		hud.setOffsetX((Constants.BREADTH - 8) * Constants.UNIT_DISTANCE);
		hud.setOffsetY(0);

		hud.setBreadth(8);
		hud.setLength(Constants.LENGTH - 5);

		msgEngine = new MessageEngine();

		msgEngine.setOffsetX((Constants.BREADTH - 8) * Constants.UNIT_DISTANCE);
		msgEngine.setOffsetY((Constants.LENGTH - 5) * Constants.UNIT_DISTANCE);

		msgEngine.setBreadth(8);
		msgEngine.setLength(5);

	}

	public void initEngines() {
		gameEngine.init();
		hud.init();
	}

	/**
	 * Starts the game after the initial stage.
	 */
	public static void startGame() {
		gameStarted = true;
	}

	/**
	 * Ends the game.
	 */
	public static void endGame() {
		gameEnded = true;
		gameStarted = false;
		gameEndPage = new GameEndPage();
	}

	/**
	 * 
	 * Set the current {@code currentPage} to the {@code Page} given as
	 * parameter.
	 * 
	 * @param p
	 *            The {@code Page} to which the current page has to be set.
	 */
	public static void setCurrentPage(WindowPage p) {
		currentPage = p;
	}

}
