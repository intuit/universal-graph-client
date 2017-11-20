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

import java.util.List;

/**
 * A query that can be performed against the persistence store
 * 
 * @author ajain17
 */
public interface Query {
    
    /**
     * Performs the query
     * 
     * @return
     * @throws AccessControlException 
     */
    public OperationResult<com.intuit.ugc.api.Query.Result> execute()
        throws AccessControlException;


    /**
     * The results of query execution
     * 
     */
    public static interface Result {

        /**
         * Returns the root entity requested in the query
         * 
         * @return  If more than a single entity in the result, returns the 
         *          first. Returns null if no root entity in result
         */
        public Entity getEntity();

        /**
         * Returns the list of root entities requested in the query
         * 
         * @return an empty list if no entities in result
         */
        public List<Entity> getEntities();

        /**
         * Returns the root relationship requested in the query
         * 
         * @return  If more than a single relationships in the result, returns 
         *          the first. Returns null if no root relationship in result.
         */
        public Relationship getRelationship();

        /**
         * Returns the list of root relationships requested in the query
         * 
         * @return  Returns an empty list if no relationships in result
         */
        public List<Relationship> getRelationships();
    }
}
