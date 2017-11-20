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

/**
 * A compound predicate builder using attribute values
 * 
 * @author ajain17
 */
public interface Predicate {
 
    public static interface Comparison {
        
        public Predicate equalTo(Object value);
        
        public Predicate notEqualTo(Object value);
        
        public Predicate greaterThan(Object value);
        
        public Predicate lessThan(Object value);
    }
    
    public Predicate.Comparison attribute(Attribute.Name name);

    public Predicate and(Predicate... predicate);

    public Predicate or(Predicate... predicate);
}
