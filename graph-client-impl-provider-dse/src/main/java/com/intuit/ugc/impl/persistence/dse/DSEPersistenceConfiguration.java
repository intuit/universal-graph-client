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

import com.intuit.ugc.api.Persistence;

/**
 * Represents the configurations specific to the DSE persistence store to which
 * this library will connect
 * 
 * @author nverma1
 *
 */
public interface DSEPersistenceConfiguration extends Persistence.Configuration {

	public String getPersistenceRepository();

	public String getHost();

	public int getPort();

	public String getGraph();

}
