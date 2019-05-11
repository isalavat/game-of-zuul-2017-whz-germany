package de.fh_zwickau.oose.zuul;

import de.fh_zwickau.oose.zuul.controller.GameController;
import de.fh_zwickau.oose.zuul.model.Game;
import de.fh_zwickau.oose.zuul.view.GameView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * The class that creates the model, view and launches the game (GUI).
 *
 * @author salavat
 *
 */
public class GameLauncher extends Application {
	private Game game;
	private GameView gameView ;
	private GameController gameController;
	private Scene scene;

	public static void main (String args[]){
		GameLauncher.run(args);
	}


	public GameLauncher(){
		game = new Game();
		gameView = new GameView(game);
		gameController = new GameController(game, gameView);
		scene = gameView.getScene();

	}

	/**
	 * Calls the constructor of this class and launches the game
	 * @param args
	 */
	public static void run(String args[]){
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
  		primaryStage.setScene(scene);
  		primaryStage.initStyle(StageStyle.UNDECORATED);
  		primaryStage.setResizable(false);
  		primaryStage.show();
  		gameView.getDialogWindow().showDialogWindowByGivenTextFilePath("Legende","legend.txt");
  	}


}
