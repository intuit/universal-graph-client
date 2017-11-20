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
import java.util.Arrays;

import com.intuit.ugc.api.Entity;
import com.intuit.ugc.api.Queries;
import com.intuit.ugc.api.ValueSpecifier;
import com.intuit.ugc.api.Attribute.Name;
import com.intuit.ugc.api.Queries.Projection;
import com.intuit.ugc.impl.core.queryplan.OperationPipeline;
import com.intuit.ugc.impl.core.queryplan.operations.GetBatchEntityByID;
import com.intuit.ugc.impl.core.queryplan.operations.GetEntityByID;
import com.intuit.ugc.impl.core.queryplan.operations.GetEntityByProperty;
import com.intuit.ugc.impl.core.spi.GraphVisitor;
import com.toddfast.util.convert.TypeConverter.Conversion;
import com.toddfast.util.preconditions.Preconditions;

/**
 * 
 * A representation of graph persistence specific lookup query
 * 
 * @see com.intuit.ugc.api.Queries.LookupQuery
 * @author ajain17
 */
public class GraphLookUpQuery implements Queries.LookupQuery {

	private GraphVisitor repository;

	public GraphLookUpQuery(final GraphVisitor repository) {
        super();
		this.repository = repository;
	}

	@Override
	public Queries.Projection entities(Entity.ID... entityIDs) {
		Preconditions.argumentNotNull(entityIDs, "entityIDs");
		OperationPipeline pipeline = 
            new OperationPipeline();

		pipeline.add(new GetBatchEntityByID(Arrays.asList(entityIDs)));
		return new GraphProjection(repository, pipeline);
	}

	@Override
	public Queries.Projection entity(Entity.ID entityID) {
		Preconditions.argumentNotNull(entityID, "entityID");
		OperationPipeline pipeline = 
            new OperationPipeline();

		pipeline.add(new GetEntityByID(entityID));
		return new GraphProjection(repository, pipeline);
	}

	@Override
	public ValueSpecifier<Projection> entityBy(Name name) {
		return new GraphValueChange(name);
	}

	public class GraphValueChange
			implements
				ValueSpecifier<Queries.Projection> {
		private Name name;

		public GraphValueChange(Name name) {
			this.name = name;
		}

		private Queries.Projection getProjection(String value) {
			OperationPipeline pipeline = new OperationPipeline();
			pipeline.add(new GetEntityByProperty(name, value));
			return new GraphProjection(repository, pipeline);
		}

		@Override
		public Queries.Projection value(String value) {
			return getProjection(value);
		}

		@Override
		public Queries.Projection value(Integer value) {
			return getProjection(String.valueOf(value));
		}

		@Override
		public Queries.Projection value(Boolean value) {
			return getProjection(String.valueOf(value));
		}

		@Override
		public Queries.Projection value(Double value) {
			return getProjection(String.valueOf(value));
		}

		@Override
		public <T> Queries.Projection value(Object value,
				Conversion<T> converter) {
			T val = converter.convert(value);
			return getProjection(String.valueOf(val));
		}

		@Override
		public Queries.Projection value(Instant value) {
			return getProjection(String.valueOf(value));
		}
	}
}
