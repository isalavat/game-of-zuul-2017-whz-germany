package de.fh_zwickau.oose.zuul.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Optional;

import javax.swing.text.PlainView;

import com.sun.media.jfxmediaimpl.platform.Platform;

import de.fh_zwickau.oose.zuul.commands.Command;
import de.fh_zwickau.oose.zuul.commands.GoCommand;
import de.fh_zwickau.oose.zuul.commands.TakeCommand;
import de.fh_zwickau.oose.zuul.commands.UseCommand;
import de.fh_zwickau.oose.zuul.containers.FileContainer;
import de.fh_zwickau.oose.zuul.model.Game;
import de.fh_zwickau.oose.zuul.model.Item;
import de.fh_zwickau.oose.zuul.model.Key;
import de.fh_zwickau.oose.zuul.model.NPC;
import de.fh_zwickau.oose.zuul.model.Parser;
import de.fh_zwickau.oose.zuul.model.Player;
import de.fh_zwickau.oose.zuul.model.Riddle;
import de.fh_zwickau.oose.zuul.view.RiddleView;
import de.fh_zwickau.oose.zuul.view.GameView;
import de.fh_zwickau.oose.zuul.view.ItemView;
import de.fh_zwickau.oose.zuul.view.NPCView;
import de.fh_zwickau.oose.zuul.view.PlayerView;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.RadioButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * This class controls the model and game views
 * @author salavat
 *
 */
public class GameController{

	private GameView gameView;
	private Game game;
	private AnimationTimer animationTimer; // Animation Timer triggering player location and player sprite also npc
	private final String SAVE_FILE_PATH = "game.out";
	private final String LOAD_FILE_PATH = "game.out";
	private final String CONTROL = "control.txt";
	private final String HELP = "help.txt";
	private final String CONGRATULATION = "congratulation.txt";
	private final String LAST_ROOM_DESCRIPTION = "Room-9"; 
	private String succesfullText = "You guessed answer.Open the door with key!";
	private String failText = "You're not lost! try again!(Q)";
	public GameController(Game game, GameView gameView){
		this.game = game;
		this.gameView = gameView;
		this.gameView.getPlayerView().getImageView().getScene().setOnKeyPressed(new OnButtonsPressed());
		this.gameView.getPlayerView().getImageView().getScene().setOnKeyReleased(new OnDirectionReleased());
		this.gameView.getSaveButton().setOnAction(new OnSaveButtonClicked());
		this.gameView.getLoadButton().setOnAction(new OnLoadButtonClicked());
		this.gameView.getRiddleWindow().getOkButton().setOnAction(new OnOkButtonClicked());
		this.gameView.getUseButton().setOnAction(new OnUseButtonClicked());
		this.gameView.getDropButton().setOnAction(new OnDropButtonClicked());
		this.gameView.getHelpButton().setOnAction(new OnHelpButtonClicked());
		this.gameView.getControlButton().setOnAction(new OnControlsButtonClicked());
		this.gameView.getDescriptionButton().setOnAction(new OnDescriptionButtonClicked());
		this.gameView.getDialogWindow().getDialogOkButton().setOnAction(new OnDialogOkButtonClicked());
		this.gameView.getExitButton().setOnMouseClicked(new OnExitClicked());
		animationTimer = new AnimationTimer() {
			@Override
			public void handle(long arg0) {
				// TODO Auto-generated method stub
					update();
			}
		};
		animationTimer.start();


	}

	/**
	 *Event handler for the save screen button, when pressed,
	 *the current status of the player and rooms is saved
	 * @author salavat
	 */
	class OnSaveButtonClicked implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent event) {
				FileOutputStream fos;
				ObjectOutputStream oos;
				try {
					fos = new FileOutputStream(SAVE_FILE_PATH);
					oos = new ObjectOutputStream(fos);
					oos.writeObject(game);
					oos.flush();
					oos.close();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
	}

	/**
	 * The event handler for the on-screen button of the load, when pressed,
	 * loads and sets the last saved state of the player and rooms
	 * @author salavat
	 *
	 */
	class OnLoadButtonClicked implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent event) {
			FileInputStream fis;
			try {
				fis = new FileInputStream(SAVE_FILE_PATH);
				ObjectInputStream oin = new ObjectInputStream(fis);
				Game loadedGame = (Game) oin.readObject();
				game = loadedGame;
				game.setParser(new Parser());
				gameView.setGame(game);
				gameView.clearPanes();
				gameView.getPlayerView().setEntity(game.getPlayer());
				gameView.getPlayerView().update();
				gameView.fillMap();
				gameView.fillPanes();
				fis.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * event handler for the Use button
	 * @author salavat
	 *
	 */
	class OnUseButtonClicked implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent event){
			PlayerView playerView = gameView.getPlayerView();
			Optional<String> currentItemNameBox = gameView.getCurrentItemName();
			if(currentItemNameBox.isPresent() && game.getPlayer().getItem("key").isPresent()){
				Item key = game.getPlayer().getItem("key").get();
				String exitDirection = ((Key) key).getExitDirection();
				if (game.getPlayer().getCurrentRoom().getDescription().equals(LAST_ROOM_DESCRIPTION)){
					javafx.application.Platform.exit();
				}else if(playerView.isNextToTheExit() && playerView.getCollisionExitDirection().equals(exitDirection)){
					executeCommand("use", currentItemNameBox.get());
					game.getPlayer().getItemWithRemoving(gameView.getCurrentItemName().get());
					gameView.updateAfterUseCommand();
				}
			}
		}
	}

	/**
	 * event handler for the Drop button
	 * @author salavat
	 *
	 */
	class OnDropButtonClicked implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent event) {
			Optional<String> currentItemNameBox = gameView.getCurrentItemName();
			if (currentItemNameBox.isPresent()){
				executeCommand("drop", currentItemNameBox.get());
				gameView.updateAfterTakeOrDropCommand();

			}
		}
	}

	private void executeCommand(String commandName, String currentItemName){
		if(!currentItemName.equals("")){
			Command command = game.getParser().getCommand(commandName+" "+currentItemName);
			command.execute(game.getPlayer());
		}
	}

	/**
	 * event handler for the Ok button of riddle window
	 * @author salavat
	 *
	 */
	class OnOkButtonClicked implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent event) {
			NPC nps = game.getPlayer().getCurrentRoom().getNpc();
			String answer = ((RadioButton)gameView.getRiddleWindow().getRadioButToggleGroup().getSelectedToggle()).getText();
			
			Riddle riddle = nps.getRiddle();
			if(riddle.isAnswerTrue(answer)){
				Optional<Item> itemBox = nps.getItem();
				if( itemBox.isPresent() ){
					game.getPlayer().addItem(itemBox.get());
					riddle.setSloved(true);
					if(game.getPlayer().getCurrentRoom().getDescription().equals(LAST_ROOM_DESCRIPTION)){
						gameView.getDialogWindow().showDialogWindowByGivenTextFilePath("Win", CONGRATULATION);
						succesfullText = "Use the key to exit the castle";
					}
					
					
				}
				changeNpsDialogText(succesfullText);

			}else{
				changeNpsDialogText(failText);

			}
			gameView.clearPanes();
			gameView.getPlayerView().updateItemViews();
			gameView.fillPanes();
			gameView.getRiddleWindow().closeRiddleWindow();
		}

		public void changeNpsDialogText(String text){
			game.getPlayer().getCurrentRoom().getNpc().setDialogText(text);
		}

	}
	
	/**
	 * event handler for the ok button of help & control 
	 * @author salavat
	 *
	 */
	class OnDialogOkButtonClicked implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent event) {
			gameView.getDialogWindow().closeAllertWindow();
			
		}
	}

	/**
	 * event handler for the Help button
	 * @author salavat
	 *
	 */
	class OnHelpButtonClicked implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent event) {
			gameView.getDialogWindow().showDialogWindowByGivenTextFilePath("Help" ,HELP);
		}
	}

	/**
	 * event handler for the Ñontrol button
	 * @author salavat
	 *
	 */
	class OnControlsButtonClicked implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent event) {
			gameView.getDialogWindow().showDialogWindowByGivenTextFilePath("Control",CONTROL);
		}
	}

	/**
	 * event handler for the Description button
	 * @author salavat
	 *
	 */
	class OnDescriptionButtonClicked implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent event) {
			String itemViewDescription = null;
			List<ItemView> itemViews = gameView.getPlayerView().getItemViews();
			for (ItemView iView : itemViews){
				if(iView.getItem().isClicked()){
					itemViewDescription = iView.getItem().getName()+"\n"+iView.getItem().getDescription();
					break;
				}
			}
			if (itemViewDescription != null){
				gameView.getDialogWindow().showDialogWindowByGivenText("Decription of Item",itemViewDescription);
			}
			gameView.unclickAllItems();
			gameView.updateAfterTakeOrDropCommand();
		}
	}


	/**
	 * The event handler for the actions
	 * Up [W]; Down [S]; Right [D]; Left [A]; Run [R]; WALK [T];
	 * Take Item & Use Item [E]; Show riddle [Q];
	 * @author salavat
	 */
	class OnButtonsPressed implements EventHandler<KeyEvent> {

		@Override
		public void handle(KeyEvent e) {
			game.getPlayer().setDirectionsToFalse();
			switch(e.getCode()){
			case W:
				game.getPlayer().getDirections()[0] = true;
				break;
			case S:
				game.getPlayer().getDirections()[1] = true;
				break;
			case D:
				game.getPlayer().getDirections()[2] = true;
				break;
			case A:
				game.getPlayer().getDirections()[3] = true;
				break;
			case E:
				if (gameView.getPlayerView().isNextToTheExit()){
					String direction = gameView.getPlayerView().getCollisionExitDirection();
					if(game.getPlayer().getCurrentRoom().isExitOpened(direction)){
						String inputLine = "go "+direction;
						Command command = game.getParser().getCommand(inputLine);
						command.execute(game.getPlayer());
						gameView.updateAfterGoCommand();
					}
				}else{
					Optional<String>nearItemNameBox =  gameView.getPlayerView().getNearItemName();
					if(nearItemNameBox.isPresent()){
						executeCommand("take", nearItemNameBox.get());
						gameView.updateAfterTakeOrDropCommand();
					}
				}
				break;
			case R:
				game.getPlayer().setSpeed(2.5);
				break;
			case T:
				game.getPlayer().setSpeed(1.3);
				break;
			case Q:
				Riddle riddle = gameView.getPlayerView().getCurrentRoomView().getCurrentRoom().getNpc().getRiddle();
				if(gameView.getPlayerView().isNextToNps()&& !riddle.isSloved()){
					gameView.getRiddleWindow().showRiddleWindow(riddle);
				}
				break;

			default:
				break;
			}

		}
	}
	/**
	 * The event handler for the exit button
	 * @author salavat
	 */
	class OnExitClicked implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent arg0) {
			// TODO Auto-generated method stub
			javafx.application.Platform.exit();
		}

	}

	/**
	 * The event handler for the direction buttons
	 * Up-W; Down-S; Right-D; Left-A.
	 * @author salavat
	 */
	class OnDirectionReleased implements EventHandler<KeyEvent> {

		@Override
		public void handle(KeyEvent e) {
			switch(e.getCode()){
			case W:
				game.getPlayer().getDirections()[0] = false;
				break;
			case S:
				game.getPlayer().getDirections()[1] = false;
				break;
			case D:
				game.getPlayer().getDirections()[2] = false;
				break;
			case A:
				game.getPlayer().getDirections()[3] = false;
				break;

			}

		}

	}

	/**
	 * Updates the player's location if the player collides
	 *  with other objects (item, border of the room) then
	 *  the location is undone so that the player can not
	 *  walk longer through other objects (items, border of
	 *  the room).Also calls the method which is responsible
	 *  for z-order of sprites of all graphic elements
	 *  Also responsible for displaying the dialog between the game and the NPC
	 */
	public void update(){
		game.getPlayer().relocate_();
		gameView.getPlayerView().setSprite();
		gameView.getPlayerView().relocate();
		if(gameView.getPlayerView().collision()){
			game.getPlayer().relocateBack_();
		}
		if(gameView.getPlayerView().isNextToNps() && !gameView.isDialogShowed()){
			gameView.showNPSDialogLabel();
		}else if(!gameView.getPlayerView().isNextToNps() && gameView.isDialogShowed()){
			gameView.hideNPSDialogLabel();
		}
		gameView.getPlayerView().zOrder();
		NPCView npsView = gameView.getPlayerView().getCurrentRoomView().getNpcView();
		npsView.setNpsDirectionToPlayer(game.getPlayer().getX(), game.getPlayer().getY());
		npsView.setSprite();
	};



}
