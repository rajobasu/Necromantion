package objects.playerResource.holographs;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import framework.Constants;
import objects.Collectable;
import objects.ResourceUpdator;
import objects.playerResource.selectionCriterias.Identifyable;

public class IdentifyHolograph extends Holograph implements ResourceUpdator {

	public IdentifyHolograph(int x, int y, int imageCode) {
		super(x, y, imageCode);

	}

	@Override
	public BufferedImage getImage() {
		return imgLdr.getImage(image, 0);
	}

	@Override
	public Class<?> getSelectionCriteria() {
		return Identifyable.class;
	}

	@Override
	public void updateResource(Collectable c) {
		((Identifyable) c).identify();
	}

	@Override
	public void render(Graphics2D g) {
		int u = Constants.UNIT_DISTANCE;
		g.drawImage(getImage(), x * u, y * u, Constants.UNIT_DISTANCE, u, null);
	}

	@Override
	public void updateGraphics() {
	}
	
	@Override
	public String getInfo() {
		return "A identification holograph! Use this carefully." ;
	}

}
