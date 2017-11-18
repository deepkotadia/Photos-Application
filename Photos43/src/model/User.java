package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import application.Photos;

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
	private Album currentAlbum;

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
	
	public void removeAlbum(int index) {
		albums.remove(index);
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
	
	public Album getcurrentAlbum() {
		return currentAlbum;
	}
	
	public void setcurrentAlbum(Album currentAlbum) {
		this.currentAlbum = currentAlbum;
	}
	
	public List<Album> getAlbums(){
		return albums;
	}
	

}
