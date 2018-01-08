package objects.playerResource.potions;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import framework.Constants;
import framework.engines.MessageEngine;
import imageHandlers.Images;
import maps.Map;
import objects.Collectable;
import objects.GameObject;
import objects.Throwable;
import objects.playerResource.selectionCriterias.Identifyable;

public abstract class Potion extends GameObject implements Collectable, Throwable, Identifyable {

	protected int colour;

	public Potion(int x, int y, int imageCode) {
		super(x, y, imageCode);
		this.colour = imageCode;
		image = Images.POTION;
	}

	@Override
	public void throwTo(int x, int y) {

	}

	public BufferedImage getImage() {
		return imgLdr.getImage(image, this.colour);
	}

	public void tick(Map map) {
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return " Potion";
	}

	@Override
	public void identify() {
		if(Constants.hasBeenIdentified(colour))return;

		MessageEngine.writeToScreen("New Potion Identified", Color.GREEN);
		Constants.identify(colour);
	}

	@Override
	public void render(Graphics2D g) {
		int u = Constants.UNIT_DISTANCE;
		g.drawImage(getImage(), x * u, y * u, u, u, null);
	}

	@Override
	public void updateGraphics() {
		// TODO Auto-generated method stub
	}

}
