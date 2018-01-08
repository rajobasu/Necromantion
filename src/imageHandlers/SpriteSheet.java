package imageHandlers;

import java.awt.image.BufferedImage;

public class SpriteSheet {

	public static BufferedImage[] getSprites(BufferedImage image){
		final int WIDTH=image.getWidth();
		final int HEIGHT=image.getHeight();
				
		final int ROWS=HEIGHT/32;
		final int COLS=WIDTH/32;
		
		
		BufferedImage[] images= new BufferedImage[ROWS*COLS];
		
		for(int i=0;i<ROWS;i++){
			for(int j=0;j<COLS;j++){
				images[i*COLS+j]=image.getSubimage(j*32, i*32, 32, 32);
			}
		}
		
		return images;
	}

}
