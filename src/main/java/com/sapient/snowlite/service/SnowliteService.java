package com.sapient.snowlite.service;

import java.util.List;
import java.util.Map;

import com.sapient.snowlite.model.BusinessService;
import com.sapient.snowlite.model.Incident;
import com.sapient.snowlite.model.Operation;
import com.sapient.snowlite.model.User;

/**
 * Declares methods to be used at service layer
 * @author Satyam
 *
 */
public interface SnowliteService {
	
	/**
	 * Get user details
	 * @return
	 */
	User getUserDetails(String userId);
	
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
	 * get the operations together
	 * @param userId
	 * @return
	 */
	Map<String, List<Operation>> getUserOperations(String userId);
	
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
}
