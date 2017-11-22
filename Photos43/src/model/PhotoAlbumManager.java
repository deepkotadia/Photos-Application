package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import application.Photos;

/**
 * The manager class that manages a user and the user's operations
 * @author Deep Kotadia
 * @author Chinmoyi Bhushan
 *
 */
public class PhotoAlbumManager implements Serializable {
	
	/* Serialization stuff */
	private static final long serialVersionUID = 1L;
	public static final String storeDir = "dat";
	public static final String storeFile = "users.dat";
	
	private User currentUser;
	private boolean isUserLoggedIn;
	private List<User> users;
	
	/**
	 * PhotoAlbumManager
	 * 
	 * Constructor that creates arraylist of users
	 * Also adds admin user by default
	 * 
	 * @return 
	 */
	public PhotoAlbumManager() {
		users = new ArrayList<User>();
		users.add(new User("admin", "Admin", true));
		
		this.currentUser = null;
		this.isUserLoggedIn = false;	
	}
	
	/**
	 * addUser
	 * Creates and adds a new user to the users arraylist
	 * 
	 * @param userName   username of the user
	 * @param name       fullname of the user 
	 */
	public void addUser(String userName,String name) {
		User newUser = new User(userName, name, false);
		users.add(newUser);	
	}
	
	/**
	 * removeUser
	 * Removes an existing user from the users arraylist
	 * 
	 * @param userName  username of user 
	 */
	public void removeUser(String userName) {
		User userToBeDeleted = new User(userName,"",false);
		users.remove(userToBeDeleted);	
	}
	
	/**
	 * 
	 * @param userName       user name
	 * @return true if the user exists in the list of users 
	 */
	public boolean login(String userName) {
		int currentUserIndex = users.indexOf(new User(userName, "", false));
		
		if(currentUserIndex == -1) {
			return false;
		}
		
		this.setCurrentUser(users.get(currentUserIndex));
		this.setUserLoggedIn(true);		
		return true;
	}
	
	/**
	 * function for setting that the current user has logged out.
	 */
	public void logout() {
		this.setCurrentUser(null);
		this.setUserLoggedIn(false);		
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public boolean isUserLoggedIn() {
		return isUserLoggedIn;
	}

	public void setUserLoggedIn(boolean userLoggedIn) {
		this.isUserLoggedIn = userLoggedIn;
	}
	
	/** 
	  * getusers
	  * Getter to get the overall list of Users for the application
	  * 
	  * @return List  the overall list of users for the application
	  */
	public List<User> getusers() {
		return users;
	}
	
	
	 /**
	   * doesUserExist
	   * Checks if a specific username is in the users list
	   * 
	   * @param username  the username to check for in the from the overall list of users
	   * @return boolean   return true if username is in the list, false if it is not    
	   */
	public boolean doesUserExist(String username) {
		
		for(User u : users) {
			if(u.getUsername().equals(username)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * gets the index of the current user from the list of users
	 * @return int
	 */
	public int getcurrentUserIndex() {
		int index = 0;
		for(User u : users) {
			if(u.getUsername().equals(Photos.manager.getCurrentUser().getUsername())) {
				return index;
			}
			index++;
		}
		return -1;
	}
	

	/** 
	  * serialize
	  * Serialize the userdata and write it in users.dat file
	  * 
	  * @param userdata - the userlist with all user, albums, and photos info to be serialized
	  */
	public static void serialize(PhotoAlbumManager userdata) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
		oos.writeObject(userdata);
		oos.close();
	}
	
	
	/** 
	  * deserialize
	  * Read the users info from users.dat file and deserialize it
	  * 
	  * @return   
	  */
	public static PhotoAlbumManager deserialize() throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile));
		PhotoAlbumManager userdata = (PhotoAlbumManager) ois.readObject();
		ois.close();
		return userdata;
	}
	

}
