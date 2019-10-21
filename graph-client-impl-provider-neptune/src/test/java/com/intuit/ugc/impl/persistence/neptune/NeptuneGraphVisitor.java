package com.intuit.ugc.impl.persistence.neptune;

import com.intuit.ugc.impl.core.VisitOperationResult;
import com.intuit.ugc.impl.core.queryplan.operations.*;
import com.intuit.ugc.impl.core.spi.GraphVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NeptuneGraphVisitor implements GraphVisitor {

    private static final Logger LOGGER = LoggerFactory.getLogger(NeptuneGraphVisitor.class);
    private NeptuneConnectionManager connectionManager;
    private VisitOperationResult qResult = null;
    private NeptuneGraphSession graphSession = null;

    public NeptuneGraphVisitor(NeptuneConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
        this.graphSession = connectionManager.getDSEGraphSession();
    }

    @Override
    public void visit(CreateEntity operation) {

    }

    @Override
    public void visit(CreateRelationship operation) {

    }

    @Override
    public void visit(GetBatchEntityByID operation) {

    }

    @Override
    public void visit(GetEntityByID operation) {

    }

    @Override
    public void visit(GetEntityByProperty operation) {

    }

    @Override
    public void visit(GraphTerminalOperation operation) {

    }

    @Override
    public void visit(SelectEntities operation) {

    }

    @Override
    public void visit(SelectRelationships operation) {

    }

    @Override
    public void visit(UpdateEntity operation) {

    }

    @Override
    public void visit(UpdateRelationship operation) {

    }

    @Override
    public VisitOperationResult getResult() {
        return null;
    }

    @Override
    public void visit(DeleteEntity operation) {

    }

    @Override
    public void visit(DeleteRelationship operation) {

    }
}
