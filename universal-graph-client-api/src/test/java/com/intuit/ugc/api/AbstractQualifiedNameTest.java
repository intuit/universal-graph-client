package com.intuit.ugc.api;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.intuit.ugc.api.helper.QualifiedName;

public class AbstractQualifiedNameTest {
	
	@Test
	public void testAbstractQualifiedNameInit(){
		String name = "test-name";
		AbstractQualifiedName aqn = new QualifiedName(name); 
		assertEquals(aqn.getName(), name);
		assertEquals(aqn.toString(), name);
		assertNotNull(aqn.hashCode());
	}
	
	@Test
	public void testAbstractQualifiedNameEquality(){
		String name1 = "test-name-1";
		AbstractQualifiedName aqn1 = new QualifiedName(name1); 
		AbstractQualifiedName aqn2 = new QualifiedName(name1); 
		String name2 = "test-name-2";
		AbstractQualifiedName aqn3 = new QualifiedName(name2); 
		assertTrue(aqn1.equals(aqn1));
		assertTrue(aqn1.equals(aqn2));
		assertFalse(aqn1.equals(aqn3));
		assertFalse(aqn1.equals(null));
		assertFalse(aqn1.equals(name1));
	}
}
