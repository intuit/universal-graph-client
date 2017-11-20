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

import java.util.UUID;

import com.intuit.ugc.api.BatchMutation;
import com.intuit.ugc.api.Entity;
import com.intuit.ugc.api.Persistence;
import com.intuit.ugc.api.Predicate;
import com.intuit.ugc.api.Queries;
import com.intuit.ugc.api.Relationship;
import com.intuit.ugc.api.Entity.ID;
import com.intuit.ugc.impl.core.queryplan.MutationExecutorFactory;
import com.intuit.ugc.impl.core.spi.GraphVisitor;
import com.toddfast.util.convert.TypeConverter;
import com.toddfast.util.preconditions.Preconditions;


/**
 * The primary interface for interacting with persistence services. This
 * persistence implementation is based on GraphPersistence.
 * 
 * @author ajain17
 */
public abstract class AbstractGraphPersistence implements Persistence {

    private static final String ID_PREFIX = "urn:entity:intuit.datafabric.ID/";
	private GraphVisitor repository;
	private MutationExecutorFactory factory;
    
	protected AbstractGraphPersistence(GraphVisitor repository,
            MutationExecutorFactory factory) {
		this.repository = Preconditions.argumentNotNull(repository,
            "persistenceRepository::"+GraphVisitor.class.getName());
		this.factory = Preconditions.argumentNotNull(factory,
            "factor::"+MutationExecutorFactory.class.getName());
	}

	@Override
	public ID allocateID() {
		String id = ID_PREFIX + UUID.randomUUID();
		return ID.valueOf(id);
	}

	@Override
	public Queries.LookupQuery lookup() {
		return new GraphLookUpQuery(repository);
	}

	@Override
	public Queries.GraphQuery queryGraph() {
		return new GraphQueryImpl(repository);
	}

	@Override
	public Entity.Mutation prepareMutation(Entity entity) {
		Preconditions.argumentNotNull(entity,
            "entity::"+Entity.class.getName());
		GraphMutation GraphMutation = new GraphMutation(factory);
		return GraphMutation.withEntity(entity);
	}

	@Override
	public Relationship.Mutation prepareMutation(Relationship relationship) {
		Preconditions.argumentNotNull(relationship,
            "relationship::"+Relationship.class.getName());
		GraphMutation GraphMutation = new GraphMutation(factory);
		return GraphMutation.withRelationship(relationship);
	}

	@Override
	public Predicate predicates() {
		return new GraphPredicate();
	}

	@Override
	public BatchMutation prepareBatchMutation() {
		return new GraphMutation(factory);
	}

    // Load type conversions needed in this library
    static {
        TypeConverter.registerTypeConversion(new InstantTypeConversion());
        TypeConverter.registerTypeConversion(new UUIDTypeConversion());
    }
}
