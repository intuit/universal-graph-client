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

import org.testng.annotations.Test;

import com.intuit.ugc.api.BatchMutation;
import com.intuit.ugc.api.Entity;
import com.intuit.ugc.api.Attribute.Metadata;
import com.intuit.ugc.api.Attribute.Name;
import com.intuit.ugc.api.Relationship.Mutation;
import com.intuit.ugc.impl.core.GraphAttribute;
import com.intuit.ugc.impl.core.GraphRelationship;
import com.intuit.ugc.impl.core.GraphRelationshipMutation;
import com.intuit.ugc.impl.core.helper.MockBatchMutation;
import com.intuit.ugc.impl.core.helper.MockMetadata;
import com.intuit.ugc.impl.core.helper.TartanImplTestConstants;
import com.intuit.ugc.impl.core.helper.TartanImplTestUtil;
import com.intuit.ugc.impl.core.queryplan.OperationPipeline;

/**
 * 
 * @author nverma1
 *
 */
public class GraphRelationshipMutationTest {

	@Test
	public void testSetAttribute(){
		GraphRelationship graphRelationship = createGraphRelationship();
		OperationPipeline pipeline = new OperationPipeline();
		BatchMutation batchMutation = new MockBatchMutation();
		GraphRelationshipMutation graphRelationshipMutation = new GraphRelationshipMutation(graphRelationship, pipeline, batchMutation );
		Object value = "dummyValue";
		Mutation setAttribute = graphRelationshipMutation.setAttribute(TartanImplTestConstants.ATTR_DUMMY_ATTRIBUTE_NAME, value);
		assertNotNull(setAttribute);
	}
	
	@Test
	public void testDeleteAttribute(){
		GraphRelationship graphRelationship = createGraphRelationship();
		OperationPipeline pipeline = new OperationPipeline();
		BatchMutation batchMutation = new MockBatchMutation();
		GraphRelationshipMutation graphRelationshipMutation = new GraphRelationshipMutation(graphRelationship, pipeline, batchMutation );
		Mutation deleteAttribute = graphRelationshipMutation.deleteAttribute(TartanImplTestConstants.ATTR_DUMMY_ATTRIBUTE_NAME);
		assertNotNull(deleteAttribute);
	}
	
	@Test
	public void testReady(){
		GraphRelationship graphRelationship = createGraphRelationship();
		OperationPipeline pipeline = new OperationPipeline();
		BatchMutation batchMutation = new MockBatchMutation();
		GraphRelationshipMutation graphRelationshipMutation = new GraphRelationshipMutation(graphRelationship, pipeline, batchMutation );
		BatchMutation ready = graphRelationshipMutation.ready();
		assertNotNull(ready);
	}

	private GraphRelationship createGraphRelationship() {
		TartanImplTestUtil util = new TartanImplTestUtil();
		Entity.ID sourceEntityID = Entity.ID.valueOf("urn:entity:" + TartanImplTestConstants.ATTR_PARENT_ID.getName()
		+ "." + TartanImplTestConstants.ATTR_PARENT_ID.getName() + "/" + UUID.randomUUID());
		Entity.ID targetEntityID = Entity.ID.valueOf("urn:entity:" + TartanImplTestConstants.ATTR_CHILD_ID.getName()
		+ "." + TartanImplTestConstants.ATTR_PARENT_ID.getName() + "/" + UUID.randomUUID());
		Name attributeName = TartanImplTestConstants.ATTR_DUMMY_ATTRIBUTE_NAME;
		String attributeValue = "Dummy Attribute";
		Metadata metadata = new MockMetadata();
		GraphAttribute graphAttribute = new GraphAttribute(attributeName, attributeValue, metadata);
		GraphRelationship graphRelationship = util.buildGraphRelationship(
				TartanImplTestConstants.ATTR_DUMMY_RELATIONSHIP_NAME, sourceEntityID, targetEntityID, graphAttribute);
		return graphRelationship;
	}
	
	@Test
	public void testDeleteRelationship(){
		GraphRelationship graphRelationship = createGraphRelationship();
		OperationPipeline pipeline = new OperationPipeline();
		BatchMutation batchMutation = new MockBatchMutation();
		GraphRelationshipMutation graphRelationshipMutation = new GraphRelationshipMutation(graphRelationship, pipeline, batchMutation );
		graphRelationshipMutation.delete();
		assertNotNull(graphRelationshipMutation.ready());
	}
}
