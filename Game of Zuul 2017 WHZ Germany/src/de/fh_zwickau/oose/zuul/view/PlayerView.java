package de.fh_zwickau.oose.zuul.view;


import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import de.fh_zwickau.oose.zuul.model.Item;
import de.fh_zwickau.oose.zuul.model.Player;
import javafx.scene.shape.Shape;

/**
 * Class representing the player's view (that is, the player's model)
 * @author salavat
 *
 */
public class PlayerView extends MovingEntityView {

	private List<ItemView> itemViews;
	private CurrentRoomView currentRoomView;
	public PlayerView(Player player){
		super(player, 4);
		this.currentRoomView = new CurrentRoomView(((Player)entity).getCurrentRoom());
		this.currentRoomView.getCurrentRoom().setVisited(true);
		itemViews = new ArrayList<>();
		setItemViews();
		relocate();
	}

	/**
	 * updates view of player according to the player model
	 */
	public void update(){
		relocate();
		updateItemViews();
		updateCurrenRoomView();
		currentRoomView.updateItemViews();
		currentRoomView.updateFurnitureViews();
		currentRoomView.getCurrentRoom().setVisited(true);

	}

	/**
	 * updates the view of current room according
	 *  to the player's current room
	 */
	public void updateCurrenRoomView(){
		currentRoomView.setCurrentRoom(((Player)entity).getCurrentRoom());
		
		currentRoomView.updateNpcView(((Player)entity).getCurrentRoom().getNpc());
	}
	/**
	 * updates the view of items according
	 * to the player's items
	 */
	 public void updateItemViews(){
		setItemViews();
	}

	/**
	 * Check if the player is next to the door
	 * @return
	 */
	public boolean isNextToTheExit(){
		return collisionWithExit();
	}

	/**
	 * returns the optional object of item's name that
	 * is closest to the player in a radius of 70 pixels
	 * @return
	 */
	public Optional<String> getNearItemName(){
		String itemName = null;
		double distance_= 100;
		for(ItemView itemView : currentRoomView.getItemViews()){
			double distance = getDistance(boundX, boundY, itemView.getBoundX(), itemView.getBoundY());
			if (distance <70){
				if (itemName==null){
					itemName = itemView.getItem().getName();
					distance_ = distance;
				}else{
					if (distance<distance_){
						itemName = itemView.getItem().getName();
						distance_ = distance;
					}
				}
			}
		}
		return Optional.ofNullable(itemName);
	}

	/**
	 * checks if the player is near to the door (exit),
	 * if yes then returns true otherwise false
	 * @return
	 */
	private boolean collisionWithExit(){
		boolean collision = false;
		Set<String> keys = currentRoomView.getExits().keySet();
		for (String key: keys){
			if(!Shape.intersect(getBound(), currentRoomView.getExits().get(key)).getBoundsInLocal().isEmpty()){
				collision = true;
			}
		}
		return collision;
	}

	/**
	 * Check if the player is next to the given ItemView
	 * @return
	 */
	public boolean isNextToTheItemView(ItemView itemView){
		double distance = getDistance(getBoundX(), getBoundY(), itemView.getBoundX(), itemView.getBoundY());
		if (distance<100){
			return true;
		}
		return false;
	}


	/**
	 * Check if the player is next to the NPS
	 * @return
	 */
	public boolean isNextToNps(){
		boolean res = false;
		double npsX = currentRoomView.getNpcView().getBoundX();
		double npsY = currentRoomView.getNpcView().getBoundY();
		double distance = getDistance(boundX, boundY, npsX, npsY);
		if (distance < 60){
			res = true;
		}
		return res;
	}

	/**
	 * returns the distance between two given points
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	private double getDistance(double x1, double y1, double x2, double y2){
		return Point.distance(x1, y1, x2, y2);
	}

	/**
	 * Returns the direction where the exit
	 * is located next to the player
	 * @return
	 */
	public String getCollisionExitDirection(){
		String direction = "south";
		Set<String> keys = currentRoomView.getExits().keySet();
		for (String key: keys){
			if(!Shape.intersect(getBound(), currentRoomView.getExits().get(key)).getBoundsInLocal().isEmpty()){
				direction = key;
			}
		}
		return direction;
	}

	/**
	 * checks if there is a collision with other
	 * gravity objects, that is, the player has
	 * encountered other graphic objects, if yes
	 * then returns true if not then returns false
	 * @return
	 */
	public boolean collision(){
		boolean collision = false;

		for (ItemView itemView : getCurrentRoomView().getItemViews()){
			if (!Shape.intersect(getBound(), itemView.getBound()).getBoundsInLocal().isEmpty()){
				collision = true;
			}
		}

		for (FurnitureView furnitureView : getCurrentRoomView().getFurnitureViews()){
			if (!Shape.intersect(getBound(), furnitureView.getBound()).getBoundsInLocal().isEmpty()){
				collision = true;
			}
		}

		if (!Shape.intersect(getBound(), getCurrentRoomView().getNpcView().getBound()).getBoundsInLocal().isEmpty()){
			collision = true;
		}

		if(!Shape.subtract(getBound(), getCurrentRoomView().getBound()).getBoundsInLocal().isEmpty()){
			collision = true;
		}
		return collision;
	}


	/**
	 * orders the priority of displaying the player's sprites
	 * and the item. For example, if a player is behind an item
	 * in one row, then the item's sprite will be shown first
	 */
	public void zOrder(){
		getImageView().toFront();
		Set<ItemView> itemViews = getCurrentRoomView().getItemViews();
		for (ItemView itemView: itemViews){
			if (getBoundY() < itemView.getBoundY()){
				itemView.getImageView().toFront();
			}
		}

		Set<FurnitureView> furnitureViews = getCurrentRoomView().getFurnitureViews();
		for (FurnitureView furnitureView: furnitureViews){
			if (getBoundY() < furnitureView.getBoundY()){
				furnitureView.getImageView().toFront();
			}
		}

		if (getBoundY() < getCurrentRoomView().getNpcView().getBoundY()){
			getCurrentRoomView().getNpcView().getImageView().toFront();
		}

	}

	/**
	 * fills the list of itemViews according to model
	 */
	private void setItemViews(){
		itemViews.clear();
		Map<String, Item> items = ((Player)entity).getItems();
		for (String key : items.keySet()){
			itemViews.add(new ItemView(items.get(key)));
		}
	}

	public List<ItemView> getItemViews() {
		return itemViews;
	}

	public CurrentRoomView getCurrentRoomView() {
		return currentRoomView;
	}

}
