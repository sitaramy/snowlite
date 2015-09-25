package com.sapient.snowlite.model;

import java.io.Serializable;

/**
 * Represents a user operation
 * @author ssh150
 *
 */
public class UserOperation implements Serializable{

	private static final long serialVersionUID = -1650126500229915354L;
	
	private String userId;
	private int operationId;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getOperationId() {
		return operationId;
	}
	public void setOperationId(int operationId) {
		this.operationId = operationId;
	}
	
}
