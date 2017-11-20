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
import com.intuit.ugc.api.Queries;
import com.intuit.ugc.api.Relationship;
import com.intuit.ugc.api.Attribute.Metadata;
import com.intuit.ugc.api.Attribute.Name;
import com.intuit.ugc.api.Entity.ID;
import com.intuit.ugc.api.Entity.Mutation;
import com.intuit.ugc.impl.core.GraphAttribute;
import com.intuit.ugc.impl.core.GraphEntity;
import com.intuit.ugc.impl.core.GraphLookUpQuery;
import com.intuit.ugc.impl.core.GraphPredicate;
import com.intuit.ugc.impl.core.GraphRelationship;
import com.intuit.ugc.impl.core.helper.MockGraphPersistence;
import com.intuit.ugc.impl.core.helper.MockGraphVisitor;
import com.intuit.ugc.impl.core.helper.MockMetadata;
import com.intuit.ugc.impl.core.helper.MockMutationExecuterFactory;
import com.intuit.ugc.impl.core.helper.TartanImplTestConstants;
import com.intuit.ugc.impl.core.helper.TartanImplTestUtil;
import com.intuit.ugc.impl.core.queryplan.MutationExecutorFactory;
import com.intuit.ugc.impl.core.spi.GraphVisitor;

/**
 * 
 * @author nverma1
 *
 */
public class AbstractGraphPersistenceTest {
	
	@Test
	public void testAGPCreationWithNullRepository(){
		MockMutationExecuterFactory mutationExecutionFactory = new MockMutationExecuterFactory();
		try{
			@SuppressWarnings("unused")
			MockGraphPersistence mockGraphPersistence = new MockGraphPersistence(null, mutationExecutionFactory);
			fail("Should throw an exception as repository is null");
		}catch(Exception e){
			assertTrue(e instanceof IllegalArgumentException);
			assertEquals(e.getMessage(), "Parameter \"persistenceRepository::com.intuit.ugc.impl.core.spi.GraphVisitor\" cannot be null");
		}
	}
	
	@Test
	public void testAGPCreationWithNullFactory(){
		GraphVisitor repository = new MockGraphVisitor();
		try{
			@SuppressWarnings("unused")
			MockGraphPersistence mockGraphPersistence = new MockGraphPersistence(repository, null);
			fail("Should throw an exception as repository is null");
		}catch(Exception e){
			assertTrue(e instanceof IllegalArgumentException);
			assertEquals(e.getMessage(), "Parameter \"factor::com.intuit.ugc.impl.core.queryplan.MutationExecutorFactory\" cannot be null");
		}
	}
	
	@Test
	public void testSuccessfulAGPCreation(){
		MockGraphPersistence mockGraphPersistence = createGraphPersistence();
		assertNotNull(mockGraphPersistence);
	}

	private MockGraphPersistence createGraphPersistence() {
		MutationExecutorFactory mutationExecutionFactory = new MockMutationExecuterFactory();
		GraphVisitor repository = new MockGraphVisitor();
		MockGraphPersistence mockGraphPersistence = new MockGraphPersistence(repository, mutationExecutionFactory);
		return mockGraphPersistence;
	}
	
	@Test
	public void testAllocateID(){
		MockGraphPersistence graphPersistence = createGraphPersistence();
		assertTrue(graphPersistence.allocateID() instanceof ID);
	}
	
	@Test
	public void testLookup(){
		MockGraphPersistence graphPersistence = createGraphPersistence();
		assertTrue(graphPersistence.lookup() instanceof GraphLookUpQuery);
	}
	
	@Test
	public void testQueryGraph(){
		MockGraphPersistence graphPersistence = createGraphPersistence();
		assertTrue(graphPersistence.queryGraph() instanceof Queries.GraphQuery);

	}
	
	@Test
	public void testPrepareMutationWithNullEntity(){
		MockGraphPersistence graphPersistence = createGraphPersistence();
		try{
			Entity entity = null;
			graphPersistence.prepareMutation(entity);
			fail("Should have thrown with null entity");
		}catch(Exception e){
			assertTrue(e instanceof IllegalArgumentException);
			assertEquals(e.getMessage(), "Parameter \"entity::com.intuit.ugc.api.Entity\" cannot be null");
		}
	}
	
	@Test
	public void testPrepareMutationWithValidEntity(){
		Entity entity = createGraphEntity();
		MockGraphPersistence graphPersistence = createGraphPersistence();
		Mutation mutation = graphPersistence.prepareMutation(entity);
		assertNotNull(mutation);
	}

	private Entity createGraphEntity() {
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
	public void testPrepareMutationWithNullRelationship(){
		MockGraphPersistence graphPersistence = createGraphPersistence();
		try{
			Relationship relationship = null;
			graphPersistence.prepareMutation(relationship);
			fail("Should have thrown with null relationship");
		}catch(Exception e){
			assertTrue(e instanceof IllegalArgumentException);
			assertEquals(e.getMessage(), "Parameter \"relationship::com.intuit.ugc.api.Relationship\" cannot be null");
		}
	}
	
	@Test
	public void testPrepareMutationWithValidRelationship(){
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
		MockGraphPersistence graphPersistence = createGraphPersistence();
		Relationship.Mutation mutation = graphPersistence.prepareMutation(graphRelationship);
		assertNotNull(mutation);
	}
	
	@Test
	public void testPredicate(){
		MockGraphPersistence graphPersistence = createGraphPersistence();
		assertTrue(graphPersistence.predicates() instanceof GraphPredicate);
	}
	
	@Test
	public void testPrepareBatchMutation(){
		MockGraphPersistence graphPersistence = createGraphPersistence();
		assertTrue(graphPersistence.prepareBatchMutation() instanceof BatchMutation);
	}
}
