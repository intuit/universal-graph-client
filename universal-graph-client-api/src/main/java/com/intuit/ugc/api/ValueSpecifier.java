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
 * A common interface for specifying the value of something
 * 
 * @author ajain17
 */
public interface ValueSpecifier<B> {
    
    public B value(String value);

    public B value(Integer value);

    public B value(Double value);

    public B value(Boolean value);

    public B value(Instant value);

    /**
     * Support conversion to an arbitrary type
     * 
     * @param <T>
     * @param value
     * @param converter
     * @return 
     */
    public <T> B value(Object value, TypeConverter.Conversion<T> converter);
}
