package com.sapient.snowlite.model;

import java.io.Serializable;

public class BusinessService implements Serializable{
	private static final long serialVersionUID = -999976834508844005L;
	
	private int serviceId;
	private String serviceName;
	private String assignmentGroup;
	
	public int getServiceId() {
		return serviceId;
	}
	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getAssignmentGroup() {
		return assignmentGroup;
	}
	public void setAssignmentGroup(String assignmentGroup) {
		this.assignmentGroup = assignmentGroup;
	}
	
}
