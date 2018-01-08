package framework;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import framework.engines.GameEngine;
import imageHandlers.ImageLoader;
import imageHandlers.Images;
import objects.entities.Player;

public class LightMap {

	private BufferedImage lightMap;
	private BufferedImage lightCircle;
	@SuppressWarnings("unused")
	private Player player;
	private Color customBackgroungColor;


	public LightMap() {
		
		lightCircle = ImageLoader.INSTANCE().getImage(Images.ALPHA_COMPOSITE__OVAL, 0);
		player = Player.getINSTANCE();
		customBackgroungColor = new Color(0, 0, 0, 140);


		update();

	}

	private BufferedImage createNewBufferedImage() {
		return new BufferedImage(GameEngine.INSTANCE.getBreadth() * Constants.UNIT_DISTANCE,
				GameEngine.INSTANCE.getLength() * Constants.UNIT_DISTANCE, BufferedImage.TYPE_INT_ARGB);
	}

	public void update() {
		lightMap=createNewBufferedImage();
		Graphics2D g = (Graphics2D) lightMap.getGraphics();

		g.setColor(customBackgroungColor);
		g.fillRect(0, 0, lightMap.getWidth(), lightMap.getHeight());
		
		g.setComposite(AlphaComposite.SrcOut);
		
		g.drawImage(lightCircle, 140, 89,null);
		g.dispose();
		/// int x=
	}

	public void render(Graphics2D g) {
		g.drawImage(lightCircle, 0, 0, null);
	}

}
