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
import com.intuit.ugc.api.Relationship;
import com.intuit.ugc.api.Relationship.Name;
import com.intuit.ugc.impl.core.GraphPredicate;

/**
 * 
 * @author nverma1
 *
 */
public class SelectEntitiesTest {

	@Test
	public void testSelectEntities(){
		Name relationName = Relationship.Name.valueOf("test-relationship");
		Direction direction = Direction.IN_OUT;
		SelectEntities selectEntities = new SelectEntities(relationName , direction );
		assertEquals(selectEntities.getDirection(), direction);
		Predicate predicate = new GraphPredicate();
		selectEntities.condition(predicate);
		assertEquals(selectEntities.getPredicate(), predicate);
		assertEquals(selectEntities.getRelationName(), relationName);
		assertEquals(selectEntities.toString(), "Operation [Select Entities] with relation label [test-relationship]");
		try{
			selectEntities.condition(predicate);
			fail("resetting predicate should throw exception");
		}catch(Exception e){
			assertTrue(e instanceof IllegalStateException);
		}
	}
}
