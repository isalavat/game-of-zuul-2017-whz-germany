package de.fh_zwickau.oose.zuul.spritecutters;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

/**
 * A class that are responsible for cutting the sprite
 * @author salavat
 *
 */
public class FourDirectionSpriteCutter extends SpriteCutter{

	/**
	 * returns the required sprite from the
	 * spritesheet depending on the direction
	 * and number of sparayts in the on-door store
	 * @param spriteSheet
	 * @param spriteCount
	 * @param direction
	 * @return
	 */
	public Image getSprite(Image spriteSheet,int spriteCount, boolean directions[], double speed){

		if (speed<=1.5){
			delay = 4;

		}else if(speed > 1.7 && speed < 3.0){
			delay = 3;
		}

		if (count>delay){
			count = 0;
		}

		width = (int) (spriteSheet.getWidth()/spriteCount);
		height = (int) (spriteSheet.getHeight()/4);


		if(iteration == spriteCount-1){
			iteration=0;
		}
		if (count == delay){
			iteration++;
			count = 0;
		}
		count++;
		if(spriteCount == 1) {
			iteration=0;
			count=0;
		}
		PixelReader reader = spriteSheet.getPixelReader();
		if (directions[0]){
			return new WritableImage(reader, width*iteration, 0, width, height);
		}else if(directions[1]){
			return new WritableImage(reader, width*iteration, height*2, width, height);
		}else if(directions[2]){
			return new WritableImage(reader, width*iteration, height*3, width, height);
		}else if(directions[3]){
			return new WritableImage(reader, width*iteration, height, width, height);
		}

		return  new WritableImage(reader, 0, height*2, width, height);

	}
}








