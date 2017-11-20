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

package com.intuit.ugc.impl.core;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import org.testng.annotations.Test;

import com.intuit.ugc.api.Predicate;
import com.intuit.ugc.api.Attribute.Name;
import com.intuit.ugc.api.Predicate.Comparison;
import com.intuit.ugc.impl.core.GraphPredicate;
import com.intuit.ugc.impl.core.helper.TartanImplTestConstants;

/**
 * 
 * @author nverma1
 *
 */
public class GraphPredicateTest {
	@Test
	public void testGraphPredicateNoArgConstructor(){
		GraphPredicate graphPredicate = new GraphPredicate();
		assertNull(graphPredicate.getName());
		assertEquals(graphPredicate.getpOperation(), GraphPredicate.PredicateOperation.NONE);
		assertNull(graphPredicate.getpComparison());
		assertNull(graphPredicate.getValue());
	}
	
	@Test
	public void testGraphPredicateEquals(){
		Name name = TartanImplTestConstants.ATTR_DUMMY_ATTRIBUTE_NAME;
		GraphPredicate.GraphComparison graphComparison = new GraphPredicate.GraphComparison(name );
		GraphPredicate graphPredicate = (GraphPredicate) graphComparison.equalTo(TartanImplTestConstants.ATTR_DUMMY_ATTRIBUTE_NAME);
		compareGraphPredicateParams(name, graphPredicate);
		assertEquals(graphPredicate.getpComparison(), GraphPredicate.PredicateComparison.EQUAL);
	}
	
	@Test
	public void testGraphPredicateNotEqualTo(){
		Name name = TartanImplTestConstants.ATTR_DUMMY_ATTRIBUTE_NAME;
		GraphPredicate.GraphComparison graphComparison = new GraphPredicate.GraphComparison(name );
		GraphPredicate graphPredicate = (GraphPredicate) graphComparison.notEqualTo(TartanImplTestConstants.ATTR_DUMMY_ATTRIBUTE_NAME);
		compareGraphPredicateParams(name, graphPredicate);
		assertEquals(graphPredicate.getpComparison(), GraphPredicate.PredicateComparison.NOT_EQUAL);
	}
	
	@Test
	public void testGraphPredicateGreaterThan(){
		Name name = TartanImplTestConstants.ATTR_DUMMY_ATTRIBUTE_NAME;
		GraphPredicate.GraphComparison graphComparison = new GraphPredicate.GraphComparison(name );
		GraphPredicate graphPredicate = (GraphPredicate) graphComparison.greaterThan(TartanImplTestConstants.ATTR_DUMMY_ATTRIBUTE_NAME);
		compareGraphPredicateParams(name, graphPredicate);
		assertEquals(graphPredicate.getpComparison(), GraphPredicate.PredicateComparison.GREATER);
	}
	
	@Test
	public void testGraphPredicateLesserThan(){
		Name name = TartanImplTestConstants.ATTR_DUMMY_ATTRIBUTE_NAME;
		GraphPredicate.GraphComparison graphComparison = new GraphPredicate.GraphComparison(name );
		GraphPredicate graphPredicate = (GraphPredicate) graphComparison.lessThan(TartanImplTestConstants.ATTR_DUMMY_ATTRIBUTE_NAME);
		compareGraphPredicateParams(name, graphPredicate);
		assertEquals(graphPredicate.getpComparison(), GraphPredicate.PredicateComparison.LESS);
	}

	private void compareGraphPredicateParams(Name name, GraphPredicate graphPredicate) {
		assertEquals(graphPredicate.getName(), name);
		assertEquals(graphPredicate.getpOperation(), GraphPredicate.PredicateOperation.NONE);
		assertEquals(graphPredicate.getValue(), TartanImplTestConstants.ATTR_DUMMY_ATTRIBUTE_NAME);
	}
	
	@Test
	public void testGraphPredicateComparison(){
		Name name = TartanImplTestConstants.ATTR_DUMMY_ATTRIBUTE_NAME;
		GraphPredicate graphPredicate = new GraphPredicate();
		Comparison comparisonAttribute = graphPredicate.attribute(name);
		assertNotNull(comparisonAttribute);
	}
	
	@Test
	public void testPredicateAnd(){
		GraphPredicate graphPredicate1 = new GraphPredicate();
		GraphPredicate graphPredicate2 = new GraphPredicate();
		GraphPredicate graphPredicate3 = new GraphPredicate();
		GraphPredicate andPredicate = (GraphPredicate) graphPredicate1.and(graphPredicate2, graphPredicate3);
		assertEquals(andPredicate.getNextPredicate(), graphPredicate3);
		assertEquals(andPredicate, graphPredicate2);
	}
	
	@Test
	public void testPredicateOr(){
		GraphPredicate graphPredicate1 = new GraphPredicate();
		GraphPredicate graphPredicate2 = new GraphPredicate();
		Predicate andPredicate = graphPredicate1.or(graphPredicate2);
		assertEquals(andPredicate, graphPredicate2);
	}
}
