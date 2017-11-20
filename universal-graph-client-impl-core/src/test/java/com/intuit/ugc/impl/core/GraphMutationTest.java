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
import static org.testng.Assert.fail;

import java.util.UUID;

import org.testng.annotations.Test;

import com.intuit.ugc.api.BatchMutation;
import com.intuit.ugc.api.Entity;
import com.intuit.ugc.api.NewEntity;
import com.intuit.ugc.api.NewRelationship;
import com.intuit.ugc.api.OperationResult;
import com.intuit.ugc.api.Attribute.Metadata;
import com.intuit.ugc.api.Attribute.Name;
import com.intuit.ugc.api.BatchMutation.Result;
import com.intuit.ugc.api.Entity.Mutation;
import com.intuit.ugc.impl.core.GraphAttribute;
import com.intuit.ugc.impl.core.GraphEntity;
import com.intuit.ugc.impl.core.GraphMutation;
import com.intuit.ugc.impl.core.GraphRelationship;
import com.intuit.ugc.impl.core.helper.MockMetadata;
import com.intuit.ugc.impl.core.helper.MockMutationExecuterFactory;
import com.intuit.ugc.impl.core.helper.TartanImplTestConstants;
import com.intuit.ugc.impl.core.helper.TartanImplTestUtil;

/**
 * 
 * @author nverma1
 *
 */
public class GraphMutationTest {
	@Test
	public void testCreateEntityWithNullEntity(){
		MockMutationExecuterFactory mutationExecutionFactory = new MockMutationExecuterFactory();
		GraphMutation graphMutation = new GraphMutation(mutationExecutionFactory);
		try{
			graphMutation.createEntity(null);
			fail("Should have thrown an exception with null entity");
		}catch(Exception e){
			assertTrue(e instanceof IllegalArgumentException);
			assertEquals(e.getMessage(), "Parameter \"newEntity\" cannot be null");
		}
	}
	
	@Test
	public void testCreateEntityWithValidEntity(){
		MockMutationExecuterFactory mutationExecutionFactory = new MockMutationExecuterFactory();
		GraphMutation graphMutation = new GraphMutation(mutationExecutionFactory);
		TartanImplTestUtil util = new TartanImplTestUtil();
		NewEntity newEntity = util.createNewEntity(TartanImplTestConstants.ATTR_PARENT_ID);
		BatchMutation createEntity = graphMutation.createEntity(newEntity);
		assertNotNull(createEntity);
	}
	
	@Test
	public void testCreateRelationshipWithNullRelationship(){
		MockMutationExecuterFactory mutationExecutionFactory = new MockMutationExecuterFactory();
		GraphMutation graphMutation = new GraphMutation(mutationExecutionFactory);
		try{
			graphMutation.createRelationship(null);
			fail("Should have thrown an exception with null relationship");
		}catch(Exception e){
			assertTrue(e instanceof IllegalArgumentException);
			assertEquals(e.getMessage(), "Parameter \"newRelationship\" cannot be null");
		}
	}
	
	@Test
	public void testCreateRelationshipWithValidRelationship(){
		TartanImplTestUtil util = new TartanImplTestUtil();
		NewEntity sourceEntity = util.createNewEntity(TartanImplTestConstants.ATTR_CHILD_KEY);
		NewEntity targetEntity = util.createNewEntity(TartanImplTestConstants.ATTR_PARENT_KEY);
		NewRelationship newRelationship = NewRelationship
				.between(sourceEntity.getEntityID(), targetEntity.getEntityID()).withLabel(TartanImplTestConstants.ATTR_PARENT_CHILD_KEY).build();
		
		MockMutationExecuterFactory mutationExecutionFactory = new MockMutationExecuterFactory();
		GraphMutation graphMutation = new GraphMutation(mutationExecutionFactory);
		BatchMutation createRelationship = graphMutation.createRelationship(newRelationship);
		assertNotNull(createRelationship);
	}
	
	
	@Test
	public void testExecute(){
		MockMutationExecuterFactory mutationExecutionFactory = new MockMutationExecuterFactory();
		GraphMutation graphMutation = new GraphMutation(mutationExecutionFactory);
		OperationResult<Result> execute = graphMutation.execute();
		assertNotNull(execute);
	}
	
	
	@Test
	public void testWithEntityWhenEntityIsNull(){
		MockMutationExecuterFactory mutationExecutionFactory = new MockMutationExecuterFactory();
		GraphMutation graphMutation = new GraphMutation(mutationExecutionFactory);
		try{
			graphMutation.withEntity(null);
			fail("Should have thrown an exception with null entity");
		}catch(Exception e){
			assertTrue(e instanceof IllegalArgumentException);
			assertEquals(e.getMessage(), "Parameter \"entity\" cannot be null");
		}
	}
	
	@Test
	public void testWithEntityWhenEntityIsNotNull(){
		MockMutationExecuterFactory mutationExecutionFactory = new MockMutationExecuterFactory();
		GraphMutation graphMutation = new GraphMutation(mutationExecutionFactory);
		Mutation withEntity = graphMutation.withEntity(buildEntity());
		assertNotNull(withEntity);
	}
	
	private Entity buildEntity(){
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
	public void testWithRelationshipWhenRelationshipIsNull(){
		MockMutationExecuterFactory mutationExecutionFactory = new MockMutationExecuterFactory();
		GraphMutation graphMutation = new GraphMutation(mutationExecutionFactory);
		try{
			graphMutation.withRelationship(null);
			fail("Should have thrown an exception with null entity");
		}catch(Exception e){
			assertTrue(e instanceof IllegalArgumentException);
			assertEquals(e.getMessage(), "Parameter \"relationship\" cannot be null");
		}
	}
	
	@Test
	public void testWithRelationshipWhenRelationshipIsNotNull(){
		MockMutationExecuterFactory mutationExecutionFactory = new MockMutationExecuterFactory();
		GraphMutation graphMutation = new GraphMutation(mutationExecutionFactory);
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
		assertNotNull(graphMutation.withRelationship(graphRelationship));
	}
}
