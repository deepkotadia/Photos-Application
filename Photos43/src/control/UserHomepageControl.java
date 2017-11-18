/**
 * 
 */
package control;

import java.util.ArrayList;
import java.util.List;

import application.Photos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Album;
import model.User;

/**
 * @author Deep Kotadia
 * @author Chinmoyi Bhushan
 *
 * This class controls the User's Home Page
 */

public class UserHomepageControl implements LogoutInterface {
	
	@FXML Button searchBtn, Logout;
	
	@FXML MenuButton AlbumOption;
	
	@FXML ListView<Album> albumsList;
	
	@FXML Text welcomeText;
	
	private ObservableList<Album> obsList;
	private static List<Album> albumsOfUser = new ArrayList<Album>();
	
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
	
	public void handleLogout(ActionEvent event) {
		try {
			logoutfnc(event); //logout function from LogoutInterface
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
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
				//numPhotosLabel.setText("Number of Photos: " + album.getCount());
			}
			
		}
	}

}
