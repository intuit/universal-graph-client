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

import java.util.Collections;
import java.util.List;

import com.intuit.ugc.api.BatchMutation;

/**
 * A representation of Graph persistence specific mutation result 
 *
 * @see com.intuit.ugc.api.BatchMutation.Result 
 * @see com.intuit.ugc.impl.core.VisitOperationResult
 * @author ajain17
 */
public class GraphMutationResult implements BatchMutation.Result, VisitOperationResult {

    private List<String> entityKeys = Collections.emptyList();

	public GraphMutationResult() {
        super();
	}

    public GraphMutationResult(List<String> entityKeys) {
        super();
        this.entityKeys = Collections.unmodifiableList(entityKeys);
	}

    public List<String> getEntityKeys() {
        return entityKeys;
    }
}
