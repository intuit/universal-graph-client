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

import static org.testng.Assert.assertNotNull;

import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.datastax.driver.dse.graph.SimpleGraphStatement;
import com.intuit.ugc.impl.persistence.dse.helper.DSEConfigurationMock;

import mockit.Mocked;

/**
 * 
 * @author nverma1
 *
 */
public class DSEConnectionManagerTest {
	@Mocked DSEGraphSession dseGraphSession;
	
	@Test
	public void testGetStatement(){
		DSEConfigurationMock config = new DSEConfigurationMock();
		DSEConnectionManager dseConnectionManager = new DSEConnectionManager(config);
		SimpleGraphStatement statement = dseConnectionManager.getStatement("mock-command");
		
		AssertJUnit.assertNotNull(statement);
		AssertJUnit.assertEquals(statement.getGraphName(), config.getGraph());
		assertNotNull(dseConnectionManager.getDSEGraphSession());
	}
}
