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

/**
 * 
 * @author nverma1
 *
 */
public class DSEMetadataTest {
	@Test
	public void testCreateDSEMetadata(){
		DSEMetadata<String> metadata = new DSEMetadata<>(String.class);
		assertEquals(metadata.getType(), String.class);
	}
}
