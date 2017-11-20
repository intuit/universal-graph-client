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

import com.google.inject.Inject;
import com.intuit.ugc.api.BatchMutation;
import com.intuit.ugc.api.Entity;
import com.intuit.ugc.api.NewEntity;
import com.intuit.ugc.api.NewRelationship;
import com.intuit.ugc.api.OperationResult;
import com.intuit.ugc.api.Relationship;
import com.intuit.ugc.impl.core.queryplan.MutationExecutorFactory;
import com.intuit.ugc.impl.core.queryplan.OperationFeederImpl;
import com.intuit.ugc.impl.core.queryplan.OperationPipeline;
import com.intuit.ugc.impl.core.queryplan.operations.CreateEntity;
import com.intuit.ugc.impl.core.queryplan.operations.CreateRelationship;
import com.toddfast.util.preconditions.Preconditions;

/**
 * A representation of batch mutation in graph persistence store
 * 
 * @see com.intuit.ugc.api.BatchMutation
 * @author ajain17
 * 
 */
public class GraphMutation implements BatchMutation {

    private OperationPipeline pipeline;
    private OperationFeederImpl graphOperation;

    @Inject
    public GraphMutation(MutationExecutorFactory factory) {
        this.pipeline = new OperationPipeline();
        this.graphOperation = factory.create(this.pipeline);
    }

    @Override
    public BatchMutation createEntity(NewEntity newEntity) {
        Preconditions.argumentNotNull(newEntity, "newEntity");
        CreateEntity createEntity = new CreateEntity(newEntity);
        pipeline.add(createEntity);
        return this;
    }

    @Override
    public BatchMutation createRelationship(NewRelationship newRelationship) {
        Preconditions.argumentNotNull(newRelationship, "newRelationship");
        CreateRelationship createRelationships = new CreateRelationship(
                newRelationship);
        pipeline.add(createRelationships);
        return this;
    }

    @Override
    public OperationResult<Result> execute() {
        return () -> (GraphMutationResult) graphOperation.operationResult();
    }

    @Override
    public Entity.Mutation withEntity(Entity entity) {
        Preconditions.argumentNotNull(entity, "entity");
        return new GraphEntityMutation(entity, pipeline, this);
    }

    @Override
    public Relationship.Mutation withRelationship(Relationship relationship) {
        Preconditions.argumentNotNull(relationship, "relationship");
        return new GraphRelationshipMutation(relationship, pipeline, this);
    }
}
