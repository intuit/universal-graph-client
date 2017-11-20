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

import java.util.UUID;

import org.testng.annotations.Test;

import com.intuit.ugc.api.Entity;
import com.intuit.ugc.api.Attribute.Metadata;
import com.intuit.ugc.api.Attribute.Name;
import com.intuit.ugc.impl.core.GraphAttribute;
import com.intuit.ugc.impl.core.GraphEntity;
import com.intuit.ugc.impl.core.helper.MockMetadata;
import com.intuit.ugc.impl.core.helper.TartanImplTestConstants;

/**
 * 
 * @author nverma1
 *
 */
public class GraphEntityTest {
	@Test
	public void testGraphEntityCreation(){
		GraphEntity.Builder builder = new GraphEntity.Builder();
		Name name = TartanImplTestConstants.ATTR_DUMMY_ATTRIBUTE_NAME;
		String attributeValue = "Dummy Attribute";
		Metadata metadata = new MockMetadata();
		GraphAttribute graphAttribute = new GraphAttribute(name, attributeValue, metadata);
		builder.addAttribute(graphAttribute);
		Entity.ID newEntityID = Entity.ID.valueOf("urn:entity:" + TartanImplTestConstants.ATTR_PARENT_ID.getName()
				+ "." + TartanImplTestConstants.ATTR_PARENT_ID.getName() + "/" + UUID.randomUUID());
		builder.setID(newEntityID);
		GraphEntity builtEntity = builder.build();
		assertEquals(builtEntity.getID(), newEntityID);
		assertEquals(builtEntity.getAttribute(name), graphAttribute);
		String toStringValue = "GraphEntity{id="+newEntityID+", attributes={" + name+"="+graphAttribute+"}}";
		assertEquals(builtEntity.toString(), toStringValue);
		assertEquals(1, builtEntity.getAttributes().size());
	}
	
	@Test
	public void testGraphEntityCreationWithNullEntityID(){
		GraphEntity.Builder builder = new GraphEntity.Builder();
		String expectedErrorMessage = "Missing required properties: \"[id]\"";
		try{
			builder.build();
			fail("Should have thrown exception with null id");
		}catch(Exception e){
			assertTrue(e instanceof IllegalStateException);
			assertEquals(e.getMessage(), expectedErrorMessage);
		}
	}
	
	@Test
	public void tesGraphEntityBuilderSetWithNullEntityID(){
		GraphEntity.Builder builder = new GraphEntity.Builder();
		String expectedErrorMessage = "Parameter \"value::com.intuit.ugc.api.Entity$ID\" cannot be null";
		try{
			builder.setID(null);
			fail("Should have thrown exception with null id");
		}catch(Exception e){
			assertTrue(e instanceof IllegalArgumentException);
			assertEquals(e.getMessage(), expectedErrorMessage);
		}
	}
}
