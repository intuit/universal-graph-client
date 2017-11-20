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

package com.intuit.ugc.impl.core;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.testng.annotations.Test;

import com.intuit.ugc.impl.core.GraphMutationResult;

/**
 * 
 * @author nverma1
 *
 */
public class GraphMutationResultTest {

	@Test
	public void testCreateGraphMutationResultWithEntityKeys(){
		List<String> entityKeys = new ArrayList<String>(Arrays.asList("Key1", "Key2", "Key3"));
		GraphMutationResult graphMutationResult = new GraphMutationResult(entityKeys );
		List<String> entityKeysReturned = graphMutationResult.getEntityKeys();
		assertNotNull(entityKeysReturned);
		assertEquals(entityKeys.size(), entityKeysReturned.size());
		assertEquals(entityKeys, entityKeysReturned);
	}
	
	@Test
	public void testCreateGraphMutationResultWithoutKeys(){
		GraphMutationResult graphMutationResult = new GraphMutationResult();
		assertEquals(Collections.emptyList(), graphMutationResult.getEntityKeys());
	}
}
