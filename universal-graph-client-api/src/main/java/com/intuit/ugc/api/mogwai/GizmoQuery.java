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

import java.time.Instant;

import com.intuit.ugc.api.Attribute;
import com.intuit.ugc.api.Entity;
import com.intuit.ugc.api.Persistence;
import com.intuit.ugc.api.PersistenceException;
import com.intuit.ugc.api.Queries.GraphTraversal;
import com.toddfast.util.convert.TypeConverter;

/**
 * A query on a Gizmo API
 *
 * @see com.intuit.ugc.api.mogwai.Gizmo
 * @author ajain17
 */
/*pkg*/ class GizmoQuery implements Gizmo.Query {

    public GizmoQuery(Persistence persistence) {
        super();
        this.persistence = persistence;
    }

    @Override
    public Gizmo.Pipeline v(Entity.ID id) {
        GraphTraversal traversal = persistence.queryGraph().fromRootEntity(id);
        return new GizmoPipeline(traversal);
    }

    @Override
    public Gizmo.Pipeline v(String id) {
        GraphTraversal traversal = 
            persistence.queryGraph().fromRootEntity(Entity.ID.valueOf(id));
        return new GizmoPipeline(traversal);
    }

    @Override
    public Gizmo.Pipeline v(Attribute.Name name, String value) {
        return _v(name,value,TypeConverter.to(String.class));
    }

    @Override
    public Gizmo.Pipeline v(Attribute.Name name, Integer value) {
        return _v(name,value,TypeConverter.to(Integer.class));
    }

    @Override
    public Gizmo.Pipeline v(Attribute.Name name, Boolean value) {
        return _v(name,value,TypeConverter.to(Boolean.class));
    }

    @Override
    public Gizmo.Pipeline v(Attribute.Name name, Double value) {
        return _v(name,value,TypeConverter.to(Double.class));
    }

    @Override
    public Gizmo.Pipeline v(Attribute.Name name, Instant value) {
        return _v(name,value,TypeConverter.to(Instant.class));
    }

    @Override
    public <T> Gizmo.Pipeline v(Attribute.Name name, T value, 
            TypeConverter.Conversion<T> conversion) {
        return _v(name,value,conversion);
    }
        
    private <T> Gizmo.Pipeline _v(Attribute.Name name, T value, 
            TypeConverter.Conversion<T> conversion) {

        Entity entity = persistence.lookup()
            .entityBy(name).value(value,conversion)
            .ready()
            .execute()
            .getResult()
            .getEntity();

        if (entity == null) {
            throw new PersistenceException("Entity with attribute \""+
                name+"\" = \""+value+"\" not found");
        }
        
        GraphTraversal traversal = 
            persistence.queryGraph()
                .fromRootEntity(entity.getID());

        return new GizmoPipeline(traversal);
    }

    private Persistence persistence;
}
