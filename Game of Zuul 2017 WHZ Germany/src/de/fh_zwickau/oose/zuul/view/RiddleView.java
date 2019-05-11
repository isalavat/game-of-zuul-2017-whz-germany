package de.fh_zwickau.oose.zuul.view;

import java.util.Set;
import de.fh_zwickau.oose.zuul.model.Riddle;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/**
 * The class representing a Riddle of NPC in the form of a dialog window
 * @author salavat
 */
public class RiddleView {
	private Stage stage;
	private Scene scene;
	private FlowPane rootPane;
	private Label questionLabel;
	private FlowPane head;
	private FlowPane middle;
	private FlowPane bottom;
	private ToggleGroup radioButToggleGroup;
	private Button okButton;
	private Group radButGroup;
	private Riddle riddle;
	
	public RiddleView(){
		createScene();
		createStage();
		riddle = null;
	}
	
	/**
	 * creates a Stage for the riddle dialog window
	 */
	private void createStage() {
		stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.setScene(scene);
		
	}
	
	/**
	 * creates a Scene of Stage for the riddle dialog window
	 */
	private void createScene() {
		rootPane = new FlowPane();
		rootPane.setAlignment(Pos.TOP_CENTER);
		rootPane.setPadding(new Insets(8));
		rootPane.setVgap(5);
		head   = new FlowPane();
		head.setAlignment(Pos.TOP_CENTER);
		head.setBackground(new Background(new BackgroundFill(Color.CHARTREUSE, null, null)));
		head.setPadding(new Insets(5));
		middle = new FlowPane();
		middle.setAlignment(Pos.TOP_CENTER);
		bottom = new FlowPane();
		bottom.setAlignment(Pos.TOP_CENTER);
		questionLabel = new Label();
		questionLabel.setAlignment(Pos.CENTER);
		head.getChildren().add(questionLabel);
		radButGroup = new Group();
		radioButToggleGroup = new ToggleGroup();
		okButton = new Button("OK");
		okButton.setPrefSize(150, 20);
		bottom.getChildren().add(okButton);
		rootPane.getChildren().addAll(head, middle, bottom);
		scene = new Scene(rootPane);
	}
	
	/**
	 * shows the riddle dialog window with the given riddle
	 * @param riddle
	 */
	public void showRiddleWindow(Riddle riddle_){
		setRiddle(riddle_);
		stage.show();
	}
	
	private void setRiddle(Riddle riddle_) {
		riddle = riddle_;
		middle.getChildren().clear();
		String question = riddle.getQuestion();
		Set<String> answers = riddle.getAnswers().keySet();
		questionLabel.setText(question);
		questionLabel.setFont(Font_.first_font);
		for (String answers_ : answers){
			RadioButton rb = new RadioButton();
			rb.setPrefSize(250, 50);
			rb.setToggleGroup(radioButToggleGroup);
			rb.setText(answers_);
			rb.setFont(Font_.first_font);
			middle.getChildren().add(rb);
		}
		stage.setTitle("Riddle");
	}
	
	/**
	 *closes the dialog window
 	 */
	public void closeRiddleWindow(){
		stage.close();
	}

	
	public Button getOkButton() {
		return okButton;
	}

	public ToggleGroup getRadioButToggleGroup() {
		return radioButToggleGroup;
	}	
	
	
	
}
