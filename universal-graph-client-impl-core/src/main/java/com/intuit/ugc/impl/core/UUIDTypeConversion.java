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

import java.util.UUID;

import com.toddfast.util.convert.TypeConverter;

/**
 * A class to support the conversion of a value in an {@link java.util.UUID} type
 *
 * @author ajain17
 */
public class UUIDTypeConversion implements TypeConverter.Conversion<UUID> {

    @Override
    public Object[] getTypeKeys() {
        return new Object[] { UUID.class, UUID.class.getName() };
    }

    @Override
    public UUID convert(Object value) {
        if (value == null) {
            return null;
        }
        else
        if (value instanceof UUID) {
            return (UUID)value;
        }
        else
        if (value instanceof String) {
            return UUID.fromString((String)value);
        }
        else
        if (value.getClass().isArray()) {
            if (value.getClass().getComponentType() == Byte.TYPE) {
                return UUID.nameUUIDFromBytes((byte[])value);
            }
            else
            if (value.getClass().getComponentType() == Character.TYPE) {
            	return UUID.fromString(String.valueOf((char[])value));
            }
            else {
                throw new IllegalArgumentException("Cannot convert to UUID "+
                    "from array of \""+
                    value.getClass().getComponentType()+"\"");
            }
        }
        else {
            return UUID.fromString(value.toString());
        }
    }
}
