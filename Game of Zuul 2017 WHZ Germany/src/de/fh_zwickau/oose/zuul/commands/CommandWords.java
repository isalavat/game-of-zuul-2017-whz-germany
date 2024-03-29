package de.fh_zwickau.oose.zuul.commands;
import java.util.HashMap;
import java.util.Iterator;

/**
 * This class is the main class of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game.
 *
 * This class holds a collection of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Michael Kolling and David J. Barnes
 * @extended_by Erlan Modlaliev and Salavat Islamov
 */

public class CommandWords
{
    private HashMap<String, Command> commands;

    /**
     * Constructor - initialise the command words.
     */
    public CommandWords()
    {
        commands = new HashMap<String, Command>();
        commands.put("go", new GoCommand());
        commands.put("help", new HelpCommand(this));
        commands.put("quit", new QuitCommand());
        commands.put("take", new TakeCommand());
        commands.put("drop", new DropCommand());
        commands.put("use", new UseCommand());
    }

    /**
     * Given a command word, find and return the matching command object.
     * Return null if there is no command with this name.
     */
    public Command get(String word)
    {
        return (Command)commands.get(word);
    }

    /**
     * Print all valid commands to System.out.
     */
    public void showAll()
    {
    	String outputLine = "";
        for(Iterator i = commands.keySet().iterator(); i.hasNext(); ) {
            outputLine+=i.next() + "  ";
          }
        
      }
}
