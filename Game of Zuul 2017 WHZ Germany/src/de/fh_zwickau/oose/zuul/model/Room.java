package de.fh_zwickau.oose.zuul.model;
import java.util.Set;

import de.fh_zwickau.oose.zuul.containers.FileContainer;
import de.fh_zwickau.oose.zuul.containers.ItemContainer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game.
 *
 * A "Room" represents one location in the scenery of the game.  It is
 * connected to other rooms via exits.  For each existing exit, the room
 * stores a reference to the neighboring room.
 *
 * @author  Michael Kolling and David J. Barnes
 * @extended_by Erlan Moldaliev and Salavat Islamov
 */

public class Room  implements Serializable
{
    private String description;
    private Map<String, Room> exits;        // stores exits of this room.
    private Map<String, Boolean> exitStates;
    private boolean visited;
    private Map<String, ExitPoint> exitPoints;
    private Map<String ,Item> items = null;
    private Set<Furniture> furnitures = null;
    private NPC npc;
    private ItemContainer itemContainer = null;
    private double points[];
    private String imagePath;
    
    /**
     * Create a room described "description". Initially, it has no exits.
     * "description" is something like "in a kitchen" or "in an open court
     * yard".
     */
    public Room(String description, String imagePath, double points[], NPC npc)
    {
        this.description = description;
        this.imagePath = imagePath;
        this.points = points;
        exits = new HashMap<String, Room>();
        this.items = new HashMap<String, Item>();
        this.furnitures = new HashSet<Furniture>();
        this.exitStates = new HashMap<String, Boolean>();
        visited = false;
        this.itemContainer = new ItemContainer();
        exitPoints = new HashMap<String, ExitPoint>();
        this.npc = npc;
    }

    /**
     * Define an exit from this room.
     */
    public void setExit(String direction, Room neighbor)
    {
        exits.put(direction, neighbor);
        exitStates.put(direction, false);//exit state to closed
        setExitPoint(direction); 
    }

    /**
     * sets exit coordinates (ExitPoint) based on the 
     * exits available in the room
     * @param direction
     */
    private void setExitPoint(String direction){
    	double minX;
        double minY;
        double x;
        double y;
        switch(direction){
        case "east":
        	minX = getMinValue(points[4], points[6]);
        	x = minX+(Math.abs(points[6]-points[4]))/2;
            minY = getMinValue(points[5], points[7]);
        	y = minY+(Math.abs(points[7]-points[5]))/2;
        	exitPoints.put(direction, new ExitPoint(x,y));
        	break;
        case "west":
        	minX = getMinValue(points[0], points[2]);
        	x = minX+(Math.abs(points[0]-points[2]))/2;
        	minY = getMinValue(points[1], points[3]);
        	y = minY+(Math.abs(points[1]-points[3]))/2;
        	exitPoints.put(direction, new ExitPoint(x,y));
        	break;
        case "north":
        	minX = getMinValue(points[2], points[4]);
        	x = minX+(Math.abs(points[2]-points[4]))/2;
        	minY = getMinValue(points[3], points[5]);
        	y = minY+(Math.abs(points[3]-points[5]))/2;
        	exitPoints.put(direction, new ExitPoint(x, y));
        	break;
        case "south":
        	minX = getMinValue(points[0], points[6]);
        	x = minX+(Math.abs(points[0]-points[6]))/2;
        	minY = getMinValue(points[1], points[7]);
        	y = minY+(Math.abs(points[1]-points[7]))/2;
        	exitPoints.put(direction, new ExitPoint(x, y));
        	break;
        }
    }
    
    /**
     * returns a small value(double) from the specified two
     * @param first
     * @param second
     * @return
     */
    private double getMinValue(double first, double second){
    	if (first>second){
    		return second;
    	}
    	return first;
    }

    /**
     * Return the description of the room (the one that was defined in the
     * constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a long description of this room, in the form:
     *     You are in the kitchen.
     *     Exits: north west
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(Iterator<String> iter = keys.iterator(); iter.hasNext(); )
            returnString += " " + iter.next();
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     */
    public Optional<Room> getExit(String direction)
    {
        return Optional.ofNullable(exits.get(direction));
    }
    /**
     * returns the coordinate of the exit in a given direction
     * @param direction
     * @return
     */
    public Optional<ExitPoint> getExitPoint(String direction){
		return  Optional.ofNullable(exitPoints.get(direction));
	}

	/**
     * adds the given item to the room's items
     * @param item - Item
     */
	public void addItem(Item item){
		itemContainer.add(items, item);
	}

	/**
	 * Returns an Optional object of item by given name,
	 * at the same time removing it from map of items
     * from the room's items
	 * @param itemName  - String
	 * @return
	 */
	public Optional<Item> getItemWithRemoving(String itemName){
		return itemContainer.getItemWithRemoving(items, itemName);
	}
	
	/**
	 * Returns an Optional object of item by given name
     * from the room's items
	 * @param itemName  - String
	 * @return
	 */
	public Optional<Item> getItem(String itemName){
		return itemContainer.getItem(items, itemName);
	}

	/**
	 * Checks if the item exists by the given name in the room's items.
	 * If there is by given name an item, returns true, otherwise false
	 * @param itemName - String
	 * @return
	 */
	public boolean isItemContained(String itemName){
		return itemContainer.contains(items, itemName);
	}

	public Map<String, Item> getItems() {
		return items;
	}

	public void addFurniture(Furniture furniture){
		furnitures.add(furniture);
	}

	public Set<Furniture> getFurnitures() {
		return furnitures;
	}

	public String getDescription() {
		return description;
	}

	public double[] getPoints() {
		return points;
	}

	public String getImagePath() {
		return imagePath;
	}

	public NPC getNpc() {
		return npc;
	}

	public void setExitState(String direction, boolean opened){
		exitStates.put(direction, opened);
	}

	public Map<String, Boolean> getExitStates() {
		return exitStates;
	}

	/**
	 * set the state (open || close) of the 
	 * opposite exit from the neighbor room
	 * @param direction
	 * @param opened
	 */
	public void setNeighborExitState(String direction, boolean opened){
		Room neighbor = getExit(direction).get();
		switch(direction){
		case "east":
			neighbor.setExitState("west", opened);
			break;
		case "west":
			neighbor.setExitState("east", opened);
			break;
		case "south":
			neighbor.setExitState("north", opened);
			break;
		case "north":
			neighbor.setExitState("south", opened);
			break;
		}

	}


	public boolean isExitOpened(String direction){
		return exitStates.get(direction);
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

}

