package com.sapient.snowlite.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sapient.snowlite.dao.SnowliteDAO;
import com.sapient.snowlite.exception.SnowliteException;
import com.sapient.snowlite.model.BusinessService;
import com.sapient.snowlite.model.Incident;
import com.sapient.snowlite.model.Operation;
import com.sapient.snowlite.model.Request;
import com.sapient.snowlite.model.SnowliteRequest;
import com.sapient.snowlite.model.User;
import com.sapient.snowlite.model.UserOperation;

/**
 * Implements the service layer methods
 * @author Satyam
 *
 */
@Service
public class SnowliteServiceImpl implements SnowliteService {
	
	private static final Logger LOG = LoggerFactory.getLogger(SnowliteServiceImpl.class);

	@Autowired
	private SnowliteDAO snowliteDAO;
	
	@Override
	public User getUserDetails(String userId)  throws SnowliteException{
		return snowliteDAO.getUserDetails(userId);
	}

	@Override
	public List<Operation> getOperationsForUser(String userId)  throws SnowliteException{
		return snowliteDAO.getOperationsForUser(userId);
	}

	@Override
	public List<Integer> getFrequentlyUsedOperations(String userId)  throws SnowliteException{
		return snowliteDAO.getFrequentlyUsedOperations(userId);
	}

	@Override
	public Map<String, List<Operation>> getUserOperations(String userId)  throws SnowliteException{
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
	public List<BusinessService> getBusinessServices()  throws SnowliteException{
		return snowliteDAO.getBusinessServices();
	}

	@Override
	public List<Incident> getIncidents(String userId)  throws SnowliteException{
		return snowliteDAO.getIncidents(userId);
	}

	@Override
	public void saveIncident(Incident incident)  throws SnowliteException{
		snowliteDAO.saveIncident(incident);
	}

	@Override
	public void saveRequest(SnowliteRequest snowliteRequest, User user, String assignmentGroup, List<Operation> operations) throws SnowliteException {
		LOG.info("Processing request...");

		String opr = snowliteRequest.getOperationId();
		Operation operation = operations.stream().filter(op -> op.getOperationUrl().equals(opr)).findFirst().get();

		UserOperation userOperation = new UserOperation();
		userOperation.setUserId(user.getUserId());
		userOperation.setOperationId(operation.getOperationId());
		
		Request request = new Request();
		request.setDescription(snowliteRequest.getDescription());
		request.setShortDescription(snowliteRequest.getShortDescription());
		request.setBusinessService(Integer.parseInt(snowliteRequest.getBusinessService()));
		request.setRequestedResource(snowliteRequest.getRequestedResource());
		request.setUser(user);
		if(operation.isApprovalNeeded()){
			request.setApproval(false);
		}
		else{
			request.setApproval(true);
		}
		request.setApprovingManager(user.getTeam().getApprovalManager().getUserId());
		request.setAssignmentGroup(assignmentGroup);
		request.setStatus("OPEN");
		
		LOG.info("Calling dao to save request...");
		snowliteDAO.saveRequest(request);
		LOG.info("Calling dao to save user operation...");
		snowliteDAO.saveUserOperation(userOperation);
	}


}