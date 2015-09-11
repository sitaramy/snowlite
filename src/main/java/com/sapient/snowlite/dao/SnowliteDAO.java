package com.sapient.snowlite.dao;

import java.util.List;

import com.sapient.snowlite.model.Operation;
import com.sapient.snowlite.model.User;

public interface SnowliteDAO {

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
	
}
