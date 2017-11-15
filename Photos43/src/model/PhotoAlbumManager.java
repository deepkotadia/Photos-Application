package model;

import java.util.ArrayList;
import java.util.List;

public class PhotoAlbumManager {
	private User currentUser;
	private boolean isUserLoggedIn;
	private List<User> users;
		
	public PhotoAlbumManager() {
		users = new ArrayList<User>();
		users.add(new User("admin", "Admin", true));
		
		this.currentUser = null;
		this.isUserLoggedIn = false;	
	}
	
	public void addUser(User user) {
		//TODO
		
	}
	
	public void removeUser(User user) {
		//TODO
		
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

}
