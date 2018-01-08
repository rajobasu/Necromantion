package factories;

import objects.playerResource.weapons.Weapon;
import objects.playerResource.weapons.tier1.Dagger;
import objects.playerResource.weapons.tier1.ShortSword;
import objects.playerResource.weapons.tier2.Quarterstaff;
import squidpony.squidmath.XoRoRNG;

public class RandomWeaponGenerator {

	private static XoRoRNG rng;
	static {
		rng=new XoRoRNG();
	}
	
	private  RandomWeaponGenerator() {
		// TODO Auto-generated constructor stub
	}

	public static Weapon getRandomWeapon(int x,int y){
		final int WEAPON_COUNT=3;
		switch(rng.nextInt(WEAPON_COUNT)){
			case 0:return new Dagger(x, y, 0);
			case 1:return new ShortSword(x, y, 0);
			case 2:return new Quarterstaff(x, y, 0);
		}
		
		
		
		return null;
	}
}
