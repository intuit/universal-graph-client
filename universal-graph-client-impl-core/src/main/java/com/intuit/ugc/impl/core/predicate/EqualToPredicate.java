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
public class EqualToPredicate implements StreamPredicate {

    private Attribute.Name attributeName;
    protected Object value;

    public EqualToPredicate() {
    }

    public EqualToPredicate(Attribute.Name attributeName) {
        this.attributeName = attributeName;
    }

    EqualToPredicate(Attribute.Name attributeName, Object value) {
        this.attributeName = attributeName;
        this.value = value;
    }

    @Override
    public boolean apply(Collection<Attribute> attributes) {
        return Objects.nonNull(attributeName)
                && Objects.nonNull(value)
                && attributes.stream()
                    .anyMatch(attr ->
                        attr.getName().equals(attributeName)
                            && value.equals(attr.getValue(value.getClass()))
                );
    }
}
