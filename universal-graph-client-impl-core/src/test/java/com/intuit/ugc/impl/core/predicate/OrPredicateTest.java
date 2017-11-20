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

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.testng.annotations.Test;

import com.intuit.ugc.api.Attribute;
import com.intuit.ugc.impl.core.GraphAttribute;
import com.intuit.ugc.impl.core.helper.MockMetadata;

/**
 * 
 * @author nverma1
 *
 */
public class OrPredicateTest {

	@Test
	public void testOrPredicateInitWithoutParam(){
		OrPredicate orPredicate = new OrPredicate();
		assertNull(orPredicate.getPredicates());
		StreamPredicate[] predicates = new StreamPredicate[0];
		orPredicate.setPredicates(predicates);
		assertNotNull(orPredicate.getPredicates());
		try{
			orPredicate.setPredicates(predicates);
			fail("resetting predicates should throw exception");
		}catch(Exception e){
			assertTrue(e instanceof IllegalStateException);
		}
	}
	
	@Test
	public void testOrPredicateWithEmptyParams(){
		StreamPredicate[] predicates = new StreamPredicate[0];
		OrPredicate orPredicate = new OrPredicate(predicates);
		assertNotNull(orPredicate.getPredicates());
	}
	
	@Test
	public void testApplyWithValidAttributes(){
		Attribute.Name attributeName = Attribute.Name.valueOf("test-attr");
		Object attributeValue = "test-attr-value";
		EqualToPredicate equalToPredicate = new EqualToPredicate(attributeName, attributeValue);
		GraphAttribute graphAttribute = new GraphAttribute(attributeName, attributeValue, new MockMetadata());
		List<Attribute> attributes = new ArrayList<Attribute>(
				Arrays.asList(graphAttribute));

		StreamPredicate[] predicates = new StreamPredicate[1];
		predicates[0] = equalToPredicate;
		OrPredicate orPredicate = new OrPredicate(predicates);
		assertTrue(orPredicate.apply(attributes));
	}
	
	@Test
	public void testApplyWithInvalidAttributes(){
		Attribute.Name attributeName = Attribute.Name.valueOf("test-attr");
		Object attributeValue = "test-attr-value";
		EqualToPredicate equalToPredicate = new EqualToPredicate(attributeName, attributeValue+"1");
		GraphAttribute graphAttribute = new GraphAttribute(attributeName, attributeValue, new MockMetadata());
		List<Attribute> attributes = new ArrayList<Attribute>(
				Arrays.asList(graphAttribute));

		StreamPredicate[] predicates = new StreamPredicate[1];
		predicates[0] = equalToPredicate;
		OrPredicate orPredicate = new OrPredicate(predicates);
		assertFalse(orPredicate.apply(attributes));
	}
	
	@Test
	public void testApplyWithEmptyPredicateList(){
		StreamPredicate[] predicates = new StreamPredicate[0];
		OrPredicate orPredicate = new OrPredicate(predicates);
		assertFalse(orPredicate.apply(Collections.emptyList()));
	}
}
