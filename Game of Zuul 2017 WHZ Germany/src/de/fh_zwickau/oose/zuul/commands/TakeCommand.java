package de.fh_zwickau.oose.zuul.commands;

import de.fh_zwickau.oose.zuul.model.Player;

/**
 * Implementation of the 'take' user command.
 */
public class TakeCommand  extends Command{
	
	/**
	 * if "take itemName" was entered, it tries to take
	 * an item by given name from current room.
	 */
	@Override
	public boolean execute(Player player) {
		// TODO Auto-generated method stub
		if(hasSecondWord()){
			String itemName = getSecondWord();
			player.take(itemName);
		}
		return false;
	}
}
