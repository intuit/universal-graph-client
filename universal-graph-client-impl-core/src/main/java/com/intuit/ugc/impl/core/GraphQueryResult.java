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

import java.util.List;

import com.intuit.ugc.api.Entity;
import com.intuit.ugc.api.Query;
import com.intuit.ugc.api.Relationship;
import com.intuit.ugc.impl.core.spi.QueryResult;

/**
 * A representation of a query result after the query is executed on a graph persistence store
 *
 * implements {@link com.intuit.ugc.api.Query.Result}
 * @author ajain17
 */
public class GraphQueryResult implements Query.Result {

	private final QueryResult<Entity, Relationship> queryResult;

	public GraphQueryResult(
			final QueryResult<Entity, Relationship> queryResult) {
		this.queryResult = queryResult;
	}

	@Override
	public Entity getEntity() {
		return queryResult.getEntityResponse().size() > 0 
            ? queryResult.getEntityResponse().get(0) 
            : null;
	}

	@Override
	public List<Entity> getEntities() {
		return queryResult.getEntityResponse();
	}

	@Override
	public Relationship getRelationship() {
		return queryResult.getRelationshipResponse().size() > 0 
            ? queryResult.getRelationshipResponse().get(0) 
            : null;
	}

	@Override
	public List<Relationship> getRelationships() {
		return queryResult.getRelationshipResponse();
	}
}
