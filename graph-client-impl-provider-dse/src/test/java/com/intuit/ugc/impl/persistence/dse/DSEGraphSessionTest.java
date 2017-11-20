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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.fail;

import org.testng.annotations.Test;

import com.datastax.driver.dse.DseCluster;
import com.datastax.driver.dse.DseCluster.Builder;
import com.datastax.driver.dse.DseSession;
import com.intuit.ugc.impl.persistence.dse.helper.DSEConfigurationMock;

import mockit.Expectations;
import mockit.Mocked;

/**
 * 
 * @author nverma1
 *
 */
public class DSEGraphSessionTest {
	@Mocked DseCluster dseCluster;
	@Mocked DseSession dseSession;
	@Mocked Builder builder;

	@Test
	public void testCreateDSESession(){
		DSEConfigurationMock config = new DSEConfigurationMock();
		
		new Expectations() {{
			dseCluster.builder();result = builder;
			builder.addContactPoint(config.getHost());result=builder;
			builder.withPort(config.getPort()); result = builder;
			builder.build();result=dseCluster;
			dseCluster.connect();result = dseSession;
			dseSession.close();
			dseCluster.close();
		}};
		
		DSEGraphSession dseGraphSession = new DSEGraphSession(config);
		assertNotNull(dseGraphSession);
		assertEquals(dseGraphSession.getSession(), dseSession);
		try{
			dseGraphSession.closeConnections();
		}catch(Exception e){
			fail("shouldn't have thrown exception");
		}
	}
}
