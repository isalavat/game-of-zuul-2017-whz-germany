package de.fh_zwickau.oose.zuul.model;

import java.io.Serializable;
/**
 * represents the coordinate point, the main 
 * attributes of which are points x and y
 * @author salavat
 *
 */
public class ExitPoint implements Serializable{
	private double x;
	private double y;
	public ExitPoint(double x, double y){
		this.x = x;
		this.y = y;
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	
}
