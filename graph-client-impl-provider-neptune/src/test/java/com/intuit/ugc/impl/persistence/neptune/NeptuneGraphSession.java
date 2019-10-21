package com.intuit.ugc.impl.persistence.neptune;

import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.apache.tinkerpop.gremlin.driver.remote.DriverRemoteConnection;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;

public class NeptuneGraphSession {

    private Cluster cluster  = null;
    private GraphTraversalSource graphSource = null;

    public NeptuneGraphSession(NeptunePersistenceConfiguration config) { init(config); }

    protected void init(NeptunePersistenceConfiguration config) {
        Cluster.Builder builder = Cluster.build();
        builder.addContactPoint(config.getHost());
        builder.port(config.getPort());
        builder.enableSsl(true);
        builder.keyCertChainFile(config.getKeyFile());
        cluster = builder.create();
        graphSource = new GraphTraversalSource(DriverRemoteConnection.using(cluster));
    }

    /**
     * Get DSESession
     *
     * @return
     */
    public GraphTraversalSource getSession() {
        return graphSource;
    }

    /**
     * Close connection
     */
    public void closeConnections() throws Exception {
        if (graphSource != null) {
            graphSource.close();
            graphSource = null;
        }
        if (cluster != null) {
            cluster.close();
            cluster = null;
        }
    }

}
