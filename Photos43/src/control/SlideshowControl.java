/**
 * 
 */
package control;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Album;
import model.Photo;

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
	
	private static List<Photo> photosInAlbum = new ArrayList<Photo>();
	
	private static Album currAlbum = null;
	
	private static int currindex;
	
	
	public void start(Stage app_stage) {
		
		app_stage.setTitle(Photos.manager.getCurrentUser().getcurrentAlbum().getAlbumName() + " Album Slideshow");
		albumname.setText(Photos.manager.getCurrentUser().getcurrentAlbum().getAlbumName());
		numphotos.setText(Photos.manager.getCurrentUser().getcurrentAlbum().getPhotos().size() + " photos");
		
		Image currimg = new Image(new File(Photos.manager.getCurrentUser().getcurrentAlbum().getcurrentPhoto().getPhotoPath()).toURI().toString());
		imgslide.setImage(currimg);
		
		if(SingleAlbumControl.photo_currindex == 0) {
			prev.setVisible(false);
		}
		else if(SingleAlbumControl.photo_currindex == Photos.manager.getCurrentUser().getcurrentAlbum().getPhotos().size()-1) {
			next.setVisible(false);
		}
		
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
		
		//int currindex;
		
		photosInAlbum.clear();
		int albumsize = Photos.manager.getCurrentUser().getcurrentAlbum().getPhotos().size();
		
		for(int i = 0; i < albumsize; i++) {
			photosInAlbum.add(Photos.manager.getCurrentUser().getcurrentAlbum().getPhotos().get(i));
		}
		
		//photosInAlbum now consist of all the photos in current album
		//Photo currphoto = Photos.manager.getCurrentUser().getcurrentAlbum().getcurrentPhoto();
		
		if(currAlbum != Photos.manager.getCurrentUser().getcurrentAlbum()) { //different album, change of album
			currindex = SingleAlbumControl.photo_currindex;
			currAlbum = Photos.manager.getCurrentUser().getcurrentAlbum();
		}
		//else { //still the same album
			//go to previous photo
			currindex--;
		//}
		
		if(currindex == 0) { //at the very first pic, cannot go prev anymore after this
			prev.setVisible(false);
			//return;
		}
		else if(currindex < albumsize-1) { //not at the last pic, can go next
			next.setVisible(true);
			//return;
		}
		
		//set the previous pic as the current photo
		Photo newPhoto = Photos.manager.getCurrentUser().getcurrentAlbum().getPhotos().get(currindex);
		Photos.manager.getCurrentUser().getcurrentAlbum().setcurrentPhoto(newPhoto);
		
		Image newimg = new Image(new File(Photos.manager.getCurrentUser().getcurrentAlbum().getPhotos().get(currindex).getPhotoPath()).toURI().toString());
		imgslide.setImage(newimg);
		
	}
	
	
	/**
	  * 
	  * Let's user see the next photo in album in enlarged view with its tags
	  */
	public void handleNext(ActionEvent event) throws IOException {
		
		photosInAlbum.clear();
		int albumsize = Photos.manager.getCurrentUser().getcurrentAlbum().getPhotos().size();
		
		for(int i = 0; i < albumsize; i++) {
			photosInAlbum.add(Photos.manager.getCurrentUser().getcurrentAlbum().getPhotos().get(i));
		}
		
		//photosInAlbum now consist of all the photos in current album
		//Photo currphoto = Photos.manager.getCurrentUser().getcurrentAlbum().getcurrentPhoto();
		
		if(currAlbum != Photos.manager.getCurrentUser().getcurrentAlbum()) { //different album, change of album
			currindex = SingleAlbumControl.photo_currindex;
			currAlbum = Photos.manager.getCurrentUser().getcurrentAlbum();
		}
		//else { //still the same album
			//go to next photo
			currindex++;
		//}
		
		if(currindex == albumsize-1) { //at the very last pic, cannot go next anymore after this
			next.setVisible(false);
			//return;
		}
		else if(currindex > 0) { //not at the first pic, can go prev
			prev.setVisible(true);
			//return;
		}
		
		//set the previous pic as the current photo
		Photo newPhoto = Photos.manager.getCurrentUser().getcurrentAlbum().getPhotos().get(currindex);
		Photos.manager.getCurrentUser().getcurrentAlbum().setcurrentPhoto(newPhoto);
		
		Image newimg = new Image(new File(Photos.manager.getCurrentUser().getcurrentAlbum().getPhotos().get(currindex).getPhotoPath()).toURI().toString());
		imgslide.setImage(newimg);
		
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
