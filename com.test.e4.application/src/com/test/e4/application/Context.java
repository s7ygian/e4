package com.test.e4.application;

import java.util.ArrayList;
import java.util.List;

import com.test.e4.application.model.Project;

public class Context {

	private Context() {
	}

	private static Context INSTANCE;

	public static synchronized Context getInstance() {
		if (Context.INSTANCE == null) {
			Context.INSTANCE = new Context();
		}
		return Context.INSTANCE;
	}

	public List<Project> projects = new ArrayList<>();

}
