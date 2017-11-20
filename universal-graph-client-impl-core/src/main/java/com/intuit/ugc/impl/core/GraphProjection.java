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

import com.google.inject.Inject;
import com.intuit.ugc.api.AccessControlException;
import com.intuit.ugc.api.Attribute;
import com.intuit.ugc.api.OperationResult;
import com.intuit.ugc.api.Queries;
import com.intuit.ugc.api.Query;
import com.intuit.ugc.impl.core.queryplan.Operation;
import com.intuit.ugc.impl.core.queryplan.OperationFeederImpl;
import com.intuit.ugc.impl.core.queryplan.OperationPipeline;
import com.intuit.ugc.impl.core.queryplan.TerminalOperation;
import com.intuit.ugc.impl.core.queryplan.operations.GraphTerminalOperation;
import com.intuit.ugc.impl.core.spi.GraphVisitor;
import com.intuit.ugc.impl.core.spi.QueryResultImpl;

/**
 * Represents a projection in a graph persistence store
 * 
 * implements {@link com.intuit.ugc.api.Queries.Projection}
 * 
 * @author ajain17
 */
public class GraphProjection implements Queries.Projection {

	private GraphVisitor repository;
	private final OperationPipeline pipeline;

	@Inject
	public GraphProjection(GraphVisitor repository,
            OperationPipeline aPipeline) {
		this.pipeline = aPipeline;
		this.repository = repository;
		this.pipeline.add(new GraphTerminalOperation());
	}

	private TerminalOperation getTerminal() {
	    Operation currentOperation = pipeline.getTailOperation();
		if (currentOperation instanceof TerminalOperation) {
		    TerminalOperation graphOps = (TerminalOperation) currentOperation;
			return graphOps;
		} else {
			return null;
			// Throw Exception for Invalid include operation
		}
	}

	@Override
	public Queries.Projection includeAttribute(Attribute.Name name) {
		getTerminal().include(name);
		return this;
	}

	@Override
	public Queries.Projection includeAttributes(Attribute.Family namespace) {
		getTerminal().include(namespace);
		return this;
	}

	@Override
	public Query ready() {
		return new QueryImpl(repository, pipeline);
	}
	
	private class QueryImpl implements Query {
	    private OperationFeederImpl graphOperation;
	    
	    QueryImpl (GraphVisitor persistenceRepository, OperationPipeline operationsPipeline){
	        this.graphOperation = new OperationFeederImpl(persistenceRepository, operationsPipeline);
	    }
	    
        @Override
        public OperationResult<Result> execute() throws AccessControlException {
            return () -> new GraphQueryResult((QueryResultImpl) graphOperation.operationResult());
        }
	    
	}
}
