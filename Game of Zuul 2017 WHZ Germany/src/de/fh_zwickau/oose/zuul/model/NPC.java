package de.fh_zwickau.oose.zuul.model;

import java.util.Optional;

/**
 * A class of NPCs that must be communicated with 
 * the player. That is, NPCs contain a puzzle that 
 * the player must decide to continue the game
 * @author salavat
 *
 */
public class NPC extends MovingEntity {
	
	private Riddle riddle; //a riddle that has a question and answers to this question. Only one answer is correct
	private String dialogText; //Ask the player if he wants to solve his riddle
	private Item item;
	public NPC(double x, double y, String spriteSheetPath, Item item, Riddle riddle, String answer) {
		super(x, y, spriteSheetPath);
		this.item = item;
		this.riddle = riddle;
		this.dialogText = answer;
	}

	public Riddle getRiddle() {
		return riddle;
	}

	public void setRiddle(Riddle riddle) {
		this.riddle = riddle;
	}

	public String getDialogText() {
		return dialogText;
	}

	public void setDialogText(String question) {
		this.dialogText = question;
	}

	public Optional<Item> getItem() {
		Item i = item;
		item = null;
		return Optional.ofNullable(i);
	}
}
