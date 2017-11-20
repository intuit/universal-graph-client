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
import com.intuit.ugc.impl.core.GraphAttributeOperations;
import com.intuit.ugc.impl.core.GraphEntity;

/**
 * 
 * @author nverma1
 *
 */
public class UpdateEntityTest {

	@Test
	public void testUpdateEntity(){
		Entity entity = new GraphEntity.Builder().setID(Entity.ID.valueOf("test-entity")).build();
		GraphAttributeOperations operations = new GraphAttributeOperations();
		UpdateEntity updateEntity = new UpdateEntity(entity , operations );
		assertEquals(updateEntity.getEntity(), entity);
		assertEquals(updateEntity.getOperations(), operations);
		assertEquals(updateEntity.toString(), "Operation [Update Entity] entity id [test-entity]");
	}
}
