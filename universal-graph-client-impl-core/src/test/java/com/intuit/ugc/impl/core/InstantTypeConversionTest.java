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
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.time.Instant;
import java.util.Date;

import org.testng.annotations.Test;

import com.intuit.ugc.impl.core.InstantTypeConversion;

/**
 * 
 * @author nverma1
 *
 */
public class InstantTypeConversionTest {
	@Test
	public void testInstantTypeConversionWithNullInput(){
		InstantTypeConversion instantTypeConversion = new InstantTypeConversion();
		assertNull(instantTypeConversion.convert(null));
	}
	
	@Test
	public void testInstantTypeConversionWithInstantInput(){
		InstantTypeConversion instantTypeConversion = new InstantTypeConversion();
		Instant convertedInstant = instantTypeConversion.convert(Instant.EPOCH);
		assertEquals(convertedInstant, Instant.EPOCH);
	}
	
	@Test
	public void testInstantTypeConversionWithDateInput(){
		InstantTypeConversion instantTypeConversion = new InstantTypeConversion();
		assertTrue(instantTypeConversion.convert(new Date()) instanceof Instant);
	}
	
	@Test
	public void testInstantType(){
		InstantTypeConversion instantTypeConversion = new InstantTypeConversion();
		Object[] typeKeys = instantTypeConversion.getTypeKeys();
		assertEquals(typeKeys[0], Instant.class);
		assertEquals(typeKeys[1], Instant.class.getName());
	}
	
	@Test
	public void testIntantTypeConversionWithCharacterSequence(){
		InstantTypeConversion instantTypeConversion = new InstantTypeConversion();
		CharSequence instantCharSeq = "2014-12-03T10:15:30.00Z";
		Instant convertedInstant = instantTypeConversion.convert(instantCharSeq);
		assertEquals(convertedInstant.getNano(), Instant.parse(instantCharSeq).getNano());
	}
	
	@Test
	public void testInstantTypeConversionWithString(){
		InstantTypeConversion instantTypeConversion = new InstantTypeConversion();
		String instantStr = new String("2014-12-03T10:15:30.00Z");
		InstantFormat obj = new InstantFormat(instantStr);
		Instant convertedInstant = instantTypeConversion.convert(obj);
		assertEquals(convertedInstant.getNano(), Instant.parse(instantStr).getNano());
	}
}


class InstantFormat {
	private String instantStr;
	
	public InstantFormat(String instantStr){
		this.instantStr = instantStr;
	}
	
	@Override
	public String toString() {
		return this.instantStr;
	}
}
