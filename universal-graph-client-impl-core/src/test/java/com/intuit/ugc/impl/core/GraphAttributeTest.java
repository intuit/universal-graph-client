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
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.time.Instant;
import java.util.UUID;

import org.testng.annotations.Test;

import com.intuit.ugc.api.Attribute.Metadata;
import com.intuit.ugc.api.Attribute.Name;
import com.intuit.ugc.impl.core.GraphAttribute;
import com.intuit.ugc.impl.core.helper.MockMetadata;
import com.intuit.ugc.impl.core.helper.TartanImplTestConstants;

/**
 * 
 * @author nverma1
 *
 */
public class GraphAttributeTest {
	@Test
	public void testGraphAttributeCreationWithStringValue() {
		Name name = TartanImplTestConstants.ATTR_DUMMY_ATTRIBUTE_NAME;
		String attributeValue = "Dummy Attribute";
		Metadata metadata = new MockMetadata();
		GraphAttribute graphAttribute = new GraphAttribute(name, attributeValue, metadata);
		assertValues(name, metadata, graphAttribute);
		assertEquals(graphAttribute.getValue(String.class), attributeValue);
		assertEquals(graphAttribute.getString(), attributeValue);

	}

	@Test
	public void testGraphAttributeCreationWithNullAttributeValue() {
		Name name = TartanImplTestConstants.ATTR_DUMMY_ATTRIBUTE_NAME;
		String attributeValue = null;
		Metadata metadata = new MockMetadata();
		try {
			@SuppressWarnings("unused")
			GraphAttribute graphAttribute = new GraphAttribute(name, attributeValue, metadata);
			fail("Should have failed with null attribute value");
		} catch (Exception e) {
			assertTrue(e instanceof IllegalArgumentException);
			assertEquals(e.getMessage(), "Parameter \"attributeValue\" cannot be null");
		}
	}
	
	@Test
	public void testGraphAttributeCreationWithNullMetadata() {
		Name name = TartanImplTestConstants.ATTR_DUMMY_ATTRIBUTE_NAME;
		String attributeValue = "Dummy Attribute";
		Metadata metadata = null;
		try {
			@SuppressWarnings("unused")
			GraphAttribute graphAttribute = new GraphAttribute(name, attributeValue, metadata);
			fail("Should have failed with null attribute value");
		} catch (Exception e) {
			assertTrue(e instanceof IllegalArgumentException);
			assertEquals(e.getMessage(), "Parameter \"metadata\" cannot be null");
		}
	}

	@Test
	public void testGraphAttributeCreationWithIntegerValue() {
		Name name = TartanImplTestConstants.ATTR_DUMMY_ATTRIBUTE_NAME;
		Integer attributeValue = 1;
		Metadata metadata = new MockMetadata();
		GraphAttribute graphAttribute = new GraphAttribute(name, attributeValue, metadata);
		assertValues(name, metadata, graphAttribute);
		assertEquals(graphAttribute.getInteger(), attributeValue);
	}

	@Test
	public void testGraphAttributeCreationWithDoubleValue() {
		Name name = TartanImplTestConstants.ATTR_DUMMY_ATTRIBUTE_NAME;
		Double attributeValue = 1.1;
		Metadata metadata = new MockMetadata();
		GraphAttribute graphAttribute = new GraphAttribute(name, attributeValue, metadata);
		assertValues(name, metadata, graphAttribute);
		assertEquals(graphAttribute.getDouble(), attributeValue);
	}

	@Test
	public void testGraphAttributeCreationWithBooleanValue() {
		Name name = TartanImplTestConstants.ATTR_DUMMY_ATTRIBUTE_NAME;
		Boolean attributeValue = true;
		Metadata metadata = new MockMetadata();
		GraphAttribute graphAttribute = new GraphAttribute(name, attributeValue, metadata);
		assertValues(name, metadata, graphAttribute);
		assertEquals(graphAttribute.getBoolean(), attributeValue);
	}

	@Test
	public void testGraphAttributeCreationWithUUIDValue() {
		Name name = TartanImplTestConstants.ATTR_DUMMY_ATTRIBUTE_NAME;
		UUID attributeValue = UUID.randomUUID();
		Metadata metadata = new MockMetadata();
		GraphAttribute graphAttribute = new GraphAttribute(name, attributeValue, metadata);
		assertValues(name, metadata, graphAttribute);
		assertEquals(graphAttribute.getUUID(), attributeValue);
	}

	@Test
	public void testGraphAttributeCreationWithInstantValue() {
		Name name = TartanImplTestConstants.ATTR_DUMMY_ATTRIBUTE_NAME;
		Instant attributeValue = Instant.now();
		Metadata metadata = new MockMetadata();
		GraphAttribute graphAttribute = new GraphAttribute(name, attributeValue, metadata);
		assertValues(name, metadata, graphAttribute);
		assertEquals(graphAttribute.getInstant(), attributeValue);
	}

	private void assertValues(Name name, Metadata metadata, GraphAttribute graphAttribute) {
		assertEquals(graphAttribute.getMetadata(), metadata);
		assertEquals(graphAttribute.getName(), name);
	}
}
