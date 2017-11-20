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

import com.datastax.driver.dse.DseCluster;
import com.datastax.driver.dse.DseSession;

/**
 * This class provides functionality to connect DSE Graph
 * 
 * @author nverma1
 *
 */
public class DSEGraphSession {
	private DseCluster dseCluster = null;
	private DseSession dseSession = null;

	/**
	 * Constructor
	 * 
	 * @param config
	 */
	public DSEGraphSession(DSEPersistenceConfiguration config) {
		init(config);
	}
	
	/**
	 * Initilizes the dse cluster and session.
	 * Scope is protected to enable mocking this during testing.
	 * @param config
	 */
	protected void init(DSEPersistenceConfiguration config){
		dseCluster = DseCluster.builder().addContactPoint(config.getHost()).withPort(config.getPort()).build();
		dseSession = dseCluster.connect();
	}
	
	/**
	 * Get DSESession
	 * 
	 * @return
	 */
	public DseSession getSession() {
		return dseSession;
	}
	
	/**
	 * Close connection
	 */
	public void closeConnections() {
		if (dseSession != null) {
			dseSession.close();
			dseSession = null;
		}
		if (dseCluster != null) {
			dseCluster.close();
			dseCluster = null;
		}
	}
	
}
