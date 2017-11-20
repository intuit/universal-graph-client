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
import static org.testng.Assert.assertTrue;

import java.util.UUID;

import org.testng.annotations.Test;

import com.intuit.ugc.api.Entity;
import com.intuit.ugc.api.Attribute.Metadata;
import com.intuit.ugc.api.Attribute.Name;
import com.intuit.ugc.impl.core.GraphAttribute;
import com.intuit.ugc.impl.core.GraphRelationship;
import com.intuit.ugc.impl.core.helper.MockMetadata;
import com.intuit.ugc.impl.core.helper.TartanImplTestConstants;
import com.intuit.ugc.impl.core.helper.TartanImplTestUtil;

/**
 * 
 * @author nverma1
 *
 */
public class GraphRelationshipTest {

	@Test
	public void testGraphRelationshipCreation(){
		TartanImplTestUtil util = new TartanImplTestUtil();
		Entity.ID sourceEntityID = Entity.ID.valueOf("urn:entity:" + TartanImplTestConstants.ATTR_PARENT_ID.getName()
		+ "." + TartanImplTestConstants.ATTR_PARENT_ID.getName() + "/" + UUID.randomUUID());
		Entity.ID targetEntityID = Entity.ID.valueOf("urn:entity:" + TartanImplTestConstants.ATTR_CHILD_ID.getName()
		+ "." + TartanImplTestConstants.ATTR_PARENT_ID.getName() + "/" + UUID.randomUUID());
		Name attributeName = TartanImplTestConstants.ATTR_DUMMY_ATTRIBUTE_NAME;
		String attributeValue = "Dummy Attribute";
		Metadata metadata = new MockMetadata();
		GraphAttribute graphAttribute = new GraphAttribute(attributeName, attributeValue, metadata);
		GraphRelationship graphRelationship = util.buildGraphRelationship(
				TartanImplTestConstants.ATTR_DUMMY_RELATIONSHIP_NAME, sourceEntityID, targetEntityID, graphAttribute);
		assertNotNull(graphRelationship);
		assertEquals(graphRelationship.getName(),TartanImplTestConstants.ATTR_DUMMY_RELATIONSHIP_NAME);
		assertEquals(graphRelationship.getSourceID(), sourceEntityID);
		assertEquals(graphRelationship.getTargetID(), targetEntityID);
		assertEquals(graphRelationship.getAttributes().size(), 1);
		assertEquals(graphRelationship.getAttribute(attributeName), graphAttribute);
	}
	
	@Test
	public void testCreateGraphRelationshipWithNullParams(){
		GraphRelationship.Builder builder = new GraphRelationship.Builder();
		try{
			builder.build();
		}catch(Exception e){
			assertTrue(e instanceof IllegalStateException);
			assertEquals(e.getMessage(), "Missing required properties: \"[name, sourceID, targetID]\"");
		}
	}
}
