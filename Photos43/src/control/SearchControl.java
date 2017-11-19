package control;

import java.io.IOException;
import java.util.Optional;

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
import model.Album;
import model.Photo;

/**
 * @author Deep Kotadia
 * @author Chinmoyi Bhushan
 *
 * This class controls the Search Page for user
 */

public class SearchControl implements LogoutInterface {
	@FXML Button dateSearch, tagSearch, createAlbum, logoutBtn;
	
	@FXML ListView<Photo> photosList;
	
	@FXML TextArea detailBox;

	public void start(Stage app_stage) {
		// TODO Auto-generated method stub
		
		
	}
	
	/**
	 * Displays Dialog box for giving date range to search for
	 */
	public void handleSearchByDate(ActionEvent event) {
		   Dialog<Album> dialog = new Dialog<>();
		   dialog.setTitle("Search for photos by Date");
		   dialog.setHeaderText("Start Date");
		   dialog.setResizable(true);
		   
		   Label startDateLabel = new Label("Start Date: ");
		   //TextField albumnameTextField = new TextField();
		   Label endDateLabel = new Label("End Date: ");
		   
		   GridPane grid = new GridPane();
		   grid.add(startDateLabel, 1, 1);
		   //grid.add(albumnameTextField, 2, 1);
		   grid.add(endDateLabel, 2, 1);
		   
		   dialog.getDialogPane().setContent(grid);
		   
		   ButtonType buttonTypeOk = new ButtonType("OK", ButtonData.OK_DONE);
		   dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
		   	  
	}
	
	
	/**
	  * 
	  * Let's user go back to list of albums page (user homepage)
	  */
	public void handleBack(ActionEvent event) throws IOException {
		
		Parent parent;
		
		FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/UserHomepage.fxml"));
		parent = (Parent)loader.load();
		UserHomepageControl ctrl = loader.getController();
		Scene scene = new Scene(parent);
					
		Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();	
	                
		ctrl.start(app_stage);
	             
	    app_stage.setScene(scene);
	    app_stage.show();
		
	}
	
	
	/** 
	  * Logs out the current user's session
	  */
	public void handleLogout(ActionEvent event) {
		try {
			logoutfnc(event); //logout function from LogoutInterface
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	

}
