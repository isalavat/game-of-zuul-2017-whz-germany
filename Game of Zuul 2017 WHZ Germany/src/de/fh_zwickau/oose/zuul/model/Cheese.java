package de.fh_zwickau.oose.zuul.model;

/**
 * represents the cheese that is used as
 * an item in the game. This item has no effects
 * @author salavat
 *
 */
public class Cheese extends Item{
	
	public Cheese(String name, String description, String imagePath, double x, double y){
		super(name, description, imagePath, x, y);
		x = 200;
		y = 350;
	}
	
	@Override
	public void use(Player player) {}
}
