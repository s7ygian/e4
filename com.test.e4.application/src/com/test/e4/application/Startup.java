package com.test.e4.application;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.lifecycle.PostContextCreate;
import org.eclipse.e4.ui.workbench.lifecycle.ProcessAdditions;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Startup {

	private static final Logger logger = LoggerFactory.getLogger(Startup.class);

	@PostContextCreate
	public void postContextCreate(IEclipseContext context) {
		final Shell shell = new Shell(SWT.INHERIT_NONE);
		logger.info("starting application...");
		IEventBroker eventBroker = context.get(IEventBroker.class);
		eventBroker.subscribe(UIEvents.UILifeCycle.APP_STARTUP_COMPLETE, new AppStartupCompleteEventHandler(context));
	}

	private static final class AppStartupCompleteEventHandler implements EventHandler {

		@SuppressWarnings("unused")
		IEclipseContext context;

		AppStartupCompleteEventHandler(IEclipseContext context) {
			this.context = context;
		}

		@Override
		public void handleEvent(final Event event) {
			logger.info("...application started successfully");
		}

	}

	@ProcessAdditions
	public void processAdditions(final IEclipseContext context) {
		logger.info("adding model contributions...");
		@SuppressWarnings("unused")
		MApplication application = context.get(MApplication.class);
	}

}
