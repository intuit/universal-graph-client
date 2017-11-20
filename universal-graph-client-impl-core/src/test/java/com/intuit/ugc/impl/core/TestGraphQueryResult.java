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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.testng.annotations.Test;

import com.intuit.ugc.api.Entity;
import com.intuit.ugc.api.Relationship;
import com.intuit.ugc.api.Attribute.Metadata;
import com.intuit.ugc.api.Attribute.Name;
import com.intuit.ugc.impl.core.GraphAttribute;
import com.intuit.ugc.impl.core.GraphEntity;
import com.intuit.ugc.impl.core.GraphQueryResult;
import com.intuit.ugc.impl.core.GraphRelationship;
import com.intuit.ugc.impl.core.helper.MockMetadata;
import com.intuit.ugc.impl.core.helper.TartanImplTestConstants;
import com.intuit.ugc.impl.core.helper.TartanImplTestUtil;
import com.intuit.ugc.impl.core.spi.QueryResult;
import com.intuit.ugc.impl.core.spi.QueryResultImpl;
import com.intuit.ugc.impl.core.spi.QueryResult.CurrentOperationType;

/**
 * 
 * @author nverma1
 *
 */
public class TestGraphQueryResult {
	@Test
	public void testGetEntityWithEmptyList(){
		CurrentOperationType currentOperation = CurrentOperationType.ENTITY;
		QueryResult<Entity, Relationship> queryResult = new QueryResultImpl(currentOperation);
		GraphQueryResult graphQueryResult = new GraphQueryResult(queryResult );
		assertNull(graphQueryResult.getEntity());
	}
	
	@Test
	public void testGetEntityWithEntityList(){
		CurrentOperationType currentOperation = CurrentOperationType.ENTITY;
		QueryResult<Entity, Relationship> queryResult = new QueryResultImpl(currentOperation);
		List<Entity> entityList = new ArrayList<Entity>();
		GraphEntity builtEntity = getEntity();
		entityList.add(builtEntity);
		queryResult.setEntityOpResponse(entityList );
		GraphQueryResult graphQueryResult = new GraphQueryResult(queryResult );
		assertNotNull(graphQueryResult.getEntity());
		assertEquals(builtEntity, graphQueryResult.getEntity());
	}
	
	@Test
	public void testGetEntityList(){
		CurrentOperationType currentOperation = CurrentOperationType.ENTITY;
		QueryResult<Entity, Relationship> queryResult = new QueryResultImpl(currentOperation);
		List<Entity> entityList = new ArrayList<Entity>();
		GraphEntity builtEntity = getEntity();
		entityList.add(builtEntity);
		queryResult.setEntityOpResponse(entityList );
		GraphQueryResult graphQueryResult = new GraphQueryResult(queryResult );
		assertNotNull(graphQueryResult.getEntities());
		assertEquals(1, graphQueryResult.getEntities().size());
		assertEquals(builtEntity, (GraphEntity)graphQueryResult.getEntities().get(0));
	}

	private GraphEntity getEntity() {
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
		return builtEntity;
	}
	
	@Test
	public void testGetRealtionshipWithEmptyRelationshipList(){
		CurrentOperationType currentOperation = CurrentOperationType.RELATIONHIP;
		QueryResult<Entity, Relationship> queryResult = new QueryResultImpl(currentOperation);
		GraphQueryResult graphQueryResult = new GraphQueryResult(queryResult );
		assertNull(graphQueryResult.getRelationship());
	}
	
	@Test
	public void testGetRealtionshipWithNonEmptyRelationshipList(){
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
		
		CurrentOperationType currentOperation = CurrentOperationType.RELATIONHIP;
		QueryResult<Entity, Relationship> queryResult = new QueryResultImpl(currentOperation);
		List<Relationship>relationships = new ArrayList<Relationship>(Arrays.asList(graphRelationship));
		queryResult.setRelationshipOpResponse(relationships);
		GraphQueryResult graphQueryResult = new GraphQueryResult(queryResult );
		
		assertEquals(graphQueryResult.getRelationship(), graphRelationship);
		
		List<Relationship> relationshipsReturned = graphQueryResult.getRelationships();
		assertEquals(relationshipsReturned.size(), relationships.size());
		assertEquals(relationshipsReturned.get(0), graphRelationship);
	}
	
}
