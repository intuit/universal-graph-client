/*
 * Copyright (c) 2017.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    ajain17 & nverma1 - API , implementation and initial documentation
 */

package com.intuit.ugc.impl.persistence.dse;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.datastax.driver.dse.graph.GraphResultSet;

/**
 * 
 * @author nverma1
 *
 */
public class GraphCallbackTest {

	@Test
	public void testGraphCallback(){
		GraphCallBack graphCallback = new GraphCallBack();
		GraphResultSet result = new GraphResultSet(null);
		graphCallback.onSuccess(result);
		Exception th = new Exception("testException");
		graphCallback.onFailure(th);
		assertEquals(graphCallback.getResult(), result);
		assertEquals(graphCallback.getTh(), th);
	}
}
