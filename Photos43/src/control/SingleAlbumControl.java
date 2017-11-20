/**
 * 
 */
package control;

import java.io.File;
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
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Album;
import model.Photo;
import model.PhotoAlbumManager;
import model.User;


/**
 * @author Deep Kotadia
 * @author Chinmoyi Bhushan
 *
 */
public class SingleAlbumControl implements LogoutInterface {

	@FXML Button BackBtn, LogoutBtn, addPhoto;
	
	@FXML
	Text daterange;

	@FXML
	Text numphotos;

	@FXML
	Text albumname;
	
	@FXML
	ImageView albumimg;
	
	@FXML MenuItem viewPhoto, deletePhoto;
	
	@FXML ListView<Photo> photosList;
	
	@FXML AnchorPane root;
	
	private ObservableList<Photo> obsList;
	private static List<Photo> photosInAlbum = new ArrayList<Photo>();
	
	
	public void start(Stage app_stage) {
		
		app_stage.setTitle(Photos.manager.getCurrentUser().getcurrentAlbum().getAlbumName() + " Album Page");
		albumname.setText(Photos.manager.getCurrentUser().getcurrentAlbum().getAlbumName());
		
		populatePhotosList();
			
		obsList = FXCollections.observableArrayList(photosInAlbum);   
		
		photosList.setCellFactory(new Callback<ListView<Photo>, ListCell<Photo>>(){
			@Override
			public ListCell<Photo> call(ListView<Photo> p){
				return new EachPhoto();
			}
			
		});
		
		photosList.setItems(obsList);
		
	    if(!obsList.isEmpty()) {
	    		photosList.getSelectionModel().select(0); //select first photo of album
	    }
		
	}
	
	
	/**
	  * Populates the list of Photos for the selected Album
	  */
	public void populatePhotosList(){
		
		photosInAlbum.clear(); //refresh the list
		
		for(int i = 0; i < Photos.manager.getCurrentUser().getcurrentAlbum().getPhotos().size(); i++) {
			photosInAlbum.add(Photos.manager.getCurrentUser().getcurrentAlbum().getPhotos().get(i));
		}
		
		if(!(photosInAlbum.isEmpty())) {
			daterange.setText(Photos.manager.getCurrentUser().getcurrentAlbum().getMinDate().toString() + " - " + Photos.manager.getCurrentUser().getcurrentAlbum().getMaxDate().toString());
			Image img = new Image(new File(Photos.manager.getCurrentUser().getcurrentAlbum().getPhotos().get(0).getPhotoPath()).toURI().toString());
			albumimg.setImage(img);
			numphotos.setText(Photos.manager.getCurrentUser().getcurrentAlbum().getPhotos().size() + " photos");
		}
		else { //no photos in album, so no thumbnail pic available
			Image no_thumb = new Image(new File("/Users/deepkotadia/Desktop/Fall 2017/Software Methodology (CS 213)/photoscs213/Photos43/stockphotos/no_thumb.jpg").toURI().toString());
			albumimg.setImage(no_thumb);
			numphotos.setText(Photos.manager.getCurrentUser().getcurrentAlbum().getPhotos().size() + " photos");
			daterange.setText("-");
		}
		
	}
	
	public void refreshAlbumInfo() {
		if(!(photosInAlbum.isEmpty())) {
			daterange.setText(Photos.manager.getCurrentUser().getcurrentAlbum().getMinDate().toString() + " - " + Photos.manager.getCurrentUser().getcurrentAlbum().getMaxDate().toString());
			Image img = new Image(new File(Photos.manager.getCurrentUser().getcurrentAlbum().getPhotos().get(0).getPhotoPath()).toURI().toString());
			albumimg.setImage(img);
			numphotos.setText(Photos.manager.getCurrentUser().getcurrentAlbum().getPhotos().size() + " photos");
		}
		else { //no photos in album, so no thumbnail pic available
			Image no_thumb = new Image(new File("/Users/deepkotadia/Desktop/Fall 2017/Software Methodology (CS 213)/photoscs213/Photos43/stockphotos/no_thumb.jpg").toURI().toString());
			albumimg.setImage(no_thumb);
			numphotos.setText(Photos.manager.getCurrentUser().getcurrentAlbum().getPhotos().size() + " photos");
			daterange.setText("-");
		}
	}
	
	
	/**
	 * This class is overriding ListCell which is a cell in ListView to display all photos inside selected album
	 * 
	 * @author Chinmoyi Bhushan
	 * @author Deep Kotadia
	 *
	 */
	private class EachPhoto extends ListCell<Photo>{
		AnchorPane anchor = new AnchorPane();
		StackPane stackpane = new StackPane();
		
		ImageView imageView = new ImageView();
		Label date = new Label();
		Label caption = new Label();
		Label tags = new Label();
		
		public EachPhoto() {
			super();
			
			imageView.setFitWidth(90.0);
			imageView.setFitHeight(90.0);
			imageView.setPreserveRatio(true);
			
			StackPane.setAlignment(imageView, Pos.TOP_LEFT);
			
			stackpane.getChildren().add(imageView);
			
			stackpane.setPrefHeight(110.0);
			stackpane.setPrefWidth(90.0);
			
			AnchorPane.setLeftAnchor(stackpane, 0.0);
			
			AnchorPane.setLeftAnchor(caption, 100.0);
			AnchorPane.setTopAnchor(caption, 0.0);
			
			AnchorPane.setLeftAnchor(date, 100.0);
			AnchorPane.setTopAnchor(date, 15.0);
		
			AnchorPane.setLeftAnchor(tags, 100.0);
			AnchorPane.setTopAnchor(tags, 45.0);
			
			anchor.getChildren().addAll(stackpane, caption, date, tags);
			
			anchor.setPrefHeight(110.0);
			
			caption.setMaxWidth(250.0);
			
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
				caption.setText("");
				
			}
			
			else{
				Image img = new Image(new File(photo.getPhotoPath()).toURI().toString());
				imageView.setImage(img);
				caption.setText("Caption: " + photo.getCaption());
				//date.setText(photo.getDateAdded());
				//tags.setText("Tags for this Photo: " + photo.getTags());
			}
			
		}
	}
	
	
	/**
	  * 
	  * Prompts User to add new Photo from computer, adds the filepath to the main arraylist in PhotoAlbumManager
	  * Displays the photo from the path inside the album in SingleAlbum page
	  * 
	  */
	public void handleAddPhoto(ActionEvent event) throws IOException {
		
		String filepath = "";
		FileChooser fileChooser = new FileChooser();
		
		//Set extension filter
		FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
		FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
		FileChooser.ExtensionFilter extFilterGIF = new FileChooser.ExtensionFilter("GIF files (*.gif)", "*.GIF");
		fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG, extFilterGIF);
		
		//Show open file dialog
		File imgfile = fileChooser.showOpenDialog(null);
		if(imgfile == null) return;
		
		//Get the absolute path of image file
		filepath = imgfile.getAbsolutePath();
		Photo newPhoto = new Photo(filepath);
		
		//Add photo to the current album
		Photos.manager.getCurrentUser().getcurrentAlbum().addPhoto(filepath);
		
		photosInAlbum.add(newPhoto);
		this.refreshAlbumInfo();
		
//		populatePhotosList();
		
		photosList.refresh();
		obsList=FXCollections.observableArrayList(photosInAlbum);
		   /*Render in proper UI*/
		   photosList.setCellFactory(new Callback<ListView<Photo>, ListCell<Photo>>(){
				@Override
				public ListCell<Photo> call(ListView<Photo> p){
					return new EachPhoto();
				}
				
			});
		   photosList.setItems(obsList);
		   
		   PhotoAlbumManager.serialize(Photos.manager);
		   			   
		   //if this is first photo added in this album, then select it
		   if (obsList.size() == 1) {
			   photosList.getSelectionModel().select(0);
		   }
		   else{
			   int index = 0;
			   for(Photo s: photosInAlbum){
				   if(s.getPhotoPath().equals(filepath)){
					  photosList.getSelectionModel().select(index);
					  break;
				   }
				   index++;
			   }
		   }	
		
	}
	
	
	/**
	  * 
	  * Let's user open the photo in an enlarged view in a new window
	  */
	public void handleViewPhoto(ActionEvent event) throws IOException {
		
		Parent parent;
		
		int photoindex = photosList.getSelectionModel().getSelectedIndex();    
		
		Photo photo = Photos.manager.getCurrentUser().getcurrentAlbum().getPhotos().get(photoindex);
		
		//set as current photo since this is about to open in enlarged view
		Photos.manager.getCurrentUser().getcurrentAlbum().setcurrentPhoto(photo); //this needs to be re set every time prev and next btns are clicked on slideshow page
		
		/*Load the Slideshow Page with selected Photo*/
		FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/Slideshow.fxml"));
		parent = (Parent)loader.load();
		SlideshowControl ctrl = loader.getController();
		Scene scene = new Scene(parent);
		
		Stage app_stage = (Stage) root.getScene().getWindow();	
	                
		ctrl.start(app_stage);
	             
	    app_stage.setScene(scene);
	    app_stage.show();
		
	}
	
	
	/**
	  * 
	  * Allows user to delete a selected photo from currently open Album
	  */
	public void handleDeletePhoto(ActionEvent event) throws IOException {
		
		int photoindex = photosList.getSelectionModel().getSelectedIndex();
		   
		   Alert alert = new Alert(AlertType.CONFIRMATION);
		   alert.setTitle("Confirm Delete");
		   alert.setHeaderText(null);
		   alert.setContentText("Are you sure you want to delete this Photo? (There's no going back!)");

		   Optional<ButtonType> result = alert.showAndWait();
		   if (result.get() == ButtonType.OK) { // ... user chose OK
			   
			   Photos.manager.getCurrentUser().getcurrentAlbum().removePhoto(photoindex);
			   populatePhotosList();
			   photosList.refresh();
			   obsList=FXCollections.observableArrayList(photosInAlbum);
			   /*Render in proper UI*/
			   photosList.setCellFactory(new Callback<ListView<Photo>, ListCell<Photo>>(){
					@Override
					public ListCell<Photo> call(ListView<Photo> p){
						return new EachPhoto();
					}
					
				});
			   photosList.setItems(obsList);
			   
			   PhotoAlbumManager.serialize(Photos.manager);
			   
			   if(photosInAlbum.size() == 0) {
					deletePhoto.setVisible(false);
		       }
			   else {
				   int lastalbumindex = photosInAlbum.size();
				   if(photosInAlbum.size() == 1) { //only one photo remaining in list, so select it
					   photosList.getSelectionModel().select(0);
				   }
				   else if(photoindex == lastalbumindex) { //deleted photo was last photo in the list, so select previous photo, previous photo is now last photo
					   photosList.getSelectionModel().select(lastalbumindex-1);
				   }
				   else { //not the last photo, so select next photo
					   photosList.getSelectionModel().select(photoindex);
				   }
			   }
			      
		   } else { // ... user chose CANCEL or closed the dialog
			   return;
		   }
		   return;
		
	}
	
	
	/**
	  * 
	  * Allows user to caption a selected photo from currently open Album
	  */
	public void handleCaptionPhoto(ActionEvent event) throws IOException {
		
		int photoindex = photosList.getSelectionModel().getSelectedIndex();
		
		TextInputDialog dialog = new TextInputDialog(Photos.manager.getCurrentUser().getcurrentAlbum().getPhotos().get(photoindex).getCaption());
		dialog.setTitle("Add/Change Caption");
		dialog.setHeaderText("What Caption do you want to add?");
		dialog.setContentText("Caption:");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
		    
			String caption = (String) result.get();
			
			Photos.manager.getCurrentUser().getcurrentAlbum().getPhotos().get(photoindex).setCaption(caption);
			populatePhotosList();
			photosList.refresh();
	        obsList=FXCollections.observableArrayList(photosInAlbum);
		   /*Render in proper UI*/
		   photosList.setCellFactory(new Callback<ListView<Photo>, ListCell<Photo>>(){
				@Override
				public ListCell<Photo> call(ListView<Photo> p){
					return new EachPhoto();
				}
					
			});
		   photosList.setItems(obsList);
			   
		   PhotoAlbumManager.serialize(Photos.manager);
			
		}
		return;
		
	}
	
	
	/**
	  * 
	  * Let's user move the selected photo from one album to another
	  */
	public void handleMovePhoto(ActionEvent event) throws IOException {
		
		int photoindex = photosList.getSelectionModel().getSelectedIndex(); 
		
		Dialog<String> dialog = new Dialog<>();
		   dialog.setTitle("Move Photo to Another Album");
		   dialog.setHeaderText("Type Name of Album you want to Move your Photo to");
		   dialog.setResizable(true);
		   
		   Label albumnameLabel = new Label("Destination Album Name: ");
		   TextField albumnameTextField = new TextField();
		   
		   GridPane grid = new GridPane();
		   grid.add(albumnameLabel, 1, 1);
		   grid.add(albumnameTextField, 2, 1);
		   
		   dialog.getDialogPane().setContent(grid);
		   
		   ButtonType buttonTypeOk = new ButtonType("Move", ButtonData.OK_DONE);
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
		   
		   //wait for response from Move button
		   Optional<String> result = dialog.showAndWait();
		   
		   // if user presses Move, add the selected photo to the new requested album and remove from current album
		   if (result.isPresent()) {
			   String newAlbumName = result.get(); //store result
			   
			   //get index of album where selected photo needs to be moved
			   List<Album> albums = Photos.manager.getCurrentUser().getAlbums();
			   int albumindex = 0;
			   for(int i = 0; i < albums.size(); i++) {
				   if(albums.get(i).getAlbumName().equals(newAlbumName)) { //album found!
					   albumindex = i;
					   break;
				   }
			   }
			   
			   //get path of selected photo (to be moved)
			   String photopath = Photos.manager.getCurrentUser().getcurrentAlbum().getPhotos().get(photoindex).getPhotoPath();
			   
			   //clone the photo in new album
			   Photos.manager.getCurrentUser().getAlbums().get(albumindex).addPhoto(photopath);
			   
			   //remove photo from current album
			   Photos.manager.getCurrentUser().getcurrentAlbum().removePhoto(photoindex);
			   
			   populatePhotosList();
			   photosList.refresh();
			   obsList=FXCollections.observableArrayList(photosInAlbum);
			   /*Render in proper UI*/
			   photosList.setCellFactory(new Callback<ListView<Photo>, ListCell<Photo>>(){
					@Override
					public ListCell<Photo> call(ListView<Photo> p){
						return new EachPhoto();
					}
					
				});
			   photosList.setItems(obsList);
			   
			   PhotoAlbumManager.serialize(Photos.manager);
			   
			   if(photosInAlbum.size() == 0) {
					deletePhoto.setVisible(false);
		       }
			   else {
				   int lastalbumindex = photosInAlbum.size();
				   if(photosInAlbum.size() == 1) { //only one photo remaining in list, so select it
					   photosList.getSelectionModel().select(0);
				   }
				   else if(photoindex == lastalbumindex) { //removed photo was last photo in the list, so select previous photo, previous photo is now last photo
					   photosList.getSelectionModel().select(lastalbumindex-1);
				   }
				   else { //not the last photo, so select next photo
					   photosList.getSelectionModel().select(photoindex);
				   }
			   }
		   }
		
	}
	
	
	
	/**
	  * 
	  * Let's user copy the selected photo into another album
	  */
	public void handleCopyPhoto(ActionEvent event) throws IOException {
		
		int photoindex = photosList.getSelectionModel().getSelectedIndex(); 
		
		Dialog<String> dialog = new Dialog<>();
		   dialog.setTitle("Copy Photo to Another Album");
		   dialog.setHeaderText("Type Name of Album you want to Copy your Photo to");
		   dialog.setResizable(true);
		   
		   Label albumnameLabel = new Label("Album Name: ");
		   TextField albumnameTextField = new TextField();
		   
		   GridPane grid = new GridPane();
		   grid.add(albumnameLabel, 1, 1);
		   grid.add(albumnameTextField, 2, 1);
		   
		   dialog.getDialogPane().setContent(grid);
		   
		   ButtonType buttonTypeOk = new ButtonType("Copy", ButtonData.OK_DONE);
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
		   
		   //wait for response from Copy button
		   Optional<String> result = dialog.showAndWait();
		   
		   // if user presses Copy, add the selected photo to the new requested album
		   if (result.isPresent()) {
			   String newAlbumName = result.get(); //store result
			   
			   //get index of album where selected photo needs to be copied
			   List<Album> albums = Photos.manager.getCurrentUser().getAlbums();
			   int albumindex = 0;
			   for(int i = 0; i < albums.size(); i++) {
				   if(albums.get(i).getAlbumName().equals(newAlbumName)) { //album found
					   albumindex = i;
					   break;
				   }
			   }
			   
			   //get path of selected photo (to be copied)
			   String photopath = Photos.manager.getCurrentUser().getcurrentAlbum().getPhotos().get(photoindex).getPhotoPath();
			   
			   //clone the photo in new album
			   Photos.manager.getCurrentUser().getAlbums().get(albumindex).addPhoto(photopath);
			   
			   populatePhotosList();
			   photosList.refresh();
			   obsList=FXCollections.observableArrayList(photosInAlbum);
			   /*Render in proper UI*/
			   photosList.setCellFactory(new Callback<ListView<Photo>, ListCell<Photo>>(){
					@Override
					public ListCell<Photo> call(ListView<Photo> p){
						return new EachPhoto();
					}
					
				});
			   photosList.setItems(obsList);
			   
			   PhotoAlbumManager.serialize(Photos.manager);
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
			   
			   List<Album> albums = Photos.manager.getCurrentUser().getAlbums();
			   for(int i = 0; i < albums.size(); i++) {
				   if(albums.get(i).getAlbumName().equals(albumname)) { //album exists
					   return null;
				   }
			   }
			   return "There is no Album called " + albumname + ". Please enter a valid Album Name.";
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
