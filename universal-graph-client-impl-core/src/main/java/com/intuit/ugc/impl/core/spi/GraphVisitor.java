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

package com.intuit.ugc.impl.core.spi;

import com.intuit.ugc.impl.core.VisitOperationResult;
import com.intuit.ugc.impl.core.queryplan.operations.CreateEntity;
import com.intuit.ugc.impl.core.queryplan.operations.CreateRelationship;
import com.intuit.ugc.impl.core.queryplan.operations.DeleteEntity;
import com.intuit.ugc.impl.core.queryplan.operations.DeleteRelationship;
import com.intuit.ugc.impl.core.queryplan.operations.GetBatchEntityByID;
import com.intuit.ugc.impl.core.queryplan.operations.GetEntityByID;
import com.intuit.ugc.impl.core.queryplan.operations.GetEntityByProperty;
import com.intuit.ugc.impl.core.queryplan.operations.GraphTerminalOperation;
import com.intuit.ugc.impl.core.queryplan.operations.SelectEntities;
import com.intuit.ugc.impl.core.queryplan.operations.SelectRelationships;
import com.intuit.ugc.impl.core.queryplan.operations.UpdateEntity;
import com.intuit.ugc.impl.core.queryplan.operations.UpdateRelationship;

/**
 * Represents a graph visitor through which the graph is visited and different
 * graph operations are performed. All the specific graph provider libraries are
 * required to extend the graph visitor interface and have their own specific
 * implementation of the graph visit operations.
 * 
 * @author ajain17
 */
public interface GraphVisitor {

	/**
	 * Create Entity
	 * 
	 * @param operation
	 */
	public void visit(CreateEntity operation);

	/**
	 * Create Relationships
	 * 
	 * @param operation
	 */
	public void visit(CreateRelationship operation);

	/**
	 * Get Batch Entity By ID
	 * 
	 * @param operation
	 */
	public void visit(GetBatchEntityByID operation);

	/**
	 * Get Entity By ID
	 * 
	 * @param operation
	 */
	public void visit(GetEntityByID operation);

	/**
	 * Get Entity By Property
	 * 
	 * @param operation
	 */
	public void visit(GetEntityByProperty operation);

	/**
	 * Graph Terminal Operation
	 * 
	 * @param operation
	 */
	public void visit(GraphTerminalOperation operation);

	/**
	 * Select Entities
	 * 
	 * @param operation
	 */
	public void visit(SelectEntities operation);

	/**
	 * Select Relationships
	 * 
	 * @param operation
	 */
	public void visit(SelectRelationships operation);

	/**
	 * Update Entity
	 * 
	 * @param operation
	 */
	public void visit(UpdateEntity operation);

	/**
	 * Update Relationship
	 * 
	 * @param operation
	 */
	public void visit(UpdateRelationship operation);

	/**
	 * Get Result
	 * 
	 * @return
	 */
	public VisitOperationResult getResult();
	
	/**
	 * Delete an entity
	 *  
	 * @param operation
	 */
	public void visit(DeleteEntity operation);
	/**
	 * Delete a relationship between two entities
	 * 
	 * @param operation
	 */
	public void visit(DeleteRelationship operation);
}
