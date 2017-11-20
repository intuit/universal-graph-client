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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.intuit.ugc.api.Attribute.Name;
import com.toddfast.util.preconditions.Preconditions;

/**
 * A representation of the operations possible on graph attribute.
 * 
 * @ajain17
 */
public class GraphAttributeOperations {

	private Map<Name, Object> setOperations;
	private List<Name> unsetOperations;

	public GraphAttributeOperations() {
		setOperations = new HashMap<>();
		unsetOperations = new ArrayList<>();
	}

	/**
	 * 
     * @param   value
     *          The boxed scalar value, or null (which will unset the value)
	 */
	public void setAttributeValue(Name name, Object value) {
        if (value == null) {
            unsetAttributeValue(name);
        }
        else {
    		Preconditions.argumentNotNull(name,"name::Atribute.Name");
            setOperations.put(name, value);
        }
	}

	/**
	 * 
     * 
	 */
	public void unsetAttributeValue(Name name) {
		Preconditions.argumentNotNull(name,"name::Atribute.Name");
		unsetOperations.add(name);
	}
	
	/**
	 * 
     * 
	 */
	public Map<Name, Object> getSetValueOperations() {
		return Collections.unmodifiableMap(setOperations);
	}

	/**
	 * 
     * 
	 */
	public List<Name> getUnsetValueOperations() {
		return Collections.unmodifiableList(unsetOperations);
	}
}
