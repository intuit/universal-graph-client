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

package com.intuit.ugc.impl.core.spi;

import java.util.Collections;
import java.util.List;

import com.intuit.ugc.api.Entity;
import com.intuit.ugc.api.Relationship;

/**
 * 
 * An implementation of the {@link com.intuit.ugc.impl.core.spi.QueryResult<Entity, Relationship>}
 * This class should be used by graph specific provider to collect the result of the query executed.
 * 
 * @author ajain17
 */
public class QueryResultImpl implements QueryResult<Entity, Relationship> {

    private List<Entity> entityList = Collections.emptyList();
    private List<Relationship> relationshipList = Collections.emptyList();
    private CurrentOperationType currentOperation = null;

    public QueryResultImpl(CurrentOperationType currentOperation) {
        this.currentOperation = currentOperation;
    }

    @Override
    public CurrentOperationType getCurrentOperation() {
        return this.currentOperation;
    }

    @Override
    public void setEntityOpResponse(List<Entity> entityList) {
        this.entityList = entityList;
    }

    @Override
    public void setRelationshipOpResponse(List<Relationship> relationshipList) {
        this.relationshipList = relationshipList;
    }

    @Override
    public List<Entity> getEntityResponse() {
        return entityList;
    }

    @Override
    public List<Relationship> getRelationshipResponse() {
        return relationshipList;
    }

}
