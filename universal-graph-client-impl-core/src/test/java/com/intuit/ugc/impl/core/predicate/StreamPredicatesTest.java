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

package com.intuit.ugc.impl.core.predicate;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import org.testng.annotations.Test;

/**
 * 
 * @author nverma1
 *
 */
public class StreamPredicatesTest {

	@Test
	public void testLessThan(){
		try{
			Object value = null;;
			StreamPredicates.lessThan(value);
			fail("Should have thrown");
		}catch(Exception e){
			assertTrue(e instanceof UnsupportedOperationException);
		}
	}
	
	@Test
	public void testGreaterThan(){
		try{
			Object value = null;;
			StreamPredicates.greaterThan(value);
			fail("Should have thrown");
		}catch(Exception e){
			assertTrue(e instanceof UnsupportedOperationException);
		}
	}
	
	@Test
	public void testNotEqualTo(){
		try{
			Object value = null;;
			StreamPredicates.notEqualTo(value);
			fail("Should have thrown");
		}catch(Exception e){
			assertTrue(e instanceof UnsupportedOperationException);
		}
	}
}
