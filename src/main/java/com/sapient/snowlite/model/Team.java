package com.sapient.snowlite.model;

import java.io.Serializable;

/**
 * Represents a team
 * @author ssh150
 *
 */
public class Team implements Serializable{
	private static final long serialVersionUID = -5074933325654317087L;
	
	private String teamId;
	private String teamName;
	private String teamAssignmentGroup;
	private User approvalManager;
	private String teamDL;
	private String teamDevDL;
	private String teamQADL;
	private String databaseAssignmentGroup;
	
	public String getTeamId() {
		return teamId;
	}
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getTeamAssignmentGroup() {
		return teamAssignmentGroup;
	}
	public void setTeamAssignmentGroup(String teamAssignmentGroup) {
		this.teamAssignmentGroup = teamAssignmentGroup;
	}
	public User getApprovalManager() {
		return approvalManager;
	}
	public void setApprovalManager(User approvalManager) {
		this.approvalManager = approvalManager;
	}
	public String getTeamDL() {
		return teamDL;
	}
	public void setTeamDL(String teamDL) {
		this.teamDL = teamDL;
	}
	public String getTeamDevDL() {
		return teamDevDL;
	}
	public void setTeamDevDL(String teamDevDL) {
		this.teamDevDL = teamDevDL;
	}
	public String getTeamQADL() {
		return teamQADL;
	}
	public void setTeamQADL(String teamQADL) {
		this.teamQADL = teamQADL;
	}
	public String getDatabaseAssignmentGroup() {
		return databaseAssignmentGroup;
	}
	public void setDatabaseAssignmentGroup(String databaseAssignmentGroup) {
		this.databaseAssignmentGroup = databaseAssignmentGroup;
	}
}
