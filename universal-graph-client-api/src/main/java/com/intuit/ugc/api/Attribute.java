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

import java.util.UUID;

/**
 * A single named value that represents an attribute of a graph element.
 * 
 * @author ajain17
 */
public interface Attribute {

	/**
	 * Fetch the Metadata associated with the Attribute
	 * 
	 * @see com.intuit.ugc.api.Attribute.Metadata . An example of Metadata
	 *      can be the datatype (class) of the value specified.
	 * @return Metadata for this Attribute.
	 */
	public Attribute.Metadata getMetadata();

	/**
	 * Return the name of the attribute.
	 * 
	 * @see com.intuit.ugc.api.Attribute.Name
	 * @return name of the attribute.
	 */
	public Attribute.Name getName();

	/**
	 * get the value associated with the particular value type
	 * 
	 * @param valueType
	 * @return value
	 */
	public <V> V getValue(Class<V> valueType);

	/**
	 * get the integer value of this attribute
	 * 
	 * @return value as an integer
	 */
	public Integer getInteger();

	/**
	 * get the double value of this attribute
	 * 
	 * @return value as a double
	 */
	public Double getDouble();

	/**
	 * get the boolean value of this attribute
	 * 
	 * @return value as a boolean
	 */
	public Boolean getBoolean();

	/**
	 * get the String value of this attribute
	 * 
	 * @return value as a Strng
	 */
	public String getString();

	/**
	 * get the instant value of this attribute
	 * 
	 * @return value as an instant
	 */
    public java.time.Instant getInstant();
    
	/**
	 * get the UUID value of this attribute
	 * 
	 * @return value as UUID
	 */
	public UUID getUUID();

	/**
	 * Attribute name 
	 * 
	 * @author nverma1
	 *
	 */
	public static final class Name extends AbstractQualifiedName {
        
        private Name(String name) {
            super(name);
        }
        
		/**
		 * returns a new Name instance wrapped over the name String passed.
		 * 
		 * @param name
		 * @return
		 */
		public static Name valueOf(String name) {
			return new Name(name);
		}
	}
	
	/**
	 * To specify of family of names
	 * 
	 * @author nverma1
	 *
	 */
	public static final class Family extends AbstractQualifiedName {
		private Family(String name) {
			super(name);
		}
		
		public static Family valueOf(String name) {
			return new Family(name);
		}
	}

	/**
	 * Represents Meta - values associated with the Attribute. To be implemented
	 * by specific providers based on what meta information is required by that
	 * provider
	 * 
	 * @author nverma1
	 *
	 */
	public static interface Metadata {
		// ...
	}
}
