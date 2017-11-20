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

import static org.testng.Assert.assertNotNull;

import java.util.UUID;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.intuit.ugc.api.Entity;
import com.intuit.ugc.api.Queries.GraphTraversal;
import com.intuit.ugc.api.Queries.GraphTraversal.Direction;
import com.intuit.ugc.api.Queries.Projection;
import com.intuit.ugc.impl.core.helper.MockGraphVisitor;
import com.intuit.ugc.impl.core.helper.TartanImplTestConstants;
import com.intuit.ugc.impl.core.spi.GraphVisitor;

/**
 * 
 * @author nverma1
 *
 */
public class GraphQueryImplTest {
	
	@Test
	public void testSelectAdjacentEntitiesWithNullName() {
		GraphVisitor repository = new MockGraphVisitor();
		GraphQueryImpl graphQueryImpl = new GraphQueryImpl(repository);
		try {
			graphQueryImpl.selectAdjacentEntitiesVia(null);
			Assert.fail("Should have thrown an exception for null name");
		} catch (Exception e) {
			AssertJUnit.assertTrue(e instanceof IllegalArgumentException);
			AssertJUnit.assertEquals(e.getMessage(), "Parameter \"name::com.intuit.ugc.api.Relationship$Name\" cannot be null");
		}
	}

	@Test
	public void testSelectAdjacentEntitiesWithValidName() {
		GraphVisitor repository = new MockGraphVisitor();
		GraphQueryImpl graphQueryImpl = new GraphQueryImpl(repository);
		GraphTraversal adjacentEntitiesTraversal = graphQueryImpl
				.selectAdjacentEntitiesVia(TartanImplTestConstants.ATTR_DUMMY_RELATIONSHIP_NAME);
		AssertJUnit.assertNotNull(adjacentEntitiesTraversal);
		Projection selectProjection = adjacentEntitiesTraversal.select();
		AssertJUnit.assertNotNull(selectProjection);
	}

	@Test
	public void testSelectAdjacentEntitiesWithNullRelationshipNameAndValidDirection() {
		GraphVisitor repository = new MockGraphVisitor();
		GraphQueryImpl graphQueryImpl = new GraphQueryImpl(repository);
		Direction direction = Direction.IN_OUT;
		try {
			graphQueryImpl.selectAdjacentEntitiesVia(null, direction);
			Assert.fail("Should have thrown an exception for null name");
		} catch (Exception e) {
			AssertJUnit.assertTrue(e instanceof IllegalArgumentException);
			AssertJUnit.assertEquals(e.getMessage(), "Parameter \"name::com.intuit.ugc.api.Relationship$Name\" cannot be null");
		}
	}

	@Test
	public void testSelectAdjacentEntitiesWithRelationshipNameButNullDirection() {
		GraphVisitor repository = new MockGraphVisitor();
		GraphQueryImpl graphQueryImpl = new GraphQueryImpl(repository);
		try {
			graphQueryImpl.selectAdjacentEntitiesVia(TartanImplTestConstants.ATTR_DUMMY_RELATIONSHIP_NAME, null);
			Assert.fail("Should have thrown an exception for null direction");
		} catch (Exception e) {
			AssertJUnit.assertTrue(e instanceof IllegalArgumentException);
			AssertJUnit.assertEquals(e.getMessage(),
					"Parameter \"direction::com.intuit.ugc.api.Queries$GraphTraversal$Direction\" cannot be null");
		}
	}

	@Test
	public void testSelectAdjacentEntitiesWithValidRelationshipNameAndDirection() {
		GraphVisitor repository = new MockGraphVisitor();
		GraphQueryImpl graphQueryImpl = new GraphQueryImpl(repository);
		Direction direction = Direction.IN_OUT;
		GraphTraversal adjacentEntitiesTraversal = graphQueryImpl
				.selectAdjacentEntitiesVia(TartanImplTestConstants.ATTR_DUMMY_RELATIONSHIP_NAME, direction);
		AssertJUnit.assertNotNull(adjacentEntitiesTraversal);
	}
	
	@Test
	public void testSelectAdjacentRelationshipWithNullRelationshipName(){
		GraphVisitor repository = new MockGraphVisitor();
		GraphQueryImpl graphQueryImpl = new GraphQueryImpl(repository);
		try {
			graphQueryImpl.selectAdjacentRelationships(null);
			Assert.fail("Should have thrown an exception for null name");
		} catch (Exception e) {
			AssertJUnit.assertTrue(e instanceof IllegalArgumentException);
			AssertJUnit.assertEquals(e.getMessage(), "Parameter \"name::com.intuit.ugc.api.Relationship$Name\" cannot be null");
		}
	}
	
	@Test
	public void testSelectAdjacentRelationshipWithValidRelationshipName() {
		GraphVisitor repository = new MockGraphVisitor();
		GraphQueryImpl graphQueryImpl = new GraphQueryImpl(repository);
		GraphTraversal adjacentEntitiesTraversal = graphQueryImpl
				.selectAdjacentRelationships(TartanImplTestConstants.ATTR_DUMMY_RELATIONSHIP_NAME);
		AssertJUnit.assertNotNull(adjacentEntitiesTraversal);
		Projection selectProjection = adjacentEntitiesTraversal.select();
		AssertJUnit.assertNotNull(selectProjection);
	}
	
	@Test
	public void testSelectAdjacentRelationshipWithNullNameValidDirection() {
		GraphVisitor repository = new MockGraphVisitor();
		GraphQueryImpl graphQueryImpl = new GraphQueryImpl(repository);
		Direction direction = Direction.IN_OUT;
		try {
			graphQueryImpl.selectAdjacentRelationships(null, direction);
			Assert.fail("Should have thrown an exception for null name");
		} catch (Exception e) {
			AssertJUnit.assertTrue(e instanceof IllegalArgumentException);
			AssertJUnit.assertEquals(e.getMessage(), "Parameter \"name::com.intuit.ugc.api.Relationship$Name\" cannot be null");
		}
	}
	
	@Test
	public void testSelectAdjacentRelationshipWithNameButNullDirection() {
		GraphVisitor repository = new MockGraphVisitor();
		GraphQueryImpl graphQueryImpl = new GraphQueryImpl(repository);
		try {
			graphQueryImpl.selectAdjacentRelationships(TartanImplTestConstants.ATTR_DUMMY_RELATIONSHIP_NAME, null);
			Assert.fail("Should have thrown an exception for null direction");
		} catch (Exception e) {
			AssertJUnit.assertTrue(e instanceof IllegalArgumentException);
			AssertJUnit.assertEquals(e.getMessage(),
					"Parameter \"direction::com.intuit.ugc.api.Queries$GraphTraversal$Direction\" cannot be null");
		}
	}
	
	@Test
	public void testSelectAdjacentRelationshipWithValidRelationshipNameAndDirection() {
		GraphVisitor repository = new MockGraphVisitor();
		GraphQueryImpl graphQueryImpl = new GraphQueryImpl(repository);
		Direction direction = Direction.IN_OUT;
		GraphTraversal adjacentEntitiesTraversal = graphQueryImpl
				.selectAdjacentRelationships(TartanImplTestConstants.ATTR_DUMMY_RELATIONSHIP_NAME, direction);
		AssertJUnit.assertNotNull(adjacentEntitiesTraversal);
	}
	
	@Test
	public void testFromRootEntityWithNullId(){
		GraphVisitor repository = new MockGraphVisitor();
		GraphQueryImpl graphQueryImpl = new GraphQueryImpl(repository);
		try{
			graphQueryImpl.fromRootEntity(null);
			Assert.fail("Should have thrown exception");
		}catch(Exception e){
			AssertJUnit.assertTrue(e instanceof IllegalArgumentException);
			AssertJUnit.assertEquals(e.getMessage(), "Parameter \"id\" cannot be null");
		}
	}
	
	@Test
	public void testFromRootEntityWithValidId(){
		GraphVisitor repository = new MockGraphVisitor();
		GraphQueryImpl graphQueryImpl = new GraphQueryImpl(repository);
		Entity.ID sourceEntityID = Entity.ID.valueOf("urn:entity:" + TartanImplTestConstants.ATTR_PARENT_ID.getName()
		+ "." + TartanImplTestConstants.ATTR_PARENT_ID.getName() + "/" + UUID.randomUUID());
		GraphTraversal fromRootEntity = graphQueryImpl.fromRootEntity(sourceEntityID);
		AssertJUnit.assertNotNull(fromRootEntity);
	}
	
	@Test
	public void testWhereWithContextOperation(){
		GraphVisitor repository = new MockGraphVisitor();
		GraphQueryImpl graphQueryImpl = new GraphQueryImpl(repository);
		GraphTraversal adjacentEntitiesTraversal = graphQueryImpl
				.selectAdjacentEntitiesVia(TartanImplTestConstants.ATTR_DUMMY_RELATIONSHIP_NAME);
		GraphPredicate graphPredicate = new GraphPredicate();
		GraphTraversal traversal = graphQueryImpl.where(graphPredicate);
		assertNotNull(traversal);
	}
}
