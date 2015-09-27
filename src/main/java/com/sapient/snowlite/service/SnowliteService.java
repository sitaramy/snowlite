package com.sapient.snowlite.service;

import java.util.List;
import java.util.Map;

import com.sapient.snowlite.exception.SnowliteException;
import com.sapient.snowlite.model.Application;
import com.sapient.snowlite.model.BusinessService;
import com.sapient.snowlite.model.DBRelease;
import com.sapient.snowlite.model.DBRequest;
import com.sapient.snowlite.model.Incident;
import com.sapient.snowlite.model.Operation;
import com.sapient.snowlite.model.Request;
import com.sapient.snowlite.model.SnowliteRequest;
import com.sapient.snowlite.model.User;
import com.sapient.snowlite.model.UserOperation;

/**
 * Declares methods to be used at service layer
 * @author Satyam
 *
 */
public interface SnowliteService {
	
	/**
	 * @return
	 * @throws SnowliteException
	 */
	List<User> getUsers() throws SnowliteException;
	
	/**
	 * Get user details
	 * @return
	 */
	User getUserDetails(String userId) throws SnowliteException;
	
	/**
	 * List of applications
	 * @return
	 */
	List<Application> getApplications() throws SnowliteException;
	
	/**
	 * get DB Release
	 * @return
	 */
	List<DBRelease> getDBRelease() throws SnowliteException;
	
	/**
	 * get DB Release
	 * @return
	 */
	List<DBRequest> getDBRequest(String userId) throws SnowliteException;
	
	/**
	 * Get all the operations available to the user
	 * @param userId
	 * @return
	 */
	List<Operation> getOperationsForUser(String userId) throws SnowliteException;
	
	/**
	 * Gets the frequently used operations in last 10 days
	 * @param userId
	 * @return
	 */
	List<Integer> getFrequentlyUsedOperations(String userId) throws SnowliteException;

	/**
	 * get the operations together
	 * @param userId
	 * @return
	 */
	Map<String, List<Operation>> getUserOperations(String userId) throws SnowliteException;
	
	/**
	 * Gets the available business services
	 * @return
	 */
	List<BusinessService> getBusinessServices() throws SnowliteException;
	
	/**
	 * Save an incident
	 * @param incident
	 */
	void saveIncident(Incident incident, UserOperation userOperation) throws SnowliteException;
	
	/**
	 * Get incidents raised by user
	 * @param userId
	 * @return
	 */
	List<Incident> getIncidents(String userId) throws SnowliteException;
	
	/**
	 * get a particular incident
	 * @param incidentId
	 * @return
	 */
	Incident getIncident(long incidentId) throws SnowliteException;
	
	/**
	 * Get requests raised by user
	 * @param userId
	 * @return
	 */
	List<Request> getRequests(String userId) throws SnowliteException;
	
	/**
	 * Get a particular request
	 * @param requestId
	 * @return
	 */
	Request getRequest(long requestId) throws SnowliteException; 
	
	/**
	 * Saves a request
	 * @param snowliteRequest
	 * @param user
	 * @param assignmentGroup
	 * @param operations
	 * @throws SnowliteException
	 */
	void saveRequest(SnowliteRequest snowliteRequest, User user, String assignmentGroup, List<Operation> operations) throws SnowliteException;
	
	/**
	 * Saves db release
	 * @param dbRelease
	 */
	void saveDBRelease(SnowliteRequest snowliteRequest, User user, String assignmentGroup, List<Operation> operations) throws SnowliteException;
	
	/**
	 * Saves db request
	 * @param dbRelease
	 */
	void saveDBRequest(SnowliteRequest snowliteRequest, User user, String assignmentGroup, List<Operation> operations) throws SnowliteException;
	
	/**
	 * Retrieve the pending approval item for manager 
	 * @param userId
	 * @return
	 */
	List<Request> getPendingRequests(String type, String userId)  throws SnowliteException;

	/**
	 * Update the aproval status of the request
	 * @param requestid
	 * @param status
	 * @return
	 */
	int updateApprovalStatus(String requestid, String status)  throws SnowliteException;
}
