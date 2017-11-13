package photos;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import java.util.*;


public class Photos extends Application {
	
	Stage mainStage;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		mainStage = primaryStage;
		
		FXMLLoader loader= new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/Login.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		
		Scene scene = new Scene(root,700,500);
		mainStage.setScene(scene);
		mainStage.setTitle("Photo Album");
		mainStage.setResizable(false);
		mainStage.show();
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		launch(args);

	}

}
