package com.test.e4.application.handlers;

import java.util.List;

import org.eclipse.e4.core.di.annotations.Execute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.e4.application.Context;
import com.test.e4.application.model.Project;

public class CloseHandler {

	private static final Logger logger = LoggerFactory.getLogger(CloseHandler.class);
	
	@Execute
	public void execute() {
		List<Project> projects = Context.getInstance().projects;
		if (!projects.isEmpty()) {
			Project project = projects.get(projects.size() - 1);
			project.close();
			projects.remove(project);
			logger.info("...project removed");
		}
	}

}
