/**
 * 
 */
package control;

import java.io.IOException;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.*;

/**
 * @author Deep Kotadia
 * @author Chinmoyi Bhushan
 *
 * This class controls the Admin's Home Page
 */
public class AdminHomepageControl {
	
	@FXML         
	   ListView<String> listView;      
	
	@FXML
		Button AddBtn, DeleteBtn, LogoutBtn;
	
	private static List<String> nameandusername = new ArrayList<String>();
	private static List<User> users = new ArrayList<User>();
	private PhotoAlbumManager ulist;
	private ObservableList<String> obsList;
	
	/**
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * 
	 */
	public void start(Stage MainStage) throws ClassNotFoundException, IOException {
		
		ulist = PhotoAlbumManager.deserialize();
		users = ulist.getusers();
		populatenameandusername();
		
		obsList = FXCollections.observableArrayList(nameandusername);               
	    listView.setItems(obsList);
	    
		
	}
	
	/**
	  * 
	  * Prompts Admin to add new User, adds it to the main arraylist
	  */
	protected void handleAddUser(ActionEvent event) throws IOException {
		   
		   Dialog<User> dialog = new Dialog<>();
		   dialog.setTitle("Create a New User");
		   dialog.setHeaderText("Add a New User");
		   dialog.setResizable(true);
		   
		   Label usernameLabel = new Label("Username: ");
		   Label nameLabel = new Label("User's Full Name: ");
		   TextField usernameTextField = new TextField();
		   TextField nameTextField = new TextField();
		   
		   GridPane grid = new GridPane();
		   grid.add(usernameLabel, 1, 1);
		   grid.add(usernameTextField, 2, 1);
		   grid.add(nameLabel, 1, 2);
		   grid.add(nameTextField, 2, 2);
		   
		   dialog.getDialogPane().setContent(grid);
		   
		   ButtonType buttonTypeOk = new ButtonType("Add", ButtonData.OK_DONE);
		   dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
		   
		   dialog.setResultConverter(new Callback<ButtonType, User>() {
			   @Override
			   public User call(ButtonType b) {
				   if (b == buttonTypeOk) {
					   
					   String error = checkFields(usernameTextField.getText(),nameTextField.getText());
					   
					   /*if (error != null) {
						   showError(error);
						   return null;
					   }*/
											   
					   return new User(usernameTextField.getText().trim(),nameTextField.getText().trim(), false);
				   }
				   return null;
			   }
			
		   });
		   
		   //wait for response from add button
		   Optional<User> result = dialog.showAndWait();
		   
		   // if user presses Add, add the user to the overall list
		   if (result.isPresent()) {
			   User tempUser = (User) result.get(); //store result
			   //ulist.addUserToList(tempUser);
			   obsList.add("Name: " + tempUser.getName() + ", " + "UserName: " + tempUser.getUsername());
			   users.add(tempUser);
			   PhotoAlbumManager.serialize(ulist);
			   			   
			   //if this is first user added, then select it
			   if (obsList.size() == 1) {
				   listView.getSelectionModel().select(0);
			   }
			   else
			   {
				   int index = 0;
				   for(User s: ulist.getusers())
				   {
					   
					   if(s == tempUser)
					   {
						  listView.getSelectionModel().select(index);
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
	   private String checkFields(String username, String name) {
		   if (username.trim().isEmpty())
			   return "Username is a required field.";
		   else if (name.trim().isEmpty())
			   return "Full Name is a required field.";
		   
		   if (ulist.doesUserExist(username))
			   return "This username is already taken, please try another username.";
		   else	   
		   return null;
	}
	   
	
	/**
	  * 
	  * Delete selected user from list
	  */
	protected void handleDeleteUser(ActionEvent event) {
		
	}
	
	
	public static void populatenameandusername(){
		
		for(int i = 0; i < users.size(); i++) {
			nameandusername.add("Name: " + users.get(i).getName() + ", " + "UserName: " + users.get(i).getUsername());
		}	
	}
	

}
