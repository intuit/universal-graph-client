/*
 * Copyright (c) 2017.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    nverma1 - API and implementation and initial documentation
 */

package com.intuit.ugc.impl.core.queryplan.operations;

import com.intuit.ugc.api.Entity;
import com.intuit.ugc.impl.core.queryplan.Operation;

/**
 * Represents an entity deletion operation. The operation itself is stored in
 * the pipeline as an instance of this class. And once execution is started over
 * the pipeline, all the operations are executed one by one.
 * @author nverma1
 *
 */
public class DeleteEntity implements Operation {
	private final Entity entity;
	
	public DeleteEntity(Entity entity){
		this.entity = entity;
	}
	
	public Entity getEntity(){
		return this.entity;
	}
	
    @Override
    public String toString() {
        return "Operation [Delete Entity] entity id ["
                + entity.getID().getRawID() + "]";
    }
}
