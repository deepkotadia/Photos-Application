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
	
	@FXML Text daterange, numphotos, albumname;
	
	@FXML ImageView albumimg;
	
	@FXML MenuItem viewPhoto, deletePhoto;
	
	@FXML ListView<Photo> photosList;
	
	private ObservableList<Photo> obsList;
	private static List<Photo> photosInAlbum = new ArrayList<Photo>();
	
	
	public void start(Stage app_stage) {
		
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
	    		photosList.getSelectionModel().select(0); //select first album of user
	    }
		
	}
	
	
	/**
	  * Populates the list of Photos for the selected Album
	  */
	public static void populatePhotosList(){
		
		photosInAlbum.clear(); //refresh the list
		
		for(int i = 0; i < Photos.manager.getCurrentUser().getcurrentAlbum().getPhotos().size(); i++) {
			photosInAlbum.add(Photos.manager.getCurrentUser().getcurrentAlbum().getPhotos().get(i));
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
			
			//for (Album userAlbum: Photos.manager.getCurrentUser().getAlbums()) {
				//albumName.setText("Album name: " + userAlbum.getAlbumName());
			//}
		}
		
		@Override
		public void updateItem(Photo photo, boolean empty) {
			super.updateItem(photo, empty);
			
			setText(null);
			if(photo == null)
			{
				//imageView.setImage(null);
				caption.setText("");
				
			}
			//		setGraphic(null);
			else{//(photo != null){
				//imageView.setImage(photo.getPhoto());
				//caption.setText("Album name: " + photo.getCaption());
				//dateLabel.setText(photo.getDate());
				//tags.setText("Tags for this Photo: " + photo.getTags());
			}
			
		}
	}
	

}
