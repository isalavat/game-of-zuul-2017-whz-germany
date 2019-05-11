package de.fh_zwickau.oose.zuul.containers;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import de.fh_zwickau.oose.zuul.model.Item;


/**
 * The class that has methods for working with
 * map of items.
 * @author salavat
 */
public class ItemContainer implements Serializable{
	/**
	 * Returns an Optional object of item by a given name from
	 * the given map, at the same time removing it from this map
	 * @param Map(String, Item) items
	 * @param itemName - String
	 * @return Optional(Item)
	 */
	public Optional<Item> getItemWithRemoving(Map<String, Item> items,String itemName){
		Optional<Item> item = Optional.ofNullable(items.get(itemName));
		items.remove(itemName);
		return item;
	}
	
	/**
	 * Returns an Optional object of item by a given name from
	 * the given map, at the same time without removing it from this map
	 * @param Map(String, Item) items
	 * @param itemName - String
	 * @return Optional(Item)
	 */
	public Optional<Item> getItem(Map<String, Item> items,String itemName){
		Optional<Item> item = Optional.ofNullable(items.get(itemName));
		return item;
	}
	
	/**
	 * Checks if the item exists by the given name in the map.
	 * If there is by given name an item, returns true, otherwise false 
	 * @param Map(String, Item) items
	 * @param itemName - String 
	 * @return boolean
	 */
	public boolean contains(Map<String,Item> items,String itemName){
		boolean isConatined = items.containsKey(itemName);
		return isConatined;
	}
	
	/**
	 * adds the given item to the given map, where the key is the name
	 * of the item and the value is the given item
	 * @param items - Map(String,Item)
	 * @param item - Item
	 */
	public void add(Map<String,Item> items,Item item){
		items.put(item.getName(), item);
	}
	
	
	
}
