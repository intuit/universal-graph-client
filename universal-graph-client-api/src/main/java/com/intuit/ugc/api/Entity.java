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

import java.util.Objects;

/**
 * An entity in the persistence store
 * 
 * @author ajain17
 */
public interface Entity {

	/**
	 * return unique id of this entity
	 * @return {@link com.intuit.ugc.api.Entity.ID}
	 */
	public Entity.ID getID();

	public Attribute getAttribute(Attribute.Name name)
		throws AccessControlException;
    
    /**
     * Represents a change to the persistent state of the entity
     * 
     */
    public static interface Mutation {

        public ValueSpecifier<Mutation> withAttribute(Attribute.Name name);

        public Entity.Mutation deleteAttribute(Attribute.Name name);

        public Entity.Mutation deleteAttributes(Attribute.Family family);

        public com.intuit.ugc.api.BatchMutation ready();

		public Entity.Mutation delete();
    }

    /**
     * An ID for an entity
     * 
     */
	public static class ID {

        private ID(String id) {
            super();
            this.id=id;
        }

        /**
		 * Returns the raw, unqualified ID
		 *
		 */
        public String getRawID() {
            return id;
        }

        @Override
        public int hashCode() {
            int hash=3;
            hash=HASH_SEED*hash+Objects.hashCode(this.id);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj==null) {
                return false;
            }
            if (getClass()!=obj.getClass()) {
                return false;
            }
            final ID other=(ID)obj;
            if (!Objects.equals(this.id,other.id)) {
                return false;
            }
            return true;
        }

		@Override
		public String toString() {
			return this.id;
		}
        
		public static ID valueOf(String id) {
    		return new ID(id);
		}

        private String id;
        
        /** A large prime number that must be unique to this class to 
            preserve identity semantics */
        private static final int HASH_SEED=59;
	}
}
