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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.testng.annotations.Test;

import com.intuit.ugc.api.Entity;
import com.intuit.ugc.api.Relationship;
import com.intuit.ugc.impl.core.spi.QueryResultImpl;
import com.intuit.ugc.impl.core.spi.QueryResult.CurrentOperationType;

/**
 * 
 * @author nverma1
 *
 */
public class QueryResultImplUnitTest {
	
	@Test
	public void testQueryResultImplWithRelationshipCreation(){
		CurrentOperationType currentOperation = CurrentOperationType.RELATIONHIP;
		QueryResultImpl queryResultImpl = new QueryResultImpl(currentOperation );
		assertNotNull(queryResultImpl);
		List<Relationship> relationshipList = new ArrayList<Relationship>();
		queryResultImpl.setRelationshipOpResponse(relationshipList);
		assertNotNull(queryResultImpl.getRelationshipResponse());
		assertEquals(queryResultImpl.getEntityResponse(), Collections.emptyList());
		assertEquals(queryResultImpl.getCurrentOperation(), currentOperation);
	}
	
	@Test
	public void testQueryResultImplWithEntityCreation(){
		CurrentOperationType currentOperation = CurrentOperationType.ENTITY;
		QueryResultImpl queryResultImpl = new QueryResultImpl(currentOperation);
		assertNotNull(queryResultImpl);
		List<Entity> entityList = new ArrayList<Entity>();
		queryResultImpl.setEntityOpResponse(entityList);
		assertNotNull(queryResultImpl.getEntityResponse());
		assertEquals(queryResultImpl.getRelationshipResponse(), Collections.emptyList());
		assertEquals(queryResultImpl.getCurrentOperation(), currentOperation);
	}
}
