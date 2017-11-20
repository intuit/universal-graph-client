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

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.driver.dse.graph.GraphResultSet;
import com.intuit.ugc.api.Attribute.Family;
import com.intuit.ugc.api.Attribute.Name;
import com.intuit.ugc.api.Entity;
import com.intuit.ugc.api.Entity.ID;
import com.intuit.ugc.api.NewEntity;
import com.intuit.ugc.api.NewRelationship;
import com.intuit.ugc.api.Relationship;
import com.intuit.ugc.impl.core.GraphMutationResult;
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
import com.intuit.ugc.impl.core.spi.GraphVisitor;
import com.intuit.ugc.impl.core.spi.QueryResult;
import com.intuit.ugc.impl.core.spi.QueryResultImpl;

/**
 * Implements the {@link com.intuit.ugc.impl.core.spi.GraphVisitor}
 * Contains the implementation for all the major operations required to be done over a DSE graph.
 * 
 * @author nverma1
 *
 */
public class DSEGraphVisitor implements GraphVisitor {
	private static final Logger LOGGER = LoggerFactory.getLogger(DSEGraphVisitor.class);
	private DSEConnectionManager connectionManager;
	private VisitOperationResult qResult = null;
	private GraphEntityOperations entityOperations = null;
	private GraphRelationshipOperations relationshipOperations = null;
			
    public DSEGraphVisitor(DSEConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
        this.relationshipOperations = new GraphRelationshipOperations(connectionManager);
        this.entityOperations = new GraphEntityOperations(connectionManager, this.relationshipOperations);
    }

    @Override
    public void visit(CreateEntity operation) {
    	//obtain the newEntity instance to be created from the CreateEntity operation
    	NewEntity newEntity = operation.newEntity();
    	GraphMutationResult queryResult = entityOperations.createEntity(newEntity);
		qResult = queryResult;
    }

	@Override
    public void visit(CreateRelationship operation) {
        NewRelationship newRelationship = operation.newRelationship();
        relationshipOperations.createRelationship(newRelationship);
		qResult = new GraphMutationResult(Collections.emptyList());
    }

	@Override
    public void visit(GetBatchEntityByID operation) {
        List<ID> entityIDs = operation.getEntityIDs();
        qResult = entityOperations.getEntitiesByID(entityIDs);
    }

    @Override
    public void visit(GetEntityByID operation) {
    	Entity.ID entityId = operation.getId();
		GraphResultSet result = entityOperations.getEntityById(entityId);
		qResult = entityOperations.createQueryResult(result);
    }

    @Override
    public void visit(GetEntityByProperty operation) {
    	GraphResultSet result = entityOperations.getEntityByProperty(operation);
		qResult = entityOperations.createQueryResult(result);
    }

    @Override
    public void visit(GraphTerminalOperation operation) {
        // Filter query result based on name and namespace provided 
        QueryResult<Entity, Relationship> queryResult = (QueryResult<Entity, Relationship>) qResult;
        List<Name> nameList = operation.getNameList();
        List<Family> namespaceList = operation.getNamespaceList();

        if (queryResult
                .getCurrentOperation() == QueryResult.CurrentOperationType.ENTITY) {
        	queryResult.setEntityOpResponse(
                    entityOperations.filterEntity(queryResult.getEntityResponse(), nameList,
                            namespaceList));
        } else {
        	queryResult.setRelationshipOpResponse(
                    relationshipOperations.filterRelationship(queryResult.getRelationshipResponse(),
                            nameList, namespaceList));
        }

        qResult = queryResult;
    }

    @Override
    public void visit(SelectEntities operation) {
        QueryResultImpl previous = null;
        
        if (qResult != null) {
            previous = (QueryResultImpl) qResult;
        }
        
        qResult = entityOperations.selectEntities(operation.getRelationName(),
                operation.getDirection(), operation.getPredicate(), previous);
        
    }

	@Override
    public void visit(SelectRelationships operation) {
        QueryResultImpl previous = null;
        
        if (qResult != null) {
            previous = (QueryResultImpl) qResult;
        }

		QueryResult<Entity, Relationship> queryResult = relationshipOperations.selectRelationships(operation.getRelationshipName(),
				operation.getDirection(), operation.getPredicate(), previous);
		qResult = queryResult;
    }

    @Override
    public void visit(UpdateEntity operation) {
        qResult = entityOperations.updateEntity(operation.getEntity(),
                operation.getOperations());
    }

	@Override
    public void visit(UpdateRelationship operation) {
        qResult = relationshipOperations.updateRelationship(operation.getRelationship(),
                operation.getOperations());

    }

	@Override
	public void visit(DeleteEntity operation) {
		entityOperations.deleteEntity(operation.getEntity());
		qResult = new GraphMutationResult(Collections.emptyList());
	}
	
	@Override
	public void visit(DeleteRelationship operation) {
		relationshipOperations.deleteRelationship(operation.getRelationship());
		qResult = new GraphMutationResult(Collections.emptyList());
	}

	@Override
    public VisitOperationResult getResult() {
    	 return qResult;
    }
}
