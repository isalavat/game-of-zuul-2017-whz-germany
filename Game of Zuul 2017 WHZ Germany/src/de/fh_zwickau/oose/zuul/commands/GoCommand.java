package de.fh_zwickau.oose.zuul.commands;

import de.fh_zwickau.oose.zuul.model.Player;

/**
 * Implementation of the 'go' user command.
 *
 * @author Michael Kolling
 * @extended_by Erlan Moldaliev and Salavat Islamov
 */
public class GoCommand extends Command
{
    /**
     * Constructor for objects of class GoCommand
     */
    public GoCommand()
    {
    }

    /**
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message. Returns always 'false'.
     */
    public boolean execute(Player player)
    {
        if(hasSecondWord()) {
            String direction = getSecondWord();
           

            if(direction.equals("back")) {		//Checking if that's not a direction,
            	player.walkBack();				//but a word "back", that means to go to previous room
            } else {
            	player.walk(direction);
            }
        }
        return false;
    }
}
