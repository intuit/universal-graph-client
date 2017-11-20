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

package com.intuit.ugc.impl.core.queryplan;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.Test;

import com.intuit.ugc.api.Attribute;
import com.intuit.ugc.api.Entity;
import com.intuit.ugc.api.Entity.ID;
import com.intuit.ugc.api.NewEntity;
import com.intuit.ugc.api.NewRelationship;
import com.intuit.ugc.api.Queries.GraphTraversal.Direction;
import com.intuit.ugc.api.Relationship;
import com.intuit.ugc.api.Relationship.Name;
import com.intuit.ugc.impl.core.GraphEntity;
import com.intuit.ugc.impl.core.GraphRelationship;
import com.intuit.ugc.impl.core.VisitOperationResult;
import com.intuit.ugc.impl.core.helper.MockGraphVisitor;
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
import com.intuit.ugc.impl.core.spi.GraphVisitor;
import com.intuit.ugc.impl.core.spi.RepositoryException;

/**
 * 
 * @author nverma1
 *
 */
public class OperationFeederImplTest {

	@Test
	public void testOperationFeederImplWithVariousOperations(){
		GraphVisitor repository = new MockGraphVisitor();
		OperationPipeline operationPipeline = new OperationPipeline();
		OperationFeederImpl operationFeederImpl = new OperationFeederImpl(repository , operationPipeline);
		NewEntity newEntity = NewEntity.newInstance(Entity.ID.valueOf("entity-test")).build();
		operationPipeline.add(new CreateEntity(newEntity));
		ID sourceId = Entity.ID.valueOf("source-id");
		ID targetId = Entity.ID.valueOf("target-id");
		Name relationshipName = Relationship.Name.valueOf("test-name");
		NewRelationship newRelationship = NewRelationship.between(sourceId, targetId).withLabel(relationshipName).build();
		operationPipeline.add(new CreateRelationship(newRelationship));
		List<ID> entityIds = new ArrayList<ID>(Arrays.asList(sourceId, targetId));
		operationPipeline.add(new GetBatchEntityByID(entityIds ));
		operationPipeline.add(new GetEntityByID(sourceId));
		operationPipeline.add(new GetEntityByProperty(Attribute.Name.valueOf("test-property"),"test"));
		Entity entity = new GraphEntity.Builder().setID(sourceId).build();
		operationPipeline.add(new UpdateEntity(entity, null));
		Relationship relationship = new GraphRelationship.Builder().setName(relationshipName).setSourceID(sourceId)
				.setTargetID(targetId).build();
		operationPipeline.add(new UpdateRelationship(relationship , null));
		operationPipeline.add(new GraphTerminalOperation());
		operationPipeline.add(new SelectEntities(relationshipName, Direction.IN_OUT));
		operationPipeline.add(new SelectRelationships(relationshipName, Direction.IN_OUT));
		operationPipeline.add(new DeleteEntity(entity));
		operationPipeline.add(new DeleteRelationship(relationship));
		try{
			VisitOperationResult result = operationFeederImpl.operationResult();
			assertNotNull(result);
		}catch(Exception e){
			fail("Shouldn't throw");
		}
	}
	
	@Test
	public void testOperationThatThrowsException(){
		GraphVisitor repository = new TestGraphVisitor();
		try{
			OperationPipeline operationPipeline = new OperationPipeline();
			OperationFeederImpl operationFeederImpl = new OperationFeederImpl(repository , operationPipeline);
			NewEntity newEntity = NewEntity.newInstance(Entity.ID.valueOf("entity-test")).build();
			operationPipeline.add(new CreateEntity(newEntity));
			VisitOperationResult result = operationFeederImpl.operationResult();
		}catch(Exception e){
			assertTrue(e instanceof RepositoryException);
		}
	}
	
	class TestGraphVisitor implements GraphVisitor{

		@Override
		public void visit(CreateEntity operation) {
			throw new RepositoryException(new Exception("test-fail"));
		}

		@Override
		public void visit(CreateRelationship operation) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visit(GetBatchEntityByID operation) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visit(GetEntityByID operation) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visit(GetEntityByProperty operation) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visit(GraphTerminalOperation operation) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visit(SelectEntities operation) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visit(SelectRelationships operation) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visit(UpdateEntity operation) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visit(UpdateRelationship operation) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public VisitOperationResult getResult() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void visit(DeleteEntity operation) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visit(DeleteRelationship operation) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
