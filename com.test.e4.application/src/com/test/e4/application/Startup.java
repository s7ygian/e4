package com.test.e4.application;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.lifecycle.PostContextCreate;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("restriction")
public class Startup {

	private static final Logger logger = LoggerFactory.getLogger(Startup.class);

	@PostContextCreate
	public void postContextCreate(IEventBroker eventBroker) {
		eventBroker.subscribe(UIEvents.UILifeCycle.APP_STARTUP_COMPLETE, new AppStartupCompleteEventHandler());
	}

	private static final class AppStartupCompleteEventHandler implements EventHandler {

		@Override
		public void handleEvent(final Event event) {
			logger.info("WORKS TOO!");
		}

	}

}
