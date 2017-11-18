/**
 * 
 */
package control;

import java.io.IOException;
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
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Album;
import model.PhotoAlbumManager;
import model.User;

/**
 * @author Deep Kotadia
 * @author Chinmoyi Bhushan
 *
 * This class controls the User's Home Page
 */

public class UserHomepageControl implements LogoutInterface {
	
	@FXML Button searchBtn, Logout, addAlbum;
	
	@FXML MenuButton AlbumOption;
	
	@FXML MenuItem viewAlbum, renameAlbum, deleteAlbum;
	
	@FXML ListView<Album> albumsList;
	
	@FXML Text welcomeText;
	
	private ObservableList<Album> obsList;
	private static List<Album> albumsOfUser = new ArrayList<Album>();
	
	int addorrename = -1; //0 if call from add, 1 if call from rename
	
	public void start(Stage app_stage) {
		
		welcomeText.setText("Welcome, " + Photos.manager.getCurrentUser().getName() + "!");
		populateAlbumList();
			
		obsList = FXCollections.observableArrayList(albumsOfUser);   
		
		albumsList.setCellFactory(new Callback<ListView<Album>, ListCell<Album>>(){
			@Override
			public ListCell<Album> call(ListView<Album> p){
				return new EachAlbum();
			}
			
		});
		
		albumsList.setItems(obsList);
		
	    if(!obsList.isEmpty()) {
	    		albumsList.getSelectionModel().select(0); //select first album of user
	    }
		
	}
	
	/**
	  * Populates the list of Albums for the user
	  */
	public static void populateAlbumList(){
		
		albumsOfUser.clear(); //refresh the list
		
		for(int i = 0; i < Photos.manager.getCurrentUser().getAlbums().size(); i++) {
			albumsOfUser.add(Photos.manager.getCurrentUser().getAlbums().get(i));
		}	
	}
	
	
	/**
	 * This class is overriding ListCell which is a cell in ListView to display information about each album
	 * 
	 * @author Chinmoyi Bhushan
	 * @author Deep Kotadia
	 *
	 */
	private class EachAlbum extends ListCell<Album>{
		AnchorPane anchor = new AnchorPane();
		StackPane stackpane = new StackPane();
		
		ImageView imageView = new ImageView();
		Label albumName = new Label();
		Label dateRange = new Label();
		Label numberOfPhotos = new Label();
		
		public EachAlbum() {
			super();
			
			imageView.setFitWidth(90.0);
			imageView.setFitHeight(90.0);
			imageView.setPreserveRatio(true);
			
			StackPane.setAlignment(imageView, Pos.TOP_LEFT);
			
			stackpane.getChildren().add(imageView);
			
			stackpane.setPrefHeight(110.0);
			stackpane.setPrefWidth(90.0);
			
			AnchorPane.setLeftAnchor(stackpane, 0.0);
			
			AnchorPane.setLeftAnchor(albumName, 100.0);
			AnchorPane.setTopAnchor(albumName, 0.0);
			
			AnchorPane.setLeftAnchor(dateRange, 100.0);
			AnchorPane.setTopAnchor(dateRange, 15.0);
		
			AnchorPane.setLeftAnchor(numberOfPhotos, 100.0);
			AnchorPane.setTopAnchor(numberOfPhotos, 45.0);
			
			anchor.getChildren().addAll(stackpane, albumName, dateRange, numberOfPhotos);
			
			anchor.setPrefHeight(110.0);
			
			albumName.setMaxWidth(250.0);
			
			setGraphic(anchor);	
			
			//for (Album userAlbum: Photos.manager.getCurrentUser().getAlbums()) {
				//albumName.setText("Album name: " + userAlbum.getAlbumName());
			//}
		}
		
		@Override
		public void updateItem(Album album, boolean empty) {
			super.updateItem(album, empty);
			
			setText(null);
			if(album == null)
			{
				//imageView.setImage(null);
				albumName.setText("");
				
			}
			//		setGraphic(null);
			else{//(album != null){
				//imageView.setImage(album.getAlbumPhoto());
				albumName.setText("Album name: " + album.getAlbumName());
				//dateRangeLabel.setText(album.getDateRange());
				//oldestPhotoLabel.setText("Oldest Photo: " + album.getOldestPhotoDate());
				numberOfPhotos.setText("Number of Photos: " + album.getPhotos().size());
			}
			
		}
	}


	/**
	  * 
	  * Prompts User to add new Album, adds it to the main arraylist in PhotoAlbumManager
	  */
	public void handleAddAlbum(ActionEvent event) throws IOException {
		
		addorrename = 0;
		   
		   Dialog<Album> dialog = new Dialog<>();
		   dialog.setTitle("Create a New Album");
		   dialog.setHeaderText("Add Name of New Album");
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
					   
					   String error = checkFields(albumnameTextField.getText());
					   
					   if (error != null) {
						   Alert alert = new Alert(AlertType.ERROR);
						   alert.setTitle("Error Dialog");
						   alert.setHeaderText(error);
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
		   
		   //wait for response from add button
		   Optional<Album> result = dialog.showAndWait();
		   
		   // if user presses Add, add the new album to the overall list of user's albums
		   if (result.isPresent()) {
			   Album newAlbum = (Album) result.get(); //store result
			   
			   Photos.manager.getCurrentUser().addAlbum(albumnameTextField.getText());
			   populateAlbumList();
			   albumsList.refresh();
			   obsList=FXCollections.observableArrayList(albumsOfUser);
			   /*Render in proper UI*/
			   albumsList.setCellFactory(new Callback<ListView<Album>, ListCell<Album>>(){
					@Override
					public ListCell<Album> call(ListView<Album> p){
						return new EachAlbum();
					}
					
				});
			   albumsList.setItems(obsList);
			   
			   PhotoAlbumManager.serialize(Photos.manager);
			   			   
			   //if this is first album added, then select it
			   if (obsList.size() == 1) {
				   albumsList.getSelectionModel().select(0);
			   }
			   else{
				   int index = 0;
				   for(Album s: albumsOfUser){
					   if(s.getAlbumName().equals(newAlbum.getAlbumName())){
						  albumsList.getSelectionModel().select(index);
						  break;
					   }
					   index++;
				   }
			   }	   
		   }
	}
	
	
	  /**
	    * 
	    * Check the fields, return null if no errors found
	    * @return the error message in string format, null if no errors
	    */
	   private String checkFields(String albumname) {
		   if (albumname.trim().isEmpty())
			   return "Albumname is a required field.";
		   
		   if(addorrename != 1) { //need not do this for rename
			   for(int i = 0; i < albumsOfUser.size(); i++) {
				   if(albumsOfUser.get(i).getAlbumName().equals(albumname)) { //duplicate album name not allowed
					   return "An album with the same name already exists in your account, please try another name.";
				   }
			   }
		   }
		   
		   return null;
	}
	   
	
	/**
	  * 
	  * Delete selected user from list
	  * @throws IOException 
	  */
	public void handleDeleteAlbum(ActionEvent event) throws IOException {
		
		int albumindex = albumsList.getSelectionModel().getSelectedIndex();
		   
		   Alert alert = new Alert(AlertType.CONFIRMATION);
		   alert.setTitle("Confirm Delete");
		   alert.setHeaderText(null);
		   alert.setContentText("Are you sure you want to delete this Album? (There's no going back!)");

		   Optional<ButtonType> result = alert.showAndWait();
		   if (result.get() == ButtonType.OK) { // ... user chose OK
			   
			   Photos.manager.getCurrentUser().getAlbums().remove(albumindex);
			   populateAlbumList();
			   albumsList.refresh();
			   obsList=FXCollections.observableArrayList(albumsOfUser);
			   /*Render in proper UI*/
			   albumsList.setCellFactory(new Callback<ListView<Album>, ListCell<Album>>(){
					@Override
					public ListCell<Album> call(ListView<Album> p){
						return new EachAlbum();
					}
					
				});
			   albumsList.setItems(obsList);
			   
			   PhotoAlbumManager.serialize(Photos.manager);
			   
			   if(albumsOfUser.size() == 0) {
					deleteAlbum.setVisible(false);
		       }
			   else {
				   int lastalbumindex = albumsOfUser.size();
				   if(albumsOfUser.size() == 1) { //only one user remaining in list, so select it and display its details
					   albumsList.getSelectionModel().select(0);
				   }
				   else if(albumindex == lastalbumindex) { //deleted user was last user in the list, so select previous user, previous user is now last user
					   albumsList.getSelectionModel().select(lastalbumindex-1);
				   }
				   else { //not the last user, so select next user
					   albumsList.getSelectionModel().select(albumindex);
				   }
			   }
			      
		   } else { // ... user chose CANCEL or closed the dialog
			   return;
		   }
		   return;

	}
	
	
	/**
	  * 
	  * Opens selected album in new window displaying all its photos
	  */
	public void handleViewAlbum(ActionEvent event) throws IOException {
		
		Parent parent;
		
		//User currentUser = Photos.manager.getCurrentUser();
		int albumindex = albumsList.getSelectionModel().getSelectedIndex();    
		//String albumname = albumsOfUser.get(albumindex).getAlbumName();
		int currentUserIndex = Photos.manager.getcurrentUserIndex();
		Album album = Photos.manager.getusers().get(currentUserIndex).getAlbums().get(albumindex);
		
		Photos.manager.getusers().get(currentUserIndex).setcurrentAlbum(album); //set as current album
		
		/*Load the selected Album's Page*/
		FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/SingleAlbum.fxml"));
		parent = (Parent)loader.load();
		UserHomepageControl ctrl = loader.getController();
		Scene scene = new Scene(parent);
					
		Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();	
	                
		ctrl.start(app_stage);
	             
	    app_stage.setScene(scene);
	    app_stage.show();
		
	}
	
	
	/**
	  * 
	  * Allows User to Rename a selected Album
	  */
	public void handleRenameAlbum(ActionEvent event) throws IOException {
		
		addorrename = 1;
		
		int albumindex = albumsList.getSelectionModel().getSelectedIndex();    
		String oldname = albumsOfUser.get(albumindex).getAlbumName();
		
		   Dialog<String> dialog = new Dialog<>();
		   dialog.setTitle("Rename Album");
		   //dialog.setHeaderText("Add Name of New Album");
		   dialog.setResizable(true);
		   
		   Label albumnameLabel = new Label("Album Name: ");
		   TextField albumnameTextField = new TextField();
		   albumnameTextField.setText(oldname);
		   albumnameTextField.selectAll();
		   
		   GridPane grid = new GridPane();
		   grid.add(albumnameLabel, 1, 1);
		   grid.add(albumnameTextField, 2, 1);
		   
		   dialog.getDialogPane().setContent(grid);
		   
		   ButtonType buttonTypeOk = new ButtonType("Rename", ButtonData.OK_DONE);
		   dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
		   
		   dialog.setResultConverter(new Callback<ButtonType, String>() {
			   @Override
			   public String call(ButtonType b) {
				   if (b == buttonTypeOk) {
					   
					   String error = checkFields(albumnameTextField.getText());
					   
					   if (error != null) {
						   Alert alert = new Alert(AlertType.ERROR);
						   alert.setTitle("Error Dialog");
						   alert.setHeaderText(error);
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
											   
					   return albumnameTextField.getText().trim();
				   }
				   return null;
			   }
			
		   });
		   
		   //wait for response from add button
		   Optional<String> result = dialog.showAndWait();
		   
		   // if user presses Rename, rename the album to the new name
		   if (result.isPresent()) {
			   String newAlbumName = result.get(); //store result
			   
			   Photos.manager.getCurrentUser().getAlbums().get(albumindex).setAlbumName(newAlbumName);
			   populateAlbumList();
			   albumsList.refresh();
			   obsList=FXCollections.observableArrayList(albumsOfUser);
			   /*Render in proper UI*/
			   albumsList.setCellFactory(new Callback<ListView<Album>, ListCell<Album>>(){
					@Override
					public ListCell<Album> call(ListView<Album> p){
						return new EachAlbum();
					}
					
				});
			   albumsList.setItems(obsList);
			   
			   PhotoAlbumManager.serialize(Photos.manager);
			   			   
			   //if this is first album added, then select it
			   if (obsList.size() == 1) {
				   albumsList.getSelectionModel().select(0);
			   }
			   else{
				   int index = 0;
				   for(Album s: albumsOfUser){
					   if(s.getAlbumName().equals(newAlbumName)){
						  albumsList.getSelectionModel().select(index);
						  break;
					   }
					   index++;
				   }
			   }	   
		   }
		
	}
	
	
	/**
	  * 
	  * Opens new Search Window and allows user to search for photos based on Search Criteria
	  */
	public void handleSearch(ActionEvent event) throws IOException {
		
	}
	
	
	/**
	  * 
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
