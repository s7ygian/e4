package com.test.e4.application.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.e4.application.Context;
import com.test.e4.application.model.Project;

public class OpenHandler {

	private static final Logger logger = LoggerFactory.getLogger(OpenHandler.class);

	@Execute
	public void execute() {
		Project project = new Project();
		project.open();
		Context.getInstance().projects.add(project);
		logger.info("...project added");
	}
	
}
