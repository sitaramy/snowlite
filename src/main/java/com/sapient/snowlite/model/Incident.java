package com.sapient.snowlite.model;

import java.io.Serializable;

public class Incident extends Task implements Serializable {

	private static final long serialVersionUID = -1926894995306177160L;

	private String requestedFor;
	private String environment;
	private String businessService;
	private String assignmentGroup;
	private int assignmentGroupId;

	public Incident(){
		setDisplayPrefix("INC");
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
	
	@Override
	public String toString() {
		return "Incident [requestedFor=" + requestedFor + ", environment="
				+ environment + ", businessService=" + businessService
				+ ", assignmentGroup=" + assignmentGroup + ", getId()="
				+ getId() + ", getShortDescription()="
				+ getShortDescription() + ", getStatus()=" + getStatus() + "]";
	}

	public int getAssignmentGroupId() {
		return assignmentGroupId;
	}

	public void setAssignmentGroupId(int assignmentGroupId) {
		this.assignmentGroupId = assignmentGroupId;
	}
	
	

}
