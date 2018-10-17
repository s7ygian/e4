package com.test.e4.application.model;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.eclipse.core.runtime.Platform;
import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.eclipse.persistence.jpa.PersistenceProvider;
import org.osgi.framework.Bundle;
import org.osgi.framework.wiring.BundleWiring;

public class Project {

	private EntityManagerFactory entityManagerFactory;

	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public void open() {
		@SuppressWarnings("rawtypes")
		Map map = new HashMap();
		Bundle bundle = Platform.getBundle("com.test.e4.application.persistence");
		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);
		ClassLoader classLoader = bundleWiring.getClassLoader();
		map.put(PersistenceUnitProperties.CLASSLOADER, classLoader);
		map.put("javax.persistence.jdbc.url", "jdbc:h2:~/h2db/testDB_" + UUID.randomUUID() + ";MV_STORE=FALSE");
		PersistenceProvider persistenceProvider = new PersistenceProvider();
		entityManagerFactory = persistenceProvider.createEntityManagerFactory("h2-eclipselink", map);
		entityManager = entityManagerFactory.createEntityManager();
	}

	public void close() {
		entityManager.close();
		entityManagerFactory.close();
	}

}
