package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

/*
 * REQUIREMENTS:
 * 		- Load the sqlite-jdbc-3.39.3.0.jar as part of your referenced libraries 
 * 			(code will break without access to database)
 * 		- Make sure that both src and resources files are source files in Eclipse
 * 
 */
public class Main extends Application {
	
	// layout parameters
	private static final int LAYOUT_WIDTH	 = 617;
	private static final int LAYOUT_HEIGHT	 = 355;
	private static final String TITLE		 = "Personal Index Card Manager";
    private static Stage stg;
    
    @Override
    public void start(Stage primaryStage) {
        try {
        	stg = primaryStage;
        	primaryStage.setResizable(false);
        	Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/Home.fxml"));
        	primaryStage.setTitle(TITLE);
        	primaryStage.setScene(new Scene(root, LAYOUT_WIDTH, LAYOUT_HEIGHT));
        	primaryStage.show();
        } catch (Exception e) {
        	e.getStackTrace();
        }
    }
    
    // go to page "fxml/pageName.fxml"
    public void changeScene(String fxml) {
        Parent pane;
		try {
			
			// Loads the next scene
			pane = FXMLLoader.load(getClass().getClassLoader().getResource(fxml));
			stg.getScene().setRoot(pane);
			stg.sizeToScene(); // Keeps the window to be the same size as scene 
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
	public static void main(String[] args) {
		launch(args);
	}

	
}
