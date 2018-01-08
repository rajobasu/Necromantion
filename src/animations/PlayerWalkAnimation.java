package animations;

import java.awt.Graphics2D;

import framework.Constants;
import imageHandlers.ImageLoader;
import imageHandlers.Images;

public class PlayerWalkAnimation extends ExclusiveAnimation {

	public double diffx;
	public double diffy;

	private int stX;
	private int stY;
	private int endX;
	private int endY;

	public PlayerWalkAnimation(int stX, int stY, int endX, int endY) {
		super(2);
		this.stX = stX;
		this.stY = stY;
		this.endX = endX;
		this.endY = endY;

		diffx = (this.endX - stX) / 3.0;
		diffy = (this.endY - stY) / 3.0;

	}

	public void renderAnimation(Graphics2D g) {
		int u = Constants.UNIT_DISTANCE;
		g.drawImage(ImageLoader.INSTANCE().getImage(Images.PLAYER_WALK, getSceneIndex()),
				(int) ((stX + diffx * sceneIndex) * u), (int) ((stY + diffy * sceneIndex) * u), u, u, null);
	}

}
