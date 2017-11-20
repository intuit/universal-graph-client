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

package com.intuit.ugc.impl.persistence.dse.helper;

import com.intuit.ugc.impl.persistence.dse.DSEConnectionManager;
import com.intuit.ugc.impl.persistence.dse.DSEPersistenceConfiguration;

/**
 * 
 * @author nverma1
 *
 */
public class DSEConnectionManagerMock extends DSEConnectionManager {

	public DSEConnectionManagerMock(DSEPersistenceConfiguration config) {
		super(config);
	}

}
