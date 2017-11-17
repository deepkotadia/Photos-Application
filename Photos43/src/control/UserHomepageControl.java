/**
 * 
 */
package control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
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

	public void start(Stage app_stage) {
		// TODO Auto-generated method stub
		
	}
	
	public void handleLogout(ActionEvent event) {
		try {
			logoutfnc(event); //logout function from LogoutInterface
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
