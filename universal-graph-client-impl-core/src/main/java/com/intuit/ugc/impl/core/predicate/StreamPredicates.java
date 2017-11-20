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

/**
 * 
 * @author ajain17
 *
 */
public final class StreamPredicates {
    private StreamPredicates() {
    }

    public static StreamPredicate and(StreamPredicate... predicates){
        return new AndPredicate(predicates);
    }

    public static StreamPredicate or(StreamPredicate... predicates) {
        return new OrPredicate(predicates);
    }

    public static StreamPredicate equalTo(Attribute.Name attributeName, Object value) {
        return new EqualToPredicate(attributeName, value);
    }

    public static StreamPredicate notEqualTo(Object value) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    public static StreamPredicate greaterThan(Object value) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    public static StreamPredicate lessThan(Object value) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
