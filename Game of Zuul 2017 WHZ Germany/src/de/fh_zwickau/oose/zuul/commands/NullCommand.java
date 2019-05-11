package de.fh_zwickau.oose.zuul.commands;

import de.fh_zwickau.oose.zuul.model.Player;

/**
 * Implementation of the null command.
 * 
 * @author Michael Kolling
 * @version 1.0 (December 2002)
 */
public class NullCommand extends Command
{
    
    /**
     * Constructor for objects of class HelpCommand
     */
    public NullCommand()
    {
        // nothing to do
    }
    
    /**
     * Print out an error message
     */
    public boolean execute(Player player)
    {	
    	String text = "I don't understand...(Type \"help\" for help.)";
    	return false;
    }
}
