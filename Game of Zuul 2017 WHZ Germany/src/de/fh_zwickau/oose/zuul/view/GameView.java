package de.fh_zwickau.oose.zuul.view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import de.fh_zwickau.oose.zuul.containers.FileContainer;
import de.fh_zwickau.oose.zuul.model.Game;
import de.fh_zwickau.oose.zuul.model.Item;
import de.fh_zwickau.oose.zuul.model.Room;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

/**
 * A class representing the GUI of game
 * @author salavat
 *
 */
public class GameView  {

	private Scene scene;
	private FlowPane rootPane;
	private final double WIDTH = 775;
	private final double HEIGHT = 500;
	private Pane roomPane;
	private FlowPane itemButtonsPane;
	private FlowPane inventarPane;
	private FlowPane leftPane;
	private FlowPane rightPane;
	private Button saveButton;
	private Button loadButton;
	private Button helpButton;
	private Button controlButton;
	private Button useButton;
	private Button dropButton;
	private Button descriptionButton;
	private Button exitButton;
	private String currentItemName;
	private FlowPane map;
	private Label inventarLabel;
	private Label dialogLabel;
	private ImageView dialogBackroung;
	private Group bounds; //Group for the boundaries of the player and items
						  //that allow you to find out the collision between them
	private Group imageViews;//Group for the sprites of the player and items
	private Group room;
	private Group dialogGroup;
	private boolean dialogShowed = false;
	private RiddleView riddleWindow;
	private DialogView dialogWindow;
	private PlayerView playerView;

	private Game game;
	private Font_ font;

	public GameView(Game game){
		init();
		currentItemName = null;
		this.game = game;
		font = new Font_();
		bounds = new Group();
		imageViews = new Group();
		room = new Group();
		dialogGroup = new Group();
		roomPane.getChildren().addAll(room, bounds, imageViews, dialogGroup);
		playerView = new PlayerView(this.game.getPlayer());
		riddleWindow = new RiddleView();
		dialogWindow = new DialogView();

		fillMap();
		addPlayer();
		addPlayerItems();
		addCurrentRoom();
		addCurrentRoomNps();
		addCurrentRoomItems();
		addCurrentRoomFurnitures();
	}


	/**
	 * creates and configures the components
	 * of the game window, with 2 parts of the
	 * window: left and right
	 */
	private void init(){
		rootPane = new FlowPane();
		rootPane.setPrefSize(WIDTH, HEIGHT);
		rootPane.setPadding(new Insets(5));
		rootPane.setHgap(5);
		rootPane.setVgap(5);
		rootPane.setBackground(new Background(new BackgroundFill(Color.DIMGRAY, null, null)));

		leftPane = new FlowPane();
		leftPane.setPrefSize(550, 500);
		leftPane.setAlignment(Pos.TOP_LEFT);
		leftPane.setHgap(5);
		leftPane.setVgap(5);


		rightPane = new FlowPane();
		rightPane.setPrefSize(208, 350);
		rightPane.setAlignment(Pos.TOP_CENTER);
		rightPane.setPadding(new Insets(5));
		rightPane.setHgap(5);
		rightPane.setVgap(5);
		rightPane.setBackground(new Background(new BackgroundFill(Color.GRAY,null,null)));

		rootPane.getChildren().addAll(leftPane, rightPane);

		roomPane = new Pane();
		roomPane.setPrefSize(550, 350);
		roomPane.setBackground(new Background(new BackgroundFill(Color.DARKSEAGREEN,null,null)));

		dialogLabel = new Label();
		dialogLabel = new Label();
		dialogLabel.setWrapText(true);
		dialogLabel.setPrefSize(170, 70);
		dialogLabel.setAlignment(Pos.CENTER);

		dialogLabel.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
		try(InputStream fis = ClassLoader.getSystemResourceAsStream("cloud.png")){
			dialogBackroung = new ImageView(new Image(fis));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		inventarLabel = new Label();
		inventarLabel.setWrapText(true);
		inventarLabel.setPrefSize(266, 134);
		inventarLabel.setAlignment(Pos.CENTER);
		inventarLabel.setText("Player's inventar");

		saveButton = new Button("Save");
		saveButton.setPrefSize(97, 32);
		helpButton = new Button("Help");
		helpButton.setPrefSize(97, 32);
		loadButton = new Button("Load");
		loadButton.setPrefSize(97, 32);
		controlButton = new Button("Controls");
		controlButton.setPrefSize(97, 32);
//		saveButton.setBackground(new Background(new BackgroundFill(Color.BISQUE, null, new Insets(2))));

		map = new FlowPane();
		map.setPrefSize(200, 200);
		map.setPadding(new Insets(2));
		map.setVgap(2);
		map.setHgap(2);

		inventarPane = new FlowPane();
		inventarPane.setPrefSize(332, 134);
		inventarPane.setAlignment(Pos.TOP_LEFT);
		inventarPane.setPadding(new Insets(2));
		inventarPane.setHgap(2);
		inventarPane.setVgap(2);
		inventarPane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

		useButton = new Button("Use");
		useButton.setPrefSize(97, 38);

		dropButton = new Button("Drop");
		dropButton.setPrefSize(97, 38);

		descriptionButton = new Button("Description");
		descriptionButton.setPrefSize(97, 38);

		setExitButton(new Button("Exit"));
		getExitButton().setPrefSize(97, 38);

		itemButtonsPane = new FlowPane();
		itemButtonsPane.setPrefSize(101, 134);
		itemButtonsPane.setPadding(new Insets(5));
		itemButtonsPane.setVgap(5);
		itemButtonsPane.setHgap(5);
		itemButtonsPane.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
		itemButtonsPane.getChildren().addAll(useButton, dropButton, descriptionButton);

		Rectangle botRight = new Rectangle(150, 150,
				new ImagePattern(new Image(FileContainer.getFileInputStreamByFileName("bilbo.png"))));

		Rectangle botMid = new Rectangle(100, 130,
				new ImagePattern(new Image(FileContainer.getFileInputStreamByFileName("eye.png"))));

		leftPane.getChildren().addAll(roomPane, inventarPane, itemButtonsPane, botMid);
		rightPane.getChildren().addAll(saveButton, helpButton,loadButton, controlButton, map, getExitButton(), botRight);

		scene = new Scene(rootPane);
	}

	/**
	 * It is responsible for displaying the status of the room
	 * map and also for the state of the visited rooms and for
	 * displaying location of player
	 */
	public void fillMap() {
		map.getChildren().clear();
		for(Room eachRoom : game.getRooms()) {
			BorderPane mapPane = new BorderPane();
			mapPane.setPrefSize(64, 64);
			if(eachRoom.isVisited()==false) {
				mapPane.setMouseTransparent(true);
				mapPane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
				map.getChildren().add(mapPane);
				continue;
			}
			if(game.getPlayer().getCurrentRoom().equals(eachRoom)) {
				try (InputStream fis = FileContainer.getFileInputStreamByFileName("face.png")){
					mapPane.setCenter(new ImageView(new Image(fis)));
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
			else {
				mapPane.setBackground(new Background(new BackgroundFill(Color.GREY, null, null)));
			}
			mapPane.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, null, null)));
			mapPane.setOnMouseExited(e ->
				mapPane.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, null, null))));
			mapPane.setOnMouseEntered(e ->
				mapPane.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, null))));

			if(eachRoom.getExitStates().containsKey("east")) {
				if(eachRoom.getExitStates().get("east")) {
					mapPane.setRight(new Rectangle(10, 32, Color.GREEN));
				} else {
					mapPane.setRight(new Rectangle(10, 32, Color.RED));
				}
				BorderPane.setAlignment(mapPane.getRight(), Pos.CENTER_RIGHT);
			} else {
				mapPane.setRight(new Rectangle(10,32, Color.TRANSPARENT));
			}
			if(eachRoom.getExitStates().containsKey("north")) {
				if(eachRoom.getExitStates().get("north")) {
					mapPane.setTop(new Rectangle(32, 10, Color.GREEN));
				} else {
					mapPane.setTop(new Rectangle(32, 10, Color.RED));
				}
				BorderPane.setAlignment(mapPane.getTop(), Pos.TOP_CENTER);
			} else {
				mapPane.setTop(new Rectangle(32, 10, Color.TRANSPARENT));
			}
			if(eachRoom.getExitStates().containsKey("west")) {
				if(eachRoom.getExitStates().get("west")) {
					mapPane.setLeft(new Rectangle(10, 32, Color.GREEN));
				} else {
					mapPane.setLeft(new Rectangle(10, 32, Color.RED));
				}
				BorderPane.setAlignment(mapPane.getLeft(), Pos.CENTER_LEFT);
			} else {
				mapPane.setLeft(new Rectangle(10, 32, Color.TRANSPARENT));
			}
			if(eachRoom.getExitStates().containsKey("south")) {
				if(eachRoom.getExitStates().get("south")) {
					mapPane.setBottom(new Rectangle(32, 10, Color.GREEN));
				} else {
					mapPane.setBottom(new Rectangle(32, 10, Color.RED));
				}
				BorderPane.setAlignment(mapPane.getBottom(), Pos.BOTTOM_CENTER);
			} else {
				mapPane.setBottom(new Rectangle(32, 10, Color.TRANSPARENT));
			}

			map.getChildren().add(mapPane);

		}


	}
	/**
	 * shows the dialog text of the npc
	 * over the head of npc
	 */
	public void showNPSDialogLabel(){
		dialogShowed = true;
		dialogGroup.getChildren().clear();
		String dialogText = playerView.getCurrentRoomView().getNpcView().getDialogText();
		double x = playerView.getCurrentRoomView().getNpcView().getBoundX();
		double y = playerView.getCurrentRoomView().getNpcView().getBoundY()-150;
		dialogLabel.setText(dialogText);
		try {
			dialogLabel.setFont(Font.loadFont(
					FileContainer.getFileInputStreamByFileName("SFPixelate.ttf"), 14.5));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dialogLabel.relocate(x+10, y-50);
		dialogBackroung.relocate(x-15, y-70);
		dialogGroup.getChildren().addAll(dialogBackroung, dialogLabel);
	}

	/**
	 * hides the dialog text of the npc
	 */
	public void hideNPSDialogLabel(){
		dialogShowed = false;
		dialogGroup.getChildren().clear();
	}

	/**
	 * Adds a sprite and player's border to the scene
	 */
	public void addPlayer(){
		imageViews.getChildren().add(playerView.getImageView());
		bounds.getChildren().add(playerView.getBound());
	}



	/**
	 * Adds a player's items to the scene
	 */
	public void addPlayerItems(){
		Map<String, Item> items = game.getPlayer().getItems();
		inventarPane.getChildren().clear();
		for (String itemName : items.keySet()){
			StackPane itemSlot = new StackPane();
			itemSlot.setPrefSize(64, 64);
			Item currentItem = items.get(itemName);
			Rectangle item = new Rectangle(64, 64);
			Rectangle itemImage = new Rectangle(64, 64);
			try {
				itemImage.setFill(new ImagePattern(new Image(FileContainer.getFileInputStreamByFileName(currentItem.getImagePath()))));
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			itemSlot.setOnMousePressed(e-> {
				unclickAllItems();
				currentItem.setClicked(true);
				currentItemName = currentItem.getName();
				addPlayerItems();
			});

			if(currentItem.isClicked()) {
				item.setFill(Color.BISQUE);
			} else {
				item.setFill(Color.DARKSLATEGRAY);
			}
			itemSlot.getChildren().add(item);
			itemSlot.getChildren().add(itemImage);
			inventarPane.getChildren().add(itemSlot);
		}
		for(int i = 0; i < 10 - items.size(); i++) {
			StackPane itemSlot = new StackPane();
			itemSlot.setPrefSize(64, 64);
			Rectangle emptySlot = new Rectangle(64,64,Color.GREY);
			itemSlot.getChildren().add(emptySlot);
			inventarPane.getChildren().add(itemSlot);
		}
	}
	
	/**
	 * deactivating all the player's clicked
	 * and not clicked items
	 */
	public void unclickAllItems() {
		currentItemName = null;
		for(String otherItem : game.getPlayer().getItems().keySet()) {
			game.getPlayer().getItems().get(otherItem).setClicked(false);
		}
	}

	/**
	 * Adds the image and boundaries of the player's current room to the scene
	 */
	public void addCurrentRoom(){
		room.getChildren().add(playerView.getCurrentRoomView().getBound());
		room.getChildren().add(playerView.getCurrentRoomView().getImageView());

		Set<String> keys = playerView.getCurrentRoomView().getExits().keySet();
		for (String key: keys){
			room.getChildren().add(playerView.getCurrentRoomView().getExits().get(key));
		}

	}

	/**
	 * Adds the current room's NPC to the scene
	 */
	private void addCurrentRoomNps(){
		imageViews.getChildren().add(playerView.getCurrentRoomView().getNpcView().getImageView());
		bounds.getChildren().add(playerView.getCurrentRoomView().getNpcView().getBound());
	}

	/**
	 * Adds a current room's items to the scene
	 */
	private void addCurrentRoomItems(){
		Set<ItemView> itemViews = playerView.getCurrentRoomView().getItemViews();
		for (ItemView itemView : itemViews){
			bounds.getChildren().add(itemView.getBound());
			imageViews.getChildren().add(itemView.getImageView());
			itemView.relocate();
		}
	}

	/**
	 * Adds a current room's furniture to the scene
	 */
	private void addCurrentRoomFurnitures(){
		Set<FurnitureView> furnitureViews = playerView.getCurrentRoomView().getFurnitureViews();
		for (FurnitureView furnitureView : furnitureViews){
			bounds.getChildren().add(furnitureView.getBound());
			imageViews.getChildren().add(furnitureView.getImageView());
			furnitureView.relocate();

		}
	}

	/**
	 * Removes all game elements and adds
	 * an updated state of the game elements
	 * after the go command is executed
	 */
	public void updateAfterGoCommand(){
		clearPanes();
		getPlayerView().update();
		fillPanes();
		fillMap();
	}


	/**
	 * Removes all game elements and adds
	 * an updated state of the game elements
	 * after the use command is executed
	 */
	public void updateAfterUseCommand(){
		clearPanes();
		unclickAllItems();
		getPlayerView().updateItemViews();
		fillMap();
		fillPanes();
	}

	/**
	 * Removes all game elements and adds
	 * an updated state of the game elements
	 * after the take or drop command is executed
	 */
	public void updateAfterTakeOrDropCommand(){
		unclickAllItems();
		clearPanes();
		getPlayerView().updateItemViews();
		getPlayerView().getCurrentRoomView().updateItemViews();
		fillPanes();
	}

	/**
	 * Removes the player, current room and all items from the scene
	 */
	public void clearPanes(){
		room.getChildren().clear();
		bounds.getChildren().clear();
		imageViews.getChildren().clear();
		inventarPane.getChildren().clear();
	}

	/**
	 * Adds player, current room and all items to scene
	 */
	public void fillPanes(){
		addPlayer();
		addPlayerItems();
		hideNPSDialogLabel();
		addCurrentRoom();
		addCurrentRoomNps();
		addCurrentRoomItems();
		addCurrentRoomFurnitures();
	}

	public void setGame(Game game){
		this.game = game;
	}

	public Scene getScene(){
		return this.scene;
	}

	public FlowPane getRootPane() {
		return rootPane;
	}

	public double getWIDTH() {
		return WIDTH;
	}

	public double getHEIGHT() {
		return HEIGHT;
	}

	public PlayerView getPlayerView() {
		return playerView;
	}

	public Button getSaveButton() {
		return saveButton;
	}
	public Button getLoadButton() {
		return loadButton;
	}
	public RiddleView getRiddleWindow() {
		return riddleWindow;
	}

	public DialogView getDialogWindow() {
		return dialogWindow;
	}


	public boolean isDialogShowed() {
		return dialogShowed;
	}
	public Optional<String> getCurrentItemName() {
		return Optional.ofNullable(currentItemName);
	}


	public Button getUseButton() {
		return useButton;
	}


	public Button getDropButton() {
		return dropButton;
	}

	public Button getHelpButton() {
		return helpButton;
	}

	public Button getControlButton() {
		return controlButton;
	}


	public Button getDescriptionButton() {
		return descriptionButton;
	}


	public Button getExitButton() {
		return exitButton;
	}


	public void setExitButton(Button exitButton) {
		this.exitButton = exitButton;
	}



}
