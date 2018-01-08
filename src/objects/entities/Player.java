package objects.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Queue;

import animations.ExclusiveAnimation;
import animations.IdlePlayerAnimation;
import animations.PlayerAttackAnimation;
import animations.PlayerWalkAnimation;
import framework.Constants;
import framework.actionCommands.Action;
import framework.actionCommands.AttackAction;
import framework.actionCommands.CollectAction;
import framework.actionCommands.MovementAction;
import framework.engines.GameEngine;
import framework.engines.MessageEngine;
import imageHandlers.Images;
import maps.Map;
import objects.Collectable;
import objects.GameObject;
import objects.Tile;
import objects.misc.BackPack;
import objects.playerResource.armours.Shield;
import objects.playerResource.food.Food;
import objects.playerResource.weapons.Weapon;
import objects.roomItems.Door;

public class Player extends Entity {

	public enum SkillLevelImprovement {
		ITEM_COLLECTED(5), ENEMY_KILLED(10);
		public int deltaSkillImprovement;

		private SkillLevelImprovement(int imp) {
			deltaSkillImprovement = imp;
		}
	}

	private static Player INSTANCE;
	private BackPack backPack;

	private int moves;
	private int hunger;

	private int XP;
	private int tillNextXP;
	private int strength;

	private Weapon weapon;
	private Shield shield;

	private boolean canMove;
	private final int initialOffsetXP = 50;

	public static final int HUNGER_LIMIT = 200;
	public static final int STARVE_LIMIT = 350;

	private static Queue<Action> actionQueue;

	/**
	 * Creates the Player object at the given location on the {@code Map}.
	 * Again, {@code imageCode} is not needed. It also initializes all the
	 * properties to their relative values.
	 * 
	 * @param x
	 *            The x co-ordinate of the location where the player is to be
	 *            created.
	 * @param y
	 *            The y co-ordinate of the location where the player is to be
	 *            created.
	 * @param imageCode
	 *            Not required... kept for legacy purposes.
	 */
	public Player(int x, int y, int imageCode) {
		super(x, y, imageCode, 100);

		INSTANCE = this;
		image = Images.PLAYER_IDLE;
		backPack = BackPack.INSTANCE();

		weapon = backPack.getWeapon();
		shield = backPack.getShield();

		hunger = 0;

		XP = 1;
		strength = 10;

		tillNextXP = XP * 5 + initialOffsetXP;

		canMove = true;

		actionQueue = new LinkedList<>();

		currentAnimation = new IdlePlayerAnimation();
	}

	/**
	 * Moves the player in the given direction and magnitude given by the
	 * {@link MovementAction}. However, this is a fail-soft method, which means
	 * that if the movement is not possible, then no signal will be given, it
	 * will just discard this request.
	 * 
	 * @param map
	 *            The {@code Map} on which the Player is currently on.
	 * @param ac
	 *            The requested {@code MovementAction}.
	 */
	public void move(Map map, MovementAction ac) {

		deltaX = ac.getDeltaX();
		deltaY = ac.getDeltaY();

		prevX = x;
		prevY = y;

		x += deltaX;
		y += deltaY;

		if (checkCollision(map)) {

			x -= deltaX;
			y -= deltaY;

		} else {
			GameEngine.addExclsiveAnimation(this);
			pendingAnimation = new PlayerWalkAnimation(prevX, prevY, x, y);
		}
		if (x < prevX) {
			facingLeft = true;

		} else if (x > prevX) {
			facingLeft = false;
		}

		deltaX = 0;
		deltaY = 0;

		// System.out.println("Position is " + x + " " + y);
	}

	@Override
	public void render(Graphics2D g) {

		if (currentAnimation instanceof IdlePlayerAnimation)
			image = Images.PLAYER_IDLE;
		if (currentAnimation instanceof PlayerAttackAnimation)
			image = Images.PLAYER_HIT;

		if (currentAnimation instanceof PlayerWalkAnimation) {
			BufferedImage img = imgLdr.getImage(Images.PLAYER_WALK, currentAnimation.getSceneIndex());
			drawImage(g, img, (int) ((prevX + deltaX * currentAnimation.getSceneIndex()) * Constants.UNIT_DISTANCE),
					(int) ((prevY + deltaY * currentAnimation.getSceneIndex()) * Constants.UNIT_DISTANCE),
					Constants.UNIT_DISTANCE, Constants.UNIT_DISTANCE);
			return;
		}

		BufferedImage img = imgLdr.getImage(image, currentAnimation.getSceneIndex());
		drawImage(g, img, x * Constants.UNIT_DISTANCE, y * Constants.UNIT_DISTANCE, Constants.UNIT_DISTANCE,
				Constants.UNIT_DISTANCE);

	}

	public void tick(Map map) {
		super.tick(map);
		// check if level can be updated;

		if (tillNextXP <= 0) {
			XP++;
			tillNextXP = initialOffsetXP + XP * 10;

			MessageEngine.writeToScreen("Skill level improved :: " + XP);
		}

		weapon = backPack.getWeapon();
		shield = backPack.getShield();

		if (skipTurn) {
			skipTurn = false;
			return;
		}

		LinkedList<GameObject> collectables = map.getCollectables();
		LinkedList<Enemy> enemies = map.getEnemies();

		synchronized (actionQueue) {
			Action ac = actionQueue.poll();
			if (ac instanceof MovementAction) {
				if (canMove)
					move(map, (MovementAction) ac);
			} else if (ac instanceof AttackAction) {
				attack(enemies);
			} else if (ac instanceof CollectAction) {
				collectItem(collectables, map);
			}
		}

		GameEngine.INSTANCE.playerUpdatedSuccessfully();

	}

	/**
	 * Collect a item if it is on the same spot as the player is currently at.
	 * If present, insert it into the {@code BackPack} and remove it from the
	 * {@code Map}. If multiple objects are present on the same spot, then any
	 * one object is selected randomly.
	 * 
	 * @param objects
	 *            The list of objects to check the candidate items from.
	 * @param map
	 *            The current {@code Map} the player is currently in.
	 */
	private void collectItem(LinkedList<GameObject> objects, Map map) {

		for (GameObject c : objects) {
			if (!(c instanceof Collectable))
				continue;

			if (c.isColliding(x, y)) {
				backPack.addItem((Collectable) c);
				map.removeCollectable(c);
				MessageEngine.writeToScreen("You collected a " + c.getName());
				updateSkillLevel(SkillLevelImprovement.ITEM_COLLECTED);

				break;
			}
		}
	}
	/**
	 * 
	 * @param enemies
	 */
	private void attack(LinkedList<Enemy> enemies) {

		for (Enemy e : enemies) {
			if (e == null)
				continue;
			if (e.isNearby(x, y)) {

				if (e.getX() < x)
					facingLeft = true;
				else if (e.getX() > x)
					facingLeft = false;

				Weapon w = backPack.getWeapon();
				int damage = w.inflictDamage(e);
				String s = "";
				if (damage != Constants.DODGED) {
					s += "You hit " + e.getName();
					s += " for ";
					s += damage;
					s += " damage !";
				} else {
					s += e.getName() + " dodged you attack";
				}
				MessageEngine.writeToScreen(s, damage == Constants.DODGED ? Color.MAGENTA : Color.GREEN);
				GameEngine.addExclsiveAnimation(this);
				pendingAnimation = new PlayerAttackAnimation();

				break;
			}
		}

	}

	public boolean checkCollision(Map map) {
		if (!map.isWalkable(x, y))
			return true;

		Tile tile = map.getTile(x, y);

		if (tile instanceof Door) {
			// MessageEngine.writeToScreen("You have opened a door");
			Door d = (Door) tile;
			if (d.isLocked() && !d.open(0)) {
				return true;
			}
		}

		for (GameObject ob : map.getEnemies()) {
			if (ob.isColliding(x, y)) {
				return true;
			}
		}
		return false;
	}

	public void eatFood(Food food) {
		hunger = Math.max(0, hunger - food.getValue());
		MessageEngine.writeToScreen("You ate a " + food.getClass().getSimpleName());
	}

	public void increaseMoves() {
		// MessageEngine.writeToScreen("move increased "+hunger);
		hunger++;
		hunger = Math.min(STARVE_LIMIT + 1, hunger);
		moves++;
		if (moves % 10 == 0 && hunger <= HUNGER_LIMIT) {

			increaseLife(2);
		}
		if (hunger > STARVE_LIMIT) {
			if (moves % 15 == 0) {
				decreaseLife(3);
				;
			}
		}
	}

	public int getMoves() {
		return moves;
	}

	@Override
	public void updateGraphics() {

		if (pendingAnimation != null && pendingAnimation instanceof ExclusiveAnimation && GameEngine.canLock(this)) {
			currentAnimation = pendingAnimation;
			pendingAnimation = null;
		}

		if (currentAnimation.hasEnded()) {
			if (currentAnimation instanceof ExclusiveAnimation)
				GameEngine.removeAnmation(this);

			currentAnimation = new IdlePlayerAnimation();
		} else {
			currentAnimation.updateAnimation();
			if (currentAnimation instanceof PlayerWalkAnimation) {
				deltaX = ((PlayerWalkAnimation) currentAnimation).diffx;
				deltaY = ((PlayerWalkAnimation) currentAnimation).diffy;
			} else {
				deltaX = 0;
				deltaY = 0;

			}
		}

		if (!(currentAnimation instanceof PlayerWalkAnimation)) {
			prevX = x;
			prevY = y;
		}

	}

	@Override
	public boolean hasOngoingExclusiveAnimations() {
		// TODO Auto-generated method stub
		return currentAnimation instanceof ExclusiveAnimation;
	}

	public static Player getINSTANCE() {
		return INSTANCE;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public Shield getShield() {
		return shield;
	}

	public void disableMovement() {
		canMove = false;
	}

	public void enableMovement() {
		canMove = true;
	}

	public void clearActionQueue() {
		actionQueue.clear();
	}

	private boolean hasPendingActions() {
		return actionQueue.size() != 0;
	}

	public void addAction(Action action) {
		synchronized (actionQueue) {
			if (hasPendingActions())
				clearActionQueue();
			actionQueue.offer(action);
		}
	}

	public void updateSkillLevel(SkillLevelImprovement imp) {
		// MessageEngine.writeToScreen("New skill improvement "
		// +imp.toString());
		tillNextXP -= imp.deltaSkillImprovement;
	}

	public int getXP() {
		return XP;
	}

	public int getStrength() {
		return strength;
	}

	public void increaseStrength() {
		maxLife += 30;
		life += 30;
		strength++;
	}

	@Override
	public String getInfo() {
		return "This is the Player";
	}

}
