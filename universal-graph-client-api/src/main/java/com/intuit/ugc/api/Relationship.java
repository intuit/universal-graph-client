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

import com.intuit.ugc.api.Queries.Projection;

/**
 * Represents a relationship between any two entities in the persistence store.
 * A relationship is from a source entity to a target entity. 
 *
 * @author ajain17
 */
public interface Relationship {

	/**
	 * fetches the relationship name
	 * 
	 * @return the name of this relationship
	 */
    public Name getName();

    /**
     * get the source entity id
     * 
     * @return the entity id of the source entity of this relationship
     */
    public Entity.ID getSourceID();
    
    /**
     * get the target entity id
     * 
     * @return the entity id of the target entity of this relationship
     */
    public Entity.ID getTargetID();
    
	public Attribute getAttribute(Attribute.Name name)
		throws AccessControlException;
      
    /**
     * Represents a change to the persistent state of the relationship
     * 
     * @author ajain17
     */
    public static interface Mutation {

    	/**
    	 * select an attribute of a relationship 
    	 * @param name name of the attribute
    	 * @param value value of the attribute 
    	 * @return the corresponding Mutation
    	 */
        public Relationship.Mutation setAttribute(Attribute.Name name, 
                Object value);

    	/**
    	 * delete an attribute of a relationship 
    	 * @param name name of the attribute
    	 * @return the corresponding Mutation
    	 */
        public Relationship.Mutation deleteAttribute(Attribute.Name name);

        public com.intuit.ugc.api.BatchMutation ready();

		public Relationship.Mutation delete();
    }
  
	/**
	 * Represents the name of the relationship
	 * 
	 * @author ajain17
	 */
	public static final class Name extends AbstractQualifiedName {

        private Name(String name) {
            super(name);
        }
        
		public static Name valueOf(String name) {
			return new Name(name);
		}
	}
}
