package com.intuit.ugc.api;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

public class InvalidFormatExceptionTest {
	
	@Test
	public void testInit(){
		Exception ex = new InvalidFormatException();
		assertNotNull(ex);
		assertTrue(ex instanceof InvalidFormatException);
	}
	
	@Test
	public void testInitWithStringArg(){
		Exception ex = new InvalidFormatException("test-exception");
		assertNotNull(ex);
		assertTrue(ex instanceof InvalidFormatException);
	}
	
	@Test
	public void testInitWithStringArgAndThrowable(){
		Exception ex = new InvalidFormatException("test-exception", new Exception());
		assertNotNull(ex);
		assertTrue(ex instanceof InvalidFormatException);
	}
}
