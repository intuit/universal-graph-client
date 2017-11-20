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

import java.time.Instant;
import java.util.Date;

import com.toddfast.util.convert.TypeConverter;

/**
 * A class to support the conversion of a value in an {@link java.time.Instant} type
 *
 * @author ajain17
 */
public class InstantTypeConversion implements TypeConverter.Conversion<Instant> {

    @Override
    public Object[] getTypeKeys() {
        return new Object[] { Instant.class, Instant.class.getName() };
    }

    @Override
    public Instant convert(Object value) {
        if (value == null) {
            return null;
        }
        else
        if (value instanceof Instant) {
            return (Instant)value;
        }
        else
        if (value instanceof Date) {
            return ((Date)value).toInstant();
        }
        else
        if (value instanceof CharSequence) {
            return Instant.parse((CharSequence)value);
        }
        else {
            return Instant.parse(value.toString());
        }
    }
}
