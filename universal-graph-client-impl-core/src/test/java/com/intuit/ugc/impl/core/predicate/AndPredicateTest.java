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
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.util.Collection;
import java.util.Collections;

import org.testng.annotations.Test;

import com.intuit.ugc.api.Attribute;

/**
 * 
 * @author nverma1
 *
 */
public class AndPredicateTest {

	@Test
	public void testAndPredicateInitWithNoArgs(){
		AndPredicate andPredicate = new AndPredicate();
		StreamPredicate[] predicates = new StreamPredicate[0];
		andPredicate.setPredicates(predicates);
		assertNotNull(andPredicate.getPredicates());
	}
	
	@Test
	public void testAndPredicateSetTwice(){
		StreamPredicate[] predicates = new StreamPredicate[0];
		AndPredicate andPredicate = new AndPredicate(predicates);
		try{
			andPredicate.setPredicates(predicates);
			fail("again setting predicates should throw");
		}catch(Exception e){
			assertTrue(e instanceof IllegalStateException);
		}
	}
	
	@Test
	public void testApplyPredicates(){
		StreamPredicate[] predicates = new StreamPredicate[0];
		AndPredicate andPredicate = new AndPredicate(predicates);
		Collection<Attribute> attributes = Collections.emptyList();
		assertTrue(andPredicate.apply(attributes));
	}
	
	@Test
	public void testApplyToNullPredicates(){
		StreamPredicate[] predicates = new StreamPredicate[1];
		predicates[0] = new EqualToPredicate();
		AndPredicate andPredicate = new AndPredicate(predicates);
		Collection<Attribute> attributes = Collections.emptyList();
		assertFalse(andPredicate.apply(attributes));
	}
}
