package com.test.e4.application.handlers;

import org.eclipse.e4.core.di.annotations.Execute;

import com.test.e4.application.Context;
import com.test.e4.application.Project;

public class OpenHandler {

	@Execute
	public void execute() {
		Project project = new Project();
		project.open();
		Context.getInstance().projects.add(project);
	}
	
}
