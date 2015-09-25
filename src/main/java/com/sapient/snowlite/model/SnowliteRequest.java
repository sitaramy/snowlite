package com.sapient.snowlite.model;

import java.io.Serializable;

/**
 * @author ssh150
 *
 */
public class SnowliteRequest implements Serializable{

	private static final long serialVersionUID = -4144668570098189329L;
	
	private String shortDescription;
	private String operationId;
	private String requestedFor;
	private String description;
	private String businessService;
	private String environment;
	private String requestedResource;
	
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
		if(description != null){
			String[] descArr = description.split("@@nl@@");
			StringBuilder builder = new StringBuilder();
			int index = 0;
			for(String s : descArr){
				builder.append(s);
				if(index < descArr.length - 1){
					builder.append(System.getProperty("line.separator"));
				}
				index++;
			}
			description = builder.toString();
		}
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
	public String getOperationId() {
		return operationId;
	}
	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	public String getRequestedResource() {
		return requestedResource;
	}
	public void setRequestedResource(String requestedResource) {
		this.requestedResource = requestedResource;
	}
	
}
