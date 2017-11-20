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

package com.intuit.ugc.impl.core.predicate;

import com.intuit.ugc.api.Attribute;
import com.intuit.ugc.api.Predicate.Comparison;

/**
 * 
 * @author ajain17
 *
 */
public class StreamComparison implements Comparison {
    StreamPredicateBuilder pb;

    StreamComparison(StreamPredicateBuilder pb) {
        this.pb = pb;
    }

    public StreamComparison get(Attribute.Name name) {
        pb.setAttribute(name);
        return this;
    }

    private StreamPredicateBuilder addPredicate(StreamPredicate predicate) {
        pb.addPredicate(predicate);
        return pb;
    }

    @Override
    public StreamPredicateBuilder equalTo(Object value) {
        return addPredicate(StreamPredicates.equalTo(pb.getAttribute(), value));
    }

    @Override
    public StreamPredicateBuilder notEqualTo(Object o) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public StreamPredicateBuilder greaterThan(Object o) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public StreamPredicateBuilder lessThan(Object o) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}