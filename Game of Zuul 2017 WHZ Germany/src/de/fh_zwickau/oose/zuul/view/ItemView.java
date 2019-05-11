package de.fh_zwickau.oose.zuul.view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import de.fh_zwickau.oose.zuul.containers.FileContainer;
import de.fh_zwickau.oose.zuul.model.Item;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
/**
 * Class representing the item's view (that is, the item's model)
 * @author salavat
 */
 
public class ItemView {
	
	private Item item;
	private ImageView imageView;
	private Ellipse bound;
	private double boundX;
	private double boundY;

	public ItemView(Item item){
		this.item = item;
		try(InputStream fis = FileContainer.getFileInputStreamByFileName(item.getImagePath())) {
			this.imageView = new ImageView(new Image(fis));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		this.bound = new Ellipse();
		this.bound.setRadiusX(this.imageView.getImage().getWidth()*0.5);
		this.bound.setRadiusY(this.imageView.getImage().getHeight()*0.15);
		this.bound.setStroke(Color.TRANSPARENT);
		this.bound.setFill(Color.TRANSPARENT);
		relocate();
	}

	/**
	 * moves the item in accordance with the model (coordinate)
	 */
	public void relocate(){
		this.imageView.relocate(item.getX(), item.getY());
		this.boundX = this.item.getX();
		this.boundY = this.item.getY()+this.imageView.getImage().getHeight()*0.6;
		bound.relocate(boundX, boundY);
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

	public String getItemViewName(){
		return item.getName();
	}

	public Item getItem() {
		return item;
	}

}
