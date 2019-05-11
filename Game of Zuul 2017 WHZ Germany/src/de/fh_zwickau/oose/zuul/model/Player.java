package de.fh_zwickau.oose.zuul.model;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import de.fh_zwickau.oose.zuul.containers.FileContainer;
import de.fh_zwickau.oose.zuul.containers.ItemContainer;

/**
 * This class represents players in the game. Each player has
 * a current location.
 *
 * @author Michael Kolling
 * @version 1.0 (December 2002)
 */
public class Player extends MovingEntity
{
    private Map<String, Item> items;
    private ItemContainer itemContainer;
    private Deque<Room> previousRooms = new ArrayDeque<>();
    private Room currentRoom;
  
    /**
     * Constructor for objects of class Player
     */
    public Player(double x, double y, String spriteSheetPath, double speed)
    {
    	super(x, y, spriteSheetPath, speed);
    	currentRoom = null;
        this.items = new LinkedHashMap<String, Item>();
        this.itemContainer = new ItemContainer();
       
    }

    /**
     *  returns a player's player's map
     * @return
     */
    public Map<String, Item> getItems(){
    	return items;
    }

	/**
     * Try to walk in a given direction. If there is a door
     * this will change the player's location.
     */
    public void walk(String direction)

    {
        // Try to leave current room.
        Optional<Room> nextRoomBox = currentRoom.getExit(direction);
        if(nextRoomBox.isPresent()){
        	previousRooms.addFirst(getCurrentRoom());
        	Room nextRoom = nextRoomBox.get();
        	setLocation(direction, nextRoom);
        	setCurrentRoom(nextRoom);
        }
    }
    
    /**
     * Set the coordinates of the player at the entrance 
     * to the room, depending on which door he came from. 
     * For example, if a player has entered from the eastern 
     * entrance, the coordinates will be adjusted next to 
     * this entry
     * @param direction
     * @param nextRoom
     */
    public void setLocation(String direction, Room nextRoom){
    	
    	switch(direction){
    	case "east":
    		this.x = nextRoom.getExitPoint("west").get().getX();
    		break;
    	case "west":
    		this.x = nextRoom.getExitPoint("east").get().getX()-90;
    		break;
    	case "north":
    		this.y = nextRoom.getExitPoint("south").get().getY()-115;
    		break;
    	case "south":
    		this.y = nextRoom.getExitPoint("north").get().getY()-80;
    		break;	
    	}
    	
    }
    
    /**
     * Trying to walk in direction "back"
     * if there is a previous room, it switches to it.
     */
    public void walkBack() {
    	Room previousRoom = previousRooms.pollFirst();
    	if(previousRoom == null) {
    		return;
    	}
    	setCurrentRoom(previousRoom);
    }
   
    /**
     * Trying to take an item by given name. If the current 
     * room doesn't have this item, it will be printed out
     * @param itemName - String
     */
    public void take(String itemName){
    	Optional<Item> itemBox = getCurrentRoom().getItemWithRemoving(itemName);
    	if(itemBox.isPresent()){
    		addItem(itemBox.get());
    	}
	}
    
    /**
     * Trying to drop an item by given name. If player doesn't have 
     * such item, it will be printed out
     * @param itemName - String
     */
    public void drop(String itemName){
    	Optional<Item> itemBox = itemContainer.getItemWithRemoving(items, itemName);
    	
    	if(itemBox.isPresent()){
    		itemBox.get().setX(x+30);
    		itemBox.get().setY(y+100);
    		getCurrentRoom().addItem(itemBox.get());
    	}
    }
    
    /**
     * Trying to use an item by given name, which has an effect
     * @param itemName - String
     */
    public void use(String itemName){
    	items.get(itemName).use(this);
    }
    
    /**
     * Returns an Optional object of item by a given name 
     * from the player's items removing it from the items map
     * @param itemName - Stirng 
     * @return
     */
    public Optional<Item> getItemWithRemoving(String itemName){
    	return itemContainer.getItemWithRemoving(items, itemName);
    }
    
    /**
     * Returns an Optional object of item by a given name 
     * from the player's items without removing it from the items map
     * @param itemName - Stirng 
     * @return
     */
    public Optional<Item> getItem(String itemName){
    	return itemContainer.getItem(items, itemName);
    }
    
    /**
     * adds the given item to the player's items
     * @param item - Item
     */
    public void addItem(Item item){
    	itemContainer.add(items, item);
    }
    
   /**
	 * Checks if the item exists by the given name in the player's items.
	 * If there is by given name an item, returns true, otherwise false
	 * @param itemName - String
	 * @return boolean
	 */
	public boolean isItemContained(String itemName){
		return itemContainer.contains(items, itemName);
	}
	
	 /**
     * Return the current room for this player.
     */
    public Room getCurrentRoom()
    {
        return currentRoom;
    }

    /**
     * Set the current room for this player.
     */
    public void setCurrentRoom(Room room)
    {
        currentRoom = room;
    }
}
