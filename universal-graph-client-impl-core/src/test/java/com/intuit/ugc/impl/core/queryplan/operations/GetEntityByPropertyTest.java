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

import com.intuit.ugc.api.Attribute;
import com.intuit.ugc.api.Attribute.Name;

/**
 * 
 * @author nverma1
 *
 */
public class GetEntityByPropertyTest {
	
	@Test
	public void testGetEntityByProperty(){
		Name name = Attribute.Name.valueOf("test-property");
		GetEntityByProperty getEntityByProperty = new GetEntityByProperty(name,
				"test-property-val");
		assertEquals(getEntityByProperty.getName(),name);
		assertEquals(getEntityByProperty.getValue(), "test-property-val");
		assertEquals(getEntityByProperty.toString(), "Operation [Get Entity] Key [test-property] Value [test-property-val]");
	}
}
