package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
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
	 * @param user - User to add to the list
	 */
	public void addUser(User user) {
		users.add(user);
	}
	
	/**
	 * removeUser
	 * Removes an existing user from the users arraylist
	 * 
	 * @param user - User to remove from the list
	 */
	public void removeUser(User user) {
		users.remove(user);
	}
	
	public void login(String userName) {
		int currentUserIndex = users.indexOf(new User(userName, "", false));
		this.setCurrentUser(users.get(currentUserIndex));
		this.setUserLoggedIn(true);		
	}
	
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
	  * @return the overall list of users for the application
	  */
	public List<User> getusers() {
		return users;
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
	  * @return the deserialized userdata with all user, albums, and photos info
	  */
	public static PhotoAlbumManager deserialize() throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile));
		PhotoAlbumManager userdata = (PhotoAlbumManager) ois.readObject();
		ois.close();
		return userdata;
	}
	

}
