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
import com.intuit.ugc.api.Relationship;
import com.toddfast.util.convert.TypeConverter;

/**
 * A simple API adapter layer to make the Tartan graph query builder API look 
 * more like the Blueprints/Gremlin API
 *
 * @author ajain17
 */
public class Gizmo {

    private Gizmo(Persistence persistence) {
        super();
        this.persistence = persistence;
    }

    private Persistence getPersistence() {
        return persistence;
    }
    
    public Gizmo.Query g() {
        return new GizmoQuery(getPersistence());
    }
    
    public Gizmo.Predicate _() {
        return new GizmoPredicate(getPersistence().predicates());
    }
    
    public static Gizmo newInstance(Persistence persistence) {
        return new Gizmo(persistence);
    }
    
    public static interface Predicate {

        public Predicate eq(String name, Object value);

        public Predicate neq(String name, Object value);

        public Predicate gt(String name, Object value);

        public Predicate lt(String name, Object value);

        public Predicate and(Predicate... predicate);

        public Predicate or(Predicate... predicate);
    }

    public static interface Query {

        /**
         * Query Gizmo-style!
         * 
         */
        public Pipeline v(Entity.ID id);

        /**
         * Even more Gizmo!
         * 
         */
        public Pipeline v(String id);

        /**
         * Lookup by attribute
         * 
         */
        public Pipeline v(Attribute.Name name, String value);

        /**
         * Lookup by attribute
         * 
         */
        public Pipeline v(Attribute.Name name, Integer value);

        /**
         * Lookup by attribute
         * 
         */
        public Pipeline v(Attribute.Name name, Boolean value);

        /**
         * Lookup by attribute
         * 
         */
        public Pipeline v(Attribute.Name name, Double value);

        /**
         * Lookup by attribute
         * 
         */
        public Pipeline v(Attribute.Name name, Instant value);

        /**
         * Lookup by attribute
         * 
         */
        public <T> Pipeline v(Attribute.Name name, T value, 
            TypeConverter.Conversion<T> converter);
    }        
        
    public static interface Pipeline {
        
        /**
         * 
         * 
         */
        public Pipeline out(Relationship.Name name);

        /**
         * 
         * 
         */
        public Pipeline out(String name);

        /**
         * 
         * 
         */
        public Pipeline in(Relationship.Name name);

        /**
         * 
         * 
         */
        public Pipeline in(String name);

        /**
         *
         * 
         */
        public Pipeline outE(Relationship.Name name);

        /**
         *
         * 
         */
        public Pipeline outE(String name);

        /**
         *
         * 
         */
        public Pipeline inE(Relationship.Name name);

        /**
         *
         * 
         */
        public Pipeline inE(String name);

        /**
         * Filter the current working set of elements (entities or 
         * relationships)
         * 
         */
        public Pipeline filter(Predicate predicate);

        /**
         * 
         * 
         */
        public com.intuit.ugc.api.Query select(Attribute.Name... name);

        /**
         * 
         * 
         */
        public com.intuit.ugc.api.Query select(String... name);
    }
    
    private Persistence persistence;
}
