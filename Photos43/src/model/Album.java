package model;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The Album class
 * @author Deep Kotadia
 * @author Chinmoyi Bhushan
 *
 */
public class Album implements Serializable {
	
	private static final long serialVersionUID = 1501113523008844050L;
	private String albumName;
	private Date dateCreated;
	private List<Photo> photos;
	private Date minDate, maxDate;
	private Photo currentPhoto;

	/**
	 * constructor for Album
	 * @param albumName   name of the album 
	 * @return 
	 */
	public Album(String albumName) {
		this.albumName = albumName;
		this.dateCreated = new Date();
		this.minDate = null;
		this.maxDate = null;
		photos = new ArrayList<Photo>();
	}
	
	/**
	 * to add a photo to an album 
	 * @param photoPath  file path of the photo 
	 */
	public void addPhoto(String photoPath) {
		Photo newPhoto = new Photo(photoPath);
		photos.add(newPhoto);
		
		if(photos.size() == 1) {
			maxDate = newPhoto.getDateAdded();
			minDate = newPhoto.getDateAdded();
		}
		
		else if(newPhoto.getDateAdded().compareTo(maxDate) > 0) {
			this.maxDate = newPhoto.getDateAdded();
		}
		
		else if(newPhoto.getDateAdded().compareTo(minDate) < 0) {
			this.minDate = newPhoto.getDateAdded();
		}
	}
	
	/**
	 * to copy a photo from an album
	 * 
	 * @param photoPath  filepath
	 * @param caption  caption of photo
	 * @param tags   list of tags for that photo
	 */
	public void copyPhoto(String photoPath, String caption, List<Tag> tags) {
		Photo newPhoto = new Photo(photoPath, caption, tags);
		photos.add(newPhoto);
		
		if(photos.size() == 1) {
			maxDate = newPhoto.getDateAdded();
			minDate = newPhoto.getDateAdded();
		}
		
		else if(newPhoto.getDateAdded().compareTo(maxDate) > 0) {
			this.maxDate = newPhoto.getDateAdded();
		}
		
		else if(newPhoto.getDateAdded().compareTo(minDate) < 0) {
			this.minDate = newPhoto.getDateAdded();
		}
	}
	
	/**
	 * to add a list of photos 
	 * @param photosForNewAlbum   list of photos to be added to the album
	 */
	public void addPhotos(List<Photo> photosForNewAlbum) {
		photos.addAll(photosForNewAlbum);
		this.refreshMinDate();
		this.refreshMaxDate();
	}
	
	/**
	 * to remove a photo from a list of photos at a given index 
	 * @param photoIndex        index of the photo to remove 
	 */
	public void removePhoto(int photoIndex) {
		Photo photoToRemove = photos.remove(photoIndex);
		
		if(photos.isEmpty()) {
			maxDate = null;
			minDate = null;
		}
		
		else if(photoToRemove.getDateAdded().compareTo(minDate) == 0){
			this.refreshMinDate();
		}
		
		else if(photoToRemove.getDateAdded().compareTo(maxDate) == 0) {
			this.refreshMaxDate();
		}
	}
	
	private void refreshMinDate() {
		Date newMinDate = photos.get(0).getDateAdded();
		
		for (Photo p : photos){
			if (p.getDateAdded().compareTo(newMinDate) < 0) {
				newMinDate = p.getDateAdded();
			}
		}
		
		this.minDate = newMinDate;	
	}
	
	private void refreshMaxDate() {
		Date newMaxDate = photos.get(0).getDateAdded();
		
		for (Photo p : photos) {
			if (p.getDateAdded().compareTo(newMaxDate) > 0) {
				newMaxDate = p.getDateAdded();
			}
		}
		
		this.maxDate = newMaxDate;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		
		if(!(obj instanceof Album)){
			return false;			
		}
		
		Album album = (Album)obj;
		
		return album.getAlbumName().equals(this.getAlbumName()) 
				&& album.getDateCreated().equals(this.getDateCreated());
	}
	
	@Override
	public int hashCode() {
		int result= 11;
		result = 17 * result + this.getAlbumName().hashCode();
		return 17 * result + this.getDateCreated().hashCode();
	}

	public String getAlbumName() {
		return albumName;
	}

	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	public Date getDateCreated() {
		return dateCreated;
	}
	
	public List<Photo> getPhotos() {
		return photos;
	}
	
	public Date getMinDate() {
		return minDate;
	}

	public Date getMaxDate() {
		return maxDate;
	}
	
	public Photo getcurrentPhoto() {
		return currentPhoto;
	}
	
	public void setcurrentPhoto(Photo currentPhoto) {
		this.currentPhoto = currentPhoto;
	}
}
