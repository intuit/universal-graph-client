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
import static org.testng.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.Test;

import com.intuit.ugc.api.Attribute;
import com.intuit.ugc.api.Attribute.Name;
import com.intuit.ugc.impl.core.GraphAttribute;
import com.intuit.ugc.impl.core.helper.MockMetadata;

/**
 * 
 * @author nverma1
 *
 */
public class StreamPredicateBuilderTest {

	@Test
	public void testStreamPredicateBuilderAndWithMultiPredicate() {
		StreamPredicateBuilder spb = new StreamPredicateBuilder();
		try{
			spb.and(null, null);
			fail("should throw unsupported operation exception");
		}catch(Exception e){
			assertTrue(e instanceof UnsupportedOperationException);
		}
	}
	
	@Test
	public void testStreamPredicateBuilderOrWithMultiPredicate() {
		StreamPredicateBuilder spb = new StreamPredicateBuilder();
		try{
			spb.or(null, null);
			fail("should throw unsupported operation exception");
		}catch(Exception e){
			assertTrue(e instanceof UnsupportedOperationException);
		}
	}
	
	@Test
	public void testStreamPredicateBuilderAttribute() {
		StreamPredicateBuilder spb = new StreamPredicateBuilder();
		try{
			spb.attribute(null);
			fail("should throw unsupported operation exception");
		}catch(Exception e){
			assertTrue(e instanceof UnsupportedOperationException);
		}
	}
	
	@Test
	public void testStreamPredicateBuilderSetAttribute(){
		StreamPredicateBuilder spb = new StreamPredicateBuilder();
		Name attributeName = Attribute.Name.valueOf("attr-name");
		spb.setAttribute(attributeName );
		assertEquals(spb.getAttribute(), attributeName);
	}
	
	@Test
	public void testStreamPredicateSetMultiAttributes(){
		StreamPredicateBuilder spb = new StreamPredicateBuilder();
		Name attributeName = Attribute.Name.valueOf("attr-name");
		Object attributeValue = "attr-value";
		GraphAttribute graphAttribute = new GraphAttribute(attributeName, attributeValue, new MockMetadata());
		List<Attribute> attributes = new ArrayList<Attribute>(
				Arrays.asList(graphAttribute));
		StreamPredicate predicate = new EqualToPredicate(attributeName, attributeValue);
		spb.addPredicate(predicate);
		assertTrue(spb.apply(attributes));
	}
	
	@Test
	public void getComparison(){
		StreamPredicateBuilder spb = new StreamPredicateBuilder();
		assertNotNull(spb.getComparison());
	}
	
	@Test
	public void testOrWithDiffPredicateInstance(){
		StreamPredicateBuilder spb = new StreamPredicateBuilder();
		StreamPredicateBuilder spb2 = new StreamPredicateBuilder();
		try{
			spb.or(spb2);
			fail("should throw");
		}catch(Exception e){
			assertTrue(e instanceof IllegalStateException);
		}
	}
	
	@Test
	public void testOrWithNullPredicateInstance(){
		StreamPredicateBuilder spb = new StreamPredicateBuilder();
		StreamPredicateBuilder spb2 = null;
		try{
			spb.or(spb2);
			fail("should throw");
		}catch(Exception e){
			assertTrue(e instanceof IllegalStateException);
		}
	}
	
	@Test
	public void testAndWithNullPredicateInstance(){
		StreamPredicateBuilder spb = new StreamPredicateBuilder();
		StreamPredicateBuilder spb2 = null;
		try{
			spb.and(spb2);
			fail("should throw");
		}catch(Exception e){
			assertTrue(e instanceof IllegalStateException);
		}
	}
	
	@Test
	public void testOrSamePredicateInstance(){
		StreamPredicateBuilder spb = new StreamPredicateBuilder();
		Name attributeName = Attribute.Name.valueOf("attr-name");
		Object attributeValue = "attr-value";
		StreamPredicate predicate = new EqualToPredicate(attributeName, attributeValue);
		spb.addPredicate(predicate);
		spb.addPredicate(predicate);
		assertEquals(spb.or(spb), spb);
	}
	
	@Test
	public void testAndWithDiffPredicateInstance(){
		StreamPredicateBuilder spb = new StreamPredicateBuilder();
		StreamPredicateBuilder spb2 = new StreamPredicateBuilder();
		try{
			spb.and(spb2);
			fail("should throw");
		}catch(Exception e){
			assertTrue(e instanceof IllegalStateException);
		}
	}
	
	@Test
	public void testAndSamePredicateInstance(){
		StreamPredicateBuilder spb = new StreamPredicateBuilder();
		Name attributeName = Attribute.Name.valueOf("attr-name");
		Object attributeValue = "attr-value";
		StreamPredicate predicate = new EqualToPredicate(attributeName, attributeValue);
		spb.addPredicate(predicate);
		spb.addPredicate(predicate);
		assertEquals(spb.and(spb), spb);
	}
}
