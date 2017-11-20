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
import com.intuit.ugc.api.Entity.ID;

/**
 * 
 * @author nverma1
 *
 */
public class GetEntityByIdTest {

	@Test
	public void testGetEntityById(){
		ID id = Entity.ID.valueOf("test-id");
		GetEntityByID getEntityById = new GetEntityByID(id);
		assertEquals(getEntityById.getId(), id);
		assertEquals(getEntityById.toString(), "Operation [Get Entity] Entity ID [test-id]");
	}
}
