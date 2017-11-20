package com.intuit.ugc.api;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

public class InvalidAttributeExceptionTest {

	@Test
	public void testInit(){
		Exception ex = new InvalidAttributeException();
		assertNotNull(ex);
		assertTrue(ex instanceof InvalidAttributeException);
	}
	
	@Test
	public void testInitWithStringArg(){
		Exception ex = new InvalidAttributeException("test-exception");
		assertNotNull(ex);
		assertTrue(ex instanceof InvalidAttributeException);
	}
	
	@Test
	public void testInitWithStringAndThrowableArg(){
		Exception ex = new InvalidAttributeException("test-exception", new Exception());
		assertNotNull(ex);
		assertTrue(ex instanceof InvalidAttributeException);
	}
}
