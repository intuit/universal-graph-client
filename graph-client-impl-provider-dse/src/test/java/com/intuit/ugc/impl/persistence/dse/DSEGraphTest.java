/*
 * Copyright (c) 2017.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    ajain17 & nverma1 - API , implementation and initial documentation
 */

package com.intuit.ugc.impl.persistence.dse;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.intuit.ugc.api.Attribute;
import com.intuit.ugc.api.BatchMutation;
import com.intuit.ugc.api.Entity;
import com.intuit.ugc.api.NewEntity;
import com.intuit.ugc.api.NewRelationship;
import com.intuit.ugc.api.OperationResult;
import com.intuit.ugc.api.Query.Result;
import com.intuit.ugc.api.Relationship;
import com.intuit.ugc.impl.core.GraphMutationResult;
import com.intuit.ugc.impl.core.GraphQueryResult;
import com.intuit.ugc.impl.core.GraphRelationship;
import com.intuit.ugc.impl.persistence.DSETestQueries;
import com.intuit.ugc.impl.persistence.UGCDSETestBase;

/**
 * 
 * @author nverma1
 *
 */
public class DSEGraphTest extends UGCDSETestBase{
	private static final String DSE_VERTEX_NAME_KEY = "dse.vertex.Name";
	private static final String DSE_VERTEX_VERTEX2 = "dse.vertex.Vertex2";
	private static final String DSE_VERTEX_VERTEX1 = "dse.vertex.Vertex1";
	private static final String DSE_EDGE_EDGE1 = "dse.vertex.Edge1";
	private final Logger LOG = LoggerFactory.getLogger(DSEGraphTest.class);
	private Entity.ID entityId1; 
	private Entity.ID entityId2; 
	private GraphRelationship relationship;

    @BeforeClass
    public static void setup(){
    }
    
    @SuppressWarnings("serial")
	@Test(groups = {"createEntity"})
    public void testCreateASimpleVertexAndPersist(){
    	String entityIdStr1 = DSE_VERTEX_VERTEX1;
    	Map<String, String>attributesMap = new HashMap<String, String>(){{put("dse.vertex.Label","Author"); put(DSE_VERTEX_NAME_KEY,"Julia Child");put("dse.vertex.Gender", "F");}};
		NewEntity newEntity1 = DSETestQueries.createNewEntity(entityIdStr1, attributesMap);
		String entityIdReturned = createEntity(newEntity1);
		entityId1 = Entity.ID.valueOf(entityIdReturned);
		LOG.info("created: " + entityId1.toString());
		
    	String entityIdStr2 = DSE_VERTEX_VERTEX2;
    	Map<String, String>vertexAttributesMap = new HashMap<String, String>(){{put("dse.vertex.Label","Book"); put(DSE_VERTEX_NAME_KEY,"The Art of French Cooking, Vol. 1");put("dse.vertex.Year", "1961");}};
		NewEntity newEntity2 = DSETestQueries.createNewEntity(entityIdStr2, vertexAttributesMap);
		String entityIdReturned2 = createEntity(newEntity2);
		entityId2 = Entity.ID.valueOf(entityIdReturned2);
		LOG.info("created: " + entityId2.toString());

    }
    
    @SuppressWarnings("unchecked")
	@Test(groups = {"getEntity"}, dependsOnGroups = {"createEntity"})
    public void testFetchOneEntityById(){
		OperationResult<Result> result = persistence.lookup().entity(entityId1).ready().execute();
		assertThat(result, is(notNullValue()));
		assertThat(result.getResult(), is(allOf(notNullValue(), instanceOf(GraphQueryResult.class))));
		Result result2 = result.getResult();
		assertThat(result2.getEntities(), is(allOf(notNullValue(), instanceOf(List.class), hasSize(1))));
		Entity entity = result2.getEntities().get(0);
		assertEquals(entity.getID(), entityId1);
    }
    
    @SuppressWarnings("unchecked")
	@Test(groups = {"getEntity"}, dependsOnGroups = {"createEntity"})
    public void testFetchEntityListById(){
		OperationResult<Result> result = persistence.lookup().entities(entityId1, entityId2).ready().execute();
		assertThat(result, is(notNullValue()));
		assertThat(result.getResult(), is(allOf(notNullValue(), instanceOf(GraphQueryResult.class))));
		Result result2 = result.getResult();
		assertThat(result2.getEntities(), is(allOf(notNullValue(), instanceOf(List.class), hasSize(2))));
		assertEquals(result2.getEntities().get(0).getID(), entityId1);
		assertEquals(result2.getEntities().get(1).getID(), entityId2);
    }
    
    @SuppressWarnings("unchecked")
	@Test(groups = {"getEntity"}, dependsOnGroups = {"createEntity"})
    public void testFetchEntityByProperty(){
    	Attribute.Name propertyName = Attribute.Name.valueOf(DSE_VERTEX_NAME_KEY);
		OperationResult<Result> result = persistence.lookup().entityBy(propertyName).value("Julia Child").ready().execute();
		assertThat(result, is(notNullValue()));
		Result result2 = result.getResult();
		assertNotNull(result2);
		assertTrue(result2 instanceof GraphQueryResult);
		List<Entity> entities = result2.getEntities();
		assertNotNull(entities);
		assertTrue(entities instanceof List);
		assertEquals(1, entities.size());
		Entity entity = result2.getEntities().get(0);
		assertEquals(entity.getID(), entityId1);
    }
    
    @SuppressWarnings("unchecked")
	@Test(groups = {"updateEntity"}, dependsOnGroups = {"createEntity"})
    public void testUpdateEntityAddAndRemoveProperty(){
		OperationResult<Result> result = persistence.lookup().entity(entityId1).ready().execute();
		Result queryResult = result.getResult();
		Entity entity = queryResult.getEntities().get(0);
        //update the entity
        OperationResult<BatchMutation.Result> graphResult = persistence.prepareMutation(entity)
                .withAttribute(Attribute.Name.valueOf("dse.vertex.Category")).value("recipe")
                .withAttribute(Attribute.Name.valueOf("dse.vertex.Populatiry")).value("popular")
                .deleteAttribute(Attribute.Name.valueOf("dse.vertex.Gender"))
                .ready()
                .execute();
        assertThat(result, is(notNullValue()));
        GraphMutationResult mutationResult = (GraphMutationResult)graphResult.getResult();
        assertThat(mutationResult, is(allOf(notNullValue(), instanceOf(GraphMutationResult.class))));
        List<String> entityKeys = mutationResult.getEntityKeys();
        assertEquals(entityKeys.size(), 0);
    }
    
    @SuppressWarnings("serial")
	@Test(groups = {"createRelationship"}, dependsOnGroups = {"createEntity"})
    public void testCreateASimpleEdgeAndPersist(){
		//create relationship
    	Map<String, String>edgeAttributesMap = new HashMap<String, String>(){{put("dse.vertex.Label","Authored");put("dse.vertex.Startyear","1982");}};
    	NewRelationship newRelationship = DSETestQueries.createNewRelationship(DSE_EDGE_EDGE1, edgeAttributesMap, entityId1, entityId2);
    	createRelationship(newRelationship);
    	LOG.info("created: " + DSE_EDGE_EDGE1 + "with dse.vertex.Label " + "Authored");
    }
    
    @SuppressWarnings("unchecked")
	@Test(groups = {"updateRelationship"}, dependsOnGroups = {"createEntity","createRelationship"})
    public void testUpdateRelationshipAddAndRemoveProperty(){
    	GraphRelationship.Builder builder = new GraphRelationship.Builder();
    	builder.setName(Relationship.Name.valueOf(DSE_EDGE_EDGE1));
    	builder.setSourceID(entityId1);
    	builder.setTargetID(entityId2);
    	relationship = builder.build();
        //update the entity
        OperationResult<BatchMutation.Result> graphResult = persistence.prepareMutation(relationship)
                .setAttribute(Attribute.Name.valueOf("dse.edge.Weight"),"0.8")
                .deleteAttribute(Attribute.Name.valueOf("dse.vertex.Label"))
                .ready()
                .execute();
        GraphMutationResult mutationResult = (GraphMutationResult)graphResult.getResult();
        assertThat(mutationResult, is(allOf(notNullValue(), instanceOf(GraphMutationResult.class))));
        List<String> entityKeys = mutationResult.getEntityKeys();
        assertEquals(entityKeys.size(), 0);
    }
    
    //@Test(groups = {"deleteRelationship"}, dependsOnGroups = {"createEntity","createRelationship", "updateRelationship"})
    public void testDeleteRelationship(){
    	OperationResult<BatchMutation.Result> graphResult = persistence.prepareMutation(relationship).delete().ready().execute();
		assertNotNull(graphResult);
		GraphMutationResult mutationResult = (GraphMutationResult)graphResult.getResult();
		assertEquals(0, mutationResult.getEntityKeys().size());
    }
    
    @SuppressWarnings("unchecked")
	//@Test(groups = {"deleteEntity"}, dependsOnGroups = {"createEntity","createRelationship", "deleteRelationship"})
    public void testDeleteEntity(){
		OperationResult<Result> result = persistence.lookup().entity(entityId1).ready().execute();
		Result queryResult = result.getResult();
		Entity entity = queryResult.getEntities().get(0);
		OperationResult<BatchMutation.Result> graphResult = persistence.prepareMutation(entity).delete().ready().execute();
		assertNotNull(graphResult);
		GraphMutationResult mutationResult = (GraphMutationResult)graphResult.getResult();
		assertEquals(0, mutationResult.getEntityKeys().size());
    }

	@SuppressWarnings("unchecked")
	private void createRelationship(NewRelationship newRelationship) {
        OperationResult<BatchMutation.Result> opResult = persistence
                .prepareBatchMutation().createRelationship(newRelationship).execute();
        GraphMutationResult result = (GraphMutationResult) opResult.getResult();
        assertThat(result, is(notNullValue()));
        assertThat(result.getEntityKeys(), is(allOf(notNullValue(), instanceOf(List.class), hasSize(0))));
	}

	@SuppressWarnings("unchecked")
	private String createEntity(NewEntity newEntity) {
        OperationResult<BatchMutation.Result> opResult = persistence
                .prepareBatchMutation().createEntity(newEntity).execute();
        GraphMutationResult result = (GraphMutationResult) opResult.getResult();
        assertThat(result, is(notNullValue()));
        assertThat(result.getEntityKeys(), is(allOf(notNullValue(), instanceOf(List.class), hasSize(1))));
        LOG.debug("Query execution result: "+ result.getEntityKeys());
        return result.getEntityKeys().get(0);
	}
}
