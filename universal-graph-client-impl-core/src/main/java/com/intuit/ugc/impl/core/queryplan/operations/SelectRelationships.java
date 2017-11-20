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

/**
 * Represents a relationship selection operation. The operation itself is stored in
 * the pipeline as an instance of this class. And once execution is started over
 * the pipeline, all the operations are executed one by one.
 * 
 * @author ajain17
 *
 */
public class SelectRelationships implements ContextOperation {

    private final Relationship.Name name;
    private final Direction direction;
    private Predicate predicate = null;

    public SelectRelationships(Relationship.Name name, Direction direction) {
        super();
        this.name = name;
        if (direction == null) {
            this.direction = Direction.IN_OUT;
        } else {
            this.direction = direction;
        }
    }
    
    public Relationship.Name getRelationshipName() {
        return name;
    }

    public Direction getDirection() {
        return direction;
    }

    public Predicate getPredicate() {
        return predicate;
    }

    @Override
    public String toString() {
        return "Operation [Select Relationships] with relation label ["
                + name.toString() + "]";
    }

    @Override
    public void condition(Predicate predicate) {
        if (this.predicate != null) {
			throw new IllegalStateException(
					"Operation [Select Relationships] is already initialized with predicate. Override is not allowed");
        }
        this.predicate = predicate;
    }

}
