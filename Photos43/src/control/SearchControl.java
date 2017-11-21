package control;


import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import application.Photos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Album;
import model.Photo;
import model.PhotoAlbumManager;
import model.Tag;

/**
 * @author Deep Kotadia
 * @author Chinmoyi Bhushan
 *
 * This class controls the Search Page for user
 */

public class SearchControl implements LogoutInterface {
	@FXML Button dateSearch, tagSearch, addTag, createAlbum, logoutBtn;
	
	@FXML DatePicker startDate;

	@FXML DatePicker endDate;
	
	@FXML ListView<Photo> photosList;
	
	@FXML ListView<String> listViewOfTags;
	
	@FXML TextArea detailBox;
	
	@FXML TextField nameTag, valueTag;
	
	@FXML ImageView searchedPhoto;
	
	private List<Photo> photosFromSearch = new ArrayList<Photo>();
	private List<Tag> listOfTags = new ArrayList<Tag>();
	private List<String> displayTags = new ArrayList<String>();
	private ObservableList<Photo> obsList;
	private ObservableList<String> obsList_Tag;

	public void start(Stage app_stage) {
		
		app_stage.setTitle(Photos.manager.getCurrentUser().getName() + " 's Photos Search Page");
				
	}
	
	/**
	 * returns a list of photos with the date range to search for
	 * @param event
	 */
	public void handleSearchByDate(ActionEvent event) {
			LocalDate fromDate = startDate.getValue();
			LocalDate toDate = endDate.getValue();
			
			if(fromDate == null || toDate == null) {
				 Alert alert = new Alert(AlertType.ERROR);
				 alert.setTitle("Error Dialog");
				 alert.setHeaderText("Please enter a start and end date!");
				 alert.setContentText("Dates cannot be left blank!");

				   Optional<ButtonType> buttonClicked=alert.showAndWait();
				   if (buttonClicked.get()==ButtonType.OK) {
					   alert.close();
				   }
				   else {
					   alert.close();
				   }
				return;
			}
			
			else if(toDate.isBefore(fromDate)) {
				   Alert alert = new Alert(AlertType.ERROR);
				   alert.setTitle("Error Dialog");
				   alert.setHeaderText("The date range you provided is not valid!");
				   alert.setContentText("Please make sure the start date is before the end date!");

				   Optional<ButtonType> buttonClicked=alert.showAndWait();
				   if (buttonClicked.get()==ButtonType.OK) {
					   alert.close();
				   }
				   else {
					   alert.close();
				   }
				return;
			}
			
			this.photosFromSearch = Photos.manager
				.getCurrentUser().getPhotosInDateRange(fromDate.getYear(), fromDate.getMonthValue(), fromDate.getDayOfMonth(), 
													   toDate.getYear(), toDate.getMonthValue(), toDate.getDayOfMonth());	
			this.displaySearchedPhotos();
	}
	
	/**
	 * function handler for searching by tags button
	 * @param event
	 */
	
	public void handleSearchByTag(ActionEvent event) {
		this.photosFromSearch = Photos.manager.getCurrentUser().getPhotosWithTag(listOfTags);
		this.displaySearchedPhotos();	
	}
	
	private void displaySearchedPhotos() {
		obsList = FXCollections.observableArrayList(photosFromSearch);   
		
		photosList.setCellFactory(new Callback<ListView<Photo>, ListCell<Photo>>(){
			@Override
			public ListCell<Photo> call(ListView<Photo> p){
				return new EachSearchedPhoto();
			}
			
		});
		
		photosList.setItems(obsList);
		
	    if(!obsList.isEmpty()) {
	    		photosList.getSelectionModel().select(0); //select first photo of album
	    }
	}
	
	private class EachSearchedPhoto extends ListCell<Photo>{
		AnchorPane anchor = new AnchorPane();
		StackPane stackpane = new StackPane();
		
		ImageView imageView = new ImageView();
		
		public EachSearchedPhoto() {
			super();
			
			imageView.setFitWidth(90.0);
			imageView.setFitHeight(90.0);
			imageView.setPreserveRatio(true);
			
			StackPane.setAlignment(imageView, Pos.TOP_LEFT);
			
			stackpane.getChildren().add(imageView);
			
			stackpane.setPrefHeight(110.0);
			stackpane.setPrefWidth(90.0);
			
			AnchorPane.setLeftAnchor(stackpane, 0.0);
			
			anchor.getChildren().addAll(stackpane);
			
			anchor.setPrefHeight(110.0);
				
			setGraphic(anchor);	
			
		}
		
		@Override
		public void updateItem(Photo photo, boolean empty) {
			super.updateItem(photo, empty);
			
			setText(null);
			if(photo == null)
			{
				Image no_thumb = new Image(new File("/stockphotos/no_thumb.jpg").toURI().toString());
				imageView.setImage(no_thumb);
				
			}
			
			else{
				Image img = new Image(new File(photo.getPhotoPath()).toURI().toString());
				imageView.setImage(img);
			}
			
		}
	}
	
	/**
	 * This function is the handler for adding a tag to a list of tags to be searched for 
	 * @param event
	 */
	
	public void handleAddTag(ActionEvent event) {
		if(nameTag.getText().trim().isEmpty() || valueTag.getText().trim().isEmpty()) {
			 Alert alert = new Alert(AlertType.ERROR);
			 alert.setTitle("Error Dialog");
			 alert.setHeaderText("Please enter a tag name and its value!");
			 alert.setContentText("The name and value cannot be left blank!");

			   Optional<ButtonType> buttonClicked=alert.showAndWait();
			   if (buttonClicked.get()==ButtonType.OK) {
				   alert.close();
			   }
			   else {
				   alert.close();
			   }
			return;		
		}
		
		Tag addedTag = new Tag(nameTag.getText(), valueTag.getText());
		
		listOfTags.add(addedTag);
		
		displayTags.clear();
		
		for(Tag t : listOfTags) {
			displayTags.add("Tag Name: " + t.key + "      " + "Tag Value: " + t.value );
		}
		
		obsList_Tag = FXCollections.observableArrayList(displayTags);               
		listViewOfTags.setItems(obsList_Tag);
		
		nameTag.setText("");
		valueTag.setText("");
	}
	
	/**
	 * handler for the button to create an album from the search results
	 * @param event
	 */
	public void handleCreateAlbum(ActionEvent event) {
		
		if((startDate.getValue() == null || endDate.getValue() == null) && 
				(nameTag.getText().trim().isEmpty() || valueTag.getText().trim().isEmpty())){
			 Alert alert = new Alert(AlertType.ERROR);
			 alert.setTitle("Error Dialog");
			 alert.setHeaderText("Cannot create album as search is incomplete!");
			 alert.setContentText("Either provide a date range or a tag-value pair!");

			   Optional<ButtonType> buttonClicked=alert.showAndWait();
			   if (buttonClicked.get()==ButtonType.OK) {
				   alert.close();
			   }
			   else {
				   alert.close();
			   }
			return;		
			
		}
		   Dialog<Album> dialog = new Dialog<>();
		   dialog.setTitle("Create a New Album from search results");
		   dialog.setHeaderText("Name for album created from search results ");
		   dialog.setResizable(true);
		   
		   Label albumnameLabel = new Label("Album Name: ");
		   TextField albumnameTextField = new TextField();
		   
		   GridPane grid = new GridPane();
		   grid.add(albumnameLabel, 1, 1);
		   grid.add(albumnameTextField, 2, 1);
		   
		   dialog.getDialogPane().setContent(grid);
		   
		   ButtonType buttonTypeOk = new ButtonType("Add", ButtonData.OK_DONE);
		   dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
		   
		   dialog.setResultConverter(new Callback<ButtonType, Album>() {
			   @Override
			   public Album call(ButtonType b) {
				   if (b == buttonTypeOk) {
					   if (albumnameTextField.getText().trim().isEmpty()) {
						   Alert alert = new Alert(AlertType.ERROR);
						   alert.setTitle("Error Dialog");
						   alert.setHeaderText("Album name required!");
						   alert.setContentText("Please try again");

						   Optional<ButtonType> buttonClicked=alert.showAndWait();
						   if (buttonClicked.get()==ButtonType.OK) {
							   alert.close();
						   }
						   else {
							   alert.close();
						   }
						   return null;
					   }
											   
					   return new Album(albumnameTextField.getText().trim());
				   }
				   return null;
			   }
			
		   });
		   
		   Optional<Album> result = dialog.showAndWait();
		   
		   if (result.isPresent()) {
			   //TODO date range not coming and then uncomment line 120 in SingleAlbumControl
			   //Album newAlbumFromSearch = (Album) result.get();
			   //newAlbumFromSearch.getPhotos().addAll(photosFromSearch);
			  // Photos.manager.getCurrentUser().getAlbums().add(newAlbumFromSearch); 
	
		   }
	}
		
	/**
	  * Let's user go back to list of albums page (user homepage)
	  * @param event
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
