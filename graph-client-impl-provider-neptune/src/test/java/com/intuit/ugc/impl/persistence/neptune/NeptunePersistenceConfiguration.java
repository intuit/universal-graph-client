package com.intuit.ugc.impl.persistence.neptune;

public interface NeptunePersistenceConfiguration {
    public String getHost();

    public int getPort();

    public String getGraph();

    public String getKeyFile();

}
