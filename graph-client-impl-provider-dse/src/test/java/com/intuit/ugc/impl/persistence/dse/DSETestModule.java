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

import java.io.IOException;

import com.google.inject.AbstractModule;
import com.intuit.ugc.api.Persistence;
import com.intuit.ugc.api.PersistenceException;

/**
 * 
 * @author nverma1
 *
 */
public class DSETestModule extends AbstractModule {
	@Override
    protected void configure() {
		DSEPersistenceConfigurationProperties instance = new DSEPersistenceConfigurationProperties();
		 try {
	            instance.load("graph_client_dse_test.properties",null);
	        }
	        catch (IOException e) {
	            throw new PersistenceException(e);
	        }

	        bind(Persistence.Configuration.class).toInstance(instance);
	        bind(DSEPersistenceConfiguration.class).toInstance(instance);
	}
}
