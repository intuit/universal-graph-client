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

import com.intuit.ugc.api.Relationship;
import com.intuit.ugc.impl.core.queryplan.Operation;

/**
 * Represents a relationship deletion operation. The operation itself is stored in
 * the pipeline as an instance of this class. And once execution is started over
 * the pipeline, all the operations are executed one by one.
 * 
 * @author nverma1
 *
 */
public class DeleteRelationship implements Operation{
	private final Relationship relationship;
	
	public DeleteRelationship(Relationship relationship){
		this.relationship = relationship;
	}
	
	public Relationship getRelationship(){
		return this.relationship;
	}
	
    @Override
    public String toString() {
        return "Operation [Delete Relationship] between source entity id ["
                + relationship.getSourceID().getRawID() + "]" + " and target entity id [" 
        		+ relationship.getTargetID().getRawID() + "]";
    }
}
