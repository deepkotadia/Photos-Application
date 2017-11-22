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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Album;
import model.Photo;
import model.PhotoAlbumManager;
import model.Tag;
import model.User;

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
	Text caption, date, numphotos;
	
	@FXML
	MenuButton tagOptions;
	
	@FXML
	MenuItem AddTag, DeleteTag;
	
	
	private static List<Photo> photosInAlbum = new ArrayList<Photo>();
	
	private static Album currAlbum = null;
	
	private static int currindex;
	
	
	public void start(Stage app_stage) {
		
		app_stage.setTitle(Photos.manager.getCurrentUser().getcurrentAlbum().getAlbumName() + " Album Slideshow");
		caption.setText(Photos.manager.getCurrentUser().getcurrentAlbum().getcurrentPhoto().getCaption());
		date.setText(Photos.manager.getCurrentUser().getcurrentAlbum().getcurrentPhoto().getDateAdded().toString());
		numphotos.setText(Photos.manager.getCurrentUser().getcurrentAlbum().getPhotos().size() + " photos");
		
		Image currimg = new Image(new File(Photos.manager.getCurrentUser().getcurrentAlbum().getcurrentPhoto().getPhotoPath()).toURI().toString());
		imgslide.setImage(currimg);
		
		populateTagsTextArea();
		
		/*if(SingleAlbumControl.photo_currindex == 0) {
			prev.setVisible(false);
		}
		else if(SingleAlbumControl.photo_currindex == Photos.manager.getCurrentUser().getcurrentAlbum().getPhotos().size()-1) {
			next.setVisible(false);
		}*/
		
	}
	
	
	public void populateTagsTextArea() {
		
		String tagsdisplay = "";
		
		List<Tag> listoftags = Photos.manager.getCurrentUser().getcurrentAlbum().getcurrentPhoto().getTags();
		for(int i = 0; i < listoftags.size(); i++) {
			tagsdisplay = tagsdisplay + "Tag: " + listoftags.get(i).key + "     Value: " + listoftags.get(i).value + "\n";
		}
		
		tags.setText(tagsdisplay);
	}
	
	
	/**
	  * 
	  * Let's user add a tag to photo currently in enlarged view
	  */
	public void handleAddTag(ActionEvent event) throws IOException {
		
		   Dialog<Tag> dialog = new Dialog<>();
		   dialog.setTitle("Add a New Tag");
		   dialog.setHeaderText("Add Tag Key and Value");
		   dialog.setResizable(true);
		   
		   Label keyLabel = new Label("Tag Key: ");
		   Label valueLabel = new Label("Tag Value: ");
		   TextField keyTextField = new TextField();
		   TextField valueTextField = new TextField();
		   
		   GridPane grid = new GridPane();
		   grid.add(keyLabel, 1, 1);
		   grid.add(keyTextField, 2, 1);
		   grid.add(valueLabel, 1, 2);
		   grid.add(valueTextField, 2, 2);
		   
		   dialog.getDialogPane().setContent(grid);
		   
		   ButtonType buttonTypeOk = new ButtonType("Add", ButtonData.OK_DONE);
		   dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
		   
		   dialog.setResultConverter(new Callback<ButtonType, Tag>() {
			   @Override
			   public Tag call(ButtonType b) {
				   if (b == buttonTypeOk) {
					   
					   String error = checkFieldsforAdd(keyTextField.getText(),valueTextField.getText());
					   
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
											   
					   return new Tag(keyTextField.getText().trim(),valueTextField.getText().trim());
				   }
				   return null;
			   }
			
		   });
		   
		   //wait for response from add button
		   Optional<Tag> result = dialog.showAndWait();
		   
		   // if user presses Add, add the user to the overall list
		   if (result.isPresent()) {
			   Tag newtag = (Tag) result.get(); //store result
			   
			   Photos.manager.getCurrentUser().getcurrentAlbum().getcurrentPhoto().addTag(newtag.key, newtag.value);
			   populateTagsTextArea();
			   //listView.refresh();
			   //obsList=FXCollections.observableArrayList(nameandusername);
			   //listView.setItems(obsList);
			   PhotoAlbumManager.serialize(Photos.manager);	   
		   }
		
	}
	
	
	/**
	    * 
	    * Check the fields, return null if no errors found
	    * @return the error message in string format, null if no errors
	    */
	   private String checkFieldsforAdd(String key, String value) {
		   if (key.trim().isEmpty())
			   return "Tag is a required field.";
		   else if (value.trim().isEmpty())
			   return "Value is a required field.";
		   
		   if (Photos.manager.getCurrentUser().getcurrentAlbum().getcurrentPhoto().doesTagExist(key, value))
			   return "This tag and value combination already exists for this photo, please try another combination.";
		   else	   
		   return null;
	}
	
	
	/**
	  * 
	  * Let's user delete a tag associated with photo currently in enlarged view
	  */
	public void handleDeleteTag(ActionEvent event) throws IOException {
		
		   Dialog<Tag> dialog = new Dialog<>();
		   dialog.setTitle("Delete an Existing Tag for this Photo");
		   dialog.setHeaderText("Enter Tag Key and Value to Delete");
		   dialog.setResizable(true);
		   
		   Label keyLabel = new Label("Tag Key: ");
		   Label valueLabel = new Label("Tag Value: ");
		   TextField keyTextField = new TextField();
		   TextField valueTextField = new TextField();
		   
		   GridPane grid = new GridPane();
		   grid.add(keyLabel, 1, 1);
		   grid.add(keyTextField, 2, 1);
		   grid.add(valueLabel, 1, 2);
		   grid.add(valueTextField, 2, 2);
		   
		   dialog.getDialogPane().setContent(grid);
		   
		   ButtonType buttonTypeOk = new ButtonType("Delete", ButtonData.OK_DONE);
		   dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
		   
		   dialog.setResultConverter(new Callback<ButtonType, Tag>() {
			   @Override
			   public Tag call(ButtonType b) {
				   if (b == buttonTypeOk) {
					   
					   String error = checkFieldsforDelete(keyTextField.getText(),valueTextField.getText());
					   
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
											   
					   return new Tag(keyTextField.getText().trim(),valueTextField.getText().trim());
				   }
				   return null;
			   }
			
		   });
		   
		   //wait for response from add button
		   Optional<Tag> result = dialog.showAndWait();
		   
		   // if user presses Add, add the user to the overall list
		   if (result.isPresent()) {
			   Tag newtag = (Tag) result.get(); //store result
			   
			   Photos.manager.getCurrentUser().getcurrentAlbum().getcurrentPhoto().removeTag(newtag.key, newtag.value);
			   populateTagsTextArea();
			   //listView.refresh();
			   //obsList=FXCollections.observableArrayList(nameandusername);
			   //listView.setItems(obsList);
			   PhotoAlbumManager.serialize(Photos.manager);   
		   }
		
	}
	
	
	/**
	    * 
	    * Check the fields, return null if no errors found
	    * @return the error message in string format, null if no errors
	    */
	   private String checkFieldsforDelete(String key, String value) {
		   if (key.trim().isEmpty())
			   return "Tag is a required field.";
		   else if (value.trim().isEmpty())
			   return "Value is a required field.";
		   
		   if(!(Photos.manager.getCurrentUser().getcurrentAlbum().getcurrentPhoto().doesTagExist(key, value)))
			   return "This tag and value combination doesn't exist for this photo, please try another combination that exists.";
		   else
		   return null;
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
		
		if(currindex == -1) { //was at the very first pic, cannot go prev anymore after this, set currindex to last pic in album
			currindex = albumsize-1;
			//return;
		}
		//else if(currindex < albumsize-1) { //not at the last pic, can go next
		//	next.setVisible(true);
			//return;
		//}
		
		//set the previous pic as the current photo
		Photo newPhoto = Photos.manager.getCurrentUser().getcurrentAlbum().getPhotos().get(currindex);
		Photos.manager.getCurrentUser().getcurrentAlbum().setcurrentPhoto(newPhoto);
		
		populateTagsTextArea();
		
		Image newimg = new Image(new File(Photos.manager.getCurrentUser().getcurrentAlbum().getPhotos().get(currindex).getPhotoPath()).toURI().toString());
		imgslide.setImage(newimg);
		
		caption.setText(Photos.manager.getCurrentUser().getcurrentAlbum().getcurrentPhoto().getCaption());
		date.setText(Photos.manager.getCurrentUser().getcurrentAlbum().getcurrentPhoto().getDateAdded().toString());
		
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
		
		if(currindex == albumsize) { //was at the very last pic, cannot go next anymore after this, so set currindex to first image
			currindex = 0;
			//return;
		}
		//else if(currindex > 0) { //not at the first pic, can go prev
		//	prev.setVisible(true);
			//return;
		//}
		
		//set the next pic as the current photo
		Photo newPhoto = Photos.manager.getCurrentUser().getcurrentAlbum().getPhotos().get(currindex);
		Photos.manager.getCurrentUser().getcurrentAlbum().setcurrentPhoto(newPhoto);
		
		populateTagsTextArea();
		
		Image newimg = new Image(new File(Photos.manager.getCurrentUser().getcurrentAlbum().getPhotos().get(currindex).getPhotoPath()).toURI().toString());
		imgslide.setImage(newimg);
		
		caption.setText(Photos.manager.getCurrentUser().getcurrentAlbum().getcurrentPhoto().getCaption());
		date.setText(Photos.manager.getCurrentUser().getcurrentAlbum().getcurrentPhoto().getDateAdded().toString());
		
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
