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

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.util.Properties;

import org.testng.annotations.Test;

/**
 * 
 * @author nverma1
 *
 */
public class DSEPersistenceConfigurationTest {
	@Test
	public void testDSEPersistenceConfigurationWithoutArgument(){
		try{
			DSEPersistenceConfigurationProperties props = new DSEPersistenceConfigurationProperties();
			props.load(null, null);
		}catch(Exception e){
			assertTrue(e instanceof IllegalArgumentException);
		}
	}
	
	@Test
	public void testPersistenceConfigurationWithDefaultProperties(){
		try{
			Properties defaults = new Properties();
			DSEPersistenceConfigurationProperties props = new DSEPersistenceConfigurationProperties(defaults );
		}catch(Exception e){
			fail("shouldn't throw an exception");
		}

	}
}
