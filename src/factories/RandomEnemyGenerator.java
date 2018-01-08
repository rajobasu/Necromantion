package factories;

import objects.entities.Enemy;
import objects.entities.GnollScout;
import objects.entities.MRat;
import squidpony.squidmath.XoRoRNG;

public class RandomEnemyGenerator {

	private static XoRoRNG rng=new XoRoRNG();
	
	public static Enemy getRandomEnemy(int x,int y){
		final int TOTAL_ENEMY_COUNT=2;
		
		switch(rng.nextInt(TOTAL_ENEMY_COUNT)){
			case 0:{
				return new MRat(x, y, 0);
			}
			case 1:{
				return new GnollScout(x, y, 0);
			}
				
		}
		
		return null;
	}
	
	private RandomEnemyGenerator(){}

}
