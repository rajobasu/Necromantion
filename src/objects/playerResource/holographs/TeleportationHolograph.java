package objects.playerResource.holographs;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import framework.Constants;
import maps.Map;
import objects.entities.Player;

public class TeleportationHolograph extends Holograph {

	public TeleportationHolograph(int x, int y, int imageCode) {
		super(x, y, imageCode);

	}

	@Override
	public BufferedImage getImage() {
		return imgLdr.getImage(image, 0);
	}

	@Override
	public void use(Map map) {
		Player.getINSTANCE().setLocation(map.getRandomFloor());

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
	public String getInfo() {

//		if (!Constants.hasBeenIdentified(colour)) return super.getInfo();
		
		return "Reading this holograph will teleport you to another somewhere unknown.";
	}

}
