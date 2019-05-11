package de.fh_zwickau.oose.zuul.view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import de.fh_zwickau.oose.zuul.containers.FileContainer;
import de.fh_zwickau.oose.zuul.model.ExitPoint;
import de.fh_zwickau.oose.zuul.model.Furniture;
import de.fh_zwickau.oose.zuul.model.Item;
import de.fh_zwickau.oose.zuul.model.NPC;
import de.fh_zwickau.oose.zuul.model.Room;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polygon;


/**
 * class  representing the current player's room model
 * @author salavat
 *
 */
public class CurrentRoomView {
	private NPCView npcView;
	private ImageView imageView; //picture of the currentRoom
	private Polygon bound;//The boundary for the definition of a collision
	private Map<String, Ellipse> exits; //The boundary map with direction
										//for the definition of a collision with door
	private Room currentRoom;
	private Set<ItemView> itemViews;
	private Set<FurnitureView> furnitureViews;

	public CurrentRoomView(Room currentRoom){
		this.currentRoom = currentRoom;
		itemViews = new HashSet<>();
		furnitureViews = new HashSet<>();
		exits = new HashMap<>();
		npcView = new NPCView(currentRoom.getNpc());
		setItemViews();
		setFurnitureViews();
		createAndRelocateImageAndBound();
		setExitPoints();
	}



	/**
	 * set the current room and its components
	 * @param currentRoom
	 */
	public void setCurrentRoom(Room currentRoom){
		this.currentRoom = currentRoom;
		createAndRelocateImageAndBound();
		setNpcView();
		setExitPoints();
	}

	/**
	 * sets the image and coordinate of the room according to the model
	 */
	public void createAndRelocateImageAndBound(){
		try (InputStream fis = FileContainer.getFileInputStreamByFileName(currentRoom.getImagePath())){
			imageView = new ImageView(new Image(fis));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		imageView.relocate(0, 0);
		bound = new Polygon(currentRoom.getPoints());
		bound.setFill(Color.GREEN);

	}

	/**
	 * sets exits creating an ellipse in
	 * accordance with points from the model and
	 * adding it to the exits map
	 */
	public void setExitPoints(){
		exits.clear();
		if(currentRoom.getExitPoint("east").isPresent()){
			 exits.put("east", createEllipse(currentRoom.getExitPoint("east").get()));
		}
		if(currentRoom.getExitPoint("west").isPresent()){
			 exits.put("west", createEllipse(currentRoom.getExitPoint("west").get()));
		}
		if(currentRoom.getExitPoint("north").isPresent()){
			 exits.put("north", createEllipse(currentRoom.getExitPoint("north").get()));
		}
		if(currentRoom.getExitPoint("south").isPresent()){
			 exits.put("south", createEllipse(currentRoom.getExitPoint("south").get()));
		}
	}

	/**
	 * Returns an optional object of itemView by the
	 * given name
	 * @param itemViewName
	 * @return
	 */
	public Optional<ItemView> getItemView(String itemViewName){
		ItemView itemView = null;
		for (ItemView itemView_ : itemViews){
			if(itemView_.getItemViewName().equals(itemViewName)){
				itemView = itemView_;
			}
		}
		return Optional.ofNullable(itemView);
	}

	/**
	 * Creates and returns an ellipse and sets the coordinate at a given point.
	 * This ellipse is the exit boundary
	 * @param exitPoint
	 * @return
	 */
	public Ellipse createEllipse(ExitPoint exitPoint){
		Ellipse ellipse = new Ellipse();
		ellipse.relocate(exitPoint.getX(), exitPoint.getY());
		ellipse.setFill(Color.TRANSPARENT);
		ellipse.setStroke(Color.TRANSPARENT);
		ellipse.setRadiusX(55);
		ellipse.setRadiusY(30);
		return ellipse;
	}

	/**
	 * set the view of NPC in accordance
	 * with the current state of the room and model of NPC.
	 * This method is called by the creating of a current room view
	 */
	private void setNpcView(){
		npcView.setEntity(currentRoom.getNpc());
	}

	/**
	 * clears and fills the list of itemViews according to model
	 */
	private void setItemViews(){
		itemViews.clear();
		Map<String, Item> items = currentRoom.getItems();
		for (String key : items.keySet()){
			itemViews.add(new ItemView(items.get(key)));
		}

	}

	/**
	 * updates the itemView list according to the model
	 */
	public void updateItemViews(){
		setItemViews();
	}

	/**
	 * updates the furnitureView list according to the model
	 */
	public void updateFurnitureViews(){
		setFurnitureViews();
	}

	public void updateNpcView(NPC npc){
		npcView = new NPCView(npc);
	}

	/**
	 * clears and fills the list of furnitureViews according to model
	 */
	private void setFurnitureViews() {
		furnitureViews.clear();
		Set<Furniture> furnitures = currentRoom.getFurnitures();
		for(Furniture furniture : furnitures){
			furnitureViews.add(new FurnitureView(furniture));
		}
	}

	public ImageView getImageView() {
		return imageView;
	}

	public Set<FurnitureView> getFurnitureViews() {
		return furnitureViews;
	}

	public Polygon getBound() {
		return bound;
	}

	public Set<ItemView> getItemViews() {
		return itemViews;
	}

	public Map<String, Ellipse> getExits() {
		return exits;
	}

	public Room getCurrentRoom() {
		return currentRoom;
	}

	public NPCView getNpcView() {
		return npcView;
	}

}
