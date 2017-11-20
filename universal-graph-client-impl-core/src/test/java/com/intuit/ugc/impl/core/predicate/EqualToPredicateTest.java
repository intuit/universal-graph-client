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
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.testng.annotations.Test;

import com.intuit.ugc.api.Attribute;
import com.intuit.ugc.api.Attribute.Metadata;
import com.intuit.ugc.impl.core.GraphAttribute;
import com.intuit.ugc.impl.core.helper.MockMetadata;

/**
 * 
 * @author nverma1
 *
 */
public class EqualToPredicateTest {
	
	@Test
	public void testEqualToPredicateInitWithoutParams(){
		EqualToPredicate equalToPredicate = new EqualToPredicate();
		Collection<Attribute> attributes = Collections.emptyList();
		assertFalse(equalToPredicate.apply(attributes));
	}
	
	@Test
	public void testEqualToPredicateInitWithOnlyAttrName(){
		Attribute.Name attributeName = Attribute.Name.valueOf("test-attr");
		EqualToPredicate equalToPredicate = new EqualToPredicate(attributeName);
		Collection<Attribute> attributes = Collections.emptyList();
		assertFalse(equalToPredicate.apply(attributes));
	}
	
	@Test
	public void testEqualToPredicateInitWithAttrNameAndValueNonMatch(){
		Attribute.Name attributeName = Attribute.Name.valueOf("test-attr");
		EqualToPredicate equalToPredicate = new EqualToPredicate(attributeName, "test-attr-value");
		Collection<Attribute> attributes = Collections.emptyList();
		assertFalse(equalToPredicate.apply(attributes));
	}
	
	@Test
	public void testEqualToPredicateInitWithAttrNameAndValueMatch(){
		Attribute.Name attributeName = Attribute.Name.valueOf("test-attr");
		Object attributeValue = "test-attr-value";
		EqualToPredicate equalToPredicate = new EqualToPredicate(attributeName, attributeValue);
		GraphAttribute graphAttribute = new GraphAttribute(attributeName, attributeValue, new MockMetadata());
		List<Attribute> attributes = new ArrayList<Attribute>(
				Arrays.asList(graphAttribute));
		assertTrue(equalToPredicate.apply(attributes));
	}
}
