package de.fh_zwickau.oose.zuul.spritecutters;

import javafx.scene.image.Image;
/**
 * Abstract class for the classes that are responsible for cutting the sprite
 * @author salavat
 *
 */
public abstract class SpriteCutter {
	protected int width;
	protected int height;
	protected int iteration = 0;
	protected int delay = 1;
	protected int count = 0;

	/**
	 * returns the required sprite from the spritesheet 
	 * depending on the direction and number of sprite 
	 * in the on-door store
	 * @param spriteSheet
	 * @param spriteCount
	 * @param direction
	 * @return
	 */
	public Image getSprite(Image spriteSheet,int spriteCount, boolean direction[], double speed){
		return spriteSheet;
	}

}
