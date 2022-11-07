package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.personalIndexCardManager.SQLConnector;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class securityquestionController implements Initializable
{
	private final static String DATABASE = "usersTest.db";
	
	@FXML
	private Button submitAnswerBtn;
	
	@FXML
	private Label wrongAnswerError;
	
	@FXML
	private Label securityQuestion;
	
	@FXML
	private TextField questionAnswer;
	
	@FXML public void checkQuestionOp() throws IOException
	{
		String answer = questionAnswer.getText();	
		// Check inputs with database 
		SQLConnector con = new SQLConnector();
		
		//first check if the answer matches for the user
		if ((con.findAnswer(DATABASE, "Users", answer)))
		{
			// Goes to reset password scene
			wrongAnswerError.setVisible(false);
			Stage stage2 = (Stage)submitAnswerBtn.getScene().getWindow();
			stage2.close();
			Stage primaryStage2 = new Stage();
			Parent root2 = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/newpassword.fxml"));
			
			// Settings for the scene
			primaryStage2.setTitle("New password");
			primaryStage2.setScene(new Scene(root2, 500, 300));
			primaryStage2.initModality(Modality.APPLICATION_MODAL);
			primaryStage2.show();
			
			
		}
		else
		{
			System.out.println("The answer is wrong");
			wrongAnswerError.setVisible(true);
			wrongAnswerError.setText("*Incorrect answer*");
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		SQLConnector con = new SQLConnector();
		securityQuestion.setText(con.getSecQuest(DATABASE, "Users"));
		
	}


}
