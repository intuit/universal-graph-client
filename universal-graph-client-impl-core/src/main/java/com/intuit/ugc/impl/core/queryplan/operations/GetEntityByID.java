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

import com.intuit.ugc.api.Entity.ID;
import com.intuit.ugc.impl.core.queryplan.Operation;

/**
 * Represents an operation to fetch an entity given it's id. The operation itself is stored in
 * the pipeline as an instance of this class. And once execution is started over
 * the pipeline, all the operations are executed one by one.
 * 
 * @author ajain17
 *
 */
public class GetEntityByID implements Operation {

    private final ID id;
    
    public GetEntityByID(ID id) {
        super();
        this.id = id;
    }
    
    public ID getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Operation [Get Entity] Entity ID [" + id.getRawID() + "]";
    }

}
