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

package com.intuit.ugc.impl.core.queryplan.operations;

import java.util.ArrayList;
import java.util.List;

import com.intuit.ugc.api.Entity;
import com.intuit.ugc.impl.core.queryplan.Operation;
import com.toddfast.util.preconditions.Preconditions;

/**
 * Represents an operation to fetch batch of entities given their ids. The operation itself is stored in
 * the pipeline as an instance of this class. And once execution is started over
 * the pipeline, all the operations are executed one by one.
 * 
 * @author ajain17
 *
 */
public class GetBatchEntityByID implements Operation {

    private final List<Entity.ID> entityIDs;

    public GetBatchEntityByID(List<Entity.ID> entityIDs) {
        super();
        this.entityIDs = new ArrayList<Entity.ID>(
                Preconditions.collectionItemsNotNull(entityIDs, "entityIDs"));
    }

    public List<Entity.ID> getEntityIDs() {
        return entityIDs;
    }

    @Override
    public String toString() {
        return "Operation [Get Entity] Entity ID " + entityIDs;
    }

}
