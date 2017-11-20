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

import javax.inject.Inject;

import com.datastax.driver.dse.graph.SimpleGraphStatement;

/**
 * A connection manager that wraps up the underlying DSE connection. Also
 * contains API to create and return a
 * {@link com.datastax.driver.dse.graph.SimpleGraphStatement} to the caller and
 * one to create and return a
 * {@link com.intuit.ugc.impl.persistence.dse.DSEGraphSession} instance
 * 
 * @author nverma1
 *
 */
public class DSEConnectionManager {
	private DSEPersistenceConfiguration config;

	@Inject
	public DSEConnectionManager(DSEPersistenceConfiguration config) {
		this.config = config;
	}

	/**
	 * From the string passed, a
	 * {@link com.datastax.driver.dse.graph.SimpleGraphStatement} is created and
	 * returned to the caller for execution
	 * 
	 * @param cmd
	 *            a String representing a valid gremlin query.
	 * @return A {@link com.datastax.driver.dse.graph.SimpleGraphStatement}
	 */
	public SimpleGraphStatement getStatement(String cmd) {
		SimpleGraphStatement sVertexStatement = new SimpleGraphStatement(cmd);
		sVertexStatement.setGraphName(config.getGraph());
		return sVertexStatement;
	}

	/**
	 * returns a {@link com.intuit.ugc.impl.persistence.dse.DSEGraphSession}
	 * object. This session is required to run all DSE graph queries over it.
	 * 
	 * @return {@link com.intuit.ugc.impl.persistence.dse.DSEGraphSession}
	 */
	public DSEGraphSession getDSEGraphSession() {
		return new DSEGraphSession(config);
	}
}
