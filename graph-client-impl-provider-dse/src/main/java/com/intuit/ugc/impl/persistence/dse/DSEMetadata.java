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

import com.intuit.ugc.api.Attribute.Metadata;

/**
 * Represents Metadata stored specific to data stored in DSE graph persistence store.
 * 
 * @author nverma1
 *
 * @param <T>
 */
public class DSEMetadata<T> implements Metadata {
    public DSEMetadata(Class<T> type) {
        super();
        this.type = type;
    }

    public Class<T> getType() {
        return this.type;
    }

    private final Class<T> type;
}
