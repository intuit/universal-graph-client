package com.intuit.ugc.impl.persistence.neptune;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class NeptuneDSEPersistenceRepositoryProvider implements Provider<NeptuneGraphVisitor> {

    NeptuneConnectionManager manager;

    @Inject
    public NeptuneDSEPersistenceRepositoryProvider(NeptuneConnectionManager manager) {
        this.manager = manager;
    }

    @Override
    public NeptuneGraphVisitor get() {
        return new NeptuneGraphVisitor(this.manager);
    }
}
