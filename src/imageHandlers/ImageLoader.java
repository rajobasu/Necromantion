package imageHandlers;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import framework.Constants;

public class ImageLoader {
	private BufferedImage rug1;
	private BufferedImage wall1;
	private BufferedImage water;

	private BufferedImage[] player_IDLE;
	private BufferedImage[] player_HIT;
	private BufferedImage[] player_WALK;

	private BufferedImage door_open;
	private BufferedImage door_close;

	private BufferedImage backPack;

	private BufferedImage[] mRat_IDLE;
	private BufferedImage[] mRat_HIT;

	private BufferedImage gnollScout;

	private BufferedImage shortSword;
	private BufferedImage quarterStaff;
	private BufferedImage dagger;

	private BufferedImage clothArmor;
	private BufferedImage leatherArmor;

	private BufferedImage ration;

	private BufferedImage deactivatedTrap;
	private BufferedImage toxicGasTrap;
	private BufferedImage fireTrap;
	private BufferedImage paralyticGasTrap;
	private BufferedImage teleportationTrap;
	private BufferedImage spikeTrap;
	private BufferedImage poisonTrap;

	private BufferedImage[] toxicGas;
	private BufferedImage paralyticGas;

	private BufferedImage grass;
	private BufferedImage trampledGrass;
	private BufferedImage burntGrass;

	private BufferedImage[] fire;

	private BufferedImage ascendingStairs;
	private BufferedImage descendingStairs;

	private BufferedImage[] potions;

	private BufferedImage[] scrolls;

	private BufferedImage ovalAlpComp;
	private BufferedImage noTransp_Mask;
	private BufferedImage mediumTransp_Mask;

	private BufferedImage rightShadow_trnpMask;
	private BufferedImage leftShadow_trnpMask;
	private BufferedImage upShadow_trnpMask;
	private BufferedImage downShadow_trnpMask;

	private BufferedImage inventoryBackground;
	private BufferedImage backButton;
	private BufferedImage invSlot;

	private BufferedImage heart;
	private BufferedImage HUDbackground;
	private BufferedImage gameEnd;

	private static ImageLoader instance;

	private ImageLoader() {

		try {
			rug1 = ImageIO.read(ImageLoader.class.getResource("/PD-Floor.png"));
			wall1 = ImageIO.read(ImageLoader.class.getResource("/PD-Wall.png"));
			water = ImageIO.read(ImageLoader.class.getResource("/PD-WaterFloor.png"));

			player_HIT = SpriteSheet.getSprites(ImageIO.read(ImageLoader.class.getResource("/PD-Player_HIT.png")));
			player_WALK = SpriteSheet.getSprites(ImageIO.read(ImageLoader.class.getResource("/PD-Player_WALK.png")));
			player_IDLE = SpriteSheet.getSprites(ImageIO.read(ImageLoader.class.getResource("/PD-Player_IDLE.png")));

			door_open = ImageIO.read(ImageLoader.class.getResource("/PD-OpenDoor.png"));
			door_close = ImageIO.read(ImageLoader.class.getResource("/PD-CloseDoor.png"));
			backPack = ImageIO.read(ImageLoader.class.getResource("/PD-BackPack.png"));

			mRat_IDLE = SpriteSheet.getSprites(ImageIO.read(ImageLoader.class.getResource("/PD-MRat_IDLE.png")));
			mRat_HIT = SpriteSheet.getSprites(ImageIO.read(ImageLoader.class.getResource("/PD-MRat_HIT.png")));

			gnollScout = ImageIO.read(ImageLoader.class.getResource("/PD-GnollScout.png"));
			shortSword = ImageIO.read(ImageLoader.class.getResource("/PD-ShortSword.png"));
			quarterStaff = ImageIO.read(ImageLoader.class.getResource("/PD-Quarterstaff.png"));
			dagger = ImageIO.read(ImageLoader.class.getResource("/PD-Dagger.png"));
			clothArmor = ImageIO.read(ImageLoader.class.getResource("/PD-ClothArmour.png"));
			leatherArmor = ImageIO.read(ImageLoader.class.getResource("/PD-LeatherArmor.png"));
			ration = ImageIO.read(ImageLoader.class.getResource("/PD-Ration.png"));
			toxicGas = SpriteSheet.getSprites(ImageIO.read(ImageLoader.class.getResource("/PD-ToxicGas.png")));
			paralyticGas = ImageIO.read(ImageLoader.class.getResource("/PD-ParalyticGas.png"));

			teleportationTrap = ImageIO.read(ImageLoader.class.getResource("/PD-TeleportationTrap.png"));
			toxicGasTrap = ImageIO.read(ImageLoader.class.getResource("/PD-ToxicGasTrap.png"));
			deactivatedTrap = ImageIO.read(ImageLoader.class.getResource("/PD-DeactivatedTrap.png"));
			paralyticGasTrap = ImageIO.read(ImageLoader.class.getResource("/PD-ParalyticGasTrap.png"));
			spikeTrap = ImageIO.read(ImageLoader.class.getResource("/PD-SpikeTrap.png"));
			poisonTrap = ImageIO.read(ImageLoader.class.getResource("/PD-PoisonTrap.png"));

			grass = ImageIO.read(ImageLoader.class.getResource("/PD-Grass.png"));
			trampledGrass = ImageIO.read(ImageLoader.class.getResource("/PD-TrampledGrass.png"));

			ovalAlpComp = ImageIO.read(ImageLoader.class.getResource("/PD-OvalAlphaCompositeLight.png"));
			noTransp_Mask = ImageIO.read(ImageLoader.class.getResource("/PD-NoTransparencyMask.png"));
			mediumTransp_Mask = ImageIO.read(ImageLoader.class.getResource("/PD-MediumTransparencyMask.png"));

			rightShadow_trnpMask = ImageIO.read(ImageLoader.class.getResource("/PD-FromRight_TransparencyMask.png"));
			leftShadow_trnpMask = ImageIO.read(ImageLoader.class.getResource("/PD-FromLeft_TransparencyMask.png"));
			upShadow_trnpMask = ImageIO.read(ImageLoader.class.getResource("/PD-FromUp_TransparencyMask.png"));
			downShadow_trnpMask = ImageIO.read(ImageLoader.class.getResource("/PD-FromDown_TransparencyMask.png"));

			backButton = ImageIO.read(ImageLoader.class.getResource("/PD-BackButton.png"));
			invSlot = ImageIO.read(ImageLoader.class.getResource("/PD-Slot.png"));

			inventoryBackground = ImageIO.read(ImageLoader.class.getResource("/InventoryBackGroundImages.png"));

			fire = SpriteSheet.getSprites(ImageIO.read(ImageLoader.class.getResource("/PD-Fire.png")));

			burntGrass = ImageIO.read(ImageLoader.class.getResource("/PD-BurntGrass.png"));
			fireTrap = ImageIO.read(ImageLoader.class.getResource("/PD-FireTrap.png"));
			ascendingStairs = ImageIO.read(ImageLoader.class.getResource("/PD-AscendingStairs.png"));
			descendingStairs = ImageIO.read(ImageLoader.class.getResource("/PD-DescendingStairs.png"));

			heart = ImageIO.read(ImageLoader.class.getResource("/HeartImage.png"));
			HUDbackground = ImageIO.read(ImageLoader.class.getResource("/HUDBackground.png"));
			gameEnd = ImageIO.read(ImageLoader.class.getResource("/GameEnd.png"));

			// random potion colour selection
			LinkedList<BufferedImage> potionTempList = new LinkedList<>();
			for (int i = 0; i < Constants.POTION_COUNT; i++)
				potionTempList.add(ImageIO.read(ImageLoader.class.getResource("/PD-Potion-Colour" + (i + 1) + ".png")));
			Collections.shuffle(potionTempList);
			potions = potionTempList.toArray(new BufferedImage[Constants.POTION_COUNT]);

			scrolls = new BufferedImage[1];

			scrolls[0] = ImageIO.read(ImageLoader.class.getResource("/PD-Scroll.png"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public BufferedImage getImage(Images img, int type) {
		if (img == Images.INV_BACKGROUND) return inventoryBackground;
		if (img == Images.FLOOR) return rug1;
		if (img == Images.WALL) return wall1;
		if (img == Images.PLAYER_IDLE) return player_IDLE[type];
		if (img == Images.PLAYER_HIT) return player_HIT[type];
		if (img == Images.PLAYER_WALK) return player_WALK[type];
		if (img == Images.BACKPACK) return backPack;
		if (img == Images.MRAT_IDLE) return mRat_IDLE[type];
		if (img == Images.MRAT_HIT) return mRat_HIT[type];
		if (img == Images.GNOLL_SCOUT) return gnollScout;
		if (img == Images.SHORT_SWORD) return shortSword;
		if (img == Images.QUARTERSTAFF) return quarterStaff;
		if (img == Images.DAGGER) return dagger;
		if (img == Images.CLOTH_ARMOR) return clothArmor;
		if (img == Images.LEATHER_ARMOR) return leatherArmor;
		if (img == Images.RATION) return ration;
		if (img == Images.DEACTIVATED_TRAP) return deactivatedTrap;
		if (img == Images.TOXIC_GAS_TRAP) return toxicGasTrap;
		if (img == Images.PARALYTIC_GAS_TRAP) return paralyticGasTrap;
		if (img == Images.TOXIC_GAS) return toxicGas[type];
		if (img == Images.PARALYTIC_GAS) return paralyticGas;
		if (img == Images.GRASS) return grass;
		if (img == Images.TRAMPLED_GRASS) return trampledGrass;
		if (img == Images.BURNT_GRASS) return burntGrass;
		if (img == Images.FIRE) return fire[type];
		if (img == Images.FIRE_TRAP) return fireTrap;
		if (img == Images.ASCENDING_STAIRS) return ascendingStairs;
		if (img == Images.DESCENDING_STAIRS) return descendingStairs;
		if (img == Images.POTION) return potions[type];
		if (img == Images.SCROLL) return scrolls[0];
		if (img == Images.ALPHA_COMPOSITE__OVAL) return ovalAlpComp;
		if (img == Images.BACK_BUTTON) return backButton;
		if (img == Images.INVENTORY_SLOT) return invSlot;
		if (img == Images.NO_TRANSPARENCY_MASK) return noTransp_Mask;
		if (img == Images.MEDIUM_TRANSPARENCY_MASK) return mediumTransp_Mask;
		if (img == Images.RIGHT_SHADOW_TRANSPARENCY_MASK) return rightShadow_trnpMask;
		if (img == Images.BOTTOM_SHADOW_TRANSPARENCY_MASK) return downShadow_trnpMask;
		if (img == Images.UP_SHADOW_TRANSPARENCY_MASK) return upShadow_trnpMask;
		if (img == Images.LEFT_SHADOW_TRANSPARENCY_MASK) return leftShadow_trnpMask;
		if (img == Images.HEART) return heart;
		if (img == Images.HUD_BACKGROUND) return HUDbackground;
		if (img == Images.GAME_END) return gameEnd;
		if (img == Images.WATER_FLOOR) return water;
		if (img == Images.TELEPORTATION_TRAP) return teleportationTrap;
		if (img == Images.SPIKE_TRAP) return spikeTrap;
		if (img == Images.POISON_TRAP) return poisonTrap;
		if (img == Images.DOOR) {
			if (type == 0) return door_close;
			if (type == 1) return door_open;
		}
		return null;
	}

	public static ImageLoader INSTANCE() {
		if (instance == null) {
			instance = new ImageLoader();
		}

		return instance;
	}
}
