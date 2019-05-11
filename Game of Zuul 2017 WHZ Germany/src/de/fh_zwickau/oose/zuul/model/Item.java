package de.fh_zwickau.oose.zuul.model;

import java.io.Serializable;
/**
 * represents the item that a player can have in 
 * his inventory. And also they can be in the rooms. 
 * Some items that extend from this class have an 
 * effect. The effect depends on how the abstract 
 * method "use" was implemented
 * @author salavat
 *
 */
public abstract class Item  implements Serializable{

	protected String name;
	protected String description;
	protected double x;
	protected double y;
	private boolean clicked; //the state was clicked with the mouse
	protected String imagePath;
	public Item(String name, String description, String imagePath, double x, double y){
		this.name = name;
		this.description = description;
		this.y = y;
		this.x = x;
		this.imagePath = imagePath;
		this.clicked = false;
	}

	public Item(String name, String description, String imagePath){
		this.name = name;
		this.description = description;
		this.y = 100;
		this.x = 100;
		this.imagePath = imagePath;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void  setDescription(String description) {
		this.description = description;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public String getImagePath() {
		return imagePath;
	}

	public boolean isClicked() {
		return clicked;
	}

	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}
	/**
	 * An abstract method that is implemented by 
	 * some items that have an effect. an abstract 
	 * method that is implemented by some themes 
	 * that have an effect. That is, the behavior 
	 * of the effect is determined here
	 * @param player
	 */
	public abstract void use(Player player);

}
