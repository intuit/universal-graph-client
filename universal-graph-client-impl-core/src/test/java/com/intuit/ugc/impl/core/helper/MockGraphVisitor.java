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
import com.intuit.ugc.impl.core.spi.GraphVisitor;

/**
 * 
 * @author nverma1
 *
 */
public class MockGraphVisitor implements GraphVisitor {

	@Override
	public void visit(CreateEntity operation) {

	}

	@Override
	public void visit(CreateRelationship operation) {

	}

	@Override
	public void visit(GetBatchEntityByID operation) {

	}

	@Override
	public void visit(GetEntityByID operation) {

	}

	@Override
	public void visit(GetEntityByProperty operation) {

	}

	@Override
	public void visit(GraphTerminalOperation operation) {

	}

	@Override
	public void visit(SelectEntities operation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(SelectRelationships operation) {

	}

	@Override
	public void visit(UpdateEntity operation) {

	}

	@Override
	public void visit(UpdateRelationship operation) {

	}

	@Override
	public VisitOperationResult getResult() {
		return new VisitOperationResult() {
		};
	}

	@Override
	public void visit(DeleteEntity operation) {
		
	}

	@Override
	public void visit(DeleteRelationship operation) {
	}

}
