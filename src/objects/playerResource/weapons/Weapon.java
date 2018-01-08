package objects.playerResource.weapons;

import framework.Utilities;
import maps.Map;
import objects.Collectable;
import objects.GameObject;
import objects.Throwable;
import objects.entities.Enemy;
import objects.entities.Player;
import objects.playerResource.selectionCriterias.Identifyable;
import objects.playerResource.selectionCriterias.Upgradable;

public abstract class Weapon extends GameObject implements Collectable, Throwable, Identifyable, Upgradable {

	protected int averageDamage;
	protected int minStrength;
	protected int level;

	public Weapon(int averageDamage, int minStrength, int level, int x, int y) {
		super(x, y, 0);
		this.averageDamage = averageDamage;
		this.minStrength = minStrength;
		this.level = level;
	}

	public int getAverageDamage() {
		return averageDamage;
	}

	public void setAverageDamage(int averageDamage) {
		this.averageDamage = averageDamage;
	}

	public int getMinStrength() {
		return minStrength;
	}

	public void setMinStrength(int minStrength) {
		this.minStrength = minStrength;
	}

	public int getLevel() {
		return level;
	}

	/**
	 * Inflicts some damage on the enemy factoring in some randomness and based
	 * on several factors of both the enemy and the weapon itself.
	 * 
	 * @param enemy
	 *            The enemy to inflict the damage upon.
	 * @return The damage that is inflicted.
	 */
	public int inflictDamage(Enemy enemy) {
		if (p == null)
			p = Player.getINSTANCE();

		int x = Utilities.getDamage(averageDamage, enemy.getDefenseStrength(), p.getXP());

		enemy.decreaseLife(Math.max(x, 0));

		return x;
	}

	public void updateGraphics() {
	}

	public void tick(Map map) {
	}

	public void throwTo(int x, int y) {
	}

	public void use(Map map) {

	}

	/**
	 * This method gives some additional information about this particular type
	 * of {@code Weapon}, including some of it's statistics. This is basically
	 * intended to be used along with the {@code getInfo()} method.
	 * 
	 * @return Some additional information.
	 */
	protected String getAdditionalInfo() {
		String toRet = "";
		if (p == null)
			p = Player.getINSTANCE();

		toRet += "Min Strength   : " + minStrength;
		toRet += "\n";
		toRet += "Level          : " + level;
		toRet += "\n";
		toRet += "Average Damage : " + averageDamage;
		toRet += "\n";
		if (p.getStrength() < minStrength) {
			toRet += "Because of your inadequate strength, this weapon might be too heavy for you.";
		}

		return toRet;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Weapon";
	}
	
}
