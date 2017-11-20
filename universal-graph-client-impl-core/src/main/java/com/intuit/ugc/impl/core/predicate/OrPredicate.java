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

import java.util.Collection;
import java.util.Objects;

import com.intuit.ugc.api.Attribute;

/**
 * 
 * @author ajain17
 *
 */
public final class OrPredicate implements StreamPredicate, CompoundPredicate {

    protected StreamPredicate[] predicates;

    public OrPredicate() {
    }

    public OrPredicate(StreamPredicate... predicates) {
        this.predicates = predicates;
    }


    @Override
    public StreamPredicate[] getPredicates() {
        return predicates;
    }

    @Override
    public void setPredicates(StreamPredicate[] predicates) {
        //immutable
        if (Objects.isNull(this.predicates)) {
            this.predicates = predicates;
        } else {
            throw new IllegalStateException(
                "Cannot reset predicates in an OrPredicate after they have "+
                "been already set");
        }
    }

    @Override
    public boolean apply(Collection<Attribute> attributes) {
        for (StreamPredicate predicate : predicates) {
            if (predicate.apply(attributes)) {
                return true;
            }
        }
        return false;
    }
}
