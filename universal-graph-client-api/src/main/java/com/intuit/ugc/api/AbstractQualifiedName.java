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

import java.util.Objects;

/**
 * Base class for all types of names. For now, it only wraps a simple name
 * represented by a string datatype. But the capabilities can be extended to
 * include a namespace, if required, in future.
 *
 * @author ajain17
 */
public abstract class AbstractQualifiedName {
	
    private String name;
    
    protected AbstractQualifiedName(String name) {
        super();
        this.name = name;
    }
    
    /**
     * Return the name wrapped inside this class
     * 
     * @return The name, or null if there is no name
     */
    public final String getName() {
        return name;
    }

    /**
     * Returns the name
     * 
     * @return 
     */
    @Override
    public String toString() {
        return name;
    }            

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(getName());
        return hash;
    }

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }

        if (object == null) {
            return false;
        }

        if (getClass() != object.getClass()) {
            return false;
        }

        final AbstractQualifiedName other = (AbstractQualifiedName)object;
        if (!Objects.equals(this.getName(), other.getName())) {
            return false;
        }

        return true;
    }
}
