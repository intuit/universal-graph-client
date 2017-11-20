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

package com.intuit.ugc.impl.core.queryplan;

/**
 * 
 * @author ajain17
 *
 */
public interface OperationIterator {
    /**
     * Return true if AssemblyLine has any operation in queue
     * 
     * @return
     */
    public boolean hasNext();

    /**
     * Get next operation from AssemblyLine queue
     * @return
     */
    public Operation next();
}
