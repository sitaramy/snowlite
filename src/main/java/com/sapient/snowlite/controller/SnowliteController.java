package com.sapient.snowlite.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sapient.snowlite.exception.SnowliteException;
import com.sapient.snowlite.model.Application;
import com.sapient.snowlite.model.BusinessService;
import com.sapient.snowlite.model.DBRelease;
import com.sapient.snowlite.model.Incident;
import com.sapient.snowlite.model.Operation;
import com.sapient.snowlite.model.Request;
import com.sapient.snowlite.model.SnowliteRequest;
import com.sapient.snowlite.model.Task;
import com.sapient.snowlite.model.User;
import com.sapient.snowlite.service.SnowliteService;

@Controller
public class SnowliteController {

	private static final String SNOWLITE_HOME = "service-now";
	private final static Logger logger = LoggerFactory.getLogger(SnowliteController.class);

	@Autowired
	private SnowliteService snowliteService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(ModelMap model, HttpServletRequest request) throws SnowliteException {

		String userId = request.getRemoteUser();
		
		logger.info("Fetching details for user: {}", userId);
		User loggedInUser = snowliteService.getUserDetails(userId);
		request.getSession().setAttribute("loggedInUser", loggedInUser);
		
		logger.info("Fetching available applications...");
		List<Application> applications = snowliteService.getApplications();
		request.getSession().setAttribute("applications", applications);
		
		logger.info("Fetching available operations...");
		Map<String, List<Operation>> operations = snowliteService.getUserOperations(userId);
		request.getSession().setAttribute("availableOperations", snowliteService.getOperationsForUser(userId));
		
		logger.info("Fetching business services...");
		List<BusinessService> businessServices = snowliteService.getBusinessServices();
		request.getSession().setAttribute("businessServices", businessServices);
		
		model.addAllAttributes(operations);
		return SNOWLITE_HOME;
	}
	
	@RequestMapping(value = "/getUserIncident", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Task> getIncidents(HttpServletRequest request) throws SnowliteException{
		List<Task> tasks = new ArrayList<Task>();
		User loggedInUser = (User)request.getSession().getAttribute("loggedInUser");
		
		logger.info("Fetching service requets for user: {}", loggedInUser.getUserId());
		List<Request> requests = snowliteService.getRequests(loggedInUser.getUserId());
		if(requests != null){
			tasks.addAll(requests);
		}
		
		logger.info("Fetching incidents for user: {}", loggedInUser.getUserId());
		List<Incident> incidents = snowliteService.getIncidents(loggedInUser.getUserId());
		if(incidents != null){
			tasks.addAll(incidents);
		}
		
		request.getSession().setAttribute("userTasks", tasks);
		return tasks;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/saveIncident", method = RequestMethod.POST)
	public @ResponseBody String saveNewIncident(@RequestBody SnowliteRequest snowliteRequest, HttpServletRequest request) throws SnowliteException{
		
		User loggedInUser = (User)request.getSession().getAttribute("loggedInUser");
		
		Incident inc = new Incident();
		inc.setRequestedFor(snowliteRequest.getRequestedFor());
		inc.setEnvironment(snowliteRequest.getEnvironment());
		inc.setShortDescription(snowliteRequest.getDescription());
		inc.setShortDescription(snowliteRequest.getDescription());
		inc.setDescription(snowliteRequest.getDescription());
		inc.setBusinessService(snowliteRequest.getBusinessService());
		inc.setStatus("OPEN");
		
		int bsId = Integer.parseInt(snowliteRequest.getBusinessService());
		List<BusinessService> businessServices = (List<BusinessService>)request.getSession().getAttribute("businessServices");
		String assignmentGroup = businessServices.stream().filter(bs -> bs.getServiceId() == bsId).findFirst().get().getAssignmentGroup();
		inc.setAssignmentGroup(assignmentGroup);
		inc.setUser(loggedInUser);
		snowliteService.saveIncident(inc);
		return "success";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/saveRequest", method = RequestMethod.POST)
	public @ResponseBody String saveNewRequest(@RequestBody SnowliteRequest snowliteRequest, HttpServletRequest request) throws SnowliteException{
		User loggedInUser = (User)request.getSession().getAttribute("loggedInUser");
		int bsId = Integer.parseInt(snowliteRequest.getBusinessService());
		List<BusinessService> businessServices = (List<BusinessService>)request.getSession().getAttribute("businessServices");
		String assignmentGroup = businessServices.stream().filter(bs -> bs.getServiceId() == bsId).findFirst().get().getAssignmentGroup();
		List<Operation> operations = (List<Operation>)request.getSession().getAttribute("availableOperations");
		snowliteService.saveRequest(snowliteRequest, loggedInUser, assignmentGroup, operations);
		return "success";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/saveDBRelease", method = RequestMethod.POST)
	public @ResponseBody String saveNewRelease(@RequestBody SnowliteRequest snowliteRequest, HttpServletRequest request) throws SnowliteException{
		User loggedInUser = (User)request.getSession().getAttribute("loggedInUser");
		int bsId = Integer.parseInt(snowliteRequest.getBusinessService());
		List<BusinessService> businessServices = (List<BusinessService>)request.getSession().getAttribute("businessServices");
		String assignmentGroup = businessServices.stream().filter(bs -> bs.getServiceId() == bsId).findFirst().get().getAssignmentGroup();
		List<Operation> operations = (List<Operation>)request.getSession().getAttribute("availableOperations");
		snowliteService.saveDBRelease(snowliteRequest, loggedInUser, assignmentGroup, operations);
		return "success";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/saveDBRequest", method = RequestMethod.POST)
	public @ResponseBody String saveNewDBRequest(@RequestBody SnowliteRequest snowliteRequest, HttpServletRequest request) throws SnowliteException{
		User loggedInUser = (User)request.getSession().getAttribute("loggedInUser");
		int bsId = Integer.parseInt(snowliteRequest.getBusinessService());
		List<BusinessService> businessServices = (List<BusinessService>)request.getSession().getAttribute("businessServices");
		String assignmentGroup = businessServices.stream().filter(bs -> bs.getServiceId() == bsId).findFirst().get().getAssignmentGroup();
		List<Operation> operations = (List<Operation>)request.getSession().getAttribute("availableOperations");
		snowliteService.saveDBRequest(snowliteRequest, loggedInUser, assignmentGroup, operations);
		return "success";
	}
	
	@RequestMapping(value = "/getDBReleases", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE, headers="Accept=application/json")
	@ResponseBody
	public List<DBRelease> getDBReleases(HttpServletRequest request) throws SnowliteException{
		logger.info("Fetching available db releases...");
		List<DBRelease> release = snowliteService.getDBRelease();
		if(release == null){
			release = new ArrayList<DBRelease>();
		}
		return release;
	}
}