package com.test.e4.application.handlers;

import java.util.List;

import org.eclipse.e4.core.di.annotations.Execute;

import com.test.e4.application.Context;
import com.test.e4.application.Project;

public class CloseHandler {

	@Execute
	public void execute() {
		List<Project> projects = Context.getInstance().projects;
		if (!projects.isEmpty()) {
			Project project = projects.get(projects.size() - 1);
			project.close();
			projects.remove(project);
		}
	}

}
