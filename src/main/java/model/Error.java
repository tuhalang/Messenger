package model;

import java.io.Serializable;

public class Error implements Serializable{

	private static final long serialVersionUID = 3016413777145785336L;
	
	private String code;
	private String description;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
