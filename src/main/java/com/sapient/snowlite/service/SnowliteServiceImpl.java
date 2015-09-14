package com.sapient.snowlite.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sapient.snowlite.dao.SnowliteDAO;
import com.sapient.snowlite.model.BusinessService;
import com.sapient.snowlite.model.Incident;
import com.sapient.snowlite.model.Operation;
import com.sapient.snowlite.model.User;

/**
 * Implements the service layer methods
 * @author Satyam
 *
 */
@Service
public class SnowliteServiceImpl implements SnowliteService {

	@Autowired
	private SnowliteDAO snowliteDAO;
	
	@Override
	public User getUserDetails(String userId) {
		return snowliteDAO.getUserDetails(userId);
	}

	@Override
	public List<Operation> getOperationsForUser(String userId) {
		return snowliteDAO.getOperationsForUser(userId);
	}

	@Override
	public List<Integer> getFrequentlyUsedOperations(String userId) {
		return snowliteDAO.getFrequentlyUsedOperations(userId);
	}

	@Override
	public Map<String, List<Operation>> getUserOperations(String userId) {
		List<Operation> allOperations = getOperationsForUser(userId);
		List<Integer> preferredOperations = getFrequentlyUsedOperations(userId);
		
		List<Operation> frequentlyUsedOperations = new LinkedList<Operation>();
		
		if(allOperations != null && preferredOperations != null && !preferredOperations.isEmpty()){
			for(int operationId : preferredOperations){
				 Optional<Operation> opr = allOperations.stream().filter(op -> op.getOperationId() == operationId).findFirst();
				 if(opr != null && opr.isPresent()){
					 frequentlyUsedOperations.add(new Operation(opr.get()));
					 allOperations.remove(opr.get());
				 }
			}
		}
		
		Map<String, List<Operation>> operationsMap = new HashMap<String, List<Operation>>();
		operationsMap.put("allOperations", allOperations);
		operationsMap.put("preferredOperations", frequentlyUsedOperations);
		
		return operationsMap;
	}

	@Override
	public List<BusinessService> getBusinessServices() {
		return snowliteDAO.getBusinessServices();
	}

	@Override
	public List<Incident> getIncidents(String userId) {
		return snowliteDAO.getIncidents(userId);
	}

	@Override
	public void saveIncident(Incident incident) {
		snowliteDAO.saveIncident(incident);
	}


}