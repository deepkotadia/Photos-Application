package model;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Deep Kotadia
 * @author Chinmoyi Bhushan
 *
 */
public class Photo implements Serializable {

	
	private String photoPath;
	private String caption;
	private List<String> nameTags;
	private List<String> locationTags;
	private Date dateAdded;
	
	/**
	 * 
	 */
	public Photo(String photoPath) {
		this.photoPath = photoPath;
		nameTags = new ArrayList<String>();
		locationTags = new ArrayList<String>();
		File photoFile = new File(photoPath);
		this.dateAdded = new Date(photoFile.lastModified());
		this.caption = "";
	}

	public void addNameTag(String nameTag) {
		nameTags.add(nameTag);		
	}
	
	public void removeNameTag(String nameTag) {
		nameTags.remove(nameTag);		
	}
	
	public void addLocationTag(String locationTag) {
		locationTags.add(locationTag);		
	}
	
	public void removeLocationTag(String locationTag) {
		locationTags.remove(locationTag);
		
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		
		if(!(obj instanceof Photo)){
			return false;			
		}
		
		Photo photo = (Photo)obj;
		
		return photo.getPhotoPath().equals(this.getPhotoPath());
	}
	
	@Override
	public int hashCode() {
		int result = 11;
		return 17 * result + this.getPhotoPath().hashCode();			
	}	
	
	public String getPhotoPath() {
		return photoPath;
	}
	
	public List<String> getNameTags(){
		return nameTags;
	}
	
	public List<String> getLocationTag(){
		return locationTags;
	}

	public Date getDateAdded() {
		return dateAdded;
	}
	
	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	public String getCaption() {
		return caption;
	}
}
