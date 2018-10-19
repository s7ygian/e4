package com.test.e4.application;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.eclipse.persistence.jpa.PersistenceProvider;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.wiring.BundleWiring;


public class Project {

	private EntityManagerFactory entityManagerFactory;

	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public void open() {
		@SuppressWarnings("rawtypes")
		Map map = new HashMap();
		BundleContext ctx =  FrameworkUtil.getBundle(this.getClass()).getBundleContext();
		Bundle[] bundles = ctx.getBundles();
		Bundle bundle = null;
		for (Bundle installedBundle : bundles) {
			if (installedBundle.getSymbolicName().contains("com.test.e4.application.persistence")) {
				bundle = installedBundle;
				break;
			}
		}
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
