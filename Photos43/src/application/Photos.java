package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;


public class Photos extends Application {
	
	Stage mainStage;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			mainStage = primaryStage;
			
			FXMLLoader loader= new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/Login.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			
			Scene scene = new Scene(root,700,500);
			mainStage.setScene(scene);
			mainStage.setTitle("Photo Album");
			mainStage.setResizable(false);
			mainStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
