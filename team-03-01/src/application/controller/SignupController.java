package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import application.personalIndexCardManager.SQLConnector;
import application.personalIndexCardManager.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;


import javafx.scene.control.TextField;

import javafx.scene.control.Label;

import javafx.scene.control.ComboBox;

import javafx.scene.control.PasswordField;



public class SignupController implements Initializable {
	
	private final static String DATABASE = "usersTest.db";
	
	@FXML
	private Button homeBtn;
	@FXML
	private TextField emailTxtField;
	@FXML
	private PasswordField passField;
	@FXML
	private ComboBox<String> securityQuestBox;
	@FXML
	private TextField ansTxtField;
	@FXML
	private Button signupBtn;
	@FXML
	private Label errorLabel;
	
	// signup if "Signup" button is clicked
	@FXML
	public void userSignup () throws IOException {
		Main m = new Main();
		
		// if one txt field is empty or question was not picked, display error, else create user acc
		if(emailTxtField.getText().isEmpty() 
			|| passField.getText().isEmpty() 
			|| ansTxtField.getText().isEmpty()
			|| securityQuestBox.getValue() == null)
		{
			System.out.println("One is empty.");
			
			errorLabel.setVisible(true);
			errorLabel.setText("***Please make sure all the fields are filled and a question was selected***");
		} else {
			String email = emailTxtField.getText();
			String pass = passField.getText();
			String securityQuest = securityQuestBox.getSelectionModel().getSelectedItem().toString();
			String securityAns = ansTxtField.getText();
			
			System.out.println("Signing up...");
			System.out.println("Email: " + email);
			System.out.println("Pass: " + pass);
			System.out.println("Security Quest: " + securityQuest);
			System.out.println("Security Ans: " + securityAns);
			
			errorLabel.setVisible(false);
				
			// create user acc and add it to db
			SQLConnector con = new SQLConnector();
	    	
			if(con.findEmail(DATABASE, "Users", email)) {
				errorLabel.setVisible(true);
				errorLabel.setText("***An account is associated with that email***");
				return;
			}
			con.addUser(DATABASE, "Users", new User(email, pass, securityQuest, securityAns));
			con.storeEmailInput(DATABASE, email);
			System.out.println("User added.");
			
			// change page/scene to main home menu of the application (homeTest for testing)
			m.changeScene("fxml/Courses.fxml");
			System.out.println("Scene changed.");
		}
	}
	
	public void userHome() throws IOException {
		Main m = new Main();
		
		// change page/scene to main home menu of the application (homeTest for testing)
		m.changeScene("fxml/home.fxml");
	}
	
	public void initialize(URL location, ResourceBundle resources) {
		errorLabel.setVisible(false);
		
		// list of questions
		ObservableList<String> list = 
				FXCollections.observableArrayList(
						"What is your favorite video game",
						"What was the name of your elementary school",
						"What is your favorite food",
						"What is your favorite school subject");
		
		securityQuestBox.setItems(list);
	}

}
