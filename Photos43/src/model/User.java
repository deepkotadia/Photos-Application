package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Deep Kotadia
 * @author Chinmoyi Bhushan
 *
 */
public class User implements Serializable {
	
	private String username;
	private String name;
	private boolean isAdmin;
	private List<Album> albums;

	/**
	 * 
	 */
	public User(String username, String name, boolean isAdmin) {
		this.username = username;
		this.name = name;
		this.isAdmin = isAdmin;
		albums = new ArrayList<Album>();		
	}
	
	public void addAlbum(String albumName) {
		Album newAlbum = new Album(albumName);
		albums.add(newAlbum);		
	}
	
	public void removeAlbum(String albumName) {
		Album removeAlbum = new Album(albumName);
		albums.remove(removeAlbum);
	}
	
	@Override
	public boolean equals( Object obj) {
		if (obj == this)
			return true;
		
		if(!(obj instanceof User)){
			return false;			
		}
		
		User user = (User)obj;
		
		return user.getUsername().equals(this.getUsername());
	}
	
	@Override 
	public int hashCode() {
		int result = 11;
		return 17 * result + this.getUsername().hashCode();
	}

	public String getUsername() {
		return username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<Album> getAlbums(){
		return albums;
	}

}
