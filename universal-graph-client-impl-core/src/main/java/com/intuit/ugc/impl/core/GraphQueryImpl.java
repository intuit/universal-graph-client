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

import com.intuit.ugc.api.Predicate;
import com.intuit.ugc.api.Queries;
import com.intuit.ugc.api.Relationship;
import com.intuit.ugc.api.Entity.ID;
import com.intuit.ugc.api.Queries.Projection;
import com.intuit.ugc.impl.core.queryplan.ContextOperation;
import com.intuit.ugc.impl.core.queryplan.Operation;
import com.intuit.ugc.impl.core.queryplan.OperationPipeline;
import com.intuit.ugc.impl.core.queryplan.operations.GetEntityByID;
import com.intuit.ugc.impl.core.queryplan.operations.SelectEntities;
import com.intuit.ugc.impl.core.queryplan.operations.SelectRelationships;
import com.intuit.ugc.impl.core.spi.GraphVisitor;
import com.toddfast.util.preconditions.Preconditions;

/**
 * Represents an implementation of a Graph Query
 * 
 * @author ajain17
 */
public class GraphQueryImpl 
        implements Queries.GraphQuery, Queries.GraphTraversal {

	private GraphVisitor repository;
	private final OperationPipeline pipeline;

	public GraphQueryImpl(GraphVisitor repository) {
		this.repository = repository;
		this.pipeline = new OperationPipeline();
	}

	@Override
	public Queries.GraphTraversal selectAdjacentEntitiesVia(
			Relationship.Name name) {
		Preconditions.argumentNotNull(name, "name::"+Relationship.Name.class.getName());
		pipeline.add(new SelectEntities(name, null));
		return this;
	}

	@Override
	public Queries.GraphTraversal selectAdjacentEntitiesVia(
			Relationship.Name name, Direction direction) {
		Preconditions.argumentNotNull(name, "name::"+Relationship.Name.class.getName());
		Preconditions.argumentNotNull(direction, "direction::"+Direction.class.getName());
		pipeline.add(new SelectEntities(name, direction));
		return this;
	}

	@Override
	public Queries.GraphTraversal selectAdjacentRelationships(
			Relationship.Name name) {
		Preconditions.argumentNotNull(name, "name::"+Relationship.Name.class.getName());
		pipeline.add(new SelectRelationships(name, null));
		return this;
	}

	@Override
	public Queries.GraphTraversal selectAdjacentRelationships(
			Relationship.Name name, Direction direction) {
		Preconditions.argumentNotNull(name, "name::"+Relationship.Name.class.getName());
		Preconditions.argumentNotNull(direction, "direction::"+Direction.class.getName());
		pipeline.add(new SelectRelationships(name, direction));
		return this;
	}

	@Override
	public Queries.GraphTraversal where(Predicate predicate) {
	    Operation operation = pipeline.getTailOperation();
		if (operation instanceof ContextOperation) {
			ContextOperation graphOps = (ContextOperation) operation;
			graphOps.condition(predicate);
		} else {
			//TODO: neha: confirm : this else can never occur
			throw new UnsupportedOperationException("Invalid filter operation [" + operation.toString() + "] ");
		}
		return this;
	}

	@Override
	public Queries.GraphTraversal fromRootEntity(ID id) {
		Preconditions.argumentNotNull(id, "id");
		pipeline.add(new GetEntityByID(id));
		return this;
	}

	@Override
	public Projection select() {
		return new GraphProjection(this.repository, pipeline);
	}
}
