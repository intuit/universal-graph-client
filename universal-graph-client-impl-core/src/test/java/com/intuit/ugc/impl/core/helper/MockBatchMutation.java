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

package com.intuit.ugc.impl.core.helper;

import com.intuit.ugc.api.AccessControlException;
import com.intuit.ugc.api.BatchMutation;
import com.intuit.ugc.api.Entity;
import com.intuit.ugc.api.InvalidAttributeException;
import com.intuit.ugc.api.NewEntity;
import com.intuit.ugc.api.NewRelationship;
import com.intuit.ugc.api.OperationResult;
import com.intuit.ugc.api.Relationship;
import com.intuit.ugc.api.Entity.Mutation;

/**
 * 
 * @author nverma1
 *
 */
public class MockBatchMutation implements BatchMutation {

	@Override
	public BatchMutation createEntity(NewEntity newEntity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BatchMutation createRelationship(NewRelationship newRelationship) {
		return null;
	}

	@Override
	public Mutation withEntity(Entity entity) {
		return null;
	}

	@Override
	public com.intuit.ugc.api.Relationship.Mutation withRelationship(Relationship relationship) {
		return null;
	}

	@Override
	public OperationResult<Result> execute() throws InvalidAttributeException, AccessControlException {
		return null;
	}

}
