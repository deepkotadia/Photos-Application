package model;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Photo class 
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
	 * constructor for Photo class
	 * @param photoPath  filepath for photo 
	 * 
	 * @return 
	 */
	public Photo(String photoPath) {
		this.photoPath = photoPath;
		this.caption = "";
		File photoFile = new File(photoPath);
		this.dateAdded = new Date(photoFile.lastModified());
		this.tags = new ArrayList<Tag>();
	}
	
	/**
	 * another constructor for Photo class
	 * 
	 * @param photoPath   filepath 
	 * @param caption   caption of photo
	 * @param tags   list of tags for photo 
	 * 
	 * @return 
	 */
	public Photo(String photoPath, String caption, List<Tag> tags) {
		this.photoPath = photoPath;
		this.caption = caption;
		File photoFile = new File(photoPath);
		this.dateAdded = new Date(photoFile.lastModified());
		this.tags = new ArrayList<Tag>();
		this.tags.addAll(tags);
	}
	
	/**
	 * function to add a tag for the user 
	 * @param key   tag name 
	 * @param value   tag value 
	 */
	public void addTag(String key, String value) {
		tags.add(new Tag(key, value));
	}
	
	/**
	 * function to remove a tag specified by the user for this photo 
	 * @param key   tagname
	 * @param value  tagvalue 
	 * @return 
	 */
	public boolean removeTag(String key, String value) {
		for(int i = 0; i < tags.size(); i++) {
			if(tags.get(i).key.toLowerCase().equals(key.toLowerCase()) && tags.get(i).value.toLowerCase().equals(value.toLowerCase())) {
				tags.remove(i);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * check for if the tag exists for this photo or not 
	 * 
	 * @param key   tagname
	 * @param value  tagvalue 
	 * 
	 * @return true if exists else false 
	 */
	public boolean doesTagExist(String key, String value) {
		for(int i = 0; i < tags.size(); i++) {
			if(tags.get(i).key.toLowerCase().equals(key.toLowerCase()) && tags.get(i).value.toLowerCase().equals(value.toLowerCase())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * returns a list of tags
	 * @return List
	 */
	
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
