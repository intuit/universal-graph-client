/*
 * Copyright (c) 2017.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    tfast - initial API and implementation and/or initial documentation
 *    ajain17 & nverma1 - API implementation, enhancements and extension
 */

package com.intuit.ugc.api;

/**
 * The primary interface for interacting with persistence services
 * 
 * @author ajain17
 */
public interface Persistence {
    
    /**
     * Allocate a new entity ID
     * 
     * @return 
     */
    public Entity.ID allocateID();

    /**
     * 
     * 
     * @return 
     */
    public Queries.LookupQuery lookup();

    /**
     * 
     * 
     * @return 
     */
    public Queries.GraphQuery queryGraph();

    /**
     * Begin a new mutation
     * 
     * @return 
     */
    public com.intuit.ugc.api.BatchMutation prepareBatchMutation();

    /**
     * Returns a new entity mutation in the context of an implicit BatchMutation
     * 
     * @return An entity mutation
     */
    public Entity.Mutation prepareMutation(Entity entity);

    /**
     * Returns a new relationship mutation in the context of an implicit BatchMutation
     * 
     * @return A relationship mutation
     */
    public Relationship.Mutation prepareMutation(Relationship relationship);

    /**
     * 
     * 
     */
    public Predicate predicates();
    
    
    /**
     * Provisions a new instance of Persistence
     * 
     */
    public interface Factory {
        
        public Persistence newInstance(Configuration config);
    }  


    /**
     * Provides configuration parameters required to obtain a valid instance 
     * of Persistence
     * 
     */
    public interface Configuration {

        /**
         * Get the identifier for the endpoint. This must have meaning to the
         * the implementation.
         * 
         * @return 
         */
        public String getEndpoint();
        
        /**
         * The access token required to access the endpoint
         * 
         * @return 
         */
        public AccessToken getAccessToken();
    }
}
