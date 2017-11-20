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

import com.intuit.ugc.api.BatchMutation;
import com.intuit.ugc.api.Queries.Projection;
import com.intuit.ugc.api.Relationship;
import com.intuit.ugc.api.Attribute.Name;
import com.intuit.ugc.api.Relationship.Mutation;
import com.intuit.ugc.impl.core.queryplan.OperationPipeline;
import com.intuit.ugc.impl.core.queryplan.operations.DeleteRelationship;
import com.intuit.ugc.impl.core.queryplan.operations.UpdateRelationship;

/**
 * Represents a Relationship Mutation in a graph persistence store. 
 * 
 * implements {@link com.intuit.ugc.api.Relationship.Mutation}
 * @author ajain17
 *
 */
public class GraphRelationshipMutation implements Mutation {

	private Relationship relationship;
	private final OperationPipeline pipeline;
	private final BatchMutation batchMutation;
	private final GraphAttributeOperations operatons;
	/**
	 * A boolean to differentiate between delete and update mutations
	 */
	private boolean delete = false;

	public GraphRelationshipMutation(Relationship relationship,
            OperationPipeline pipeline,
            BatchMutation batchMutation) {
		this.relationship = relationship;
		this.pipeline = pipeline;
		this.batchMutation = batchMutation;
		this.operatons = new GraphAttributeOperations();
	}

	@Override
	public Mutation setAttribute(Name name, Object value) {
		operatons.setAttributeValue(name, value);
		return this;
	}

	@Override
	public Mutation deleteAttribute(Name name) {
		operatons.unsetAttributeValue(name);
		return this;
	}

	@Override
	public com.intuit.ugc.api.BatchMutation ready() {
		if (this.delete) {
			DeleteRelationship deleteRelationship = new DeleteRelationship(relationship);
			pipeline.add(deleteRelationship);
		} else {
			UpdateRelationship updateRelationship = new UpdateRelationship(relationship, operatons);
			pipeline.add(updateRelationship);
		}
		return batchMutation;
	}

	@Override
	public Mutation delete() {
		this.delete = true;
		return this;
	}

}
