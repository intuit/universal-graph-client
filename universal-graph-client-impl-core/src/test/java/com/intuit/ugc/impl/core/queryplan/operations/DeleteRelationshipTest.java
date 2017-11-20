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
import com.intuit.ugc.impl.core.GraphRelationship;

/**
 * 
 * @author nverma1
 *
 */
public class DeleteRelationshipTest {

	@Test
	public void testInitializeDeleteRelationship(){
		Relationship relationship = new GraphRelationship.Builder().setName(Relationship.Name.valueOf("test-entity"))
				.setSourceID(Entity.ID.valueOf("test-source")).setTargetID(Entity.ID.valueOf("test-target")).build();
		DeleteRelationship deleteRelationship = new DeleteRelationship(relationship );
		assertEquals(deleteRelationship.getRelationship(), relationship);
		assertEquals(deleteRelationship.toString(),
				"Operation [Delete Relationship] between source entity id [test-source] and target entity id [test-target]");
	}
}
