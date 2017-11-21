package model;

import java.io.Serializable;

public class Tag implements Serializable {
	public String key;
	public String value;
	
	public Tag(String key, String value) {
		this.key = key;
		this.value = value;
	}
}
