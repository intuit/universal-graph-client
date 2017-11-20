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
 * Represents a query to be run over the underlying persistence store.
 * 
 * @author ajain17
 */
public interface Queries {

	/**
	 * Represents a projection on the underlying persistence store.
	 * 
	 * @author ajain17
	 *
	 */
    public static interface Projection {

        /**
         * include an attribute name in the query
         * @param name - {@link com.intuit.ugc.api.Attribute.Name}
         * @return projection - {@link com.intuit.ugc.api.Queries.Projection}
         */
    	public Queries.Projection includeAttribute(Attribute.Name name);

        /**
         * include all attributes represented by a particular attribute family in the query
         * @param name - {@link com.intuit.ugc.api.Attribute.Family}
         * @return projection - {@link com.intuit.ugc.api.Queries.Projection}
         */
        public Queries.Projection includeAttributes(Attribute.Family namespace);

        /**
         * 
         * @return {@link com.intuit.ugc.api.Query}
         */
        public com.intuit.ugc.api.Query ready();
    }
    
    /**
     * represents a query for lookup on the underlying persistence store.
     * 
     * @author ajain17
     *
     */
    public static interface LookupQuery {
        /**
         * Constructs a fetch query for the specified entity
         * 
         * @param id - {@link com.intuit.ugc.api.Entity.ID}
         * @return 
         */
        public Queries.Projection entity(Entity.ID id);

        /**
         * Constructs a fetch query for the specified entities
         * 
         * @param id  -  {@link com.intuit.ugc.api.Entity.ID}
         * @return 
         */
        public Queries.Projection entities(Entity.ID... id);

        /**
         * Constructs a fetch query for the unique entity identified by the 
         * attribute value
         * 
         * @param name - {@link com.intuit.ugc.api.Attribute.Name}
         * @param value
         * @return 
         */
        public ValueSpecifier<Queries.Projection> entityBy(com.intuit.ugc.api.Attribute.Name name);
    }

    /**
     * Represents a graph traversal query
     * 
     * @author ajain17
     *
     */
    public static interface GraphQuery {

		/**
		 * Return a {@link com.intuit.ugc.api.Queries.GraphTraversal} object
		 * which can be used to traverse a graph starting from root
		 * 
		 * @param id
		 *            - root entity id
		 */
        public Queries.GraphTraversal fromRootEntity(Entity.ID id);
    }

    /**
     * Represents a graph traversal object
     * 
     * @author ajain17
     *
     */
    public static interface GraphTraversal {

        /**
         * No filtering
         * Select all the adjacent entities of a given relationship
         * 
         * @param name - relationship name of the given relationship
         * 
         */
        public Queries.GraphTraversal selectAdjacentEntitiesVia(Relationship.Name name);

        /**
         * No filtering
         * Select all the adjacent entities of a given relationship in the given direction
         * 
         * @param name - relationship name of the given relationship
         * @param direction - given direction in which graph traversal is required
         */
        public Queries.GraphTraversal selectAdjacentEntitiesVia(Relationship.Name name, 
            Direction direction);

        /**
         *
         * select all the adjacent relationships of a given relationship
         * 
         * @param name - relationship name of the given relationship
         */
        public Queries.GraphTraversal selectAdjacentRelationships(Relationship.Name name);

        /**
         * select all the adjacent relationships of a given relationship in the given direction
         *
         * @param name - relationship name of the given relationship
         * @param direction - given direction in which graph traversal is required
         */
        public Queries.GraphTraversal selectAdjacentRelationships(Relationship.Name name,
            Direction direction);

        /**
         * Filter the current working set of elements (entities or 
         * relationships)
         * 
         * @param predicate
         * @return 
         */
        public Queries.GraphTraversal where(Predicate predicate);

        public Queries.Projection select();
        
        /**
         * The direction of graph traversal 
         * can be in, out or both way
         * 
         * @author ajain17
         *
         */
        public static enum Direction {
            IN,
            OUT,
            IN_OUT;
        }
    }    
}
