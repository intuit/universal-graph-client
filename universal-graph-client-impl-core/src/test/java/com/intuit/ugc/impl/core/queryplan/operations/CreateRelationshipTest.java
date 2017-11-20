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

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.intuit.ugc.api.Entity;
import com.intuit.ugc.api.NewEntity;
import com.intuit.ugc.api.NewRelationship;
import com.intuit.ugc.impl.core.helper.TartanImplTestConstants;
import com.intuit.ugc.impl.core.helper.TartanImplTestUtil;
import com.intuit.ugc.impl.core.queryplan.operations.CreateRelationship;

/**
 * 
 * @author nverma1
 *
 */
public class CreateRelationshipTest {
	private TartanImplTestUtil util;
	Entity.ID realmEntityID;

	@BeforeClass
	public void setup() {
		util = new TartanImplTestUtil();
	}

	@Test
	public void testCreateRelationship() {
		NewEntity sourceEntity = util.createNewEntity(TartanImplTestConstants.ATTR_CHILD_KEY);
		NewEntity targetEntity = util.createNewEntity(TartanImplTestConstants.ATTR_PARENT_KEY);
		NewRelationship newRelationship = NewRelationship
				.between(sourceEntity.getEntityID(), targetEntity.getEntityID()).withLabel(TartanImplTestConstants.ATTR_PARENT_CHILD_KEY).build();
		
		CreateRelationship createRelationship = new CreateRelationship(newRelationship);
		assertEquals(newRelationship.getName(), createRelationship.newRelationship().getName());
		assertEquals(sourceEntity.getEntityID(), createRelationship.newRelationship().getSourceID());
		assertEquals(TartanImplTestConstants.ATTR_PARENT_CHILD_KEY.toString(), createRelationship.newRelationship().getName().toString());
		String expectedRelationString = "Operation [Create Relationship] SOURCE ID ["
                + newRelationship.getSourceID() + "]" + " TARGET ID ["
                + newRelationship.getTargetID() + "]";
		assertEquals(expectedRelationString, createRelationship.toString());
	}
}
