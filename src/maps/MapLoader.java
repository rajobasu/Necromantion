package maps;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import factories.RandomArmourGenerator;
import factories.RandomFoodGenerator;
import factories.RandomHolographGenerator;
import factories.RandomPotionGenerator;
import factories.RandomTrapGenerator;
import factories.RandomWeaponGenerator;
import framework.engines.GameEngine;
import objects.Stair;
import objects.entities.GnollScout;
import objects.entities.MRat;
import objects.entities.Player;
import objects.misc.AscendingStairs;
import objects.misc.DescendingStair;
import objects.playerResource.armours.LeatherArmor;
import objects.playerResource.food.Ration;
import objects.playerResource.potions.FirePotion;
import objects.playerResource.potions.HealingPotion;
import objects.playerResource.potions.StrengthPotion;
import objects.playerResource.weapons.tier1.Dagger;
import objects.playerResource.weapons.tier1.ShortSword;
import objects.roomItems.Door;
import objects.roomItems.Floor;
import objects.roomItems.Grass;
import objects.roomItems.Wall;
import objects.roomItems.WaterFloor;
import objects.traps.ParalyticGasTrap;
import objects.traps.TeleportationTrap;
import objects.traps.ToxicGasTrap;
import squidpony.squidgrid.mapping.DungeonGenerator;
import squidpony.squidmath.Coord;
import squidpony.squidmath.RNG;
import squidpony.squidmath.XoRoRNG;

/**
 * Loads a new Map , either from a PNG file, or dynamically creates one through
 * procedural generation.
 * 
 * @author Rajarshi
 *
 */
public class MapLoader {

	private GameEngine engine;

	public MapLoader(GameEngine engine) {
		this.engine = engine;
	}

	/**
	 * Loads a map based on the serial number given.It uses a RGB color code
	 * based approach for generating the Map from the given image file.
	 * 
	 * @param index
	 *            -The map serial number
	 * @return The map corrosponding to the given .PNG file.
	 * @throws MapNotFoundException
	 */
	public Map loadMap(int index) throws MapNotFoundException {
		/*    Item ------------------------ RGB
		 * 1  wall ------------------------ 127 | 127 | 127
		 * 2  player ---------------------- 1   | 2   | 3
		 * 3  floor ----------------------- 255 | 255 | 255
		 * 4  door ------------------------ 100 | 0/1 | 0
		 * 5  enemy ----------------------- 255 | 0   | x      {x ~~> enemy Type}
		 * 6  food ------------------------ 123 | 150 | x      {x ~~> food Type}
		 * 7  weapon ---------------------- 123 | x   | y      {x ~~> level , y ~~> Type}
		 * 8  shield ---------------------- 124 | x   | y      {x ~~> level , y ~~> Type}
		 * 9  trap ------------------------ 124 | 124 | x      {x ~~> Type }
		 * 10 Grass ----------------------- 255 | 255 | 0
		 * 11 Stair ----------------------- 198 | 198 | 0/1    { AscendingStair / DescendingStair }
		 * 12 Potion ---------------------- 89  | 98  | x      {x ~~> PotionType}
		 * 		x  ~~  Potion
		 * 	
		 * 	    1	   Healing
		 * 		2      Toxic Gas Potion
		 * 		3
		 * 		4
		 * 		5
		 * 		6
		 * 		
		 * 	
		 */

		Map map = null;
		try {
			BufferedImage image = ImageIO.read(MapLoader.class.getResource("/Map" + index + ".png"));

			int w = image.getWidth();
			int h = image.getHeight();

			map = new Map(index, w, h);

			for (int x = 0; x < w; x++) {
				for (int y = 0; y < h; y++) {
					int pixel = image.getRGB(x, y);

					int red = (pixel >> 16) & 0xff;
					int green = (pixel >> 8) & 0xff;
					int blue = (pixel) & 0xff;
					// wall
					if (red == 127 && green == 127 && blue >= 127 && blue < 137) {
						map.addTile(new Wall(x, y, blue - 127));
					}
					// player
					if (red == 1 && green == 2 && blue == 3) {
						Player p = new Player(x, y, 1);
						engine.setPlayer(p);

						map.addTile(new Floor(x, y, 0));

					}
					// floor
					if (red == 255 && green == 255 && blue == 255) {
						map.addTile(new Floor(x, y, 0));
					}
					// grass
					if (red == 255 && green == 255 && blue == 0) {
						map.addTile(new Grass(x, y, 0));

					}
					// door~~> green : locked or not
					if (red == 100 && (green == 0 || green == 1) && blue == 0) {
						map.addTile(new Door(x, y, 0, (green == 0), blue));
					}
					// blue is enemy type.Normally it will be random except for
					// special enemies
					if (red == 255 && green == 0 && blue == 0) {
						map.addEnemy(new MRat(x, y, 1));
						map.addTile(new Floor(x, y, 0));
					}
					if (red == 255 && green == 0 && blue == 1) {
						map.addEnemy(new GnollScout(x, y, 1));
						map.addTile(new Floor(x, y, 0));
					}
					// weapon : r is always=123.
					if (red == 123 && green == 1 && blue <= 100) {
						map.addCollectable(new ShortSword(x, y, blue));
						map.addTile(new Floor(x, y, 0));
					}
					if (red == 123 && green == 2 && blue <= 100) {
						map.addCollectable(new Dagger(x, y, blue));
						map.addTile(new Floor(x, y, 0));
					}

					// Armour
					if (red == 124 && green == 1 && blue <= 100) {
						map.addCollectable(new LeatherArmor(x, y, 0));
						map.addTile(new Floor(x, y, 0));
					}

					// Food
					if (red == 123 && green == 150 && blue <= 1) {
						map.addCollectable(new Ration(x, y));
						map.addTile(new Floor(x, y, 0));
					}
					// Traps :
					// Toxic Gas
					if (red == 124 && green == 124 && blue == 0) {
						map.addTile(new ToxicGasTrap(x, y, 0));
					}
					// Fire
					if (red == 124 && green == 124 && blue == 1) {
						map.addTile(new TeleportationTrap(x, y, 0));
					}
					// paralytic gas
					if (red == 124 && green == 124 && blue == 2) {
						map.addTile(new ParalyticGasTrap(x, y, 0));
					}
					// Stair
					if (red == 198 && green == 198 && blue == 0) {
						Stair ascendingStair = new AscendingStairs(x, y, 0);

						map.addTile(ascendingStair);
						map.setAscendingStair(ascendingStair);
					}

					if (red == 198 && green == 198 && blue == 1) {
						Stair ascendingStair = new DescendingStair(x, y, 0);

						map.addTile(ascendingStair);
						map.setDescendingStair(ascendingStair);
					}

					// Potions
					if (red == 89 && green == 98) {
						switch (blue) {
							case 1: {
								map.addCollectable(new HealingPotion(x, y));
								break;
							}
							case 2: {
								map.addCollectable(new FirePotion(x, y));
								break;
							}
							case 3: {
								map.addCollectable(new StrengthPotion(x, y));
								break;
							}
							case 4: {
								break;
							}
							case 5: {
								break;
							}
							case 6: {
								break;
							}

						}
						map.addTile(new Floor(x, y, 0));
					}

				}
				map.initPlayer();

			}
		} catch (IllegalArgumentException | IOException e) {

			throw new MapNotFoundException();
		}
		return map;
	}

	/**
	 * This creates a new map via procedural generation and random
	 * generators.The index parameter is used in the random generation of the
	 * items.
	 * 
	 * @param index
	 *            - The serial number of the map
	 * @return a new Map
	 */
	public Map createMap(int index) {
		int w = 40;
		int h = 40;

		DungeonGenerator dg = new DungeonGenerator(w, h, new RNG());
		dg.addDoors(60, true);
		dg.addGrass(20);
		dg.addTraps(4);
		dg.addWater(10);

		Map map;

		map = new Map(index, w + 5, h + 5);

		final char[][] arr = dg.generate();

		Coord stup = dg.stairsUp;
		Coord stdown = dg.stairsDown;

		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				if ((x == stup.x && y == stup.y) || (x == stdown.x && y == stdown.y)) continue;

				// wall
				if (arr[x][y] == '#') {
					map.addTile(new Wall(x, y, 0));
				}

				// floor
				if (arr[x][y] == '.') {
					map.addTile(new Floor(x, y, 0));
				}
				// grass
				if (arr[x][y] == '"') {
					map.addTile(new Grass(x, y, 0));

				}
				// door~~> green : locked or not
				if (arr[x][y] == '/' || arr[x][y] == '+') {
					map.addTile(new Door(x, y, 0, false, 0));
				}
				// traps
				if (arr[x][y] == '^') {
					map.addTile(RandomTrapGenerator.getRandomTrap(x, y));
				}
				// water
				if (arr[x][y] == ',' || arr[x][y] == '~') {
					map.addTile(new WaterFloor(x, y));
				}

			}
		}

		// map.ad
		int x = stup.x;
		int y = stup.y;
		Stair ascendingStair = new AscendingStairs(x, y, 0);

		map.addTile(ascendingStair);
		map.setAscendingStair(ascendingStair);

		x = stdown.x;
		y = stdown.y;

		Stair descendingStair = new DescendingStair(x, y, 0);

		map.addTile(descendingStair);
		map.setDescendingStair(descendingStair);

		// add rendom items to the map;

		XoRoRNG rng = new XoRoRNG();
		int val=rng.nextInt(3, 5);
		for (int i = 0; i < val; i++) {
			Coord crd = dg.utility.randomFloor(arr);
			map.addCollectable(RandomPotionGenerator.getRandomPotion(crd.x, crd.y));
		}

		for (int j = 0; j < rng.nextInt(2); j++) {
			Coord crd = dg.utility.randomFloor(arr);
			map.addCollectable(RandomWeaponGenerator.getRandomWeapon(crd.x, crd.y));
		}

		for (int j = 0; j < rng.nextInt(2); j++) {
			Coord crd = dg.utility.randomFloor(arr);
			map.addCollectable(RandomArmourGenerator.getRandomArmour(crd.x, crd.y));
		}

		for (int j = 0; j < rng.nextInt(1, 4); j++) {
			Coord crd = dg.utility.randomFloor(arr);
			map.addCollectable(RandomHolographGenerator.getRandomHolograph(crd.x, crd.y));
		}
		for (int j = 0; j < rng.nextInt(1, 3); j++) {
			Coord crd = dg.utility.randomFloor(arr);
			map.addCollectable(RandomFoodGenerator.getRandomFood(crd.x, crd.y));
		}

		map.initPlayer();

		return map;
	}

}
