package application.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import application.personalIndexCardManager.Course;
import application.personalIndexCardManager.SQLConnector;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CoursesController implements Initializable {

	private final static String DATABASE = "usersTest.db";
	
	@FXML private Button accntManageBtn;
	@FXML private Button logoutBtn;
	@FXML private BorderPane loginScene;
	@FXML private Button createCourseBtn;
	@FXML private FlowPane coursesPanel;
	@FXML private ScrollPane scrollPane;

	@FXML public void logout(ActionEvent event) {
		// Changes the scene from one window to another window
		URL url = getClass().getClassLoader().getResource("fxml/Home.fxml");
								
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
	@FXML public void changeLogoutBtnColor() {
		logoutBtn.setStyle("-fx-background-color: #06014a");
		logoutBtn.setTextFill(Color.WHITE);
	}
	
	/*
	 * Reverts logout button color when mouse exits
	 * the button
	 * 
	 */
	@FXML public void revertLogoutBtnColor() {
		logoutBtn.setStyle("-fx-background-color: #F9FEB5");
		logoutBtn.setTextFill(Color.BLACK);
	}
	
	/*
	 * Creates new course
	 * 
	 */
	@FXML public void createNewCourse(ActionEvent event) {
		try {
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/CourseCreation.fxml"));
			// Settings for the security question prompt
			primaryStage.setTitle("Create a Course");
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
	 * Change create course button color when mouse hovers
	 * over the button
	 * 
	 */
	@FXML public void changeNewCourseBtnColor() {
		createCourseBtn.setStyle("-fx-background-color: #e5938a");
		createCourseBtn.setTextFill(Color.BLACK);
	}

	/*
	 * Reverts create course button color when mouse exits
	 * the button
	 * 
	 */
	@FXML public void revertNewCourseBtnColor() {
		createCourseBtn.setStyle("-fx-background-color: #1a6c75");
		createCourseBtn.setTextFill(Color.WHITE);
	}
	
	private Button newCourses(String name) {
		Button btn = new Button();
		btn.setText(name);
		return btn;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		SQLConnector sql = new SQLConnector();
		for (Course c: sql.getCourses(DATABASE)) {
			Button newCourse = newCourses(c.getName());
			newCourse.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	                Main m = new Main();
	                sql.storeCurrentCourse(DATABASE, c);
	                m.changeScene("fxml/IndexCard.fxml");
	            }
	        });
			newCourse.setFont(Font.font(24));
			coursesPanel.getChildren().add(newCourse);
		}
		
		
	}


}
