package objects.entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import animations.Animation;
import framework.Constants;
import maps.Map;
import objects.Burnable;
import objects.GameObject;

/**
 * This represents an Entity. It contains the feature common to all entites such
 * as life.
 * 
 * @author Rajarshi
 *
 */
public abstract class Entity extends GameObject implements Burnable {
	public enum Effect {
		GAS_IMMUNITY, PARALYSIS, POISONED {
			@Override
			public void affect(Entity entity) {
				entity.decreaseLife(10);
				System.out.println("Player life decreased by poison.");
			}
		};

		public void affect(Entity entity) {
			// TODO Auto-generated method stub

		}

	}

	private HashMap<Effect, Integer> ongoingEffects;

	protected int life;
	protected int maxLife;

	protected int prevX;
	protected int prevY;

	/**
	 * This stores a finer value so that the movement animation of the Entity is
	 * not abrupt.It stores the x value.
	 */
	protected double deltaX;
	/**
	 * This stores a finer value so that the movement animation of the Entity is
	 * not abrupt.It stores the y value.
	 */
	protected double deltaY;

	/**
	 * This stores the current Animation that is playing.
	 */
	protected Animation currentAnimation;
	protected boolean skipTurn;
	/**
	 * This stores any animation that needs to be played. Ideally, it should
	 * replace the {@code currentAnimation} after it completes or replaces it
	 * immediately if the {@code currentAnimation} is infinite.
	 */
	protected Animation pendingAnimation;

	protected boolean facingLeft;

	public Entity(int x, int y, int imageCode, int maxLife) {
		super(x, y, imageCode);
		this.maxLife = maxLife;
		life = maxLife;

		deltaX = 0;
		deltaY = 0;

		ongoingEffects = new HashMap<>();
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public int getMaxLife() {
		return maxLife;
	}

	public void setMaxLife(int maxLife) {
		this.maxLife = maxLife;
	}

	public void increaseLife(int inc) {
		life = Math.min(maxLife, life + inc);
	}

	public void decreaseLife(int dec) {
		life = Math.max(0, life - dec);
	}

	public int getPrevX() {
		return prevX;
	}

	public void setPrevX(int prevX) {
		this.prevX = prevX;
	}

	public int getPrevY() {
		return prevY;
	}

	public void setPrevY(int prevY) {
		this.prevY = prevY;
	}

	public void skipTurn() {
		skipTurn = true;
	}

	public boolean willSkipTurn() {
		return skipTurn;
	}

	public void resume() {
		skipTurn = false;
	}

	/**
	 * 
	 * @return The actual xco-ordinate of the object. There is no need to
	 *         multiply it with {@code Constants.UNIT_DISTANCE}
	 */
	public int getActualX() {
		int u = Constants.UNIT_DISTANCE;

		return (int) ((prevX + deltaX * currentAnimation.getSceneIndex()) * u);
	}

	/**
	 * 
	 * @return The actual xco-ordinate of the object. There is no need to
	 *         multiply it with {@code Constants.UNIT_DISTANCE}
	 */
	public int getActualY() {
		int u = Constants.UNIT_DISTANCE;

		return (int) ((prevY + deltaY * currentAnimation.getSceneIndex()) * u);

	}

	/**
	 * This method draws the Entity according to the direction it is facing. It
	 * does not use it's own x and y value as the object calling it may want to
	 * be rendered at some inbetween value which is not supported by
	 * {@code x*Constants.UNIT_DISTANCE}.
	 * 
	 * 
	 * @param g
	 *            - The graphics context.
	 * @param img
	 *            - The image to be drawn.
	 * @param x
	 *            - The starting x co-ordinate.
	 * @param y
	 *            - The starting y co-ordinate.
	 * @param width
	 *            - The width of the image.
	 * @param height
	 *            - The height of the image.
	 */
	protected void drawImage(Graphics2D g, BufferedImage img, int x, int y, int width, int height) {
		if (facingLeft) {
			g.drawImage(img, x + width, y, -width, height, null);
		} else {
			g.drawImage(img, x, y, width, height, null);
		}
	}

	public void burn(Map map) {
	}

	/**
	 * reduces the number of turns of all oongoing effects and removes those
	 * which have been completed.
	 */
	public void tick(Map map) {
		for (Effect effect : Effect.values()) {
			if (ongoingEffects.containsKey(effect)) {
				effect.affect(this);
				int i = ongoingEffects.get(effect);
				i--;
				ongoingEffects.remove(effect);
				if (i > 0)
					ongoingEffects.put(effect, i);
			}
		}
		// for (Effect effect : Effect.values()) {
		// effect.affect(this);
		//
		// }

	}

	/**
	 * Checks if this entity is under the action of a particular {@code Effect}
	 * 
	 * @param effect
	 *            - The effect to be searched
	 * @return Whether the entity has that effect
	 */
	public boolean hasOngoingEffect(Effect effect) {
		return ongoingEffects.containsKey(effect);
	}

	/**
	 * Adds a new {@code Effect} with a specified number of turns. If that
	 * {@code Effect} is already present, the number of turns gets increased by
	 * that mentioned by the parameter.
	 * 
	 * @param effect
	 *            - The {@code Effect} to be added
	 * @param turns
	 *            - The number of turns the {@code Effect} will last
	 */
	public void addEffect(Effect effect, int turns) {
		if (ongoingEffects.containsKey(effect)) {
			turns += ongoingEffects.get(effect);
			ongoingEffects.remove(effect);
		}

		ongoingEffects.put(effect, turns);
	}
}
