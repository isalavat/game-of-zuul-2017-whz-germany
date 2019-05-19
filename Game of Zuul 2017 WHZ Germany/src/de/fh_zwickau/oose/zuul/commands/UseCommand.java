package de.fh_zwickau.oose.zuul.commands;

import de.fh_zwickau.oose.zuul.model.Player;
/**
 * Implementation of the 'use' user command.
 * @author Erlan Modlaliev and Salavat Islamov
 */
public class UseCommand extends Command{
	
	/**
	 * if "use itemName" was entered, it tries to use 
	 * an item by given name, which has an effect
	 */
	@Override
	public boolean execute(Player player) {
		// TODO Auto-generated method stub
		if(hasSecondWord()){
			String itemName = getSecondWord();
			player.use(itemName);
		}
		return false;
	}

}
