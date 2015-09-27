package com.sapient.snowlite.model;

import java.io.Serializable;

public class Request extends Task implements Serializable {

	private static final long serialVersionUID = -6828277386352318725L;

	private int businessService;
	private String assignmentGroup;
	private String requestedResource;
	private boolean approval;
	private String approvingManager;
	
	public Request(){
		setDisplayPrefix("REQ");
	}
	
	public int getBusinessService() {
		return businessService;
	}

	public void setBusinessService(int businessService) {
		this.businessService = businessService;
	}

	public String getAssignmentGroup() {
		return assignmentGroup;
	}

	public void setAssignmentGroup(String assignmentGroup) {
		this.assignmentGroup = assignmentGroup;
	}

	public String getRequestedResource() {
		return requestedResource;
	}

	public void setRequestedResource(String requestedResource) {
		this.requestedResource = requestedResource;
	}

	public boolean isApproval() {
		return approval;
	}

	public void setApproval(boolean approval) {
		this.approval = approval;
	}

	public String getApprovingManager() {
		return approvingManager;
	}

	public void setApprovingManager(String approvingManager) {
		this.approvingManager = approvingManager;
	}

	@Override
	public String toString() {
		return "Request [businessService=" + businessService
				+ ", assignmentGroup=" + assignmentGroup
				+ ", requestedResource=" + requestedResource + ", approval="
				+ approval + ", approvingManager=" + approvingManager
				+ ", getId()=" + getId()
				+ ", getShortDescription()=" + getShortDescription()
				+ ", getStatus()=" + getStatus() + "]";
	}
}
