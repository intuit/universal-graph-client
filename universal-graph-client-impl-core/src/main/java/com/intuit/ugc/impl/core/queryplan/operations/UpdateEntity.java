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

import com.intuit.ugc.api.Entity;
import com.intuit.ugc.impl.core.GraphAttributeOperations;
import com.intuit.ugc.impl.core.queryplan.Operation;

/**
 * Represents an entity update operation. The operation itself is stored in
 * the pipeline as an instance of this class. And once execution is started over
 * the pipeline, all the operations are executed one by one.
 * 
 * @author ajain17
 *
 */
public class UpdateEntity implements Operation {

    private final Entity entity;
    private final GraphAttributeOperations operations;

    public UpdateEntity(Entity entity, GraphAttributeOperations operations) {
        super();
        this.entity = entity;
        this.operations = operations;
    }
    
    public Entity getEntity() {
        return entity;
    }

    public GraphAttributeOperations getOperations() {
        return operations;
    }

    @Override
    public String toString() {
        return "Operation [Update Entity] entity id ["
                + entity.getID().getRawID() + "]";
    }

}
