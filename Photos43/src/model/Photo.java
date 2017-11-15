package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Deep Kotadia
 * @author Chinmoyi Bhushan
 *
 */
public class Photo implements Serializable {
	
	private String photoPath;
	private List<String> nameTags;
	private List<String> locationTags;
	
	/**
	 * 
	 */
	public Photo(String photoPath) {
		this.photoPath = photoPath;
		nameTags = new ArrayList<String>();
		locationTags = new ArrayList<String>();
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
}
