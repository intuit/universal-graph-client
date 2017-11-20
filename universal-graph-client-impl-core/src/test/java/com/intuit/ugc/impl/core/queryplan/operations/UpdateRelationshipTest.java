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

import org.testng.annotations.Test;

import com.intuit.ugc.api.Entity;
import com.intuit.ugc.api.Relationship;
import com.intuit.ugc.api.Relationship.Name;
import com.intuit.ugc.impl.core.GraphAttributeOperations;
import com.intuit.ugc.impl.core.GraphRelationship;

/**
 * 
 * @author nverma1
 *
 */
public class UpdateRelationshipTest {
	
	@Test
	public void testUpdateRelationship(){
		Relationship relationship = new GraphRelationship.Builder().setName(Name.valueOf("test-relation"))
				.setSourceID(Entity.ID.valueOf("test-source")).setTargetID(Entity.ID.valueOf("test-target")).build();
		GraphAttributeOperations operations = new GraphAttributeOperations();
		UpdateRelationship updateRelationship = new UpdateRelationship(relationship , operations );
		assertEquals(updateRelationship.getOperations(), operations);
		assertEquals(updateRelationship.getRelationship(), relationship);
		assertEquals(updateRelationship.toString(), "Operation [Update Relationship] source id [test-source] target id [test-target]");
	}
}
