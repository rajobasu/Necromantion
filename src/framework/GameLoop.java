package framework;

public class GameLoop {
	private boolean running = false;
	private Screen engine;
	private boolean pause;

	public GameLoop(Screen gm) {
		engine = gm;
	}

	public void run() {
		running = true;
		engine.initEngines();

		long lastTime = System.nanoTime();
		double amountOfTicks = 15.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;

		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;

			while (delta >= 1) {
				if (!pause)
					engine.tick();
				
				engine.render();
				delta--;
			}
			// engine.render();
		}
	}

	public void pause() {
		pause = true;
	}

	public void start() {
		pause = false;
	}

	public void stop() {
		running = false;
	}

}
