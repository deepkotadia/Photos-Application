package control;


import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
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
	
	@FXML DatePicker startDate, endDate;
	
	@FXML ListView<Photo> photosList;
	
	@FXML TextArea detailBox;

	public void start(Stage app_stage) {
		// TODO Auto-generated method stub
		
		
	}
	
	/**
	 * Displays Dialog box for giving date range to search for
	 */
	public void handleSearchByDate(ActionEvent event) {
		   
		   	  
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