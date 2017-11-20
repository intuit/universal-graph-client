package com.intuit.ugc.api;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

public class AccessControlExceptionTest {
	@Test
	public void initDefault(){
		Exception ex = new AccessControlException();
		assertNotNull(ex);
		assertTrue(ex instanceof AccessControlException);
	}
	
	@Test
	public void initWithStringArg(){
		Exception ex = new AccessControlException("test-exception");
		assertNotNull(ex);
		assertTrue(ex instanceof AccessControlException);
	}
	
	@Test
	public void initWithStringAndThrowableArg(){
		Exception ex = new AccessControlException("test-exception", new Exception());
		assertNotNull(ex);
		assertTrue(ex instanceof AccessControlException);
	}
}
