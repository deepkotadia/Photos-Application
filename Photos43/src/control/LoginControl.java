/**
 * 
 */
package control;
import model.PhotoAlbumManager;
import model.*;

import java.util.Date;
import java.util.List;


/**
 * @author Deep Kotadia
 * @author Chinmoyi Bhushan
 *
 */
public class LoginControl {

	/**
	 * 
	 */
	
	public static PhotoAlbumManager photoAlbumManager = new PhotoAlbumManager();
	
	public final String adminUserName = "admin";
	
	//TODO link to login button then pass username
	public void handleLoginButton(String userName) {
		if(userName.equals(adminUserName)) {
			//TODO admin page with a list of users and button to add user or delete user 
		}
		
		else if(photoAlbumManager.login(userName) ) {
			//TODO create the userhomepage 
		
			User currentUser = photoAlbumManager.getCurrentUser();
			List<Album> userAlbums = currentUser.getAlbums();
			
			for(Album album : userAlbums) {
				String nameOfAlbum = album.getAlbumName();
				Date dateCreated = album.getDateCreated();
				int totalPhotos = album.getPhotos().size();
				//TODO create a box for the album with the name and the date created	
			}	
		}
		
		else {
			//TODO create an alert box to request to be added by admin
		}
	}

}
