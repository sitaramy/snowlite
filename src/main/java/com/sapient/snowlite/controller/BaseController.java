package com.sapient.snowlite.controller;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BaseController {

	private static final String VIEW_INDEX = "index";
	private static final String SNOWLITE_HOME = "service-now";
	private final static org.slf4j.Logger logger = LoggerFactory.getLogger(BaseController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welcome(ModelMap model) {

		logger.debug("Index page - [Welcome]");
		return VIEW_INDEX;

	}

	@RequestMapping(value = "/app", method = RequestMethod.GET)
	public String welcomeName(ModelMap model) {

		logger.debug("[/app] requested");
		return SNOWLITE_HOME;

	}

}