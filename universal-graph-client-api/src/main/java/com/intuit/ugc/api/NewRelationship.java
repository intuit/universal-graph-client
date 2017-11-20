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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a new relationship before it has been persisted
 * 
 * @author ajain17
 */
public class NewRelationship {

    /**
     * Can't instantiate
     * 
     */
    private NewRelationship(Entity.ID source, Entity.ID target) {
        super();

        if (source == null) {
            throw new IllegalArgumentException("Parameter \"source\" "+
                "cannot be null");
        }

        if (target == null) {
            throw new IllegalArgumentException("Parameter \"target\" "+
                "cannot be null");
        }

        this.sourceID = source;
        this.targetID = target;
    }

    /**
     * Get ID of the source entity
     * 
     * @return
     */
    public Entity.ID getSourceID() {
        return sourceID;
    }

    /**
     * Get ID of the target entity
     * 
     * @return
     */
    public Entity.ID getTargetID() {
        return targetID;
    }

    /**
     * Get name of the relationship
     * 
     * @return
     */
    public Relationship.Name getName() {
        return name;
    }

    /**
     * Get relationship attributes
     * 
     * @return  An unmodifiable map of attributes
     */
    public Map<Attribute.Name, Object> getAttributes() {
        return attributes;
    }

    /**
     * Returns a new builder instance
     * 
     */
    public static Builder between(Entity.ID sourceID, Entity.ID targetID) {
        return new Builder(sourceID,targetID);
    }


    /**
     * Returns a new builder instance
     * 
     */
    public static Builder between(Entity source, Entity target) {
        return new Builder(source,target);
    }


    /**
     * Builder for a new instance
     *
     */
    public static class Builder {

        private Builder(Entity.ID source, Entity.ID target) {
            instance = new NewRelationship(source,target);
        }

        private Builder(Entity source, Entity target) {
            instance = new NewRelationship(source.getID(),target.getID());
        }

        public Builder withLabel(Relationship.Name name) {
            instance.name = name;
            return this;
        }

        public ValueSpecifier<Builder> withAttribute(Attribute.Name name) {
            ensure(name,"name");

            return new AbstractValueSpecifier<Builder>(this,name) {
                protected void setValue(Object value) {
                    ensure(value,"value");
                    instance.attributes.put(name,value);
                }
            };
        }
        
        private void ensure(Object value, String field) {
            if (value==null) {
                throw new IllegalArgumentException(
                    "\""+field+"\" cannot be null");
            }
        }

        public NewRelationship build() {
            if (instance.name == null) {
                throw new IllegalArgumentException(
                    "Relationship name cannot be null");
            }

            instance.attributes = 
                Collections.unmodifiableMap(instance.attributes);
            
            NewRelationship result=instance;
            instance=null;
            return result;
        }

        private NewRelationship instance;
    }

    private Entity.ID sourceID;
    private Entity.ID targetID;
    private Relationship.Name name;
    private Map<Attribute.Name, Object> attributes = new HashMap<>();
}
