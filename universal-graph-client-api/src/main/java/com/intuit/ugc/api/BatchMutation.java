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
 * A single batch of changes to the persistence store
 * 
 * @author ajain17
 */
public interface BatchMutation {
    
	/**
	 * create an entity for batch mutation
	 * @param newEntity
	 * @return @see {@link com.intuit.ugc.api.OperationResult}
	 */
    public com.intuit.ugc.api.BatchMutation createEntity(NewEntity newEntity);

    /**
     * create a relationship for batch mutation
     * @param newRelationship
     * @return @see {@link com.intuit.ugc.api.OperationResult}
     */
    public com.intuit.ugc.api.BatchMutation createRelationship(
        NewRelationship newRelationship);

    /**
     * Facilitate a mutation on a given entity (update)
     * @param entity
     * @return @see {@link com.intuit.ugc.api.OperationResult}
     */
    public Entity.Mutation withEntity(Entity entity);

    /**
     * Facilitate a mutation on a given relationship (update)
     * @param relationship
     * @return @see {@link com.intuit.ugc.api.OperationResult}
     */
    public Relationship.Mutation withRelationship(Relationship relationship);
    
    /**
     * execute the batch mutation
     * @return result of the batch mutation @see {@link com.intuit.ugc.api.OperationResult}
     * @throws InvalidAttributeException
     * @throws AccessControlException
     */
    public OperationResult<Result> execute()
        throws InvalidAttributeException, AccessControlException;

    public static interface Result {
        // ...
    }
}
