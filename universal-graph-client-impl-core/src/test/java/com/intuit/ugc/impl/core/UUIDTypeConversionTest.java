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

import java.util.UUID;

import org.testng.annotations.Test;

import com.intuit.ugc.impl.core.UUIDTypeConversion;

/**
 * 
 * @author nverma1
 *
 */
public class UUIDTypeConversionTest {
	@Test
	public void testUUIDTypeConversionWithNullInput(){
		UUIDTypeConversion uuidTypeConverison = new UUIDTypeConversion();
		assertNull(uuidTypeConverison.convert(null));
	}
	
	@Test
	public void testUUIDTypeConversionWithUUIDInput(){
		UUIDTypeConversion uuidTypeConverison = new UUIDTypeConversion();
		UUID uuid = UUID.randomUUID();
		assertTrue(uuidTypeConverison.convert(uuid) instanceof UUID);
	}
	
	@Test
	public void testUUIDTypeConversionWithStringInput(){
		UUIDTypeConversion uuidTypeConverison = new UUIDTypeConversion();
		String uuid = UUID.randomUUID().toString();
		UUID uuidRetval = uuidTypeConverison.convert(uuid);
		assertTrue(uuidRetval instanceof UUID);
		assertEquals(uuid, uuidRetval.toString());
	}
	
	@Test
	public void testUUIDTypeConversionWithByteArrayInput(){
		UUIDTypeConversion uuidTypeConverison = new UUIDTypeConversion();
		byte[] uuid = UUID.randomUUID().toString().getBytes();
		UUID uuidRetval = uuidTypeConverison.convert(uuid);
		assertTrue(uuidRetval instanceof UUID);
		byte[] returnedUUID = uuidRetval.toString().getBytes();
		assertEquals(uuid.length, returnedUUID.length);
	}
	
	@Test
	public void testUUIDTypeConversionWithCharArrayInput(){
		UUIDTypeConversion uuidTypeConverison = new UUIDTypeConversion();
		char[] uuidChars = UUID.randomUUID().toString().toCharArray();
		UUID uuidRetval = uuidTypeConverison.convert(uuidChars);
		assertTrue(uuidRetval instanceof UUID);
		char[] returnedUUID = uuidRetval.toString().toCharArray();
		assertEquals(uuidChars.length, returnedUUID.length);
	}
	
	@Test
	public void testUUIDTypeConversionWithNumberArrayInput(){
		UUIDTypeConversion uuidTypeConverison = new UUIDTypeConversion();
		int[] uuidChars = new int[]{1,2,3,4};
		try{
			uuidTypeConverison.convert(uuidChars);
		}catch(Exception e){
			assertTrue(e instanceof IllegalArgumentException);
			assertEquals(e.getMessage(), "Cannot convert to UUID from array of \"int\"");
		}
	}
	
	@Test
	public void testUUIDTypeConversionWithAnyClass(){
		UUIDTypeConversion uuidTypeConverison = new UUIDTypeConversion();
		class UUIDInput {
			public String uuidinput =  UUID.randomUUID().toString();
			@Override
			public String toString(){
				return this.uuidinput;
			}
		};
		UUID uuidOutput = uuidTypeConverison.convert(new UUIDInput());
		assertTrue(uuidOutput instanceof UUID);
	}
	
	@Test
	public void testGetTypeKeys(){
		UUIDTypeConversion uuidTypeConverison = new UUIDTypeConversion();
		Object[] typeKeys = uuidTypeConverison.getTypeKeys();
		assertEquals(typeKeys[0] , UUID.class);
		assertEquals(typeKeys[1], UUID.class.getName());
	}

}

