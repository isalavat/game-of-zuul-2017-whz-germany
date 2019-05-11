package de.fh_zwickau.oose.zuul.commands;

import de.fh_zwickau.oose.zuul.model.Player;

/**
 * Implementation of the 'help' user command.
 *
 * @author Michael Kolling
 * @version 1.0 (December 2002)
 */
public class HelpCommand extends Command
{
    private CommandWords commandWords;

    /**
     * Constructor for objects of class HelpCommand
     */
    public HelpCommand(CommandWords words)
    {
        commandWords = words;
    }

    /**
     * Print out some help information. Here we print some stupid,
     * cryptic message and a list of the command words.
     * If it's "help go", print out information about go command.
     * Returns always false.
     */
    public boolean execute(Player player)
    {
    	commandWords.showAll();
        return false;
    }

}
