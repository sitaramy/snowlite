package com.sapient.snowlite.model;

import java.io.Serializable;

/**
 * @author ssh150
 *
 */
public class SnowliteRequest implements Serializable{

	private static final long serialVersionUID = -4144668570098189329L;
	
	public String requestedFor;
	public String description;
	public String businessService;
	public String environment;
	
	public String getRequestedFor() {
		return requestedFor;
	}
	public void setRequestedFor(String requestedFor) {
		this.requestedFor = requestedFor;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getBusinessService() {
		return businessService;
	}
	public void setBusinessService(String businessService) {
		this.businessService = businessService;
	}
	public String getEnvironment() {
		return environment;
	}
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	
}
