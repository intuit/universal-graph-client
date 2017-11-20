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

import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.intuit.ugc.api.NewEntity;
import com.intuit.ugc.impl.core.helper.TartanImplTestConstants;
import com.intuit.ugc.impl.core.helper.TartanImplTestUtil;
import com.intuit.ugc.impl.core.queryplan.operations.CreateEntity;

/**
 * 
 * @author nverma1
 *
 */
public class CreateEntityTest {
	private TartanImplTestUtil util;
	
	@BeforeClass
	public void setup(){
		util = new TartanImplTestUtil();
	}
	
	@Test
	public void testCreateEntity() {
		NewEntity newEntity = util.createNewEntity(TartanImplTestConstants.ATTR_PARENT_ID);

		CreateEntity createEntity = new CreateEntity(newEntity);
		NewEntity newEntity2 = createEntity.newEntity();
		AssertJUnit.assertEquals(newEntity2.getEntityID(), newEntity.getEntityID());
		String expectedToString = "Operation [Create Entity] Entity ID ["
                + newEntity.getEntityID().getRawID() + "]";
		AssertJUnit.assertEquals(expectedToString, createEntity.toString());
	}
}
