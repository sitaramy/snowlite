package com.sapient.snowlite.model;

import java.io.Serializable;

public final class Operation implements Serializable{

	private static final long serialVersionUID = -3262750289594731759L;

	private int operationId;
	private String operationName;
	private String operationUrl;
	
	public Operation(){
		
	}
	
	public Operation(Operation op){
		this.operationId = op.operationId;
		this.operationName = op.operationName;
		this.operationUrl = op.operationUrl;
	}
	
	public int getOperationId() {
		return operationId;
	}
	public void setOperationId(int operationId) {
		this.operationId = operationId;
	}
	public String getOperationName() {
		return operationName;
	}
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	public String getOperationUrl() {
		return operationUrl;
	}
	public void setOperationUrl(String operationUrl) {
		this.operationUrl = operationUrl;
	}
	
}
