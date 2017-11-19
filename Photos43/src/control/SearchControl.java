package control;

import java.time.LocalDate;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
		   Dialog<LocalDate> dialog = new Dialog<>();
		   dialog.setTitle("Search for photos by Date");
		   dialog.setHeaderText("Start Date");
		   dialog.setResizable(true);
		   
		   Label startDateLabel = new Label("Start Date: ");
		   startDate.getValue();
		   Label endDateLabel = new Label("End Date: ");
		   endDate.getValue();
		   
		   GridPane grid = new GridPane();
		   grid.add(startDateLabel, 1, 1);
		   grid.add(startDate, 2, 1);
		   grid.add(endDateLabel, 2, 1);
		   grid.add(endDate, 2, 2);
		   
		   dialog.getDialogPane().setContent(grid);
		   
		   ButtonType buttonTypeOk = new ButtonType("OK", ButtonData.OK_DONE);
		   dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
		   
		   dialog.setResultConverter(new Callback<ButtonType, LocalDate>() {
			   @Override
			   public LocalDate call(ButtonType b) {
				   if (b == buttonTypeOk) {
					   
					  // String error = checkFields();
					   
					  /*if (error != null) {
						   Alert alert = new Alert(AlertType.ERROR);
						   alert.setTitle("Error Dialog");
						   alert.setHeaderText(error);
						   alert.setContentText("Please try again");

						   Optional<ButtonType> buttonClicked=alert.showAndWait();
						   if (buttonClicked.get()==ButtonType.OK) {
							   alert.close();
						   }
						   else {*/
							   //alert.close();
						  // }
						   //return null;
					   //}
											   
					  // return new LocalDate(	);
				   }
				   return null;
			   }
			
		   });
		   
		   //wait for response from add button
		   dialog.showAndWait();
		   
		  
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
