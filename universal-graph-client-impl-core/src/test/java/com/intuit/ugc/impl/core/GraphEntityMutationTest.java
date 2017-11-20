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
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.time.Instant;
import java.util.UUID;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.intuit.ugc.api.BatchMutation;
import com.intuit.ugc.api.Entity;
import com.intuit.ugc.api.NewEntity;
import com.intuit.ugc.api.ValueSpecifier;
import com.intuit.ugc.api.Attribute.Metadata;
import com.intuit.ugc.api.Attribute.Name;
import com.intuit.ugc.api.Entity.Mutation;
import com.intuit.ugc.impl.core.GraphAttribute;
import com.intuit.ugc.impl.core.GraphEntity;
import com.intuit.ugc.impl.core.GraphEntityMutation;
import com.intuit.ugc.impl.core.GraphMutation;
import com.intuit.ugc.impl.core.GraphEntityMutation.GraphValueChange;
import com.intuit.ugc.impl.core.helper.MockMetadata;
import com.intuit.ugc.impl.core.helper.MockMutationExecuterFactory;
import com.intuit.ugc.impl.core.helper.TartanImplTestConstants;
import com.intuit.ugc.impl.core.helper.TartanImplTestUtil;
import com.intuit.ugc.impl.core.queryplan.MutationExecutorFactory;
import com.intuit.ugc.impl.core.queryplan.OperationPipeline;
import com.intuit.ugc.impl.core.queryplan.operations.CreateEntity;
import com.toddfast.util.convert.TypeConverter.Conversion;
import com.toddfast.util.convert.conversion.StringTypeConversion;

/**
 * 
 * @author nverma1
 *
 */
public class GraphEntityMutationTest {
	private TartanImplTestUtil util;
	
	@BeforeClass
	public void setup(){
		util = new TartanImplTestUtil();
	}
	
	@Test
	public void testGraphEntityMutationCreation() {
		GraphEntityMutation graphEntityMutation = createGraphEntityMutation(null);
		assertNotNull(graphEntityMutation);
	}
	
	@Test
	public void testGraphEntityMutationWithNullAttribute() {
		GraphEntityMutation graphEntityMutation = createGraphEntityMutation(null);
		try{
			graphEntityMutation.withAttribute(null);
			fail("Should throw exception if called with null attribute");
		}catch(Exception e){
			assertTrue(e instanceof IllegalArgumentException);
		}
	}
	
	@Test
	public void testWithAttributeWhenProperAttributeIsPassed() {
		GraphEntityMutation graphEntityMutation = createGraphEntityMutation(null);
		ValueSpecifier<Mutation> graphValueChange = graphEntityMutation
				.withAttribute(TartanImplTestConstants.ATTR_DUMMY_ATTRIBUTE_NAME);
		assertNotNull(graphValueChange);
	}
	
	@Test
	public void testDeleteAttributeWithNullAttribute() {
		GraphEntityMutation graphEntityMutation = createGraphEntityMutation(null);
		try{
			graphEntityMutation.deleteAttribute(null);
			fail("Should throw exception if called with null attribute");
		}catch(Exception e){
			assertTrue(e instanceof IllegalArgumentException);
		}
	}
	
	@Test
	public void testDeleteAttributeWithValidAttributeName(){
		GraphEntityMutation graphEntityMutation = createGraphEntityMutation(null);
		Mutation deleteAttribute = graphEntityMutation
				.deleteAttribute(TartanImplTestConstants.ATTR_DUMMY_ATTRIBUTE_NAME);
		assertNotNull(deleteAttribute);
	}
	
	@Test
	public void testDeleteAttributes(){
		GraphEntityMutation graphEntityMutation = createGraphEntityMutation(null);
		Mutation deleteAttribute = graphEntityMutation
				.deleteAttributes(TartanImplTestConstants.ATTR_DUMMY_FAMILY);
		assertNotNull(deleteAttribute);
	}
	
	@Test
	public void testReady(){
		MutationExecutorFactory factory = new MockMutationExecuterFactory();
		BatchMutation graphBatchMutation = new GraphMutation(factory );
		GraphEntityMutation graphEntityMutation = createGraphEntityMutation(graphBatchMutation);
		BatchMutation graphBatchMutation2 = graphEntityMutation.ready();
		assertNotNull(graphBatchMutation);
		assertEquals(graphBatchMutation2, graphBatchMutation);
	}
	
	private GraphEntityMutation createGraphEntityMutation(BatchMutation batchMutation) {
		GraphEntity.Builder builder = new GraphEntity.Builder();
		Name name = TartanImplTestConstants.ATTR_DUMMY_ATTRIBUTE_NAME;
		String attributeValue = "Dummy Attribute";
		Metadata metadata = new MockMetadata();
		GraphAttribute graphAttribute = new GraphAttribute(name, attributeValue, metadata);
		builder.addAttribute(graphAttribute);
		Entity.ID newEntityID = Entity.ID.valueOf("urn:entity:" + TartanImplTestConstants.ATTR_PARENT_ID.getName()
				+ "." + TartanImplTestConstants.ATTR_PARENT_ID.getName() + "/" + UUID.randomUUID());
		builder.setID(newEntityID);
		GraphEntity entity = builder.build();

		OperationPipeline assemblyLine = new OperationPipeline();
		NewEntity newEntity = util.createNewEntity(TartanImplTestConstants.ATTR_PARENT_ID);
		CreateEntity createEntity = new CreateEntity(newEntity);
		assemblyLine.add(createEntity);
		
		if(null == batchMutation){
			MutationExecutorFactory factory = new MockMutationExecuterFactory();
			batchMutation = new GraphMutation(factory );
		}
		
		GraphEntityMutation graphEntityMutation = new GraphEntityMutation(entity, assemblyLine, batchMutation);
		return graphEntityMutation;
	}
	
	@Test
	public void testGraphValueChangeWithIntegerValue(){
		BatchMutation batchMutation = null;
		GraphEntityMutation graphEntityMutation = createGraphEntityMutation(batchMutation);
		GraphValueChange graphValueChange = graphEntityMutation.new GraphValueChange(
				TartanImplTestConstants.ATTR_DUMMY_ATTRIBUTE_NAME);
		int value = 1;
		Mutation value2 = graphValueChange.value(value);
		assertNotNull(value2);
	}
	
	@Test
	public void testGraphValueChangeWithDoubleValue(){
		GraphEntityMutation graphEntityMutation = createGraphEntityMutation(null);
		GraphValueChange graphValueChange = graphEntityMutation.new GraphValueChange(
				TartanImplTestConstants.ATTR_DUMMY_ATTRIBUTE_NAME);
		double value = 1.1;
		Mutation value2 = graphValueChange.value(value);
		assertNotNull(value2);
	}
	
	@Test
	public void testGraphValueChangeWithBooleanValue(){
		GraphEntityMutation graphEntityMutation = createGraphEntityMutation(null);
		GraphValueChange graphValueChange = graphEntityMutation.new GraphValueChange(
				TartanImplTestConstants.ATTR_DUMMY_ATTRIBUTE_NAME);
		boolean value = true;
		Mutation value2 = graphValueChange.value(value);
		assertNotNull(value2);
	}
	
	@Test
	public void testGraphValueChangeWithInstantValue(){
		GraphEntityMutation graphEntityMutation = createGraphEntityMutation(null);
		GraphValueChange graphValueChange = graphEntityMutation.new GraphValueChange(
				TartanImplTestConstants.ATTR_DUMMY_ATTRIBUTE_NAME);
		Instant value = Instant.MIN;
		Mutation value2 = graphValueChange.value(value);
		assertNull(value2);
	}
	
	@Test
	public void testGraphValueChangeWithObjectValue(){
		GraphEntityMutation graphEntityMutation = createGraphEntityMutation(null);
		GraphValueChange graphValueChange = graphEntityMutation.new GraphValueChange(
				TartanImplTestConstants.ATTR_DUMMY_ATTRIBUTE_NAME);
		Object obj = "abcd";
		Conversion<String> converter = new StringTypeConversion();
		Mutation value2 = graphValueChange.value(obj, converter);
		assertNotNull(value2);
	}
	
	@Test
	public void testMutationWithDeleteEnabled(){
		GraphEntityMutation graphEntityMutation = createGraphEntityMutation(null);
		graphEntityMutation.delete();
		BatchMutation mutation = graphEntityMutation.ready();
		assertNotNull(mutation);
	}
	
	@Test
	public void testGraphValueChangeWithStringValue(){
		GraphEntityMutation graphEntityMutation = createGraphEntityMutation(null);
		GraphValueChange graphValueChange = graphEntityMutation.new GraphValueChange(
				TartanImplTestConstants.ATTR_DUMMY_ATTRIBUTE_NAME);
		String val = "abcd";
		Mutation value2 = graphValueChange.value(val);
		assertNotNull(value2);
	}
}
