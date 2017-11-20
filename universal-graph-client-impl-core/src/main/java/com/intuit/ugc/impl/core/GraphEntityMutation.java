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

import com.intuit.ugc.api.Attribute;
import com.intuit.ugc.api.BatchMutation;
import com.intuit.ugc.api.Entity;
import com.intuit.ugc.api.ValueSpecifier;
import com.intuit.ugc.api.Attribute.Name;
import com.intuit.ugc.api.Entity.Mutation;
import com.intuit.ugc.impl.core.queryplan.OperationPipeline;
import com.intuit.ugc.impl.core.queryplan.operations.DeleteEntity;
import com.intuit.ugc.impl.core.queryplan.operations.UpdateEntity;
import com.toddfast.util.convert.TypeConverter.Conversion;
import com.toddfast.util.preconditions.Preconditions;

/**
 * A representation of Entity Mutation in graph persistence
 * 
 * @author ajain17
 */
public class GraphEntityMutation implements Entity.Mutation {

	private final Entity entity;
	private final OperationPipeline pipeline;
	private final BatchMutation batchMutation;
	private final GraphAttributeOperations attributeOperatons;
	/**
	 * Delete and update are both mutations over an entity. To differentiate
	 * between the two operations, a boolean is used here to signify if the
	 * operation intended is a delete one.
	 */
	private boolean delete = false;

	public GraphEntityMutation(Entity entity,
            OperationPipeline assemblyLine,
            BatchMutation batchMutation) {
		this.entity = entity;
		this.pipeline = assemblyLine;
		this.batchMutation = batchMutation;
		this.attributeOperatons = new GraphAttributeOperations();
	}

	@Override
	public ValueSpecifier<Mutation> withAttribute(Name name) {
		return new GraphValueChange(
            Preconditions.argumentNotNull(name,"name"));
	}

	@Override
	public Mutation deleteAttribute(Name name) {
		attributeOperatons.unsetAttributeValue(
            Preconditions.argumentNotNull(name,"name"));
		return this;
	}

	@Override
	public Mutation deleteAttributes(Attribute.Family namespace) {
		return this;
	}

	@Override
	public Mutation delete() {
		this.delete = true;
		return this;
	}

	@Override
	public com.intuit.ugc.api.BatchMutation ready() {
		if(this.delete){
			DeleteEntity deleteEntity = new DeleteEntity(entity);
			pipeline.add(deleteEntity);
		}else{
			UpdateEntity updateEntity = new UpdateEntity(entity,attributeOperatons);
			pipeline.add(updateEntity);
		}
		return batchMutation;
	}


	/**
	 * 
	 * @author ajain17
	 *
	 */
	public class GraphValueChange implements ValueSpecifier<Mutation> {
		private Name name;

		public GraphValueChange(Name name) {
			this.name = name;
		}

		@Override
		public Mutation value(String value) {
			attributeOperatons.setAttributeValue(name, value);
			return GraphEntityMutation.this;
		}

		@Override
		public Mutation value(Integer value) {
			attributeOperatons.setAttributeValue(name, value);
			return GraphEntityMutation.this;
		}

		@Override
		public Mutation value(Boolean value) {
			attributeOperatons.setAttributeValue(name, value);
			return GraphEntityMutation.this;
		}

		@Override
		public Mutation value(Double value) {
			attributeOperatons.setAttributeValue(name, value);
			return GraphEntityMutation.this;
		}

		@Override
		public <T> Mutation value(Object value, Conversion<T> converter) {
			T val = converter.convert(value);
			attributeOperatons.setAttributeValue(name, val);
			return GraphEntityMutation.this;
		}

		@Override
		public Mutation value(Instant value) {
			return null;
		}
	}
}
