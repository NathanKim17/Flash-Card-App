package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import application.personalIndexCardManager.Course;
import application.personalIndexCardManager.SQLConnector;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class CourseRenameController implements Initializable
{
	private final static String DATABASE = "usersTest.db";

	@FXML private Button submitNameBtn;

	@FXML private TextField nameSubmission;

	@FXML private Label wrongNameError;
	
	@FXML public void checkNameOp() throws IOException
	{
		String name = nameSubmission.getText();	

		// Check input with database 
		SQLConnector con = new SQLConnector();
		
		//first check if there exists the input in the database
		if (!(con.checkName(DATABASE, name)))
		{
			// Update course to the database
			con.updateCourseName(DATABASE, new Course(name));
			
			// set error to be invisible
			wrongNameError.setVisible(false);
			
			//return to courses page
			Stage stage = (Stage)submitNameBtn.getScene().getWindow();
			stage.close();
			Main m = new Main();
			m.changeScene("fxml/Courses.fxml");
			
			
		}
		else {
			System.out.println("Conflicted names detected");
			wrongNameError.setVisible(true);
			wrongNameError.setText("*That name exists in your courses*");
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
	}



}
