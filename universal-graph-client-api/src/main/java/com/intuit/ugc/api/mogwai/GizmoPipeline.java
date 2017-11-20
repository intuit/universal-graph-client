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

package com.intuit.ugc.api.mogwai;

import com.intuit.ugc.api.Attribute;
import com.intuit.ugc.api.Queries;
import com.intuit.ugc.api.Query;
import com.intuit.ugc.api.Relationship;
import com.intuit.ugc.api.Queries.GraphTraversal;
import com.intuit.ugc.api.Queries.Projection;

/**
 * A pipeline of Gizmo queries 
 * @see com.intuit.ugc.api.mogwai.Gizmo
 * @author ajain17
 */
/*pkg*/ class GizmoPipeline implements Gizmo.Pipeline {

    public GizmoPipeline(Queries.GraphTraversal graphTraversal) {
        super();
        traversal = graphTraversal;
    }

    @Override
    public Gizmo.Pipeline out(Relationship.Name name) {
        traversal = traversal.selectAdjacentEntitiesVia(
            name,
            GraphTraversal.Direction.OUT);
        return this;
    }

    @Override
    public Gizmo.Pipeline out(String name) {
        traversal = traversal.selectAdjacentEntitiesVia(
            Relationship.Name.valueOf(name),
            GraphTraversal.Direction.OUT);
        return this;
    }

    @Override
    public Gizmo.Pipeline in(Relationship.Name name) {
        traversal = traversal.selectAdjacentEntitiesVia(
            name,
            GraphTraversal.Direction.IN);
        return this;
    }

    @Override
    public Gizmo.Pipeline in(String name) {
        traversal = traversal.selectAdjacentEntitiesVia(
            Relationship.Name.valueOf(name),
            GraphTraversal.Direction.IN);
        return this;
    }

    @Override
    public Gizmo.Pipeline outE(Relationship.Name name) {
        traversal = traversal.selectAdjacentRelationships(
            name,
            GraphTraversal.Direction.OUT);
        return this;
    }

    @Override
    public Gizmo.Pipeline outE(String name) {
        traversal = traversal.selectAdjacentRelationships(
            Relationship.Name.valueOf(name),
            GraphTraversal.Direction.OUT);
        return this;
    }

    @Override
    public Gizmo.Pipeline inE(Relationship.Name name) {
        traversal = traversal.selectAdjacentRelationships(
            name,
            GraphTraversal.Direction.IN);
        return this;
    }

    @Override
    public Gizmo.Pipeline inE(String name) {
        traversal = traversal.selectAdjacentRelationships(
            Relationship.Name.valueOf(name),
            GraphTraversal.Direction.IN);
        return this;
    }

    @Override
    public Gizmo.Pipeline filter(Gizmo.Predicate predicate) {
        traversal = traversal.where(
            ((GizmoPredicate)predicate).getPredicate());
        return this;
    }

    @Override
    public Query select(Attribute.Name... name) {
        
        Projection projection = traversal.select();
        
        for (Attribute.Name n: name) {
            projection = projection.includeAttribute(n);
        }

        return projection.ready();
    }

    @Override
    public Query select(String... name) {
        
        Projection projection = traversal.select();
        
        for (String n: name) {
            projection = projection.includeAttribute(
                Attribute.Name.valueOf(n));
        }

        return projection.ready();
    }

    private GraphTraversal traversal;
}
