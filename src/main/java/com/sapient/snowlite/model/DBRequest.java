package com.sapient.snowlite.model;

import java.io.Serializable;

/**
 * @author ssh150
 *
 */
public class DBRequest implements Serializable{
 	
	private static final long serialVersionUID = 3697684976877816044L;
	
	private long dbrId;
	private String description;
	private DBRelease release;
	private String environment;
	private User user;
	private String assignmentGroup;
	private String status;
	private String displayPrefix = "DBR";
	
	public long getDbrId() {
		return dbrId;
	}
	public void setDbrId(long dbrId) {
		this.dbrId = dbrId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public DBRelease getRelease() {
		return release;
	}
	public void setRelease(DBRelease release) {
		this.release = release;
	}
	public String getEnvironment() {
		return environment;
	}
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getAssignmentGroup() {
		return assignmentGroup;
	}
	public void setAssignmentGroup(String assignmentGroup) {
		this.assignmentGroup = assignmentGroup;
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
}