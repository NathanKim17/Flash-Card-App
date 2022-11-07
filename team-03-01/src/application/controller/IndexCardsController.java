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
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class IndexCardsController {


	@FXML Button toCoursesBtn;
	@FXML Button deleteBtn;
	@FXML Button renameBtn;


	@FXML public void goToCourses(ActionEvent event) {
		// Changes the scene from one window to another window
		URL url = getClass().getClassLoader().getResource("fxml/Courses.fxml");
								
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
	
	/*
	 * Change logout button color when mouse hovers
	 * over the button
	 * 
	 */
	@FXML public void changeToCoursesBtnColor() {
		toCoursesBtn.setStyle("-fx-background-color: #06014a");
		toCoursesBtn.setTextFill(Color.WHITE);
	}
	
	/*
	 * Reverts logout button color when mouse exits
	 * the button
	 * 
	 */
	@FXML public void revertToCoursesBtnColor() {
		toCoursesBtn.setStyle("-fx-background-color: #F9FEB5");
		toCoursesBtn.setTextFill(Color.BLACK);
	}

	/*
	 * Rename the selected course
	 * 
	 */
	@FXML public void renameCourse() {
		try {
			// Loads a rename prompt
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/CourseRename.fxml"));
			
			
			// Settings for the rename prompt
			primaryStage.setTitle("Rename the Course");
			primaryStage.setScene(new Scene(root, 500, 300));
			primaryStage.alwaysOnTopProperty();
			primaryStage.centerOnScreen();
			primaryStage.setResizable(false);
			primaryStage.initModality(Modality.APPLICATION_MODAL);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Deletes the selected course
	 * 
	 */
	@FXML public void deleteCourse(ActionEvent event) {
		try {
			// Loads a delete prompt
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/CourseDeletion.fxml"));
			
			
			// Settings for the deletion prompt
			primaryStage.setTitle("Rename the Course");
			primaryStage.setScene(new Scene(root, 500, 300));
			primaryStage.alwaysOnTopProperty();
			primaryStage.centerOnScreen();
			primaryStage.setResizable(false);
			primaryStage.initModality(Modality.APPLICATION_MODAL);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
