package com.sapient.snowlite.model;

import java.io.Serializable;

public class Application implements Serializable{

	private static final long serialVersionUID = 6155119089406560642L;
	
	private int applicationId;
	private String applicationName;
	private String description;
	
	public int getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}
	public String getApplicationName() {
		return applicationName;
	}
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
