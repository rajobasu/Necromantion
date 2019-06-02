package framework.engines;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.Queue;

import Input.KeyInput;
import Input.MouseInput;
import cutscenes.Cutscene;
import cutscenes.CutsceneManager;
import framework.Camera;
import framework.Constants;
import framework.GameState;
import framework.IllegalStateException;
import framework.Screen;
import framework.actionCommands.Action;
import framework.actionCommands.CutsceneSkipAction;
import framework.actionCommands.PlayerAction;
import framework.actionCommands.SystemUpdateAction;
import framework.actionCommands.____TESTAction;
import infoDisplay.FullScreenInfoDisplayer;
import infoDisplay.InfoDisplayManager;
import maps.Location;
import maps.Map;
import maps.MapLoader;
import maps.MapManager;
import maps.MapNotFoundException;
import objects.GameObject;
import objects.Stair;
import objects.Tile;
import objects.entities.Enemy;
import objects.entities.Player;
import objects.misc.BackPack;
import ui.backPack.BackPackUI;

public class GameEngine extends Engine {

	// TODO : make the Holograph system unknown at first and then identified
	// when used for the fist time. Like the Potion system.

	// TODO : make affector system static with enums and a mapping to number of
	// turns left.

	// TODO :manage the scaling while rendering the full screen cutscene if
	// after drawing.

	// TODO :[DONE] make a method in utilities for flipping a image vertically
	// for
	// left-right distinction.

	// TODO :[PARTIALLY DONE] make the random generators

	// TODO :[PARTIALLY DONE] give the stats to the various items.

	// TODO [DONE]: change the MRat sprites to 32 32 due to Spritesheet
	// hardcodings

	// TODO [DONE ]: change the animtion control to a lock based one. For
	// enemies to
	// attack one by one,they will be updated together.
	// Then, unless it gets the exclusive animation lock, it will continue its
	// previous animation state and return true when asked
	// if has pending animation.Thus, the player cannot update unless all
	// objects have got the lock and completed the animation.
	// obtaing the lock will take place in the updategraphics method itself.

	// TODO [DONE] : erronous collisionChecking in Enemy code.MakeCollision
	// checking based on iterating over all entities , collectables and effects
	// (not tiles) as they will be quite less in number.Thus change the map
	// representation where a 2D array will just keep the tiles and lists
	// for other items.

	// TODO [DONE] : make corrections so that collision checking uses the whole
	// list for checking.

	// TODO :[DONE] make the Zoom system.Code needs to be changed in the camera
	// :
	// update and isInVeiw methods. and the render method

	// TODO: [NOT NEEDED][REQUIRED METHOD IN UTIL] change the rendering method
	// to making the x,y co-ordinates the
	// center point for the rendered images by shifting the x
	// y co-ordinates during rendering. Thus make the lightmap image a bit
	// larger so that when rendereing the sides of the dark
	// regions seem to blend with the adjacent tiles.

	// TODO :[DONE] implement the bresenham in the map. The line calculating
	// function
	// is already written in the Utilities class.

	/**
	 * This will now determine when the environmant can update due to exclusive
	 * anmations or cutscenes
	 */
	private static Queue<GameObject> pendingExclusiveAnimation;

	private static GameState gameState;
	private Player player;

	private MapManager mapManager;
	private MapLoader mapLoader;
	private KeyInput keyInput;
	private MouseInput mouseInput;
	private CutsceneManager cutsceneManager;
	private InfoDisplayManager infoDisplayManager;

	private boolean playerUpdated;
	private BackPackUI bpui;
	public static GameEngine INSTANCE;

	/**
	 * Create a new GameObject using a instance of the {@code KeyInput}
	 * 
	 * @param input
	 */
	public GameEngine(KeyInput input) {
		this.keyInput = input;
		mouseInput = new MouseInput();
		Screen.getCanvasReference().addMouseListener(mouseInput);
		INSTANCE = this;
	}

	/**
	 * Initializes the Engine and all the parts.
	 */
	@Override
	public void init() {

		BackPack.INSTANCE();
		mapLoader = new MapLoader(this);
		
		pendingExclusiveAnimation = new LinkedList<>();
		gameState = GameState.LEVELMAP;

		loadAllMaps();

		// room.addObject(new Wall(0,0,30,30,1));
		if (player == null)
			throw new NullPointerException();

		Camera.initialize(player);

		// lmp = new LightMap();

		// add the initial cutscene
		cutsceneManager = new CutsceneManager();
		// cutsceneManager.playCutscene(new InitialCutscene());
		infoDisplayManager = new InfoDisplayManager();
	}

	/**
	 * Initializes all the maps. They generate the maps randomly.
	 */
	private void loadAllMaps() {
		Map map = null;
		int maxLevel = 2;
		try {
			map = mapLoader.loadMap(1);
			mapManager = new MapManager();
			mapManager.addNewMap(map);
			for (maxLevel = 2; maxLevel < 10; maxLevel++) {
				mapManager.addNewMap(mapLoader.createMap(maxLevel));
			}

		} catch (MapNotFoundException e) {
			e.printStackTrace();
		} finally {
			mapManager.setCurrentMap(map);
		}
		System.out.println("Max level loaded is " + --maxLevel);
	}

	@Override
	public void render(Graphics2D g) {

		/////////////////////////////////////////////
		////////////////////////////////

		g.setColor(new Color(0, 0, 0));
		g.fillRect(offsetX, offsetY, breadth * Constants.UNIT_DISTANCE, length * Constants.UNIT_DISTANCE);

		Camera.update();

		switch (gameState) {
		case LEVELMAP:
			// Render the Map
			bpui = null;

			// This translates to the gameplay area. 
			g.translate(offsetX, offsetY);

			// This translates the view according to the camera ON TOP OF the gameplay area. 
			// the result is basically a composition of the two shifts, once we change to the gameplay
			// area and then again to the camera location
			g.translate(-Camera.getOffsetX(), -Camera.getOffsetY());

			// chack if there is a pending cutscene: if so , set the XOOM to
			// default;
			if (!cutsceneManager.hasPendingCutscene())
				g.scale(Constants.ZOOM, Constants.ZOOM);

			
			/*
			 * This is the main place where all the objects are rendered. 
			 */
			MapManager.getCurrentMap().render(g);

			// revert to the original default ZOOM.
			g.scale(1 / Constants.ZOOM, 1 / Constants.ZOOM);

			// always render cutscenes after the gameworld has been
			// drawn.Gives greater control to what all the cutscene can
			// control.

			
			// We now revert the changes to made by translation of the camera.
			g.translate(Camera.getOffsetX(), Camera.getOffsetY());
			
			if (cutsceneManager.hasPendingCutscene())
				cutsceneManager.renderCutscene(g);

			infoDisplayManager.render(g);
			// g.scale(Constants.ZOOM, Constants.ZOOM);

			// g.setColor(Color.GREEN);
			// g.drawString("This is a test String ", 100, 100);

			g.translate(-offsetX, -offsetY);// this refers to thw gameplay
											// display area

			break;
		case INVENTORY:
			displayBackPack(g);

			break;
		default:
			throw new IllegalStateException();
		}
		
		///////////////////////////////
		/////////////////////////////////////////

	}

	/**
	 * Updates the graphics whenever called. Updates the game environment only
	 * when some action is created. When in {@code INVENTORY} state, it disables
	 * the input mechanism so any unwanted input is not taken.
	 */
	@Override
	public void tick() {
		switch (gameState) {

		case LEVELMAP:
			keyInput.activate();
			mouseInput.activate();

			int prevHealth = player.getLife();

			infoDisplayManager.tick();

			processLatestKeyInput();
			processLatestMouseInput();

			MapManager.getCurrentMap().updateGraphics();
			if (prevHealth > 15 && player.getLife() <= 15) {
				MessageEngine.writeToScreen("Health is Very Low", Color.RED);
			}

			break;
		case INVENTORY:
			// deactivate the mouse and key input, otherwise unwanted
			// actions may be created.

			keyInput.deactivate();
			mouseInput.deactivate();

			if (bpui == null) {
				bpui = new BackPackUI(Screen.getCanvasReference());
			} else {
				bpui.tick();
			}
			break;

		default:
			throw new IllegalStateException();
		}

	}

	private void processLatestMouseInput() {
		Location latestLocation = mouseInput.popLatestLocation();
		if (latestLocation == null)
			return;

		
		LinkedList<Enemy> enemies = MapManager.getCurrentMap().getEnemies();
		for (Enemy enemy : enemies) {
			if (enemy.getLocation().equals(latestLocation)) {
				infoDisplayManager.addDisplayObject(new FullScreenInfoDisplayer(enemy));
				return;
			}
		}

		LinkedList<GameObject> collectables = MapManager.getCurrentMap().getCollectables();
		for (GameObject collectable : collectables) {
			if (collectable.getLocation().equals(latestLocation)) {
				infoDisplayManager.addDisplayObject(new FullScreenInfoDisplayer(collectable));
				return;
			}
		}

		Tile tile = MapManager.getCurrentMap().getTile(latestLocation);
		infoDisplayManager.addDisplayObject(new FullScreenInfoDisplayer(tile));

	}

	private void processLatestKeyInput() {
		Action latestAction = keyInput.popLatestAction();

		if (cutsceneManager.hasPendingCutscene()) {
			if (latestAction instanceof CutsceneSkipAction)
				cutsceneManager.endCurrentCutscene();
			else
				cutsceneManager.updateCutscene();
		} else if (latestAction instanceof PlayerAction && pendingExclusiveAnimation.isEmpty()) {

			player.addAction(latestAction);

			MapManager.getCurrentMap().tickPlayer();

			if (playerUpdated) {
				playerUpdated = false;
				MapManager.getCurrentMap().tick();
			}

			player.increaseMoves();
		} else if (latestAction instanceof SystemUpdateAction) {
			switch (((SystemUpdateAction) latestAction).getType()) {
			case SystemUpdateAction.TYPE_TO_NEXT_LEVEL: {
				toNextLevel();
				break;
			}
			case SystemUpdateAction.TYPE_TO_PREVIOUS_LEVEL: {
				toPreviousLevel();
				break;
			}
			case SystemUpdateAction.TYPE_ZOOM_IN: {
				zoomIN();
				break;
			}
			case SystemUpdateAction.TYPE_ZOOM_OUT: {
				zoomOUT();
				break;
			}

			}
		} else if (latestAction instanceof ____TESTAction) {
			infoDisplayManager.addDisplayObject(new FullScreenInfoDisplayer(player));
		}
	}

	public void setPlayer(Player p) {
		if (player == null)
			player = p;
		else {
			player.setX(p.getX());
			player.setY(p.getY());

		}
	}

	/**
	 * Displays the backpack
	 * 
	 * @param g
	 */

	private void displayBackPack(Graphics2D g) {

		if (bpui == null) {
			bpui = new BackPackUI(Screen.getCanvasReference());

		} else {
			bpui.render(g);
		}
	}

	public GameState getGameState() {
		return gameState;
	}

	public void setGameState(GameState gameState) {
		GameEngine.gameState = gameState;
		if (gameState == GameState.LEVELMAP) {
			HUDEngine.enableUI();
			if (bpui != null)
				bpui.destroy();
		} else {
			HUDEngine.disableUI();
		}
		System.out.println(gameState);

	}

	public void initBackPackUI() {
		bpui = null;
	}

	@Override
	public void debugPrint() {
		System.out.println("Camera x " + Camera.getOffsetX());
		System.out.println("Camera y " + Camera.getOffsetY());

	}

	public void toNextLevel() {
		int currentLevel = MapManager.getCurrentLevel();
		int maxLevel = mapManager.getMaxLevel();
		if (MapManager.getCurrentMap().getDescendingStair().isColliding(player.getX(), player.getY())) {
			if (currentLevel < maxLevel) {
				MessageEngine.writeToScreen("Next Level Entered", Color.GREEN);
				mapManager.increaseLevel();
			}
		}
	}

	public void toPreviousLevel() {
		int currentLevel = MapManager.getCurrentLevel();
		if (MapManager.getCurrentMap().getAscendingStair().isColliding(player.getX(), player.getY())) {
			if (currentLevel > 1) {
				MessageEngine.writeToScreen("Previous Level Entered", Color.RED);
				mapManager.decreaseLevel();
			}
		}
	}

	// to ensure that only the Stair subclasses can access this method,
	// we take the instance and call a method there giving the value.

	public void playerUpdatedSuccessfully() {
		playerUpdated = true;
	}

	// for zooming in and out.
	public void zoomIN() {
		Constants.ZOOM += 0.02;
		Constants.ZOOM = Math.min(Constants.ZOOM, 4);
	}

	public void zoomOUT() {
		Constants.ZOOM -= 0.02;
		Constants.ZOOM = Math.max(Constants.ZOOM, 0.50);
	}

	/**
	 * This is a mechanism to ensure that at a time only one
	 * {@code ExclusiveAnimation} is playing. The object that tries to play this
	 * animation has to first register itself. This method returns whether it is
	 * now the turn of that object to play its animation.
	 * 
	 * @param object
	 * @return whether object can acquire lock
	 */
	public static boolean canLock(GameObject object) {
		return pendingExclusiveAnimation.peek() == object;
	}

	public static void removeAnmation(GameObject animation) {
		pendingExclusiveAnimation.remove(animation);
	}

	/**
	 * This method registers an object that is trying to play an
	 * {@code ExclusiveAnimation}. To get the permission to actually play the
	 * animation, you have to check whether you can acquire the lock for the
	 * current animation using the {@code canLock()} method.
	 * 
	 * @param object
	 *            to be registered.
	 */
	public static void addExclsiveAnimation(GameObject anim) {
		pendingExclusiveAnimation.offer(anim);
	}

	public static void getInstance(Stair stair) {
		stair.setGameEngine(INSTANCE);
	}

	/**
	 * Not yet implemented
	 * 
	 * @param cutscene
	 */
	public void playCutscene(Cutscene cutscene) {
		cutsceneManager.playCutscene(cutscene);
	}

	/**
	 * Retunrs the current {@code KeyInput} reference
	 * 
	 * @return keyinput
	 */
	public KeyInput getKeyInput() {
		return keyInput;
	}

	public Rectangle getArea() {
		// TODO Auto-generated method stub
		return new Rectangle(offsetX, offsetY, breadth * Constants.UNIT_DISTANCE, length * Constants.UNIT_DISTANCE);
	}
}
