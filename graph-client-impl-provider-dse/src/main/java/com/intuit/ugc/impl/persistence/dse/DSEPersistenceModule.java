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

import static java.util.Objects.nonNull;

import javax.inject.Singleton;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.intuit.ugc.api.Persistence;
import com.intuit.ugc.impl.core.queryplan.MutationExecutorFactory;
import com.intuit.ugc.impl.core.spi.GraphVisitor;

/**
 * The module responsible for initializing with the DSE store specific configs
 * and bootstrapping the system.
 * 
 * @author nverma1
 *
 */
public class DSEPersistenceModule extends AbstractModule {
	private DSEPersistenceConfiguration configuration;
	
    public DSEPersistenceModule() {
        super();
    }
    
    public DSEPersistenceModule(DSEPersistenceConfiguration config) {
        this();
        this.configuration = config;
    }

	@Override
	protected void configure() {
        if ( nonNull(configuration) ) {
            bind(Persistence.Configuration.class).toInstance(configuration);
            bind(DSEPersistenceConfiguration.class).toInstance(configuration);
        }
        
        requireBinding(Persistence.Configuration.class);
        install(new FactoryModuleBuilder().build(MutationExecutorFactory.class));
        bind(GraphVisitor.class).toProvider(DSEPersistenceRepositoryProvider.class).in(Singleton.class);
        bind(Persistence.class).to(DSEPersistence.class);
	}

}
