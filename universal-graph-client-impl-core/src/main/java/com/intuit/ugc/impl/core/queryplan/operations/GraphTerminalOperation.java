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

package com.intuit.ugc.impl.core.queryplan.operations;

import java.util.ArrayList;

import java.util.List;

import com.intuit.ugc.api.Attribute.Family;
import com.intuit.ugc.api.Attribute.Name;
import com.intuit.ugc.impl.core.queryplan.TerminalOperation;

/**
 * Represents a graph terminal operation. The operation itself is stored in
 * the pipeline as an instance of this class. And once execution is started over
 * the pipeline, all the operations are executed one by one.
 * 
 * @author ajain17
 *
 */
public class GraphTerminalOperation implements TerminalOperation {
    private final List<Name> nameList;
    private final List<Family> namespaceList;

    public GraphTerminalOperation() {
        super();
        nameList = new ArrayList<>();
        namespaceList = new ArrayList<>();
    }

    public List<Name> getNameList() {
        return nameList;
    }

    public List<Family> getNamespaceList() {
        return namespaceList;
    }

    @Override
    public String toString() {
        return "Graph Terminal Operation";
    }

    @Override
    public void include(Name name) {
        nameList.add(name);
    }

    @Override
    public void include(Family namespace) {
        namespaceList.add(namespace);
    }

}
