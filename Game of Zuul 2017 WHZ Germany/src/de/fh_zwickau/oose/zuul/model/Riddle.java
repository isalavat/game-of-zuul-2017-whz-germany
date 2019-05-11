package de.fh_zwickau.oose.zuul.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * A class that represents a riddle with 
 * possible answers. This riddle of the 
 * NPC that the player must solve to get the key
 * @author salavat
 *
 */
public class Riddle implements Serializable {
	
	private String question; 
	private Map<String, Boolean> answers;
	private boolean sloved ;
	public Riddle(String question, Map<String, Boolean> answers ){
		this.question = question;
		this.answers = new LinkedHashMap<>();
		setAnswers(answers);
		this.sloved = false;
	}
	
	private void setAnswers(Map <String, Boolean> answers){
		Set<String> keys = answers.keySet();
		for (String key : keys){
			this.answers.put(key, answers.get(key));
		}
	}
	public boolean isAnswerTrue(String answer){
		boolean isAnwerTrue = false;
		if( answers.get(answer) == null){
			isAnwerTrue = false;
		}else if(answers.get(answer) == true){
			isAnwerTrue = true;
		}
		return isAnwerTrue;
	}

	public boolean isSloved() {
		return sloved;
	}

	public void setSloved(boolean sloved) {
		this.sloved = sloved;
	}

	public String getQuestion() {
		return question;
	}

	public Map<String, Boolean> getAnswers() {
		return answers;
	}
}
