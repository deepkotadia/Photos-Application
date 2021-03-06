package control;

import java.io.IOException;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.*;
import control.*;
import javafx.event.ActionEvent;
import application.Photos;


/**
 * controller class for login 
 * @author Deep Kotadia
 * @author Chinmoyi Bhushan
 *
 */
public class LoginControl {

	@FXML 
		private TextField username;
	
	@FXML 
		private Button userLogin;
	
	PhotoAlbumManager photoAlbumManager = Photos.manager;
	
	public final String adminUserName = "admin";
	
	
	/**
	 * handler for login button 
	 * @param event
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public void handleLoginBtn(ActionEvent event) throws ClassNotFoundException, IOException {
		
		String usernamestr = username.getText();
		Parent parent;
		
		
		if(usernamestr.equals(adminUserName)) { //is admin user
			FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/AdminHomepage.fxml"));
			parent = (Parent)loader.load();
			AdminHomepageControl ctrl = loader.getController();
			Scene scene = new Scene(parent);
						
			Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();	
		                
			ctrl.start(app_stage);
		             
		    app_stage.setScene(scene);
		    
		    app_stage.show();
		}
		
		else if(photoAlbumManager.login(usernamestr) ) { //user exists, so login
			
			User currentUser = photoAlbumManager.getCurrentUser();
			List<Album> userAlbums = currentUser.getAlbums();
			
			
			FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/UserHomepage.fxml"));
			parent = (Parent)loader.load();
			UserHomepageControl ctrl = loader.getController();
			Scene scene = new Scene(parent);
						
			Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();	
		                
			ctrl.start(app_stage);
		             
		    app_stage.setScene(scene);
		    app_stage.show();
		}
		
		else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("User does not exist!");
			alert.setContentText("Ask Admin to add User");
			//alert.showAndWait();
			Optional<ButtonType> buttonClicked=alert.showAndWait();
			if (buttonClicked.get()==ButtonType.OK) {
				alert.close();
			}
			else {
				alert.close();
			}
		}
	}

}
