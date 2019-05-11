package de.fh_zwickau.oose.zuul.model;
import java.util.StringTokenizer;
import de.fh_zwickau.oose.zuul.commands.Command;
import de.fh_zwickau.oose.zuul.commands.CommandWords;
import de.fh_zwickau.oose.zuul.commands.NullCommand;

/**
 * This class is responsible for choosing the right 
 * command based on the command words. This class 
 * contains a map of all the commands available in 
 * the game and, depending on the command 
 * words, can select the appropriate command
 * @author salavat
 *
 */

public class Parser 
{

    private CommandWords commands;  // holds all valid command words

    public Parser() 
    {
        commands = new CommandWords();
    }
    /**
     * returns the required command that 
     * corresponds to the command word
     * @param inputLine
     * @return
     */
    public Command getCommand(String inputLine) 
    {
        String word1;
        String word2;

        StringTokenizer tokenizer = new StringTokenizer(inputLine);

        if(tokenizer.hasMoreTokens())
            word1 = tokenizer.nextToken();      // get first word
        else
            word1 = null;
        if(tokenizer.hasMoreTokens())
            word2 = tokenizer.nextToken();      // get second word
        else
            word2 = null;

        Command command = commands.get(word1);
        if(command == null) {
        	command = new NullCommand();
        }
        else {
        	command.setSecondWord(word2);
        }
       
        return command;
    }
  }
