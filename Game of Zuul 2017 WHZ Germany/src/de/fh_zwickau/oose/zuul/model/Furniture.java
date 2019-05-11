package de.fh_zwickau.oose.zuul.model;

import java.io.Serializable;
/**
 * Class describing the model of furniture.
 * The player  can not have a furniture, it should 
 * be only in the room
 * @author salavat
 *
 */
public class Furniture implements Serializable{
	
	private double x;
	private double y;
	private String name;
	private String imagePath;
	
	public Furniture(String name, String imagePath, double x, double y) {
		this.name = name;
		this.imagePath = imagePath;
		this.x = x;
		this.y = y;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getImagePath() {
		return imagePath;
	}
	
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
}
