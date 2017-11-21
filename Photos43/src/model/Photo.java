package model;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author Deep Kotadia
 * @author Chinmoyi Bhushan
 *
 */
public class Photo implements Serializable {
	
	private String photoPath;
	private String caption;
	private Date dateAdded;
	private List<Tag> tags;
	
	/**
	 * 
	 */
	public Photo(String photoPath) {
		this.photoPath = photoPath;
		this.caption = "";
		File photoFile = new File(photoPath);
		this.dateAdded = new Date(photoFile.lastModified());
		this.tags = new ArrayList<Tag>();
	}
	
	public void addTag(String key, String value) {
		tags.add(new Tag(key, value));
	}
	
	public boolean removeTag(String key, String value) {
		for(int i = 0; i < tags.size(); i++) {
			if(tags.get(i).key.equals(key) && tags.get(i).value.equals(value)) {
				tags.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public boolean doesTagExist(String key, String value) {
		for(int i = 0; i < tags.size(); i++) {
			if(tags.get(i).key.equals(key) && tags.get(i).value.equals(value)) {
				return true;
			}
		}
		return false;
	}
	
	public List<Tag> getTags(){
		return tags;
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
