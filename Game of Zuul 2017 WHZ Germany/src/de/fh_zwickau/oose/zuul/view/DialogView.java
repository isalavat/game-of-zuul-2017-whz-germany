package de.fh_zwickau.oose.zuul.view;

import de.fh_zwickau.oose.zuul.containers.FileContainer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/**
 * The class representing the dialog window
 * @author salavat
 *
 */
public class DialogView {
	private Stage stage;
	private Scene scene;
	private FlowPane rootPane;
	private Label textLabel;
	private Button okButton;
	private FlowPane head;
	private FlowPane bottom;

	public DialogView(){
		createScene();
		createStage();
	}

	/**
	 * creates a Stage for the dialog window
	 */
	private void createStage() {
		stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.setScene(scene);

	}

	/**
	 * creates a Scene of Stage for the dialog window
	 */
	private void createScene() {
		rootPane = new FlowPane();
		rootPane.setAlignment(Pos.TOP_CENTER);
		rootPane.setPadding(new Insets(10));
		rootPane.setVgap(20);
		head = new FlowPane();
		head.setAlignment(Pos.CENTER);
		bottom = new FlowPane();
		bottom.setAlignment(Pos.CENTER);
		textLabel = new Label();
		textLabel.setAlignment(Pos.CENTER_LEFT);
		textLabel.setBackground(new Background(new BackgroundFill(Color.WHITESMOKE, null, null)));
		textLabel.setFont(Font_.first_font);
		head.getChildren().add(textLabel);
		okButton = new Button("OK");
		okButton.setPrefSize(150, 20);
		bottom.getChildren().add(okButton);
		rootPane.getChildren().addAll(head, bottom);
		scene = new Scene(rootPane);

	}

	/**
	 * shows a dialog window with a text from file by the given path
	 * @param pathOfText
	 */
	public void showDialogWindowByGivenTextFilePath(String title,String pathOfText){
		setFileTextLabel(title, pathOfText);
		stage.show();
	}

	/**
	 * shows a dialog window with the given text
	 * @param text
	 */
	public void showDialogWindowByGivenText(String title,String text){
		setTextLabel(title, text);
		stage.show();
	}

	/**
	 * sets the title and content of the label
	 * @param title
	 * @param text
	 */
	private void setTextLabel(String title, String text) {
		textLabel.setText(text);
		stage.setTitle(title);

	}

	/**
	 * sets the title of the label
 	 * and content by the given path of text file
	 * @param title
	 * @param pathOfText
	 */
	private void setFileTextLabel(String title, String pathOfText){
		textLabel.setText(FileContainer.getTextFromFile(pathOfText));
		stage.setTitle(title);
	}

	/**
	 *closes the dialog window
 	 */
	public void closeAllertWindow(){
		stage.close();
	}

	public Button getDialogOkButton() {
		return okButton;
	}

}
