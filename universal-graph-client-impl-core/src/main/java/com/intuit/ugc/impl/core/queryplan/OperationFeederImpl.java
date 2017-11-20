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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.intuit.ugc.impl.core.VisitOperationResult;
import com.intuit.ugc.impl.core.queryplan.operations.CreateEntity;
import com.intuit.ugc.impl.core.queryplan.operations.CreateRelationship;
import com.intuit.ugc.impl.core.queryplan.operations.DeleteEntity;
import com.intuit.ugc.impl.core.queryplan.operations.DeleteRelationship;
import com.intuit.ugc.impl.core.queryplan.operations.GetBatchEntityByID;
import com.intuit.ugc.impl.core.queryplan.operations.GetEntityByID;
import com.intuit.ugc.impl.core.queryplan.operations.GetEntityByProperty;
import com.intuit.ugc.impl.core.queryplan.operations.GraphTerminalOperation;
import com.intuit.ugc.impl.core.queryplan.operations.SelectEntities;
import com.intuit.ugc.impl.core.queryplan.operations.SelectRelationships;
import com.intuit.ugc.impl.core.queryplan.operations.UpdateEntity;
import com.intuit.ugc.impl.core.queryplan.operations.UpdateRelationship;
import com.intuit.ugc.impl.core.spi.GraphVisitor;
import com.intuit.ugc.impl.core.spi.RepositoryException;

/**
 * 
 * @author ajain17
 *
 */
public class OperationFeederImpl implements OperationFeeder {
    private static final Logger LOG = LoggerFactory
            .getLogger(OperationFeederImpl.class);

    private final OperationPipeline operationsPipeline;
    private final GraphVisitor repository;

    @Inject
    public OperationFeederImpl(GraphVisitor repository,
            @Assisted OperationPipeline operationsPipeline) {
        this.operationsPipeline = operationsPipeline;
        this.repository = repository;
    }

    public VisitOperationResult operationResult() {
        OperationIterator opIterator = operationsPipeline.iterator();
        while (opIterator.hasNext()) {
            Operation operation = opIterator.next();
            try {
                if (operation instanceof CreateEntity) {
                    repository.visit((CreateEntity) operation);
                } else if (operation instanceof CreateRelationship) {
                    repository.visit((CreateRelationship) operation);
                } else if (operation instanceof GetBatchEntityByID) {
                    repository.visit((GetBatchEntityByID) operation);
                } else if (operation instanceof GetEntityByID) {
                    repository.visit((GetEntityByID) operation);
                } else if (operation instanceof GetEntityByProperty) {
                    repository.visit((GetEntityByProperty) operation);
                } else if (operation instanceof UpdateEntity) {
                    repository.visit((UpdateEntity) operation);
                } else if (operation instanceof UpdateRelationship) {
                    repository.visit((UpdateRelationship) operation);
                } else if (operation instanceof GraphTerminalOperation) {
                    repository.visit((GraphTerminalOperation) operation);
                } else if (operation instanceof SelectEntities) {
                    repository.visit((SelectEntities) operation);
                } else if (operation instanceof SelectRelationships) {
                    repository.visit((SelectRelationships) operation);
                } else if (operation instanceof DeleteEntity) {
                	repository.visit((DeleteEntity)operation);
                } else if(operation instanceof DeleteRelationship) {
                	repository.visit((DeleteRelationship)operation);
                }
                LOG.info("SUCCESS " + operation.toString());
            } catch (RepositoryException e) {
                LOG.error("FAIL " + operation.toString() + " with error message \" "
                        + e.getMessage() + " \"");
                throw e;
            }
        }
        return repository.getResult();
    }

}
