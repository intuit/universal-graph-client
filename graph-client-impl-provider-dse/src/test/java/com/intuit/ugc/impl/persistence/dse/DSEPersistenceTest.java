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

import static org.testng.Assert.fail;

import org.testng.annotations.Test;

import com.intuit.ugc.impl.core.queryplan.MutationExecutorFactory;
import com.intuit.ugc.impl.core.queryplan.OperationFeederImpl;
import com.intuit.ugc.impl.core.queryplan.OperationPipeline;
import com.intuit.ugc.impl.core.spi.GraphVisitor;
import com.intuit.ugc.impl.persistence.dse.helper.DSEConfigurationMock;
import com.intuit.ugc.impl.persistence.dse.helper.DSEConnectionManagerMock;

/**
 * 
 * @author nverma1
 *
 */
public class DSEPersistenceTest {
	@Test
	public void testInitializeDSEPersistence() {
		GraphVisitor repository = new DSEGraphVisitor(new DSEConnectionManagerMock(new DSEConfigurationMock()));
		MutationExecutorFactory factory = new MutationExecutorFactory() {
			
			@Override
			public OperationFeederImpl create(OperationPipeline pipeline) {
				return null;
			}
		};
		
		try{
			DSEPersistence dsePersistence = new DSEPersistence(repository , factory);
		}catch(Exception e){
			fail("initialization shouldn't throw an error");
		}
	}
}
