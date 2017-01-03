package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	static Stage window;
	static Scene FrontEnd;
	
	@Override
	public void start(Stage primaryStage)throws IOException {
		window = primaryStage;
	     initialize();
	     
	        window.setTitle("Employee Database 1.0");
	        window.setResizable(true);
	        window.setScene(FrontEnd);
	        window.show();
	}
	
	private void initialize() throws IOException {
		FrontEnd = new Scene(FXMLLoader.load(getClass().getResource("Frontend.fxml")));
		FrontEnd.getStylesheets().add("application.css");
	}

	public static void main(String[] args) {
		launch(args);
	}
}
