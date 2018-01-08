package objects.gas;

import java.awt.Graphics2D;
import java.util.LinkedList;

import animations.ToxicGasAnimation;
import framework.Constants;
import imageHandlers.Images;
import maps.Map;
import objects.entities.Enemy;
import objects.entities.Entity.Effect;

public class ToxicGas extends Gas {

	private ToxicGasAnimation tga;

	public ToxicGas(int x, int y, int spreadQ) {
		super(x, y, 2 * spreadQ + 3, spreadQ);
		image = Images.TOXIC_GAS;
		tga = new ToxicGasAnimation();
	}

	@Override
	public void decreaseTurnsLeft() {
		turnsLeft--;
	}

	@Override
	public void render(Graphics2D g) {
		int u = Constants.UNIT_DISTANCE;
		g.drawImage(imgLdr.getImage(image, tga.getSceneIndex()), x * u, y * u, u, u, null);
	}

	@Override
	public void tick(Map map) {
		// if(canSpread())spread(map);

		if (turnsLeft <= 0) map.removeObject(this);

		decreaseTurnsLeft();

		if (p.isColliding(x, y)) {
			if (!p.hasOngoingEffect(Effect.GAS_IMMUNITY)) {
				System.out.println("Player Life decreased by gas !! ");
				p.decreaseLife(2);
			}
		}

		LinkedList<Enemy> enemies = map.getEnemies();
		for (Enemy enemy : enemies) {
			if (enemy.hasOngoingEffect(Effect.GAS_IMMUNITY)) continue;

			if (enemy.isColliding(x, y)) {
				enemy.decreaseLife(2);
			}
		}
	}

	@Override
	public void updateGraphics() {
		tga.updateAnimation();
	}

	@Override
	protected Gas getNewInstance(int x, int y, int sp) {
		return new ToxicGas(x, y, sp);
	}

}
