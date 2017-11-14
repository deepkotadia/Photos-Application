package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author Deep Kotadia
 * @author Chinmoyi Bhushan
 *
 */
public class Album {
	
	private String albumName;
	private Date dateCreated;
	private List<Photo> photos;

	/**
	 * 
	 */
	public Album(String albumName) {
		this.albumName = albumName;
		this.dateCreated = new Date();
		photos = new ArrayList<Photo>();
	}
	
	public void addPhoto(String photoPath) {
		Photo newPhoto = new Photo(photoPath);
		photos.add(newPhoto);
	}
	
	public void removePhoto(String photoPath) {
		Photo photoToRemove = new Photo(photoPath);
		photos.remove(photoToRemove);		
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

}
