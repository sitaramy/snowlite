package com.sapient.snowlite.model;

import java.io.Serializable;

/**
 * @author ssh150
 *
 */
public class DBRelease implements Serializable{

	private static final long serialVersionUID = -7693712077140624449L;
	
	private long releaseId;
	private String description;
	private User userId;
	private String assignmentGroup;
	private int assignmentGroupId;
	private Application application;
	private String status;
	private String displayPrefix = "REL";
	
	public long getReleaseId() {
		return releaseId;
	}
	public void setReleaseId(long releaseId) {
		this.releaseId = releaseId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public User getUserId() {
		return userId;
	}
	public void setUserId(User userId) {
		this.userId = userId;
	}
	public String getAssignmentGroup() {
		return assignmentGroup;
	}
	public void setAssignmentGroup(String assignmentGroup) {
		this.assignmentGroup = assignmentGroup;
	}
	public Application getApplication() {
		return application;
	}
	public void setApplication(Application application) {
		this.application = application;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDisplayPrefix() {
		return displayPrefix;
	}
	public void setDisplayPrefix(String displayPrefix) {
		this.displayPrefix = displayPrefix;
	}
	public int getAssignmentGroupId() {
		return assignmentGroupId;
	}
	public void setAssignmentGroupId(int assignmentGroupId) {
		this.assignmentGroupId = assignmentGroupId;
	}
}
