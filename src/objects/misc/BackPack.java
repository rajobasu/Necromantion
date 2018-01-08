package objects.misc;

import java.util.LinkedList;

import buttonMsc.ButtonEvent;
import buttonMsc.ButtonListener;
import objects.Collectable;
import objects.playerResource.armours.BaseArmour;
import objects.playerResource.armours.ClothArmor;
import objects.playerResource.armours.Shield;
import objects.playerResource.weapons.BaseWeapon;
import objects.playerResource.weapons.Weapon;
import objects.playerResource.weapons.tier1.Dagger;

public class BackPack implements ButtonListener {

	private static BackPack instance;
	private Weapon weapon;
	private Collectable selected;
	private Shield shield;
	private LinkedList<Collectable> items;
	private final int maxSize;

	private final Weapon baseWeapon;
	private final Shield baseArmour;
	//public static final int NONE=-1;

	private BackPack() {
		maxSize = 20;
		items = new LinkedList<>();

		setShield(new ClothArmor(0, 0, 0));

		setWeapon(new Dagger(0, 0, 0));

		//	addItem(new Dagger(0,0,0));
		//	addItem(new IdentifyHolograph(0, 0, 0));

		baseArmour = new BaseArmour();
		baseWeapon = new BaseWeapon();
	}

	public static BackPack INSTANCE() {
		if (instance == null) {
			instance = new BackPack();
		}

		return instance;
	}

	@Override
	public void buttonClicked(ButtonEvent e) {

		selected = e.getObject();

	}

	public LinkedList<Collectable> getItems() {
		return items;
	}

	public Weapon getWeapon() {
		if (weapon == null) return baseWeapon;

		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	public Collectable getSelected() {
		return selected;
	}

	public void setSelected(Collectable selected) {
		this.selected = selected;
	}

	public Shield getShield() {
		if (shield == null) return baseArmour;

		return shield;
	}

	public void setShield(Shield shield) {
		this.shield = shield;
	}

	public void addItem(Collectable c) {
		if (isFull()) return;
		items.add(c);
	}

	public void removeItem(Collectable c) {
		items.remove(c);
	}

	public boolean isFull() {
		return items.size() >= maxSize;
	}

}
