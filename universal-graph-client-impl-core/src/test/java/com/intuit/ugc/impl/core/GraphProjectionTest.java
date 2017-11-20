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

import static org.testng.Assert.assertNotNull;

import org.testng.annotations.Test;

import com.intuit.ugc.api.OperationResult;
import com.intuit.ugc.api.Query;
import com.intuit.ugc.api.Queries.Projection;
import com.intuit.ugc.api.Query.Result;
import com.intuit.ugc.impl.core.GraphProjection;
import com.intuit.ugc.impl.core.helper.MockGraphVisitor;
import com.intuit.ugc.impl.core.helper.TartanImplTestConstants;
import com.intuit.ugc.impl.core.queryplan.OperationPipeline;
import com.intuit.ugc.impl.core.queryplan.operations.GraphTerminalOperation;
import com.intuit.ugc.impl.core.spi.GraphVisitor;

/**
 * 
 * @author nverma1
 *
 */
public class GraphProjectionTest {

	@Test
	public void testIncludeAttribute(){
		GraphVisitor repository = new MockGraphVisitor();
		GraphProjection graphProjection = new GraphProjection(repository, new OperationPipeline());
		Projection includeAttribute = graphProjection.includeAttribute(TartanImplTestConstants.ATTR_DUMMY_ATTRIBUTE_NAME);
		assertNotNull(includeAttribute);
	}
	
	@Test
	public void testIncludeAttributesFamily(){
		GraphVisitor repository = new MockGraphVisitor();
		GraphProjection graphProjection = new GraphProjection(repository, new OperationPipeline());
		Projection includeAttributes = graphProjection.includeAttributes(TartanImplTestConstants.ATTR_DUMMY_FAMILY);
		assertNotNull(includeAttributes);
	}
	
	@Test
	public void testReadyAndExecute(){
		GraphVisitor repository = new MockGraphVisitor();
		GraphProjection graphProjection = new GraphProjection(repository, new OperationPipeline());
		Query ready = graphProjection.ready();
		OperationResult<Result> execute = ready.execute();
		assertNotNull(execute);
	}
	
	@Test
	public void testTerminalOperationProjection(){
		OperationPipeline operationPipeline = new OperationPipeline();
		GraphTerminalOperation terminalOperation = new GraphTerminalOperation();
		operationPipeline.add(terminalOperation);
		GraphVisitor repository = new MockGraphVisitor();
		GraphProjection graphProjection = new GraphProjection(repository, new OperationPipeline());
		Projection includeAttribute = graphProjection.includeAttribute(TartanImplTestConstants.ATTR_DUMMY_ATTRIBUTE_NAME);
		assertNotNull(includeAttribute);
	}
}
