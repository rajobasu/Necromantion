package objects;

import framework.engines.GameEngine;
import imageHandlers.Images;

/**
 * This class is basically the superclass of the two types of the stairs that
 * are present in all the levels except the last one.Thus per level, there are
 * exactly two instance of this class in the {@code Map}.
 * 
 * @author rajarshibasu
 *
 */
public abstract class Stair extends Tile implements Walkable {

	protected GameEngine gm;

	public Stair(int x, int y, Images imageCode) {
		super(x, y, imageCode);
	}

	@Override
	public void updateGraphics() {
		// TODO Auto-generated method stub

	}

	/**
	 * This method is from an earlier version of the game {@code GameEngine}
	 * reference was not public. However, since the {@code Stair}object has to
	 * cahnge the {@code Map}, this was the only class that was supposed to get
	 * access to that class. however, now a reference to the {@code GameEngine}
	 * is globally available through a static method, so this method is mostly
	 * redundant. It is mainly kept for legacy purposes. This method is
	 * primarily called by the {@code GameEngine} after it gets a request from a
	 * {@code Stair} subclass for the instance. This is done for the reason that
	 * it essentially ensures that only a {@code Stair} object can get the
	 * reference.
	 * 
	 * 
	 * 
	 * @param gm
	 *            The instance of the {@code GameEngine }object.
	 */
	public final void setGameEngine(GameEngine gm) {
		this.gm = gm;
	}
}
