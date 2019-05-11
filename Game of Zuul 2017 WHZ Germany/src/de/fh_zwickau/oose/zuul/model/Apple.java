package de.fh_zwickau.oose.zuul.model;

/**
 * represents the apple that is used as
 * an item in the game. This item has no effects
 * @author salavat
 *
 */
public class Apple extends Item {

	public Apple(String name, String description, String imagePath, double x, double y){
		super(name, description, imagePath, x, y);
		x = 300;
		y = 200;
	}
	
	@Override
	public void use(Player player) {}
}
