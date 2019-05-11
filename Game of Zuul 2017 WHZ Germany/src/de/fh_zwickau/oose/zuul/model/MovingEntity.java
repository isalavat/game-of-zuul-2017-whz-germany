package de.fh_zwickau.oose.zuul.model;

import java.io.Serializable;

/**
 * A class describing the graphical state (the sprite and 
 * the border for the collision detection) of the player 
 *. Parent class of the player and all NPCs
 * @author salavat
 *
 */
public abstract class MovingEntity  implements Serializable {
	protected double x;
	protected double y;
	protected double speed;// the speed of moving
	protected String spriteSheetPath;
	public boolean directions[]={false, false, false, false};//{up, down, right, left}
	
	protected MovingEntity(double x, double y, String spriteSheetPath, double speed){
		this.x = x;
		this.y = y;
		this.spriteSheetPath = spriteSheetPath;
		this.speed = speed;
	}
	
	protected MovingEntity(double x, double y, String spriteSheetPath){
		this.x = x;
		this.y = y;
		this.spriteSheetPath = spriteSheetPath;
		this.speed = 1;
	}
   
	/**
	 *moves the entity on the updated coordinate
	 */
	public void relocate_(){
		if(directions[0]){
			y-=speed;
		}	
		if(directions[1]){
			y+=speed;
		}	
		if(directions[2]){
			x+=speed;
		}	
		if(directions[3]){
			x-=speed;
		}	
	}
	
	/**
	 * cancels the last move (for collision)
	 */
	public void relocateBack_(){
		if(directions[0]){
			y+=speed;
		}
		if(directions[1]){
			y-=speed;
		}
		if(directions[2]){
			x-=speed;
		}	
		if(directions[3]){
			x+=speed;
		}
	}
	
	/**
	 * sets  moving entity directions to false
	 */
	public void setDirectionsToFalse(){
		for (int i = 0;i<directions.length; i++){
			directions[i] = false;
		}
	}
	
	/**
	 * returns a boolean array of directions
	 * @return
	 */
	public boolean[] getDirections() {
			return directions;
	}
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public String getSpriteSheetPath() {
		return spriteSheetPath;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}
}
