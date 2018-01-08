package objects.playerResource.armours;

import maps.Map;
import objects.Collectable;
import objects.GameObject;
import objects.Throwable;
import objects.entities.Player;
import objects.playerResource.selectionCriterias.Identifyable;
import objects.playerResource.selectionCriterias.Upgradable;

public abstract class Shield extends GameObject implements Collectable, Throwable, Identifyable, Upgradable {

	public Shield(int x, int y, int imageCode) {
		super(x, y, imageCode);

		// TODO Auto-generated constructor stub
	}

	protected int absorbDamage;
	protected int minStrength;
	protected int level;

	public int getAbsorbDamage() {
		return absorbDamage;
	}

	public void setAbsorbDamage(int absorbDamage) {
		this.absorbDamage = absorbDamage;
	}

	public int getMinStrength() {
		return minStrength;
	}

	public void setMinStrength(int minStrength) {
		this.minStrength = minStrength;
	}

	/*public int getLevel() {
		return level;
	}*/

	public void updateGraphics() {
	}

	public void throwTo(int x, int y) {
	}

	public void tick(Map map) {
	}

	public void use(Map map) {

	}

	@Override
	public String getName() {
		return "Shield";
	}

	protected String getAdditionalInfo() {
		String toRet = "";
		if (p == null) p = Player.getINSTANCE();

		toRet += "Min Strength   : " + minStrength;
		toRet += "\n";
		toRet += "Level          : " + level;
		toRet += "\n";
		toRet += "Damage absorb capability : " + absorbDamage;
		toRet += "\n";
		if (p.getStrength() < minStrength) {
			toRet += "Because of your inadequate strength, this weapon might be too heavy for you.";
		}

		return toRet;
	}

	public boolean notIdentified() {
		return false;
	}
}
