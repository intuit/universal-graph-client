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

import com.intuit.ugc.api.Predicate;

/**
 * 
 * @author ajain17
 *
 */
public interface ContextOperation extends Operation {

    /**
     * Predicate for context operation
     * 
     * @param predicate
     */
    public void condition(Predicate predicate);

}
