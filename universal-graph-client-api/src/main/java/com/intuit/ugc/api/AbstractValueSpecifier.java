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

package com.intuit.ugc.api;

import java.time.Instant;

import com.toddfast.util.convert.TypeConverter;

/**
 * Base class supporting value-specification implementation in the builders
 *
 * @author ajain17
 */
abstract class AbstractValueSpecifier<B> implements ValueSpecifier<B> {

    private B builder;
    private final Attribute.Name name;
    
    protected AbstractValueSpecifier(B builder, Attribute.Name name) {
        super();
        this.builder = builder;
        this.name = name;
    }

    @Override
    public final B value(String value) {
        setValue(value);
        return builder;
    }

    @Override
    public final B value(Integer value) {
        setValue(value);
        return builder;
    }

    @Override
    public final B value(Double value) {
        setValue(value);
        return builder;
    }

    @Override
    public final B value(Boolean value) {
        setValue(value);
        return builder;
    }

    @Override
    public final B value(Instant value) {
        setValue(value);
        return builder;
    }

    @Override
    public <T> B value(Object value, TypeConverter.Conversion<T> converter) {
        setValue(converter.convert(value));
        return builder;
    }

    /** SPI method */
    protected abstract void setValue(Object value);
}
