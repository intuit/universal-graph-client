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

import com.datastax.driver.dse.graph.GraphResultSet;
import com.google.common.util.concurrent.FutureCallback;

/**
 * Graph Callback object used for the graph query executed in async mode. Since
 * execution over DSE graph is much faster in async mode.
 * 
 * @author nverma1
 *
 */
public class GraphCallBack implements FutureCallback<GraphResultSet> {
	private GraphResultSet result = null;
	private Throwable th = null;
	
	public void onSuccess(GraphResultSet result) {
		this.result = result;
	}

	public void onFailure(Throwable t) {
		this.th = t;
	}

	public GraphResultSet getResult() {
		return result;
	}

	public Throwable getTh() {
		return th;
	}
}
