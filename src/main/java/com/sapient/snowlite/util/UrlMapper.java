package com.sapient.snowlite.util;

public enum UrlMapper {

	INCIDENT_ENTRY("/servicenow/submitIncident.do","Submit Incident"),
	CREATE_DBR("/servicenow/createDbr.do","Create DBR"),
	OPEN_BRIDGE("/servicenow/openBridge.do","Open Bridge"),
	SOFT_INSTALLATION("/servicenow/softInstallation.do","Software Installation"),
	ASSISTANCE("Other","");
	
	String url, option;
	UrlMapper(String url, String option) {
		this.url = url;
		this.option = option;
	}
	
}
