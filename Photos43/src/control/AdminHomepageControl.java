/**
 * 
 */
package control;

import java.io.IOException;
import java.util.*;

import application.Photos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
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
public class AdminHomepageControl implements LogoutInterface {
	
	@FXML         
	   ListView<String> listView;      
	
	@FXML
		Button AddBtn, DeleteBtn, LogoutBtn;
	
	private static List<String> nameandusername = new ArrayList<String>();
	//private static List<User> users = new ArrayList<User>();
	private static PhotoAlbumManager ulistmanager = Photos.manager;
	private ObservableList<String> obsList;
	
	/**
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * 
	 */
	public void start(Stage App_Stage) throws ClassNotFoundException, IOException {
		
		//ulist = PhotoAlbumManager.deserialize();
		//users = ulist.getusers();
		populatenameandusername();
		
		obsList = FXCollections.observableArrayList(nameandusername);               
	    listView.setItems(obsList);
	    
	    if(!obsList.isEmpty()) {
	    		listView.getSelectionModel().select(0); //select first user
	    }
	    
		
	}
	
	/**
	  * 
	  * Prompts Admin to add new User, adds it to the main arraylist
	  */
	public void handleAddUser(ActionEvent event) throws IOException {
		   
		   Dialog<User> dialog = new Dialog<>();
		   dialog.setTitle("Create a New User");
		   dialog.setHeaderText("Add Details of New User");
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
			   //obsList.add("Name: " + tempUser.getName() + ", " + "UserName: " + tempUser.getUsername());
			   
			   //users.add(tempUser);
			   
			   ulistmanager.addUser(tempUser.getUsername(), tempUser.getName());
			   populatenameandusername();
			   listView.refresh();
			   obsList=FXCollections.observableArrayList(nameandusername);
			   listView.setItems(obsList);
			   PhotoAlbumManager.serialize(ulistmanager);
			   			   
			   //if this is first user added, then select it
			   if (obsList.size() == 1) {
				   listView.getSelectionModel().select(0);
			   }
			   else
			   {
				   int index = 0;
				   for(User s: ulistmanager.getusers())
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
		   
		   if (ulistmanager.doesUserExist(username))
			   return "This username is already taken, please try another username.";
		   else	   
		   return null;
	}
	   
	
	/**
	  * 
	  * Delete selected user from list
	 * @throws IOException 
	  */
	public void handleDeleteUser(ActionEvent event) throws IOException {
		
		int userindex = listView.getSelectionModel().getSelectedIndex();
		   
		   Alert alert = new Alert(AlertType.CONFIRMATION);
		   alert.setTitle("Confirm Delete");
		   alert.setHeaderText(null);
		   alert.setContentText("Are you sure you want to delete this User?");

		   Optional<ButtonType> result = alert.showAndWait();
		   if (result.get() == ButtonType.OK) { // ... user chose OK
			   String userName = ulistmanager.getusers().get(userindex).getUsername();
			   //users.remove(userindex);
			   
			   ulistmanager.removeUser(userName);
			   populatenameandusername();
			   listView.refresh();
			   obsList=FXCollections.observableArrayList(nameandusername);
			   listView.setItems(obsList);
			   PhotoAlbumManager.serialize(ulistmanager);
			   
			   if(ulistmanager.getusers().size() == 0) {
					DeleteBtn.setVisible(false);
		       }
			   else {
				   int lastuserindex = ulistmanager.getusers().size();
				   if(ulistmanager.getusers().size() == 1) { //only one user remaining in list, so select it and display its details
					   listView.getSelectionModel().select(0);
				   }
				   else if(userindex == lastuserindex) { //deleted user was last user in the list, so select previous user, previous user is now last user
					   listView.getSelectionModel().select(lastuserindex-1);
				   }
				   else { //not the last user, so select next user
					   listView.getSelectionModel().select(userindex);
				   }
			   }
			      
		   } else { // ... user chose CANCEL or closed the dialog
			   return;
		   }
		   return;

	}
	
	
	public void handleLogout(ActionEvent event) {
		try {
			logoutfnc(event); //logout function from LogoutInterface
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	  * (re)Populates the nameandusername list to be displayed in the list to admin
	  */
	public static void populatenameandusername(){
		
		nameandusername.clear(); //refresh the list
		
		for(int i = 0; i < ulistmanager.getusers().size(); i++) {
			nameandusername.add("Name: " + ulistmanager.getusers().get(i).getName() + ", " + "UserName: " + ulistmanager.getusers().get(i).getUsername());
		}	
	}
	

}
