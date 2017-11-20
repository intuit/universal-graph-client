/*
 * Copyright (c) 2017.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    ajain17 & nverma1 - API , implementation and initial documentation
 */

package com.intuit.ugc.impl.persistence.dse;

import com.google.inject.Inject;
import com.intuit.ugc.impl.core.AbstractGraphPersistence;
import com.intuit.ugc.impl.core.queryplan.MutationExecutorFactory;
import com.intuit.ugc.impl.core.spi.GraphVisitor;

/**
 * represents a DSE Graph Persistence
 * 
 * is initialized with a {@link com.intuit.ugc.impl.core.spi.GraphVisitor}
 * instance specific to DSE and a
 * {@link com.intuit.ugc.impl.core.queryplan.MutationExecutorFactory}
 * 
 * @author nverma1
 *
 */
public class DSEPersistence extends AbstractGraphPersistence {

	@Inject
	public DSEPersistence(GraphVisitor repository, MutationExecutorFactory factory) {
		super(repository, factory);
	}

}
