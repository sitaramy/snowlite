package com.sapient.snowlite.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sapient.snowlite.model.Operation;
import com.sapient.snowlite.service.SnowliteService;

@Controller
public class SnowliteController {

	private static final String VIEW_INDEX = "index";
	private static final String SNOWLITE_HOME = "service-now";
	private final static Logger logger = LoggerFactory.getLogger(SnowliteController.class);

	@Autowired
	private SnowliteService snowliteService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welcome(ModelMap model, HttpServletRequest request) {

		logger.info("Fetching available operations...");
		String reqUser = request.getRemoteUser();
		System.out.println(reqUser);
		String userId = "ssh150";
		Map<String, List<Operation>> operations = snowliteService.getUserOperations(userId);
		model.addAllAttributes(operations);
		model.addAttribute("userId", userId);
		return SNOWLITE_HOME;

	}

}