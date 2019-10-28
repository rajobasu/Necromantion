package objects.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import animations.ExclusiveAnimation;
import animations.IdleMRatAnimation;
import animations.MRatAttackAnimation;
import framework.Constants;
import framework.engines.GameEngine;
import framework.engines.MessageEngine;
import imageHandlers.Images;
import maps.Map;

public class MRat extends Enemy {

	public MRat(int x, int y, int imageCode) {
		super(x, y, imageCode, 10, 5, 8);
		image = Images.MRAT_IDLE;

		currentAnimation = new IdleMRatAnimation();
	}

	@Override
	public void tick(Map map) {
		super.tick(map);

		if (life <= 0)
			return;

		if (p == null)
			p = Player.getINSTANCE();
		/*
		 * if(location.distanceTo(p.getLocation())<20){
		 * 
		 * }
		 **/
		if (p.isNearby(x, y)) {
			int damage = attackEnemy(p);

			String s = "";
			if (damage == Constants.DODGED) {
				s += "You dodged " + getName() + "'s attack";
			} else {
				s = "Rat attacked you for ";
				s += damage;
				s += " damage !.";

			}

			MessageEngine.writeToScreen(s, damage == Constants.DODGED ? Color.GREEN : Color.RED);

			GameEngine.addExclsiveAnimation(this);
			pendingAnimation = new MRatAttackAnimation();

		} else {
			move(map);
		}

		if (life == 0) {
			MessageEngine.writeToScreen("Enemy is Dead");
		}
	}

	@Override
	public void render(Graphics2D g) {

		if (currentAnimation instanceof IdleMRatAnimation)
			image = Images.MRAT_IDLE;
		if (currentAnimation instanceof MRatAttackAnimation)
			image = Images.MRAT_HIT;

		BufferedImage img = imgLdr.getImage(image, currentAnimation.getSceneIndex());

		int u = Constants.UNIT_DISTANCE;
		g.drawImage(img, x * u, y * u, u, u, null);
		super.render(g);

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

			currentAnimation = new IdleMRatAnimation();
		} else {
			currentAnimation.updateAnimation();
		}

	}

	@Override
	public String getName() {

		return "Rat";
	}

}
