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
import com.intuit.ugc.api.Relationship;
import com.toddfast.util.preconditions.Preconditions;

/**
 * represents a relationship in a graph persistence store
 * 
 * implements {@link com.intuit.ugc.api.Relationship}
 * @author ajain17
 *
 */
public final class GraphRelationship implements Relationship {

    private final Relationship.Name name;
    private final Entity.ID sourceID;
    private final Entity.ID targetID;
    private final Map<Attribute.Name, Attribute> attributes;

    private GraphRelationship(Relationship.Name name, Entity.ID sourceID, 
            Entity.ID targetID, Map<Attribute.Name, Attribute> attributes) {
        super();
        this.name = Preconditions.argumentNotNull(name,"name");
        this.sourceID = Preconditions.argumentNotNull(sourceID,"sourceID");
        this.targetID = Preconditions.argumentNotNull(targetID,"targetID");
        this.attributes = Preconditions.argumentNotNull(attributes,"attributes");
    }

    @Override
    public Relationship.Name getName() {
        return this.name;
    }

    @Override
    public Entity.ID getSourceID() {
        return this.sourceID;
    }

    @Override
    public Entity.ID getTargetID() {
        return this.targetID;
    }

    @Override
    public Attribute getAttribute(Attribute.Name name) {
        return this.attributes.get(name);
    }

    public Collection<Attribute> getAttributes(){
        return Collections.unmodifiableCollection(
            new ArrayList<Attribute>(attributes.values()));
    }

    public static final class Builder {

        private Relationship.Name name;
        private Entity.ID sourceID;
        private Entity.ID targetID;
        private Map<Attribute.Name, Attribute> attributes = new HashMap<>();

        public Builder() {
            super();
        }

        public GraphRelationship.Builder setName(Relationship.Name name) {
            this.name = Preconditions.argumentNotNull(name,"name");
            return this;
        }

        public GraphRelationship.Builder setSourceID(Entity.ID sourceID){
            this.sourceID = Preconditions.argumentNotNull(sourceID,"sourceID");
            return this;
        }

        public GraphRelationship.Builder setTargetID(Entity.ID targetID){
            this.targetID = Preconditions.argumentNotNull(targetID,"targetID");
            return this;
        }

        public GraphRelationship.Builder addAttribute(Attribute attribute) {
            attributes.put(attribute.getName(),attribute);
            return this;
        }

        public GraphRelationship build() {

            List<String> missingList = new ArrayList<>();
            if (this.name == null) {
                missingList.add("name");
            }

            if (this.sourceID == null) {
                missingList.add("sourceID");
            }

            if (this.targetID == null) {
                missingList.add("targetID");
            }

            if (!missingList.isEmpty()) {
                throw new IllegalStateException(
                    "Missing required properties: \"" + missingList + "\"");
            }

            return new GraphRelationship(name, sourceID, targetID, attributes);
        }
    }
}