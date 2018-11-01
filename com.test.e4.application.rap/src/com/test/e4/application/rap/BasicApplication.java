package com.test.e4.application.rap;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.rap.e4.E4ApplicationConfig;
import org.eclipse.rap.e4.E4EntryPointFactory;
import org.eclipse.rap.rwt.application.Application;
import org.eclipse.rap.rwt.application.Application.OperationMode;
import org.eclipse.rap.rwt.application.ApplicationConfiguration;
import org.eclipse.rap.rwt.client.WebClient;

public class BasicApplication implements ApplicationConfiguration
{

	private final static String applicationXMI = "platform:/plugin/com.test.e4.application/Application.e4xmi";

	private final static String lifeCycleURI = "bundleclass://com.test.e4.application/com.test.e4.application.Startup";

    public void configure(Application application) {
        Map<String, String> properties = new HashMap<String, String>();
        properties.put(WebClient.PAGE_TITLE, "RAP");
        E4ApplicationConfig config = E4ApplicationConfig.create(applicationXMI, lifeCycleURI);
        E4EntryPointFactory entryPointFactory = new E4EntryPointFactory(config);
        application.addEntryPoint("/rap", entryPointFactory, properties);
        application.setOperationMode( OperationMode.SWT_COMPATIBILITY );
    }

}
