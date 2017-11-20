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

import com.intuit.ugc.api.NewEntity;
import com.intuit.ugc.impl.core.queryplan.Operation;

/**
 * Represents an entity creation operation. The operation itself is stored in
 * the pipeline as an instance of this class. And once execution is started over
 * the pipeline, all the operations are executed one by one.
 * 
 * @author ajain17
 *
 */
public class CreateEntity implements Operation {

    private final NewEntity newEntity;

    public CreateEntity(NewEntity newEntity) {
        super();
        this.newEntity = newEntity;
    }

    public NewEntity newEntity() {
        return newEntity;
    }

    @Override
    public String toString() {
        return "Operation [Create Entity] Entity ID ["
                + newEntity.getEntityID().getRawID() + "]";
    }

}
