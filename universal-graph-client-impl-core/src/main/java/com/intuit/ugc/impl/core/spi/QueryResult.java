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

import java.util.List;

import com.intuit.ugc.impl.core.VisitOperationResult;

/**
 * Represents a specific
 * {@link com.intuit.ugc.impl.core.VisitOperationResult} wherein the query
 * operation returns a result as list of entities or a list of relationship or
 * both. For instance, result of a select query operation. 
 * 
 * @author ajain17
 */
public interface QueryResult<Entity, Relationship> extends VisitOperationResult {

	public enum CurrentOperationType {
		ENTITY, RELATIONHIP
	};

	public CurrentOperationType getCurrentOperation();

	public void setEntityOpResponse(List<Entity> entityList);

	public void setRelationshipOpResponse(List<Relationship> relationshipList);

	public List<Entity> getEntityResponse();

	public List<Relationship> getRelationshipResponse();
}
