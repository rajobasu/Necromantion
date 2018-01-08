package objects.playerResource.holographs;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import framework.Constants;
import imageHandlers.Images;
import objects.Collectable;
import objects.ResourceUpdator;
import objects.playerResource.selectionCriterias.Upgradable;

public class UpgradeHolograph extends Holograph implements ResourceUpdator {

	public UpgradeHolograph(int x, int y, int imageCode) {
		super(x, y, imageCode);
		image = Images.SCROLL;
	}

	@Override
	public String getInfo() {

		//if () return super.getInfo();

		return "This is a upgrade Holograph for upgrading your items.";
	}

	@Override
	public BufferedImage getImage() {

		return imgLdr.getImage(image, imageCode);
	}


	@Override
	public Class<?> getSelectionCriteria() {
		return Upgradable.class;
	}

	@Override
	public void render(Graphics2D g) {
		int u = Constants.UNIT_DISTANCE;
		g.drawImage(getImage(), x * u, y * u, Constants.UNIT_DISTANCE, u, null);

	}

	@Override
	public void updateGraphics() {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateResource(Collectable c) {
		if (c == null) System.out.println("Null reference passed to UpgradeHolograph");

		if (c instanceof Upgradable) {
			System.out.println("Valid game object passed to UpgradeHolograph");
			((Upgradable) c).upgrade();
		} else {
			System.out.println("Wrong reference passed");
		}
	}

}
