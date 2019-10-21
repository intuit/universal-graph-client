package com.intuit.ugc.impl.persistence.neptune;

import javax.inject.Inject;

public class NeptuneConnectionManager {

    private NeptunePersistenceConfiguration config;

    @Inject
    public NeptuneConnectionManager(NeptunePersistenceConfiguration config) {
        this.config = config;
    }

    public NeptuneGraphSession getDSEGraphSession() { return new NeptuneGraphSession(config); }

}
