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

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author ajain17
 *
 */
public class OperationPipeline implements OperationList {

    private final List<Operation> operations = new ArrayList<>();

    public OperationPipeline() {
    }

    @Override
    public Operation getTailOperation() {
        if (operations.size() > 0) {
            return operations.get(operations.size()-1);
        }
        else {
            return null;
        }
    }

    @Override
    public void add(Operation op) {
        operations.add(op);
    }

    @Override
    public OperationIterator iterator() {
        return new OperationIteratorImpl(this.operations);
    }

    /**
     * 
     * 
     *
     */
    private class OperationIteratorImpl implements OperationIterator {
        private int position = 0;
        private final List<Operation> operationsList;

        public OperationIteratorImpl(List<Operation> operationsList) {
            this.operationsList = operationsList;
        }

        @Override
        public boolean hasNext() {
            if (position < operationsList.size()) {
                return true;
            }
            return false;
        }

        @Override
        public Operation next() {
            Operation operation = operationsList.get(position);
            position++;
            return operation;
        }

    }
}
