package com.sapient.snowlite.model;

import java.io.Serializable;

public class Request implements Serializable{

	private static final long serialVersionUID = -6828277386352318725L;
	
	private long requestId;
	private String shortDescription;
	private String description;
	private User user;
	private int businessService;
	private String assignmentGroup;
	private String requestedResource;
	private boolean approval;
	private String approvingManager;
	private String status;
	
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
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public long getRequestId() {
		return requestId;
	}
	public void setRequestId(long requestId) {
		this.requestId = requestId;
	}
}
