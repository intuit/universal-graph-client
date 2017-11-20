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
 * Represents a new entity before it has been persisted
 * 
 * @author ajain17
 */
public class NewEntity {

    /**
     * Can't instantiate
     * 
     */
    private NewEntity(Entity.ID entityID) {
        super();
        this.entityID = entityID;
    }

    /**
     * Returns a new builder instance
     * 
     */
    public static Builder newInstance(Entity.ID entityID) {
        return new Builder(entityID);
    }

    /**
     * Get entity attributes
     * 
     * @return  An unmodifiable map of attributes
     */
    public Map<Attribute.Name, Object> getAttributes() {
        return attributes;
    }

    /**
     * Return the entity ID
     * 
     * @return
     */
    public Entity.ID getEntityID() {
        return entityID;
    }

    /**
     * Builder for a new instance
     *
     */
    public static class Builder {

        private Builder(Entity.ID entityID) {
            instance = new NewEntity(entityID);
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

        public NewEntity build() {    
            instance.attributes = 
                Collections.unmodifiableMap(instance.attributes);
            NewEntity result=instance;
            instance=null;
            return result;
        }

        private NewEntity instance;
    }

    private Entity.ID entityID;
    private Map<Attribute.Name, Object> attributes = new HashMap<>();
}
