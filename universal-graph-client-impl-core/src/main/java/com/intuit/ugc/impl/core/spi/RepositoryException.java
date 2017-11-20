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

package com.intuit.ugc.impl.core.spi;

/**
 * 
 * 
 * @author ajain17
 */
@SuppressWarnings("serial")
public class RepositoryException extends RuntimeException {

    public RepositoryException(Throwable cause) {
        super(cause);
    }

    public RepositoryException(String m, Throwable cause) {
        super(m,cause);
    }
}
