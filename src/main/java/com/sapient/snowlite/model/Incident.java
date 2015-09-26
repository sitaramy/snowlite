package com.sapient.snowlite.model;

import java.io.Serializable;

public class Incident implements Serializable{

	private static final long serialVersionUID = -1926894995306177160L;
	
	private long incidentId;
	private String shortDescription;
	private String description;
	private String requestedFor;
	private String environment;
	private String businessService;
	private String assignmentGroup;
	private User user;
	private String status;
	
	public long getIncidentId() {
		return incidentId;
	}
	public void setIncidentId(long incidentId) {
		this.incidentId = incidentId;
	}
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRequestedFor() {
		return requestedFor;
	}
	public void setRequestedFor(String requestedFor) {
		this.requestedFor = requestedFor;
	}
	public String getEnvironment() {
		return environment;
	}
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	public String getBusinessService() {
		return businessService;
	}
	public void setBusinessService(String businessService) {
		this.businessService = businessService;
	}
	public String getAssignmentGroup() {
		return assignmentGroup;
	}
	public void setAssignmentGroup(String assignmentGroup) {
		this.assignmentGroup = assignmentGroup;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
