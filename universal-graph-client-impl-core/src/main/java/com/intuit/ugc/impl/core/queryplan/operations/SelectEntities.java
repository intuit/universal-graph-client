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

import com.intuit.ugc.api.Predicate;
import com.intuit.ugc.api.Relationship;
import com.intuit.ugc.api.Queries.GraphTraversal.Direction;
import com.intuit.ugc.impl.core.queryplan.ContextOperation;
import com.intuit.ugc.impl.core.spi.RepositoryException;

/**
 * Represents an entity selection operation. The operation itself is stored in
 * the pipeline as an instance of this class. And once execution is started over
 * the pipeline, all the operations are executed one by one.
 * 
 * @author ajain17
 *
 */
public class SelectEntities implements ContextOperation {

    private final Relationship.Name relationName;
    private final Direction direction;
    private Predicate predicate = null;
    
    public SelectEntities(Relationship.Name relationName, Direction direction) {
        super();
        this.relationName = relationName;
        if (direction == null) {
            this.direction = Direction.IN_OUT;
        } else {
            this.direction = direction;
        }
    }
    
    public Relationship.Name getRelationName() {
        return relationName;
    }

    public Direction getDirection() {
        return direction;
    }

    public Predicate getPredicate() {
        return predicate;
    }

    @Override
    public String toString() {
        return "Operation [Select Entities] with relation label ["
                + relationName.toString() + "]";
    }

    @Override
    public void condition(Predicate predicate) {
		if (this.predicate != null) {
			throw new IllegalStateException(
					"Operation [Select Entities] is already initialized with predicate. Override is not allowed");
		}
        this.predicate = predicate;
    }

}
