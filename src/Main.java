 
import framework.GameLoop;
import framework.Screen;
import ui.GameWindow;

/**
 * Starting point of the Game. Blue
 * 
 * 
 * @author Rajarshi Basu
 * k
 */
public class Main {
	/**
	 * Intializes the game loop, screen, etc. and starts the game.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Game started");
		Screen sc = new Screen();

		sc.createEngines();

		GameLoop gl = new GameLoop(sc);
		new GameWindow(sc);

		gl.run();
	}
}
