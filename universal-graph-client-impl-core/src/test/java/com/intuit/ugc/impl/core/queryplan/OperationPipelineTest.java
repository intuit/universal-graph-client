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

package com.intuit.ugc.impl.core.queryplan;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.intuit.ugc.api.Attribute;
import com.intuit.ugc.api.Entity;
import com.intuit.ugc.api.NewEntity;
import com.intuit.ugc.api.Queries;
import com.intuit.ugc.api.Relationship;
import com.intuit.ugc.impl.core.GraphPredicate;
import com.intuit.ugc.impl.core.queryplan.ContextOperation;
import com.intuit.ugc.impl.core.queryplan.Operation;
import com.intuit.ugc.impl.core.queryplan.OperationIterator;
import com.intuit.ugc.impl.core.queryplan.OperationPipeline;
import com.intuit.ugc.impl.core.queryplan.TerminalOperation;
import com.intuit.ugc.impl.core.queryplan.operations.CreateEntity;
import com.intuit.ugc.impl.core.queryplan.operations.GetEntityByProperty;
import com.intuit.ugc.impl.core.queryplan.operations.GraphTerminalOperation;
import com.intuit.ugc.impl.core.queryplan.operations.SelectEntities;
import com.intuit.ugc.impl.core.queryplan.operations.SelectRelationships;

/**
 * 
 * @author nverma1
 *
 */
public class OperationPipelineTest {
    private static final Attribute.Name ATTR_PERSON_DISPLAY_NAME = Attribute.Name
            .valueOf("person.DisplayName");
    private static final Attribute.Name ATTR_PERSON_EMAIL = Attribute.Name
            .valueOf("person.Email");
    private static final Relationship.Name REL_CITY = Relationship.Name
            .valueOf("company.City");
    private static final Relationship.Name REL_CUSTOMER = Relationship.Name
            .valueOf("company.Customer");
    private static final Attribute.Name ATTR_EMAIL_ADDRESS = Attribute.Name
            .valueOf(
                    "oii.identity-core.contact-method.emailaddress.EmailAddress");

    @Test
    public void testOperationPipeline() {
        OperationPipeline operationPipeline = new OperationPipeline();
        operationPipeline
                .add(new GetEntityByProperty(ATTR_PERSON_DISPLAY_NAME, "John"));
        operationPipeline.add(new SelectEntities(REL_CUSTOMER,
                Queries.GraphTraversal.Direction.OUT));
        operationPipeline.add(new SelectRelationships(REL_CITY,
                Queries.GraphTraversal.Direction.IN));
        operationPipeline.add(new GraphTerminalOperation());

        OperationIterator vIterator = operationPipeline.iterator();
        assertTrue(vIterator.hasNext());
        assertTrue(vIterator.next() instanceof GetEntityByProperty);
        assertTrue(vIterator.next() instanceof SelectEntities);
        assertTrue(vIterator.next() instanceof SelectRelationships);
        assertTrue(vIterator.next() instanceof GraphTerminalOperation);
    }

    @Test
    public void testOperationPipelinePredicate() {
        OperationPipeline operationPipeline = new OperationPipeline();
        operationPipeline
                .add(new GetEntityByProperty(ATTR_PERSON_DISPLAY_NAME, "John"));
        operationPipeline.add(new SelectEntities(REL_CUSTOMER,
                Queries.GraphTraversal.Direction.OUT));
        operationPipeline.add(new SelectRelationships(REL_CITY,
                Queries.GraphTraversal.Direction.IN));
        Operation currentVisitor = operationPipeline.getTailOperation();
        assertTrue(currentVisitor instanceof ContextOperation);
        ContextOperation contextVisitor = (ContextOperation) currentVisitor;
        GraphPredicate predicate = new GraphPredicate();
        predicate.attribute(ATTR_EMAIL_ADDRESS).equalTo("abc1@intuit.com");
        contextVisitor.condition(predicate);
        operationPipeline.add(new GraphTerminalOperation());
        OperationIterator vIterator = operationPipeline.iterator();
        assertTrue(vIterator.hasNext());
    }

    @Test
    public void testOperationPipelineInclude() {
        OperationPipeline operationPipeline = new OperationPipeline();
        operationPipeline
                .add(new GetEntityByProperty(ATTR_PERSON_DISPLAY_NAME, "John"));
        operationPipeline.add(new SelectEntities(REL_CUSTOMER,
                Queries.GraphTraversal.Direction.OUT));
        operationPipeline.add(new SelectRelationships(REL_CITY,
                Queries.GraphTraversal.Direction.IN));
        operationPipeline.add(new GraphTerminalOperation());
        Operation currentVisitor = operationPipeline.getTailOperation();
        assertTrue(currentVisitor instanceof TerminalOperation);
        TerminalOperation terminalVisitor = (TerminalOperation) currentVisitor;
        terminalVisitor.include(ATTR_PERSON_EMAIL);
        OperationIterator vIterator = operationPipeline.iterator();
        assertTrue(vIterator.hasNext());
    }
    
    @Test
    public void testOperationPipelineWithNoOperations(){
    	OperationPipeline operationPipeline = new OperationPipeline();
    	assertNull(operationPipeline.getTailOperation());
    }
    
    @Test
    public void testOperationPipelineWithOperations(){
    	OperationPipeline operationPipeline = new OperationPipeline();
    	NewEntity newEntity = NewEntity.newInstance(Entity.ID.valueOf("test-entity")).build();
		Operation op = new CreateEntity(newEntity );
		operationPipeline.add(op);
		assertEquals(operationPipeline.getTailOperation(), op);
    }
}
