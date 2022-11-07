package application.controller;

import java.io.IOException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class HomeController {
	
	@FXML private BorderPane homeScene;
	@FXML private Button signupBtn;
	@FXML private Button loginBtn;
	
	/*
	 * Go to Signup window
	 * 
	 */
	public void toSignUp(ActionEvent event) {
		this.changeScene(event, "fxml/signup.fxml");
	}
	
	/*
	 * Go to Login window
	 * 
	 */
	public void goToLogin(ActionEvent event) {
		this.changeScene(event, "fxml/Login.fxml");
	}
	
	/*
	 * When the mouse hovers over the sign up button,
	 * the color changes
	 * 
	 */
	@FXML public void changeSignupColor(MouseEvent event) {
		signupBtn.setStyle("-fx-background-color: #99d3b9;");
	}
	
	/*
	 * When the mouse exits the sign button, the color
	 * reverts back
	 * 
	 */
	@FXML public void revertSignupColor(MouseEvent event) {
		signupBtn.setStyle("-fx-background-color: #662c46;");
	}

	/*
	 * When the mouse hovers over the login button,
	 * the color changes
	 * 
	 */
	@FXML public void changeLoginColor(MouseEvent event) {
		loginBtn.setStyle("-fx-background-color: #99d3b9;");
		
	}

	/*
	 * When the mouse exits the login button, the color
	 * reverts back
	 * 
	 */
	@FXML public void revertLoginColor(MouseEvent event) {
		loginBtn.setStyle("-fx-background-color: #662c46;");
		
	}
	
	/*
	 * Changes the scene from one scene to another
	 * 
	 */
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
	
}
