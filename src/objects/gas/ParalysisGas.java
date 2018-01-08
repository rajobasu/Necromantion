package objects.gas;

import java.awt.Graphics2D;

import framework.Constants;
import imageHandlers.Images;
import maps.Map;

public class ParalysisGas extends Gas {

	public ParalysisGas(int x, int y, int spreadQ) {
		super(x, y, 2 * spreadQ + 3, spreadQ);
		image = Images.PARALYTIC_GAS;
	}

	@Override
	public void decreaseTurnsLeft() {
		turnsLeft--;
	}

	@Override
	public void tick(Map map) {
		// if (canSpread()) spread(map);

		if (turnsLeft <= 0) map.removeObject(this);

		decreaseTurnsLeft();

		if (p.isColliding(x, y)) {
			System.out.println("Player Paralysed");
			// p.addNewAffector();
		}

	}

	@Override
	public void render(Graphics2D g) {
		int u = Constants.UNIT_DISTANCE;
		g.drawImage(imgLdr.getImage(image, 0), x * u, y * u, u, u, null);
	}

	@Override
	public void updateGraphics() {

	}

	@Override
	protected Gas getNewInstance(int x, int y, int sp) {
		return new ParalysisGas(x, y, sp);
	}

}
