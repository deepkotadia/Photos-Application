/**
 * 
 */
package control;

import java.io.IOException;

import application.Photos;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author Deep Kotadia
 * @author Chinmoyi Chushan
 *
 */
public class SlideshowControl implements LogoutInterface {
	
	@FXML 
	Button backBtn, logoutBtn, prev, next;
	
	@FXML
	ImageView imgslide;
	
	@FXML
	TextArea tags;
	
	@FXML
	Text albumname, numphotos;
	
	@FXML
	MenuButton tagOptions;
	
	@FXML
	MenuItem AddTag, DeleteTag;
	
	
	public void start(Stage app_stage) {
		
		albumname.setText(Photos.manager.getCurrentUser().getcurrentAlbum().getAlbumName());
		numphotos.setText(Photos.manager.getCurrentUser().getcurrentAlbum().getPhotos().size() + " photos");
		
	}
	
	
	/**
	  * 
	  * Let's user add a tag to photo currently in enlarged view
	  */
	public void handleAddTag(ActionEvent event) throws IOException {
		
	}
	
	
	/**
	  * 
	  * Let's user delete a tag associated with photo currently in enlarged view
	  */
	public void handleDeleteTag(ActionEvent event) throws IOException {
		
	}
	
	
	/**
	  * 
	  * Let's user see the previous photo in album in enlarged view with its tags
	  */
	public void handlePrev(ActionEvent event) throws IOException {
		
	}
	
	
	/**
	  * 
	  * Let's user see the next photo in album in enlarged view with its tags
	  */
	public void handleNext(ActionEvent event) throws IOException {
		
	}

	
	/**
	  * 
	  * Let's user go back to the Album's page (showing list of Photos inside album)
	  */
	public void handleBack(ActionEvent event) throws IOException {
		
		Parent parent;
		
		FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/SingleAlbum.fxml"));
		parent = (Parent)loader.load();
		SingleAlbumControl ctrl = loader.getController();
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
