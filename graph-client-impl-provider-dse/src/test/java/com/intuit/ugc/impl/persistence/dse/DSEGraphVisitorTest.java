/*
 * Copyright (c) 2017.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    ajain17 & nverma1 - API , implementation and initial documentation
 */

package com.intuit.ugc.impl.persistence.dse;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.testng.annotations.Test;

import com.datastax.driver.dse.graph.GraphResultSet;
import com.intuit.ugc.api.Attribute;
import com.intuit.ugc.api.Entity;
import com.intuit.ugc.api.Entity.ID;
import com.intuit.ugc.api.NewEntity;
import com.intuit.ugc.api.NewRelationship;
import com.intuit.ugc.api.Queries.GraphTraversal.Direction;
import com.intuit.ugc.api.Relationship;
import com.intuit.ugc.api.Relationship.Name;
import com.intuit.ugc.impl.core.GraphAttributeOperations;
import com.intuit.ugc.impl.core.GraphEntity;
import com.intuit.ugc.impl.core.GraphMutationResult;
import com.intuit.ugc.impl.core.GraphRelationship;
import com.intuit.ugc.impl.core.VisitOperationResult;
import com.intuit.ugc.impl.core.queryplan.operations.CreateEntity;
import com.intuit.ugc.impl.core.queryplan.operations.CreateRelationship;
import com.intuit.ugc.impl.core.queryplan.operations.DeleteEntity;
import com.intuit.ugc.impl.core.queryplan.operations.DeleteRelationship;
import com.intuit.ugc.impl.core.queryplan.operations.GetBatchEntityByID;
import com.intuit.ugc.impl.core.queryplan.operations.GetEntityByID;
import com.intuit.ugc.impl.core.queryplan.operations.GetEntityByProperty;
import com.intuit.ugc.impl.core.queryplan.operations.GraphTerminalOperation;
import com.intuit.ugc.impl.core.queryplan.operations.SelectEntities;
import com.intuit.ugc.impl.core.queryplan.operations.SelectRelationships;
import com.intuit.ugc.impl.core.queryplan.operations.UpdateEntity;
import com.intuit.ugc.impl.core.queryplan.operations.UpdateRelationship;
import com.intuit.ugc.impl.core.spi.QueryResult;
import com.intuit.ugc.impl.core.spi.QueryResult.CurrentOperationType;
import com.intuit.ugc.impl.core.spi.QueryResultImpl;
import com.intuit.ugc.impl.persistence.dse.helper.DSEConfigurationMock;
import com.intuit.ugc.impl.persistence.dse.helper.DSEConnectionManagerMock;

import mockit.Expectations;
import mockit.Mocked;

/**
 * 
 * @author nverma1
 *
 */
public class DSEGraphVisitorTest {
	@Mocked GraphEntityOperations entityOperations;
	@Mocked GraphRelationshipOperations relationshipOperations;
	@Mocked NewRelationship newRelationship;
	@Mocked GraphResultSet graphQueryResult;
	@Mocked QueryResult<Entity, Relationship> queryResult;

	@Test
	public void testCreateEntity(){
		DSEGraphVisitor dseGraphVisitor = new DSEGraphVisitor(new DSEConnectionManagerMock(new DSEConfigurationMock()));
		Entity.ID entityId = Entity.ID.valueOf("test-id");
		NewEntity newEntity = NewEntity.newInstance(entityId).build();
		CreateEntity operation = new CreateEntity(newEntity );
		GraphMutationResult queryResult = new GraphMutationResult();
		new Expectations(){
			{
				entityOperations.createEntity(newEntity); result=queryResult;
			}
		};
		dseGraphVisitor.visit(operation);
		assertEquals(dseGraphVisitor.getResult(), queryResult);
	}
	
	@Test
	public void testCreateRelationship(){
		DSEGraphVisitor dseGraphVisitor = new DSEGraphVisitor(new DSEConnectionManagerMock(new DSEConfigurationMock()));
		CreateRelationship operation = new CreateRelationship(newRelationship);
		final GraphResultSet graphResultSet = new GraphResultSet(null);
		
		new Expectations(){
			{
				relationshipOperations.createRelationship(newRelationship); result=graphResultSet;
			}
		};
		
		dseGraphVisitor.visit(operation);
		assertNotNull(dseGraphVisitor.getResult());
	}
	
	@Test
	public void testGetBatchEntityById(){
		DSEGraphVisitor dseGraphVisitor = new DSEGraphVisitor(new DSEConnectionManagerMock(new DSEConfigurationMock()));
		List<ID> entityIDs = new ArrayList<ID>(Arrays.asList(Entity.ID.valueOf("test")));
		GetBatchEntityByID operation = new GetBatchEntityByID(entityIDs);
		VisitOperationResult operationresult = new VisitOperationResult() {
		};
		
		new Expectations() {
			{
				entityOperations.getEntitiesByID(entityIDs); result = operationresult;
			}
		};
		
		dseGraphVisitor.visit(operation);
		assertEquals(dseGraphVisitor.getResult(), operationresult);
	}
	
	@Test
	public void testGetEntityById(){
		DSEGraphVisitor dseGraphVisitor = new DSEGraphVisitor(new DSEConnectionManagerMock(new DSEConfigurationMock()));
		GetEntityByID operation = new GetEntityByID(Entity.ID.valueOf("test"));
		GraphResultSet operationresult = new GraphResultSet(null);
		QueryResultImpl queryResultImpl = new QueryResultImpl(CurrentOperationType.ENTITY);
		
		new Expectations() {
			{
				entityOperations.getEntityById(operation.getId()); result = operationresult;
				entityOperations.createQueryResult(operationresult);result = queryResultImpl;
			}
		};
		
		dseGraphVisitor.visit(operation);
		assertEquals(queryResultImpl, dseGraphVisitor.getResult());

	}
	
	@Test
	public void testGetEntityByPropertyAndTerminalOperationEntity(){
		DSEGraphVisitor dseGraphVisitor = new DSEGraphVisitor(new DSEConnectionManagerMock(new DSEConfigurationMock()));
		GetEntityByProperty operation = new GetEntityByProperty(Attribute.Name.valueOf("test"), "test");
		QueryResultImpl queryResultImpl = new QueryResultImpl(CurrentOperationType.ENTITY);
		List<Entity> entitiesList = Collections.emptyList();
		
		new Expectations() {
			{
				entityOperations.getEntityByProperty(operation); result = graphQueryResult;
				entityOperations.createQueryResult(graphQueryResult);result = queryResultImpl;
				entityOperations.filterEntity(entitiesList, Collections.emptyList(), Collections.emptyList()); result=entitiesList;
			}
		};
		
		dseGraphVisitor.visit(operation);
		assertEquals(queryResultImpl, dseGraphVisitor.getResult());
		GraphTerminalOperation graphTerminalOperation = new GraphTerminalOperation();
		
		//test terminal operation with entity
		dseGraphVisitor.visit(graphTerminalOperation);
		assertEquals(dseGraphVisitor.getResult(), queryResultImpl);
	}
	
	@Test
	public void testGetEntityByPropertyAndTerminalOperationRelationship(){
		DSEGraphVisitor dseGraphVisitor = new DSEGraphVisitor(new DSEConnectionManagerMock(new DSEConfigurationMock()));
		GetEntityByProperty operation = new GetEntityByProperty(Attribute.Name.valueOf("test"), "test");
		QueryResultImpl queryResultImpl = new QueryResultImpl(CurrentOperationType.RELATIONHIP);
		List<Relationship> relationshipList = new ArrayList<Relationship>();
		
		new Expectations() {
			{
				entityOperations.getEntityByProperty(operation); result = graphQueryResult;
				entityOperations.createQueryResult(graphQueryResult);result = queryResultImpl;
				relationshipOperations.filterRelationship(Collections.emptyList(), Collections.emptyList(), Collections.emptyList()); result=relationshipList;
			}
		};
		
		dseGraphVisitor.visit(operation);
		assertEquals(queryResultImpl, dseGraphVisitor.getResult());
		GraphTerminalOperation graphTerminalOperation = new GraphTerminalOperation();
		
		//test terminal operation with entity
		dseGraphVisitor.visit(graphTerminalOperation);
		assertEquals(dseGraphVisitor.getResult(), queryResultImpl);
	}
	
	@Test
	public void testSelectEntities(){
		DSEGraphVisitor dseGraphVisitor = new DSEGraphVisitor(new DSEConnectionManagerMock(new DSEConfigurationMock()));
		Name relationName = Relationship.Name.valueOf("test-relationship");
		Direction direction = Direction.IN_OUT;
		SelectEntities operation = new SelectEntities(relationName, direction);
		QueryResultImpl queryResultImpl = new QueryResultImpl(CurrentOperationType.ENTITY);
		
		new Expectations() {
			{
				entityOperations.selectEntities(relationName, direction, null, null);result=queryResultImpl;
			}
		};
		
		dseGraphVisitor.visit(operation);
		assertEquals(dseGraphVisitor.getResult(), queryResultImpl);
		//visit a second time
		
		new Expectations() {
			{
				entityOperations.selectEntities(relationName, direction, null, queryResultImpl);result=queryResultImpl;
			}
		};
		
		dseGraphVisitor.visit(operation);
		assertEquals(dseGraphVisitor.getResult(), queryResultImpl);
	}
	
	@Test
	public void testSelectRelationships(){
		DSEGraphVisitor dseGraphVisitor = new DSEGraphVisitor(new DSEConnectionManagerMock(new DSEConfigurationMock()));
		Name relationName = Relationship.Name.valueOf("test-relationship");
		Direction direction = Direction.IN_OUT;
		SelectRelationships operation = new SelectRelationships(relationName, direction);
		QueryResultImpl queryResultImpl = new QueryResultImpl(CurrentOperationType.RELATIONHIP);
		
		new Expectations() {
			{
				relationshipOperations.selectRelationships(relationName, direction, null, null);result=queryResultImpl;
			}
		};
		
		dseGraphVisitor.visit(operation);
		assertEquals(dseGraphVisitor.getResult(), queryResultImpl);
		//visit a second time
		
		new Expectations() {
			{
				relationshipOperations.selectRelationships(relationName, direction, null, queryResultImpl);result=queryResultImpl;
			}
		};
		
		dseGraphVisitor.visit(operation);
		assertEquals(dseGraphVisitor.getResult(), queryResultImpl);
	}
	
	@Test
	public void testUpdateEntity(){
		DSEGraphVisitor dseGraphVisitor = new DSEGraphVisitor(new DSEConnectionManagerMock(new DSEConfigurationMock()));
		Entity entity = new GraphEntity.Builder().setID(Entity.ID.valueOf("test-entity")).build();
		GraphAttributeOperations operations = new GraphAttributeOperations();
		UpdateEntity operation = new UpdateEntity(entity , operations );
		QueryResultImpl queryResultImpl = new QueryResultImpl(CurrentOperationType.ENTITY);
		
		new Expectations(){
			{
				entityOperations.updateEntity(entity, operations);result=queryResultImpl;
			}
		};
		
		dseGraphVisitor.visit(operation);
		assertEquals(dseGraphVisitor.getResult(), queryResultImpl);
	}
	
	@Test
	public void testUpdateRelationship(){
		DSEGraphVisitor dseGraphVisitor = new DSEGraphVisitor(new DSEConnectionManagerMock(new DSEConfigurationMock()));
		Entity.ID sourceID = Entity.ID.valueOf("source-entity-id");
		Entity.ID targetID = Entity.ID.valueOf("target-entity-id");
		Relationship relationship = new GraphRelationship.Builder()
				.setName(Relationship.Name.valueOf("test-relationship")).setSourceID(sourceID).setTargetID(targetID)
				.build();
		GraphAttributeOperations operations = new GraphAttributeOperations();
		UpdateRelationship operation = new UpdateRelationship(relationship, operations );
		QueryResultImpl queryResultImpl = new QueryResultImpl(CurrentOperationType.RELATIONHIP);
		
		new Expectations(){
			{
				relationshipOperations.updateRelationship(relationship, operations);result=queryResultImpl;
			}
		};
		
		dseGraphVisitor.visit(operation);
		assertEquals(dseGraphVisitor.getResult(), queryResultImpl);
	}
	
	@Test
	public void testDeleteEntity(){
		DSEGraphVisitor dseGraphVisitor = new DSEGraphVisitor(new DSEConnectionManagerMock(new DSEConfigurationMock()));
		Entity.ID sourceID = Entity.ID.valueOf("source-entity-id");
		Entity.ID targetID = Entity.ID.valueOf("target-entity-id");
		Relationship relationship = new GraphRelationship.Builder()
				.setName(Relationship.Name.valueOf("test-relationship")).setSourceID(sourceID).setTargetID(targetID)
				.build();
		DeleteRelationship operation = new DeleteRelationship(relationship);
		
		new Expectations(){
			{
				relationshipOperations.deleteRelationship(relationship);;
			}
		};
		
		dseGraphVisitor.visit(operation);
		GraphMutationResult result = (GraphMutationResult) dseGraphVisitor.getResult();
		assertEquals(result.getEntityKeys(), Collections.emptyList());
	}
	
	@Test
	public void testDeleteRelationship(){
		DSEGraphVisitor dseGraphVisitor = new DSEGraphVisitor(new DSEConnectionManagerMock(new DSEConfigurationMock()));
		Entity entity = new GraphEntity.Builder().setID(Entity.ID.valueOf("test-entity")).build();
		DeleteEntity operation = new DeleteEntity(entity);
		
		new Expectations(){
			{
				entityOperations.deleteEntity(entity);
			}
		};
		
		dseGraphVisitor.visit(operation);
		GraphMutationResult result = (GraphMutationResult) dseGraphVisitor.getResult();
		assertEquals(result.getEntityKeys(), Collections.emptyList());
	}
}
