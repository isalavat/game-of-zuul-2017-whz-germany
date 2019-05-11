package de.fh_zwickau.oose.zuul.model;

/**
 * represents the Bread that is used as
 * an item in the game. This item has no effects
 * @author salavat
 *
 */
public class Bread extends Item{

	public Bread(String name, String description, String imagePath, double x, double y){
		super(name, description, imagePath, x, y);
		x = 250;
		y = 320;
	}
	
	@Override
	public void use(Player player) {}

}
