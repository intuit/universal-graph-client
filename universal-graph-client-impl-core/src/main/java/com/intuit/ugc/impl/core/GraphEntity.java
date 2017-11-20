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

package com.intuit.ugc.impl.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.intuit.ugc.api.Attribute;
import com.intuit.ugc.api.Entity;
import com.toddfast.util.preconditions.Preconditions;


/**
 * A representation of an Entity in a graph based persistence
 * 
 * @see com.intuit.ugc.api.Entity
 * 
 * @author ajain17
 */
public final class GraphEntity implements Entity {

    private final Entity.ID id;
    private final Map<Attribute.Name, Attribute> attributes;

    private GraphEntity(Entity.ID id, Map<Attribute.Name, Attribute> attributes) {
        super();
        this.id = id;
        this.attributes = Collections.unmodifiableMap(
            new HashMap<>(attributes));
    }

    @Override
    public Entity.ID getID() {
        return id;
    }

    @Override
    public Attribute getAttribute(Attribute.Name name) {
        return attributes.get(name);
    }

	public Collection<Attribute> getAttributes(){
        return Collections.unmodifiableCollection(
            new ArrayList<Attribute>(attributes.values()));
    }

    @Override
    public String toString() {
        return "GraphEntity{id=" + id + ", " + "attributes=" + attributes 
            + "}";
    }

    public static final class Builder {
        private Entity.ID id;
        private Map<Attribute.Name, Attribute> attributes = new HashMap<>();

        public Builder() {
            super();
        }

        public GraphEntity.Builder setID(Entity.ID value) {
            Preconditions.argumentNotNull(value,
                "value::"+Entity.ID.class.getName());
            id = value;
            return this;
        }

        public GraphEntity.Builder addAttribute(Attribute attribute) {
            attributes.put(attribute.getName(),attribute);
            return this;
        }

        public GraphEntity build() {
            List<String> missingList = new ArrayList<>();
            if (id == null) {
                missingList.add("id");
            }

            if (!missingList.isEmpty()) {
                throw new IllegalStateException(
                    "Missing required properties: \"" + missingList + "\"");
            }
 
            GraphEntity result = new GraphEntity(id, attributes);
            
            // Null members to help prevent misuse of dead builder
            id = null;
            
            return result;
        }
    }
}