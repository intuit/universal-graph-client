package com.intuit.ugc.api;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

public class PersistenceExceptionTest {

	@Test
	public void testInit(){
		Exception ex = new PersistenceException();
		assertNotNull(ex);
		assertTrue(ex instanceof PersistenceException);
	}
	
	@Test
	public void testInitWithString(){
		Exception ex = new PersistenceException("test-exception");
		assertNotNull(ex);
		assertTrue(ex instanceof PersistenceException);
	}
	
	@Test
	public void testInitWithStringAndThrowable(){
		Exception ex = new PersistenceException("test-exception", new Exception());
		assertNotNull(ex);
		assertTrue(ex instanceof PersistenceException);
	}
	
	@Test
	public void testInitWithThrowable(){
		Exception ex = new PersistenceException(new Exception());
		assertNotNull(ex);
		assertTrue(ex instanceof PersistenceException);
	}
}
