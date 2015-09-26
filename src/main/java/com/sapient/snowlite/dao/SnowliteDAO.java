package com.sapient.snowlite.dao;

import java.util.List;

import com.sapient.snowlite.model.Application;
import com.sapient.snowlite.model.BusinessService;
import com.sapient.snowlite.model.DBRelease;
import com.sapient.snowlite.model.DBRequest;
import com.sapient.snowlite.model.Incident;
import com.sapient.snowlite.model.Operation;
import com.sapient.snowlite.model.Request;
import com.sapient.snowlite.model.User;
import com.sapient.snowlite.model.UserOperation;

public interface SnowliteDAO {

	/**
	 * Get user details
	 * @return
	 */
	User getUserDetails(String userId);
	
	/**
	 * List of applications
	 * @return
	 */
	List<Application> getApplications();
	
	/**
	 * get DB Release
	 * @return
	 */
	List<DBRelease> getDBRelease();
	
	/**
	 * get DB Release
	 * @return
	 */
	List<DBRequest> getDBRequest(String userId);
	
	/**
	 * Get all the operations available to the user
	 * @param userId
	 * @return
	 */
	List<Operation> getOperationsForUser(String userId);
	
	/**
	 * Gets the frequently used operations in last 10 days
	 * @param userId
	 * @return
	 */
	List<Integer> getFrequentlyUsedOperations(String userId);
	
	/**
	 * Gets the available business services
	 * @return
	 */
	List<BusinessService> getBusinessServices();
	
	/**
	 * Save an incident
	 * @param incident
	 */
	void saveIncident(Incident incident);
	
	/**
	 * Get incidents raised by user
	 * @param userId
	 * @return
	 */
	List<Incident> getIncidents(String userId);
	
	/**
	 * Saves the request
	 * @param request
	 */
	void saveRequest(Request request);
	
	/**
	 * Saves the user operation
	 * @param request
	 */
	void saveUserOperation(UserOperation userOperation);
	
	/**
	 * Saves db release
	 * @param dbRelease
	 */
	void saveDBRelease(DBRelease dbRelease);
	
	/**
	 * Saves db request
	 * @param dbRelease
	 */
	void saveDBRequest(DBRequest dbRequest);
	
}
