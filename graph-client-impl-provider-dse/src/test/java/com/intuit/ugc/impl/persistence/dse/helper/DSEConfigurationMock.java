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

package com.intuit.ugc.impl.persistence.dse.helper;

import com.intuit.ugc.impl.persistence.dse.DSEPersistenceConfigurationProperties;

/**
 * 
 * @author nverma1
 *
 */
public class DSEConfigurationMock extends DSEPersistenceConfigurationProperties{

	/**
	 * Default serial version id
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getGraph() {
		return "mock-graph";
	}
	
	@Override
	public String getHost() {
		return "127.0.01";
	}
	
	@Override
	public int getPort() {
		return 1234;
	}
}
