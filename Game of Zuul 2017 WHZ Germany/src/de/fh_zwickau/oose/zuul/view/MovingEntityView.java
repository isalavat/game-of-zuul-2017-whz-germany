package de.fh_zwickau.oose.zuul.view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import de.fh_zwickau.oose.zuul.containers.FileContainer;
import de.fh_zwickau.oose.zuul.model.MovingEntity;
import de.fh_zwickau.oose.zuul.spritecutters.FourDirectionSpriteCutter;
import de.fh_zwickau.oose.zuul.spritecutters.SpriteCutter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

/**
 * Class representing the view of moving entity (that is, the player's or NPC's model)
 * @author salavat
 *
 */
public abstract class MovingEntityView {
	protected ImageView imageView; //picture of the entity
	protected Ellipse bound; //The boundary for the definition of a collision
	protected SpriteCutter spriteCutter;
	protected Image spriteSheet;
	protected int spriteCount = 9; //count of sprites per line
	protected double boundX ;
	protected double boundY ;
	protected MovingEntity entity;

	public MovingEntityView(MovingEntity entity, int spriteCount){
		this.entity = entity;
		this.spriteCount = spriteCount;
		spriteCutter = new FourDirectionSpriteCutter();
		try (InputStream fis = FileContainer.getFileInputStreamByFileName(entity.getSpriteSheetPath())){
			spriteSheet = new Image(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		this.imageView = new ImageView(spriteCutter.getSprite(spriteSheet, spriteCount, entity.getDirections(),entity.getSpeed()));
		this.bound = new Ellipse();
		this.bound.setRadiusX((spriteSheet.getWidth()/this.spriteCount)*0.25);
		this.bound.setRadiusY((spriteSheet.getHeight()/4)*0.06);
		this.bound.setStroke(Color.TRANSPARENT);
		this.bound.setFill(Color.TRANSPARENT);
	}

	/**
	 * moves the player (sprite and border) according
	 * to the coordinates of the player's model
	 */
	public void relocate(){
		imageView.relocate(entity.getX(), entity.getY());
		boundX = entity.getX()+20;
		boundY = entity.getY()+imageView.getImage().getHeight()-14;
		bound.relocate(boundX, boundY);
	}

	/**
	 * sets the right sprite from the spritsheet
	 * according to the direction of the player
	 */
	public void setSprite(){
		imageView.setImage(spriteCutter.getSprite(spriteSheet, spriteCount, entity.getDirections(), entity.getSpeed()));
	}

	public void setEntity(MovingEntity entity){
		this.entity = entity;
	}


	public ImageView getImageView() {
		return imageView;
	}

	public Ellipse getBound() {
		return bound;
	}


	public double getBoundX() {
		return boundX;
	}

	public double getBoundY() {
		return boundY;
	}
}
