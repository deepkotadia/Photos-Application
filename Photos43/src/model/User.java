package model;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import application.Photos;

/**
 * The user class 
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
	 * constructor for the user class 
	 * @param username     user name 
	 * @param name    fullname
	 * @param isAdmin  check for admin
	 * 
	 * @return  
	 */
	public User(String username, String name, boolean isAdmin) {
		this.username = username;
		this.name = name;
		this.isAdmin = isAdmin;
		albums = new ArrayList<Album>();		
	}
	/**
	 * function to add an album
	 * @param albumName   name of album
	 */
	public void addAlbum(String albumName) {
		Album newAlbum = new Album(albumName);
		albums.add(newAlbum);		
	}
	
	/**
	 * function to add an album by passing an album object
	 * @param album   Album object 
	 */
	public void addAlbum(Album album) {
		albums.add(album);
	}
	
	/**
	 * remove an album from a user's list
	 * @param index    index of album to be removed 
	 */
	public void removeAlbum(int index) {
		albums.remove(index);
	}
	
	/**
	 * function to retrieve photos that contain the tag(s) the user searches for 
	 * @param tags     list of tags for that photo 
	 * @return List
	 */
	public List<Photo> getPhotosWithTag(List<Tag> tags){
		Set<Photo> photosWithTag = new HashSet<Photo>();
		
		List<Photo> listOfPhotosWithTag = new ArrayList<Photo>();
		
		for(Tag tag : tags) {
			for(Album userAlbum : albums){
				for(Photo photo : userAlbum.getPhotos()) {
					if(photo.doesTagExist(tag.key, tag.value)) {
						photosWithTag.add(photo);
					}
				}
			}
		}
		
		listOfPhotosWithTag.addAll(photosWithTag);
		return listOfPhotosWithTag;
	}
	
	/**
	 * function to retrieve photos that are in the date range(Dates are inclusive) the user searches for 
	 * @param startYear         year of start
	 * @param startMonth        month of start date
	 * @param startDay          day of start date
	 * @param endYear           year of end date
	 * @param endMonth          month of end date
	 * @param endDay            day of the end date 
	 * 
	 * @return List
	 */
	public List<Photo> getPhotosInDateRange(int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay){
		Calendar calUserStartDate = Calendar.getInstance();
		calUserStartDate.set(startYear, startMonth, startDay);
		
		Calendar calUserEndDate = Calendar.getInstance();
		calUserEndDate.set(endYear, endMonth, endDay);
		
		List<Photo> photosInDateRange = new ArrayList<Photo>();
		
		for(Album userAlbum : albums) {
			for(Photo photo : userAlbum.getPhotos()) {
				Date photoDate = photo.getDateAdded();
				Calendar calPhotoDate = Calendar.getInstance();
				calPhotoDate.setTime(photoDate);

				int photoYear = calPhotoDate.get(Calendar.YEAR);
				int photoMonth = calPhotoDate.get(Calendar.MONTH) + 1;
				int photoDay = calPhotoDate.get(Calendar.DAY_OF_MONTH);

				Calendar calPhotoDate2 = Calendar.getInstance();
				calPhotoDate2.set(photoYear, photoMonth, photoDay);
				
				if((calPhotoDate2.compareTo(calUserStartDate) > 0 && calPhotoDate2.compareTo(calUserEndDate) < 0) 
						|| (calPhotoDate2.equals(calUserStartDate)) || calPhotoDate2.equals(calUserEndDate)) {
					photosInDateRange.add(photo);
				}
			}
		}
		
		return photosInDateRange;
	}
	
	@Override
	public boolean equals( Object obj) {
		if(obj == this)
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
/**
 * getter for username of the user
 * @return String 
 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * getter for name of the user 
	 * @return String
	 */

	public String getName() {
		return name;
	}

	/**
	 * setter for setting the name of the user
	 * @param name    setting name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 
	 * @return Album
	 */
	public Album getcurrentAlbum() {
		return currentAlbum;
	}
	
	/**
	 * setter for the current album of the user
	 * @param currentAlbum       setting current album 
	 */
	public void setcurrentAlbum(Album currentAlbum) {
		this.currentAlbum = currentAlbum;
	}
	
	/**
	 * getter for a list of albums of the user 
	 * @return List
	 */
	public List<Album> getAlbums(){
		return albums;
	}
	

}
