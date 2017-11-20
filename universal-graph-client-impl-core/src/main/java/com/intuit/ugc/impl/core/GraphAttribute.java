/*
 * Copyright (c) 2017.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    ajain17 - API and implementation and initial documentation
 *    nverma1 - enhancements
 */

package com.intuit.ugc.impl.core;

import java.time.Instant;
import java.util.UUID;

import com.intuit.ugc.api.Attribute;
import com.toddfast.util.convert.TypeConverter;
import com.toddfast.util.preconditions.Preconditions;

/**
 * A representation of Graph specific attribute
 * 
 * @see com.intuit.ugc.api.Attribute
 * @author ajain17
 */
public class GraphAttribute implements Attribute {

	private final Object attributeValue;
	private final Name name;
	private final Metadata metadata;

	public <T> GraphAttribute(Name name, Object attributeValue, 
            Attribute.Metadata metadata) {
        super();
		this.name = Preconditions.argumentNotNull(name,"name");
		this.attributeValue =
            Preconditions.argumentNotNull(attributeValue,"attributeValue");
		this.metadata = Preconditions.argumentNotNull(metadata,"metadata");
	}

	@Override
	public Metadata getMetadata() {
		return metadata;
	}

	@Override
	public Name getName() {
		return name;
	}

	@Override
	public <V> V getValue(Class<V> clazz) {
        return TypeConverter.convert(
            Preconditions.argumentNotNull(clazz,"clazz"),
            attributeValue);
	}

	@Override
	public Integer getInteger() {
        return TypeConverter.asInt(attributeValue);
	}

	@Override
	public Double getDouble() {
		return TypeConverter.asDouble(attributeValue);
	}

	@Override
	public Boolean getBoolean() {
		return TypeConverter.asBoolean(attributeValue);
	}

	@Override
	public String getString() {
		return TypeConverter.asString(attributeValue);
	}

	@Override
	public Instant getInstant() {
        return TypeConverter.convert(Instant.class,attributeValue);
	}

	@Override
	public UUID getUUID() {
        return TypeConverter.convert(UUID.class,attributeValue);
	}
}
