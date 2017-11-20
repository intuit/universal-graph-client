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
import com.intuit.ugc.api.Attribute.Family;
import com.intuit.ugc.api.Attribute.Name;

/**
 * 
 * @author nverma1
 *
 */
public class GraphTerminalOperationTest {

	@Test
	public void testGraphTerminalOperation(){
		GraphTerminalOperation graphTerminalOperation = new GraphTerminalOperation();
		Name name = Attribute.Name.valueOf("test-name");
		graphTerminalOperation.include(name);
		Family namespace = Attribute.Family.valueOf("test-namespace");
		graphTerminalOperation.include(namespace);
		assertEquals(graphTerminalOperation.getNameList().get(0), name);
		assertEquals(graphTerminalOperation.getNamespaceList().get(0), namespace);
		assertEquals(graphTerminalOperation.toString(), "Graph Terminal Operation");
	}
}
