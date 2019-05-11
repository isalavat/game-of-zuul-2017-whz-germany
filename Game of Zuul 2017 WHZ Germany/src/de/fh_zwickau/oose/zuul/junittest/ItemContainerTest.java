package de.fh_zwickau.oose.zuul.junittest;

import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;

import de.fh_zwickau.oose.zuul.containers.ItemContainer;
import de.fh_zwickau.oose.zuul.model.Apple;
import de.fh_zwickau.oose.zuul.model.Bread;
import de.fh_zwickau.oose.zuul.model.Cheese;
import de.fh_zwickau.oose.zuul.model.Item;

public class ItemContainerTest {
	private ItemContainer itemContainer;
	private HashMap<String, Item> testItems;
	private Item apple, bread, cheese;
	
	@Before
	public void init(){
		itemContainer = new ItemContainer();
		testItems = new HashMap<>();
		apple = new Apple("Apple", "Without description", "res/Golden-Apple-Sprite.png", 100, 100);
    	bread = new Bread("Bread",  "Without description","res/bread.png", 100, 150 );
    	cheese = new Cheese("Cheese", "Without description", "res/plain_cheese.png",150, 100);
    	itemContainer.add(testItems, apple);
    	itemContainer.add(testItems, bread);
    	itemContainer.add(testItems, cheese);
    }
	
	@Test
	public void getItemWithRemoving() {
		String itemName = "Apple";
		Optional<Item> testItemBox = itemContainer.getItemWithRemoving(testItems, itemName);
		Item testItem = testItemBox.get();
		assertTrue(apple.equals(testItem));
		assertFalse(itemContainer.contains(testItems, itemName));
	}

	@Test
	public void getItemWithoutRemoving() {
		String itemName = "Apple";
		Optional<Item> testItemBox = itemContainer.getItem(testItems, itemName);
		Item testItem = testItemBox.get();
		assertTrue(apple.equals(testItem));
		assertTrue(itemContainer.contains(testItems, itemName));
	}

	@Test
	public void addItem() {
		String itemName = "Apple";
		testItems.remove(itemName);
		assertFalse(itemContainer.contains(testItems, itemName));
		itemContainer.add(testItems, apple);
		assertTrue(itemContainer.contains(testItems, apple.getName()));
		
	}
	
	@Test
	public void contains() {
		assertTrue(itemContainer.contains(testItems, apple.getName()));
	}
	
}
