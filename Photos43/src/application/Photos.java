package application;
	
import control.LoginControl;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import model.PhotoAlbumManager;
import model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class Photos extends Application {
	
	Stage mainStage;
	
	public static PhotoAlbumManager manager = new PhotoAlbumManager();
	
	@Override
	public void start(Stage primaryStage) throws IOException, ClassNotFoundException {
		try {
			
			mainStage = primaryStage;
			
			FXMLLoader loader= new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/Login.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			
			//LoginControl loginController = loader.getController();
		    //loginController.start(mainStage);
			
			Scene scene = new Scene(root,600,400);
			mainStage.setScene(scene);
			mainStage.setTitle("Photo Album");
			mainStage.setResizable(false);
			mainStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		/*write to users.dat file once application is exited, for persistence*/
		mainStage.setOnCloseRequest(new EventHandler<WindowEvent>(){
			public void handle(WindowEvent we) {
				try {
					PhotoAlbumManager.serialize(manager);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Stage is closing");
			}
		});
	
	}
	
	
	public static void main(String[] args) throws ClassNotFoundException {

		try {
			manager = PhotoAlbumManager.deserialize();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		launch(args);
	}
}
