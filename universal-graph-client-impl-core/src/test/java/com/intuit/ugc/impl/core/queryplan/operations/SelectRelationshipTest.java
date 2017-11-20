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

package com.intuit.ugc.impl.core.queryplan.operations;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import org.testng.annotations.Test;

import com.intuit.ugc.api.Predicate;
import com.intuit.ugc.api.Queries.GraphTraversal.Direction;
import com.intuit.ugc.api.Relationship.Name;
import com.intuit.ugc.impl.core.GraphPredicate;

/**
 * 
 * @author nverma1
 *
 */
public class SelectRelationshipTest {

	@Test
	public void testSelectRelationship(){
		Name name = Name.valueOf("test-relationships");
		Direction direction = Direction.IN_OUT;
		SelectRelationships selectRelationship = new SelectRelationships(name , direction );
		Predicate predicate = new GraphPredicate();
		selectRelationship.condition(predicate );
		assertEquals(selectRelationship.getDirection(), direction);
		assertEquals(selectRelationship.getPredicate(), predicate);
		assertEquals(selectRelationship.getRelationshipName(), name);
		assertEquals(selectRelationship.toString(), "Operation [Select Relationships] with relation label [test-relationships]");
		try{
			selectRelationship.condition(predicate);
			fail("resetting predicate should throw exception");
		}catch(Exception e){
			assertTrue(e instanceof IllegalStateException);
		}

	}
}
