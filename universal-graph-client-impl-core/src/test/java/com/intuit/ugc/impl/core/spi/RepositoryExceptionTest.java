/*
 * Copyright (c) 2017.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    ajain17 - API and implementation and initial documentation
 *    nverma1 - enhancements
 */

package com.intuit.ugc.impl.core.spi;

import static org.testng.Assert.fail;

import org.testng.annotations.Test;

/**
 * 
 * @author nverma1
 *
 */
public class RepositoryExceptionTest {
	@Test
	public void testCreateRepositoryException(){
		try{
			RepositoryException ex = new RepositoryException(new Exception());
			ex = new RepositoryException("test-exception", new Exception());
		}catch(Exception e){
			fail("Shouldn't have thrown an exception");
		}
	}
}
