/*
 * Copyright (c) 2017.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    ajain17 & nverma1 - API , implementation and initial documentation
 */

package com.intuit.ugc.impl.persistence.dse;

import static java.util.Objects.isNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.intuit.ugc.api.AccessToken;
import com.intuit.ugc.api.PersistenceException;
import com.toddfast.util.preconditions.Preconditions;

/**
 * represents the implementation that wraps all the data specific to connection
 * information for the underlying DSE persistence store.
 * As of now, the properties are loaded from a properties config file at the time of startup. 
 * 
 * implements {@link com.intuit.ugc.impl.persistence.dse.DSEPersistenceConfiguration}
 * 
 * @author nverma1
 *
 */
public class DSEPersistenceConfigurationProperties extends Properties implements DSEPersistenceConfiguration {

	public DSEPersistenceConfigurationProperties() {
		super();
	}

	public DSEPersistenceConfigurationProperties(Properties defaults) {
		super(defaults);
	}

	public DSEPersistenceConfigurationProperties(String resourceName) throws IOException, PersistenceException {
		this(resourceName, null);
	}

	public DSEPersistenceConfigurationProperties(String resourceName, ClassLoader classLoader)
			throws IOException, PersistenceException {
		super();
		load(resourceName, classLoader);
	}

	/**
	 * Load the configuration from the specified file name
	 * 
	 * @param resourceName
	 *            The resource path/name to load
	 * @param classLoader
	 *            The class loader to use, or null to use the default
	 *            (associated with this class)
	 * @return
	 */
	public final void load(String resourceName, ClassLoader classLoader) throws IOException, PersistenceException {

		Preconditions.argumentNotNull(resourceName, "resourceName");

		if (isNull(classLoader)) {
			classLoader = DSEPersistenceConfigurationProperties.class.getClassLoader();
		}

		try {
			super.load(classLoader.getResource(resourceName).openStream());
			validateMissingRequiredKeys();

			LOG.info("Successfully loaded DSE provider configuration " + "from resource \"{}\"", resourceName);
		} catch (IOException e) {
			throw e;
		} catch (Exception e) {
			throw new PersistenceException("Could not load configuration from resource \"" + resourceName + "\"", e);
		}
	}

	@Override
	public synchronized void load(InputStream inStream) throws IOException {
		super.load(inStream);
		validateMissingRequiredKeys();
	}

	@Override
	public synchronized void load(Reader reader) throws IOException {
		super.load(reader);
		validateMissingRequiredKeys();
	}

	@Override
	public synchronized void loadFromXML(InputStream in) throws IOException, InvalidPropertiesFormatException {
		super.loadFromXML(in);
		validateMissingRequiredKeys();
	}

	private void validateMissingRequiredKeys() {
		// Validate that all required keys are present
		List<String> missingKeys = getMissingRequiredKeys();
		if (!missingKeys.isEmpty()) {
			throw new PersistenceException("Required property keys are missing: " + missingKeys);
		}
	}

	public List<String> getMissingRequiredKeys() {
		List<String> missingKeys = new ArrayList<>();
		for (Key key : Key.values()) {
			try {
				if (Key.class.getField(key.toString()).isAnnotationPresent(Required.class)) {
					if (isNull(getProperty(key.toString()))) {
						missingKeys.add(key.toString());
					}
				}
			} catch (NoSuchFieldException e) {
				// Cannot happen
				assert false : e;
			}
		}

		return missingKeys;
	}

	/**
	 * Denotes if a Key is required
	 * 
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public static @interface Required {
	}

	/**
	 * The property keys used to back the configuration property methods
	 *
	 */
	public static enum Key {
		@Required host, @Required port, @Required graph, persistenceProvider;
	}

	private static final Logger LOG = LoggerFactory.getLogger(DSEPersistenceConfigurationProperties.class);

	@Override
	public String getPersistenceRepository() {
		return getProperty(Key.persistenceProvider.toString());
	}

	@Override
	public String getHost() {
		return getProperty(Key.host.toString());
	}

	@Override
	public int getPort() {
		return Integer.parseInt(getProperty(Key.port.toString()));
	}

	@Override
	public String getGraph() {
		return getProperty(Key.graph.toString());
	}

	@Override
	public String getEndpoint() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AccessToken getAccessToken() {
		// TODO Auto-generated method stub
		return null;
	}
}
