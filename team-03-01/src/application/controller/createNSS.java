package application.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class createNSS {

	@FXML private TextField setNameField;
	@FXML private TextField termTextField;
	@FXML private TextField definitionTextField;
	
	@FXML private TextArea studySetTextArea;
	
	@FXML private Button addTermBtn;
	@FXML private Button finalizeBtn;
	
	///////////////////////////////////////////////
	/* Changes the scene from one scene to another */
	private void changeScene(ActionEvent event, String fxml) {
	
	// Changes the scene from one window to another window
	URL url = getClass().getClassLoader().getResource(fxml);
	
	try {
	// Loads the other scene
	Parent root = FXMLLoader.load(url);
	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	Scene scene = new Scene(root);
	stage.setScene(scene);
	stage.show();
	
	} catch (IOException e){
	e.printStackTrace();
	}
	}
	////////////////////////////////////////////////
	
	@FXML public void addTerm(ActionEvent event) throws IOException {
		String studySetName = setNameField.getText();
		String term = termTextField.getText();
		String definition = definitionTextField.getText();
		
		studySetTextArea.appendText(term + "\n");
		studySetTextArea.appendText(definition + "\n" + "\n");


	}
	
	/*
	 * NOTE: This method needs to be updated to add study set to the database!
	 * Also,  I will change the text area to be a table or something which allows
	 * the user to be able to edit their flashcards in the future.
	 */
	@FXML public void finalizeSet(ActionEvent event) throws IOException {
		this.changeScene(event, "fxml/Courses.fxml");
		
	}
	
	
}
