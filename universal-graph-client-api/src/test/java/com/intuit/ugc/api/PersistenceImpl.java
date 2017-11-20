/*
 * Copyright (c) 2017.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    tfast - initial API and implementation and/or initial documentation
 *    ajain17 & nverma1 - API implementation, enhancements and extension
 */

package com.intuit.ugc.api;

import com.intuit.ugc.api.BatchMutation;
import com.intuit.ugc.api.Entity;
import com.intuit.ugc.api.NewEntity;
import com.intuit.ugc.api.Persistence;
import com.intuit.ugc.api.Predicate;
import com.intuit.ugc.api.Queries;
import com.intuit.ugc.api.Relationship;

/**
 *
 *
 *
 */
public class PersistenceImpl implements Persistence {

    @Override
    public Entity.ID allocateID() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public Entity createEntity(NewEntity newEntity) {
        return null;
    }

    @Override
    public BatchMutation prepareBatchMutation() {
        return null;
    }

    @Override
    public Queries.LookupQuery lookup() {
        return null;
    }

    @Override
    public Queries.GraphQuery queryGraph() {
        return null;
    }

    @Override
    public Entity.Mutation prepareMutation(Entity entity) {
        return null;
    }

    @Override
    public Relationship.Mutation prepareMutation(Relationship relationship) {
        return null;
    }

    @Override
    public Predicate predicates() {
        return null;
    }

}
