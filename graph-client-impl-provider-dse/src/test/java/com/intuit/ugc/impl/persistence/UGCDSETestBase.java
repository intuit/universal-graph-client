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

package com.intuit.ugc.impl.persistence;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.intuit.ugc.api.Persistence;
import com.intuit.ugc.impl.core.spi.GraphVisitor;
import com.intuit.ugc.impl.persistence.dse.DSEPersistenceModule;
import com.intuit.ugc.impl.persistence.dse.DSETestModule;

/**
 * 
 * @author nverma1
 *
 */
public class UGCDSETestBase {

	public static Persistence persistence = null;
	public static GraphVisitor repository = null;

	static {
        try {
            Injector injector = Guice.createInjector(new DSETestModule(), new DSEPersistenceModule());
            repository = injector.getInstance(GraphVisitor.class);
            persistence = injector.getInstance(Persistence.class);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
	}
}
