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
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.intuit.ugc.api.Attribute;
import com.intuit.ugc.api.Attribute.Name;
import com.intuit.ugc.impl.core.GraphAttributeOperations;
import com.intuit.ugc.impl.core.helper.TartanImplTestConstants;

/**
 * 
 * @author nverma1
 *
 */
public class GraphAttributeOperationsTest {
	@Test
	public void testGraphAttributeOperationsCreation(){
		GraphAttributeOperations graphAttributeOperations = new GraphAttributeOperations();
		Name name = TartanImplTestConstants.ATTR_DUMMY_ATTRIBUTE_NAME;
		String attributeOperationValue = "DummySetOperationValue1";
		graphAttributeOperations.setAttributeValue(name , attributeOperationValue);
		Map<Name, Object> setValueOperations = graphAttributeOperations.getSetValueOperations();
		assertEquals(1, setValueOperations.size());
		Object value = setValueOperations.get(name);
		assertTrue(value instanceof String);
		assertEquals(value, attributeOperationValue);
		//set another attribute value in graph attribute operations
		Name name2 = Attribute.Name.valueOf("test.ugc.core.attribute.Setopertaionkey");
		String attributeOperationValue2 = "DummySetOperationValue2";
		graphAttributeOperations.setAttributeValue(name2, attributeOperationValue2);
		setValueOperations = graphAttributeOperations.getSetValueOperations();
		assertEquals(2, setValueOperations.size());
		//unset one operation
		graphAttributeOperations.unsetAttributeValue(name2);
		setValueOperations = graphAttributeOperations.getSetValueOperations();
		//since the object is not removed from the map on unset
		assertEquals(2, setValueOperations.size());
		List<Name> unsetValueOperations = graphAttributeOperations.getUnsetValueOperations();
		assertEquals(1, unsetValueOperations.size());
		Name unsetValueOperation = unsetValueOperations.get(0);
		assertEquals(unsetValueOperation, name2);
	}
	
	@Test
	public void testGraphAttributeOperationsCreationWithNullName(){
		GraphAttributeOperations graphAttributeOperations = new GraphAttributeOperations();
		Name name = null;
		String attributeOperationValue = "DummySetOperationValue1";
		try{
			graphAttributeOperations.setAttributeValue(name, attributeOperationValue);
			fail("Should throw an exception with null attribute name");
		}catch(Exception e){
			assertTrue(e instanceof IllegalArgumentException);
			assertEquals(e.getMessage(), "Parameter \"name::Atribute.Name\" cannot be null");
		}
	}
	
	@Test
	public void testGraphAttributeOperationsCreationWithNullValue(){
		GraphAttributeOperations graphAttributeOperations = new GraphAttributeOperations();
		Name name = TartanImplTestConstants.ATTR_DUMMY_ATTRIBUTE_NAME;
		graphAttributeOperations.setAttributeValue(name, null);
		Map<Name, Object> setValueOperations = graphAttributeOperations.getSetValueOperations();
		//since the value was null, the name was unset instead of setting it.
		assertEquals(0, setValueOperations.size());
		List<Name> unsetValueOperations = graphAttributeOperations.getUnsetValueOperations();
		assertEquals(1, unsetValueOperations.size());
		assertEquals(name, unsetValueOperations.get(0));
	}
}
