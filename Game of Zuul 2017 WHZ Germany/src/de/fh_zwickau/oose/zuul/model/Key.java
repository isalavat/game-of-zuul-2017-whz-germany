package de.fh_zwickau.oose.zuul.model;
/**
 * represents the key that is used as
 * an item in the game. This item has an effect, 
 * that allows to open the closed door
 * @author salavat
 *
 */
public class Key extends Item {
	private String keyOfRoom;
	private String exitDirection;

	public Key(String name, String description, String imagePath, String roomName, String exit) {
		super(name, description, imagePath);
		this.keyOfRoom = roomName;
		this.exitDirection = exit;
	}
	
	public String getKeyOfRoom() {
		return keyOfRoom;
	}

	public String getExitDirection() {
		return exitDirection;
	}
	
	/**
	 * Opens the exit for which this key is assigned
	 */
	@Override
	public void use(Player player) {
		if(player.getCurrentRoom().getDescription().equals(keyOfRoom)){
			player.getCurrentRoom().setExitState(exitDirection, true);
			player.getCurrentRoom().setNeighborExitState(exitDirection, true);
			
		}
	}

}
