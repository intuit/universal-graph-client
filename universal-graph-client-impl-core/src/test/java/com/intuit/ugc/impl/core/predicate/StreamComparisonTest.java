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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.intuit.ugc.api.Attribute;

/**
 * 
 * @author nverma1
 *
 */
public class StreamComparisonTest {

	@Test
	public void testStreamComparisonGreaterThan(){
		try{
			StreamPredicateBuilder pb = new StreamPredicateBuilder();
			StreamComparison streamComparison = new StreamComparison(pb );
			Object o = null;
			streamComparison.greaterThan(o);
		}catch(Exception e){
			assertTrue(e instanceof UnsupportedOperationException);
		}
	}
	
	@Test
	public void testStreamComparisonLessThan(){
		try{
			StreamPredicateBuilder pb = new StreamPredicateBuilder();
			StreamComparison streamComparison = new StreamComparison(pb );
			Object o = null;
			streamComparison.lessThan(o);
		}catch(Exception e){
			assertTrue(e instanceof UnsupportedOperationException);
		}
	}
	
	@Test
	public void testStreamComparisonNotEqualToThan(){
		try{
			StreamPredicateBuilder pb = new StreamPredicateBuilder();
			StreamComparison streamComparison = new StreamComparison(pb );
			Object o = null;
			streamComparison.notEqualTo(o);
		}catch(Exception e){
			assertTrue(e instanceof UnsupportedOperationException);
		}
	}
	
	@Test
	public void testStreamComparisonEqualsWithNullObject(){
		StreamPredicateBuilder pb = new StreamPredicateBuilder();
		StreamComparison streamComparison = new StreamComparison(pb);
		Attribute.Name attrName = Attribute.Name.valueOf("attr-name");
		StreamPredicate predicate = new EqualToPredicate(attrName);
		StreamPredicateBuilder equalToBuilder = streamComparison.get(attrName).equalTo(predicate);
		assertNotNull(equalToBuilder);
		assertEquals(equalToBuilder.getAttribute(), attrName);
	}
}
