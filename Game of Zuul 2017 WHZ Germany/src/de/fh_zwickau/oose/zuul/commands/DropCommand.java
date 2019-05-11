package de.fh_zwickau.oose.zuul.commands;

import de.fh_zwickau.oose.zuul.model.Player;

/**
 * Implementation of the 'drop' user command.
 */
public class DropCommand extends Command{

	/**
	 * if command "drop itemName" was entered, it tries to drop
	 * an item by given name.
	 */
	@Override
	public boolean execute(Player player) {
		// TODO Auto-generated method stub
		if(hasSecondWord()){
			String itemName = getSecondWord();
			player.drop(itemName);
		}
		return false;
	}

}
