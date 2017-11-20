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

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.intuit.ugc.impl.persistence.dse.helper.DSEConfigurationMock;
import com.intuit.ugc.impl.persistence.dse.helper.DSEConnectionManagerMock;

import mockit.Mocked;

/**
 * 
 * @author nverma1
 *
 */
public class GraphEntityOperationsTest {
	
	@Mocked GraphRelationshipOperations graphRelationshipOperations;
	
	private GraphEntityOperations graphEntityOperations = null;
	
	@BeforeClass
	public void setup(){
		DSEPersistenceConfiguration config = new DSEConfigurationMock();
		DSEConnectionManager connectionManager = new DSEConnectionManagerMock(config );
		graphEntityOperations = new GraphEntityOperations(connectionManager , graphRelationshipOperations);
	}

	@Test
	public void testCreateEntity(){
		
	}
}
