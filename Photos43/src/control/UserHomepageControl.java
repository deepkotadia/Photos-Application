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
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Album;

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
		// TODO Auto-generated method stub
		welcomeText.setText("Welcome, " + Photos.manager.getCurrentUser().getUsername() + "!");
		//populateAlbumList();
		
		obsList = FXCollections.observableArrayList(albumsOfUser);               
	    albumsList.setItems(obsList);
	    
	    if(!obsList.isEmpty()) {
	    		albumsList.getSelectionModel().select(0); //select first album of user
	    }
		
	}
	
	/**
	  * Populates the list of Albums for the user
	  */
	//public static void populateAlbumList(){
		
	//	albumsOfUser.clear(); //refresh the list
		
		//for(int i = 0; i < Photos.manager.getusers().size(); i++) {
			//albumsOfUser.add(Photos.manager.getusers().get(i).getAlbums());
		//}	
	//}
	
	public void handleLogout(ActionEvent event) {
		try {
			logoutfnc(event); //logout function from LogoutInterface
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
