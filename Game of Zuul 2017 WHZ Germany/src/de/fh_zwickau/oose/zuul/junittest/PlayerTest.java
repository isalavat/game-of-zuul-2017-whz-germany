package de.fh_zwickau.oose.zuul.junittest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.fh_zwickau.oose.zuul.model.Game;
import de.fh_zwickau.oose.zuul.model.Player;
import de.fh_zwickau.oose.zuul.model.Room;

public class PlayerTest {
	public Game game;
	public Player player;

	@Before
	public void init(){
		game = new Game();
		player = game.getPlayer();
	}
	
	@Test
	public void take(){
		String itemName = "Apple";
		player.take("Apple");
		assertTrue(player.isItemContained(itemName));
		assertFalse(player.getCurrentRoom().isItemContained(itemName));
	}
	
	
	@Test
	public void drop(){
		String itemName = "Cheese";
		player.drop(itemName);
		assertFalse(player.isItemContained(itemName));
		assertTrue(player.getCurrentRoom().isItemContained(itemName));
	}
	

	@Test
	public void walkBack(){
		Room firstRoom = player.getCurrentRoom();
		player.walk("south");
		player.walk("east");
		player.walkBack();
		player.walkBack();
		assertTrue(firstRoom.equals(player.getCurrentRoom()));
	}
	
	@Test
	public void walk(){
		Room previousRoom = player.getCurrentRoom();
		player.walk("east");
		assertTrue(previousRoom.equals(player.getCurrentRoom().getExit("west").get()));
		
		previousRoom = player.getCurrentRoom();
		player.walk("east");
		assertTrue(previousRoom.equals(player.getCurrentRoom().getExit("west").get()));
		
		previousRoom = player.getCurrentRoom();
		player.walk("north");
		assertTrue(previousRoom.equals(player.getCurrentRoom().getExit("south").get()));
		
		previousRoom = player.getCurrentRoom();
		player.walk("west");
		assertTrue(previousRoom.equals(player.getCurrentRoom().getExit("east").get()));
		
		previousRoom = player.getCurrentRoom();
		player.walk("west");
		assertTrue(previousRoom.equals(player.getCurrentRoom().getExit("east").get()));
		
		previousRoom = player.getCurrentRoom();
		player.walk("north");
		assertTrue(previousRoom.equals(player.getCurrentRoom().getExit("south").get()));
		
		previousRoom = player.getCurrentRoom();
		player.walk("east");
		assertTrue(previousRoom.equals(player.getCurrentRoom().getExit("west").get()));
		
		previousRoom = player.getCurrentRoom();
		player.walk("east");
		assertTrue(previousRoom.equals(player.getCurrentRoom().getExit("west").get()));
		
	}
	
	@Test
	public void relocate_(){
		double x = player.getX();
		double y = player.getY();
		player.setDirectionsToFalse();
		player.getDirections()[0] = true; //up  = true
		player.relocate_();
		assertTrue(y>player.getY());
		player.relocateBack_();
		assertTrue(y==player.getY());
		
		player.setDirectionsToFalse();
		player.getDirections()[1] = true;//down = true
		player.relocate_();
		assertTrue(y<player.getY());
		player.relocateBack_();
		assertTrue(y==player.getY());
		
		player.setDirectionsToFalse();
		player.getDirections()[2] = true;//down = true
		player.relocate_();
		assertTrue(x<player.getX());
		player.relocateBack_();
		assertTrue(x==player.getX());
		
		player.setDirectionsToFalse();
		player.getDirections()[3] = true;//down = true
		player.relocate_();
		assertTrue(x>player.getX());
		player.relocateBack_();
		assertTrue(x==player.getX());
	}
	
	@Test
	public void setDirectionToFalse(){
		for (int i = 0; i<player.getDirections().length; i++){
			player.getDirections()[i] = true;
		}
		
		player.setDirectionsToFalse();
		
		for (int i = 0; i<player.getDirections().length; i++){
			assertFalse(player.getDirections()[i]);
		}
	}
	
}
