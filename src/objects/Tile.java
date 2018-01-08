package objects;

import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

import imageHandlers.Images;

/**
 * This is the superclass for all the tiles that are used in the game. This does
 * not add much of a functionality as of yet to the subclasses over
 * {@code GameObjet} but helps primarily as a marker class.
 * 
 * @author rajarshibasu
 *
 */
public abstract class Tile extends GameObject {

	protected BufferedImage darkerVersion;
	protected boolean traversed;
	
	
	public Tile(int x, int y, Images image) {
		super(x, y, 0);
		darkerVersion = changeOpacity(imgLdr.getImage(image, 0), 0.5f);
	}

	/**
	 * This is a non used method that changes the opacity of an image, that is
	 * makes a darker version of the image.
	 * 
	 * TODO : move this method to Utilies later..maybe :)
	 * 
	 * @param sourceImage
	 *            The image whose darker version is to be created.
	 * @param opacity
	 *            The desired opacity.
	 * @return The new darker BufferedImage.
	 */
	private BufferedImage changeOpacity(BufferedImage sourceImage, float opacity) {
		// if(sourceImage==null)System.out.println("Sdgsdfdsfsfdsdfsdsffddsf");
		BufferedImage dstImage = null;
		float[] factors = new float[] { 1.0f, 1f, 1, opacity };
		float[] offsets = new float[] { 0.0f, 0.0f, 0.0f, 0.0f };
		RescaleOp op = new RescaleOp(factors, offsets, null);
		dstImage = op.filter(sourceImage, null);
		return dstImage;
	}

}
