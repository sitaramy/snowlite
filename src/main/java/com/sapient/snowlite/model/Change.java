package com.sapient.snowlite.model;

import java.io.Serializable;

public class Change extends Task implements Serializable {

	private static final long serialVersionUID = -6828277386352318725L;

	private int businessService;
	private String businessServiceName;
	private int assignmentGroupId;
	private String assignmentGroup;
	private String requestedResource;
	private boolean approval;
	private String approvingManager;
	
	public Change(){
		setDisplayPrefix("CHG");
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

	public String getBusinessServiceName() {
		return businessServiceName;
	}

	public void setBusinessServiceName(String businessServiceName) {
		this.businessServiceName = businessServiceName;
	}

	public int getAssignmentGroupId() {
		return assignmentGroupId;
	}

	public void setAssignmentGroupId(int assignmentGroupId) {
		this.assignmentGroupId = assignmentGroupId;
	}
}
