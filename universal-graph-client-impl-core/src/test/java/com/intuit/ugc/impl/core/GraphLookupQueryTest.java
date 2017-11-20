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
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.time.Instant;
import java.util.UUID;

import org.testng.annotations.Test;

import com.intuit.ugc.api.Entity;
import com.intuit.ugc.api.ValueSpecifier;
import com.intuit.ugc.api.Queries.Projection;
import com.intuit.ugc.impl.core.GraphLookUpQuery;
import com.intuit.ugc.impl.core.GraphLookUpQuery.GraphValueChange;
import com.intuit.ugc.impl.core.helper.MockGraphVisitor;
import com.intuit.ugc.impl.core.helper.TartanImplTestConstants;
import com.intuit.ugc.impl.core.spi.GraphVisitor;
import com.toddfast.util.convert.TypeConverter.Conversion;
import com.toddfast.util.convert.conversion.StringTypeConversion;

/**
 * 
 * @author nverma1
 *
 */
public class GraphLookupQueryTest {
	@Test
	public void testEntitiesWithNull(){
		GraphVisitor repository = new MockGraphVisitor();
		GraphLookUpQuery graphLookupQuery = new GraphLookUpQuery(repository);
		try{
			@SuppressWarnings("unused")
			Projection entities = graphLookupQuery.entities(null);
			fail("Should have thrown with null entities");
		}catch(Exception e){
			assertTrue(e instanceof IllegalArgumentException);
			assertEquals(e.getMessage(), "Parameter \"entityIDs\" cannot be null");
		}
	}
	
	@Test
	public void testEntitiesWithValidArgument(){
		Entity.ID newEntityID = Entity.ID.valueOf("urn:entity:" + TartanImplTestConstants.ATTR_PARENT_ID.getName()
		+ "." + TartanImplTestConstants.ATTR_PARENT_ID.getName() + "/" + UUID.randomUUID());
		GraphVisitor repository = new MockGraphVisitor();
		GraphLookUpQuery graphLookupQuery = new GraphLookUpQuery(repository);
		Projection entities = graphLookupQuery.entities(newEntityID);
		assertNotNull(entities);
	}
	
	@Test
	public void testEntityWithNullInput(){
		GraphVisitor repository = new MockGraphVisitor();
		GraphLookUpQuery graphLookupQuery = new GraphLookUpQuery(repository);
		try{
			graphLookupQuery.entity(null);
			fail("Should have thrown with null entities");
		}catch(Exception e){
			assertTrue(e instanceof IllegalArgumentException);
			assertEquals(e.getMessage(), "Parameter \"entityID\" cannot be null");
		}
	}
	
	@Test
	public void testEntityWithValidArgument(){
		Entity.ID newEntityID = Entity.ID.valueOf("urn:entity:" + TartanImplTestConstants.ATTR_PARENT_ID.getName()
		+ "." + TartanImplTestConstants.ATTR_PARENT_ID.getName() + "/" + UUID.randomUUID());
		GraphVisitor repository = new MockGraphVisitor();
		GraphLookUpQuery graphLookupQuery = new GraphLookUpQuery(repository);
		Projection entity = graphLookupQuery.entity(newEntityID);
		assertNotNull(entity);
	}
	
	@Test
	public void testFetchGraphValueChange(){
		GraphVisitor repository = new MockGraphVisitor();
		GraphLookUpQuery graphLookupQuery = new GraphLookUpQuery(repository);
		ValueSpecifier<Projection> projection = graphLookupQuery.entityBy(TartanImplTestConstants.ATTR_PARENT_ID);
		assertNotNull(projection);
	}
	
	@Test
	public void testGetProjectionWithDifferentDataTypeValue(){
		GraphVisitor repository = new MockGraphVisitor();
		GraphLookUpQuery graphLookupQuery = new GraphLookUpQuery(repository);
		GraphValueChange graphValueChange = graphLookupQuery.new GraphValueChange(TartanImplTestConstants.ATTR_PARENT_ID);
		//string value
		assertNotNull(graphValueChange.value("TestValue"));
		//double value
		Double doubleValue = 1.2;
		assertNotNull(graphValueChange.value(doubleValue));
		//instant value
		assertNotNull(graphValueChange.value(Instant.EPOCH));
		//integer value
		assertNotNull(graphValueChange.value(1));
		//boolean value
		assertNotNull(graphValueChange.value(true));
	}
	
	@Test
	public void testGraphValueChangeWithObjectValue(){
		GraphVisitor repository = new MockGraphVisitor();
		GraphLookUpQuery graphLookupQuery = new GraphLookUpQuery(repository);
		GraphValueChange graphValueChange = graphLookupQuery.new GraphValueChange(TartanImplTestConstants.ATTR_PARENT_ID);
		Object obj = "abcd";
		Conversion<String> converter = new StringTypeConversion();
		Projection value2 = graphValueChange.value(obj, converter);
		assertNotNull(value2);
	}
}
