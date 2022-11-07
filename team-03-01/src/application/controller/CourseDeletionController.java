package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import application.personalIndexCardManager.SQLConnector;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;

public class CourseDeletionController implements Initializable
{
	private final static String DATABASE = "usersTest.db";

	@FXML private Button courseBackBtn;
	@FXML private CheckBox checkDelete;
	@FXML private Button deleteBtn;
	@FXML private Label errorLbl;
	

	@FXML public void deleteCourseOp(ActionEvent event) {
		SQLConnector con = new SQLConnector();
		
		// Checks to see if the checkmark is checked
		if (checkDelete.isSelected()) {
			
			// deletes the current course
			con.deleteCourse(DATABASE);
			
			// Hides error label
			errorLbl.setVisible(false);
			
			// Change windows
			Stage stage = (Stage)deleteBtn.getScene().getWindow();
			stage.close();
			Main m = new Main();
			m.changeScene("fxml/Courses.fxml");
		} 
		else {
			// Displays error in deleting the course
			errorLbl.setVisible(true);
			errorLbl.setText("***You must check the box to delete the course***");
			System.out.println("Cannot delete without the checkmark being checked");
		}
	}

	@FXML public void goToCurrentCourse(ActionEvent event) {
		Stage stage = (Stage)courseBackBtn.getScene().getWindow();
		stage.close();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		errorLbl.setVisible(false);
	}


}
