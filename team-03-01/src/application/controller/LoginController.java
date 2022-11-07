package application.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.personalIndexCardManager.SQLConnector;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController implements Initializable {
	
	private final static String DATABASE = "usersTest.db";
	
	@FXML private BorderPane loginScene;
	@FXML private Button loginBtn;
	@FXML private Button homeBtn;
	@FXML private PasswordField passwordField;
	@FXML private Label errorLabel;
	@FXML private TextField emailField;
	@FXML private Label alertEmail;
	@FXML private Label alertPass;
	@FXML private Button resetPasswordBtn;

	
	@FXML public void userHome(ActionEvent event) {
		
		// Changes the scene from login to home
		URL url = getClass().getClassLoader().getResource("fxml/Home.fxml");
		
		try {
			// Loads the signup scene
			Parent root = FXMLLoader.load(url);
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
					
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	/*
	 * User logs into the application. Checks to see if all fields have been filled
	 * and are valid in the database. Goes to user's courses page.
	 * 
	 */
	@FXML public void userLogin(ActionEvent event) throws IOException {
		
		// Sets the visibility of the labels to false
		errorLabel.setVisible(false);
		alertEmail.setVisible(false);
		alertPass.setVisible(false);
		
		// Checks to see if the email field or password field is empty
		if (emailField.getText().isEmpty() || passwordField.getText().isEmpty()) {
			
			// Displays error message
			errorLabel.setText("***Please make sure all indicated fields have been filled***");
			errorLabel.setVisible(true);
			
			// Sets visibility to true for indicators of fields that need to be filled
			if (emailField.getText().isEmpty()) 
				alertEmail.setVisible(true);						
			if (passwordField.getText().isEmpty()) 
				alertPass.setVisible(true);
			return;
		}
		// Get inputs
		String enteredEmail = emailField.getText();
		sop("Email   : " + enteredEmail);
		String enteredPass = passwordField.getText();
		sop("Password: " + enteredPass);
				
		// Check inputs with database 
		SQLConnector con = new SQLConnector();
		
		// Check to see if the email is valid
		if (!(con.findEmail(DATABASE, "Users", enteredEmail))) {
			errorLabel.setText("***The email is invalid***");
			errorLabel.setVisible(true);
			alertEmail.setVisible(true);
			return;
		}
		
		// Check to see if the password is valid
		// Also checks email again with password for redundancy
		if(!(con.findPass(DATABASE, "Users", enteredEmail, enteredPass))) {
			errorLabel.setText("***The password is invalid***");
			errorLabel.setVisible(true);
			alertPass.setVisible(true);
			return;
		}
		
					
		// Sets the visibility of the labels to false
		errorLabel.setVisible(false);
		alertEmail.setVisible(false);
		alertPass.setVisible(false);
					
		// Changes the scene from login to user's courses window
		con.storeEmailInput(DATABASE, enteredEmail);
		this.changeScene(event, "fxml/Courses.fxml");
			
	}

	/*
	 * When the mouse cursor enters the Home button, it
	 * changes color.
	 * 
	 */
	@FXML public void changeHomeBtnColor() {
		homeBtn.setStyle("-fx-background-color: lime;");
	}

	/*
	 * When the mouse cursor exits the Home button, it
	 * reverts back to its original color.
	 * 
	 */
	@FXML public void revertHomeBtnColor() {
		homeBtn.setStyle("-fx-background-color: white;");
	}

	/*
	 * Goes to the reset password route
	 * 
	 */
	@FXML public void goToResetPass(ActionEvent event) {
		this.changeScene(event, "fxml/enteremail.fxml");
	}

	/*
	 * When the mouse cursor exits the reset password
	 * label, it changes color.
	 * 
	 */
	@FXML public void changeResetPassColor() {
		resetPasswordBtn.setTextFill(Color.valueOf("#9f8a92"));
		resetPasswordBtn.setStyle("-fx-background-color: transparent");
	}

	/*
	 * When the mouse cursor exits the reset password
	 * label, it reverts back to its original color.
	 * 
	 */
	@FXML public void revertResetPassColor() {
		resetPasswordBtn.setTextFill(Color.valueOf("#60756d"));
		resetPasswordBtn.setStyle("-fx-background-color: transparent");
	}
	
	// Changes color of button
	@FXML public void changeLoginBtnColor() {
		loginBtn.setStyle("-fx-background-color: #9f8a92");
	}

	// Reverts color of button
	@FXML public void revertLoginBtnColor() {
		loginBtn.setStyle("-fx-background-color: #60756d");
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
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Sets the visibility of the labels to false
		errorLabel.setVisible(false);
		alertEmail.setVisible(false);
		alertPass.setVisible(false);
	}

	private static void sop(Object obj) {
		System.out.println(obj);
	}

}
