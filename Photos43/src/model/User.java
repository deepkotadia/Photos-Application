package model;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
	
	public List<Photo> getPhotosWithTag(String key, String value){
		List<Photo> photosWithTag = new ArrayList<Photo>();
		
		for(Album userAlbum : albums){
			for(Photo photo : userAlbum.getPhotos()) {
				if(photo.doesTagExist(key, value)) {
					photosWithTag.add(photo);
				}
			}
		}
		
		return photosWithTag;
	}
	
	/**
	 * Dates are inclusive
	 * @param startDate
	 * @param endDate
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
				
				if(calPhotoDate2.compareTo(calUserStartDate) > 0 && calPhotoDate2.compareTo(calUserEndDate) < 0) {
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
