package model;

import java.io.Serializable;

/**
 * Tag class 
 * @author Chinmoyi Bhushan
 * @author Deep Kotadia
 *
 */

public class Tag implements Serializable {
	public String key;
	public String value;
	
	/**
	 * Constructor for the Tag class
	 * @param key  tagname
	 * @param value    tagvalue
	 * @return 
	 */
	
	public Tag(String key, String value) {
		this.key = key;
		this.value = value;
	}
}
