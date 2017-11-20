/*
 * Copyright (c) 2017.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    tfast - initial API and implementation and/or initial documentation
 *    ajain17 & nverma1 - API implementation, enhancements and extension
 */

package com.intuit.ugc.api.mogwai;

import java.util.ArrayList;
import java.util.List;

import com.intuit.ugc.api.Attribute;
import com.intuit.ugc.api.Predicate;

/**
 * A predicate of Gizmo
 * @see com.intuit.ugc.api.mogwai.Gizmo
 * @author ajain17
 */
/*pkg*/ class GizmoPredicate implements Gizmo.Predicate {

    public GizmoPredicate(Predicate predicate) {
        super();
        this.predicate = predicate;
    }

    @Override
    public Gizmo.Predicate eq(String name, Object value) {
        return new GizmoPredicate(
            getPredicate().attribute(Attribute.Name.valueOf(name))
                .equalTo(value));
    }

    @Override
    public Gizmo.Predicate neq(String name, Object value) {
        return new GizmoPredicate(
            getPredicate().attribute(Attribute.Name.valueOf(name))
                .notEqualTo(value));
    }

    @Override
    public Gizmo.Predicate gt(String name, Object value) {
        return new GizmoPredicate(
            getPredicate().attribute(Attribute.Name.valueOf(name))
                .greaterThan(value));
    }

    @Override
    public Gizmo.Predicate lt(String name, Object value) {
        return new GizmoPredicate(
            getPredicate().attribute(Attribute.Name.valueOf(name))
                .lessThan(value));
    }

    @Override
    public Gizmo.Predicate and(Gizmo.Predicate... values) {
        return new GizmoPredicate(
            getPredicate().and(unwrap(values)));
    }

    @Override
    public Gizmo.Predicate or(Gizmo.Predicate... values) {
        return new GizmoPredicate(
            getPredicate().or(unwrap(values)));
    }

    /**
     * TODO: This is buggy; inoperable with other impls of Gizmo.Predicate.
     * One fix might be to just make Gizmo.Predicate concrete and avoid the
     * problem altogether, since the abstraction of the Predicate interface
     * remains underneath.
     * 
     */
    private Predicate[] unwrap(Gizmo.Predicate... values) {
        List<Predicate> predicateList = new ArrayList<>();
        for (Gizmo.Predicate p: values) {
            predicateList.add(((GizmoPredicate)p).getPredicate());
        }

        return predicateList.toArray(new Predicate[0]);
    }

    /*pkg*/ Predicate getPredicate() {
        return predicate;
    }
    
    private Predicate predicate;
}
