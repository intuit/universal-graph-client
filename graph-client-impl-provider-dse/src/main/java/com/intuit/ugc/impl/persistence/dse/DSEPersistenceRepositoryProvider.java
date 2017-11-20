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

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Repository provider that is initialized with repository specific connection manager
 * 
 * @author nverma1
 *
 */
public class DSEPersistenceRepositoryProvider implements Provider<DSEGraphVisitor> {

    DSEConnectionManager manager;

    @Inject
    public DSEPersistenceRepositoryProvider(DSEConnectionManager manager) {
        this.manager = manager;
    }

    @Override
    public DSEGraphVisitor get() {
        return new DSEGraphVisitor(this.manager);
    }
}
