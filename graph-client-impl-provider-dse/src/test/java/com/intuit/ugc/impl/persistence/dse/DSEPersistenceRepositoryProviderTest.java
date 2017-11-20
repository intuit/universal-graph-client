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
import static org.testng.Assert.fail;

import org.testng.annotations.Test;

import com.intuit.ugc.impl.persistence.dse.helper.DSEConfigurationMock;
import com.intuit.ugc.impl.persistence.dse.helper.DSEConnectionManagerMock;

/**
 * 
 * @author nverma1
 *
 */
public class DSEPersistenceRepositoryProviderTest {

	@Test
	public void testInit() {
		try {
			DSEPersistenceRepositoryProvider provider = new DSEPersistenceRepositoryProvider(
					new DSEConnectionManagerMock(new DSEConfigurationMock()));
			DSEGraphVisitor dseGraphVisitor = provider.get();
			assertNotNull(dseGraphVisitor);
		} catch (Exception e) {
			fail("shouldn't have thrown");
		}
	}
}
