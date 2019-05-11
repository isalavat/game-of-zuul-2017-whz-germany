package de.fh_zwickau.oose.zuul.view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import de.fh_zwickau.oose.zuul.containers.FileContainer;
import de.fh_zwickau.oose.zuul.model.Furniture;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Represents a view of furniture in a room
 * @author salavat
 *
 */
public class FurnitureView {
	
	private Furniture furniture;
	private ImageView imageView;//picture of the object
	private Rectangle bound; //The boundary for the definition of a collision
	private double boundX;
	private double boundY;
	public FurnitureView(Furniture furniture){
		this.furniture = furniture;
		try(InputStream fis = FileContainer.getFileInputStreamByFileName(furniture.getImagePath())) {
			this.imageView = new ImageView(new Image(fis));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		this.bound = new Rectangle(this.imageView.getImage().getWidth(),this.imageView.getImage().getHeight()/2);
		bound.setStroke(Color.TRANSPARENT);
		bound.setFill(Color.TRANSPARENT);
		relocate();
		boundX = furniture.getX();
		boundY = furniture.getY()+this.imageView.getImage().getHeight()/2;
	}
	
	public void relocate(){
		this.imageView.relocate(furniture.getX(), furniture.getY());
		this.bound.relocate(boundX, boundY);
		
	}
	
	public ImageView getImageView() {
		return imageView;
	}
	
	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}
	
	public Rectangle getBound() {
		return bound;
	}
	
	public void setBound(Rectangle bound) {
		this.bound = bound;
	}
	
	public double getBoundX() {
		return boundX;
	}

	public double getBoundY() {
		return boundY;
	}
}
