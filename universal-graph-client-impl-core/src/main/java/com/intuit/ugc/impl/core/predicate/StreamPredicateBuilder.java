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

package com.intuit.ugc.impl.core.predicate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import com.intuit.ugc.api.Attribute;
import com.intuit.ugc.api.Predicate;

/**
 * 
 * @author ajain17
 *
 */
public class StreamPredicateBuilder implements Predicate, StreamPredicate {
	
	private List<StreamPredicate> predicateList;
	
	private Attribute.Name attributeName;

	public StreamPredicateBuilder() {
		this.predicateList = new ArrayList<>();
	}

	void addPredicate(StreamPredicate predicate) {
		this.predicateList.add(predicate);
	}

	public Attribute.Name getAttribute() {
		return attributeName;
	}

	public void setAttribute(Attribute.Name attributeName) {
		this.attributeName = attributeName;
	}

	@Override
	public boolean apply(Collection<Attribute> attributes) {
		return predicateList.get(0).apply(attributes);
	}

	public StreamComparison getComparison() {
		return new StreamComparison(this);
	}

	public StreamPredicateBuilder and(StreamPredicate predicate) throws IllegalStateException {
		if (predicate != StreamPredicateBuilder.this) {
			throw new IllegalStateException(
					"Illegal \"and\" statement expected: " + StreamPredicateBuilder.class.getSimpleName() + ", found: "
							+ ((Objects.isNull(predicate)) ? "null" : predicate.getClass().getSimpleName()));
		}
		int index = predicateList.size() - 2;
		StreamPredicate first = predicateList.remove(index);
		StreamPredicate second = predicateList.remove(index);
		predicateList.add(StreamPredicates.and(first, second));
		return this;
	}

	public StreamPredicateBuilder or(Predicate predicate) throws IllegalStateException {
		if (predicate != StreamPredicateBuilder.this) {
			throw new IllegalStateException(
					"Illegal \"or\" statement expected: " + StreamPredicateBuilder.class.getSimpleName() + ", found: "
							+ ((Objects.isNull(predicate)) ? "null" : predicate.getClass().getSimpleName()));
		}
		int index = predicateList.size() - 2;
		StreamPredicate first = predicateList.remove(index);
		StreamPredicate second = predicateList.remove(index);
		predicateList.add(StreamPredicates.or(first, second));
		return this;
	}

	@Override
	public Comparison attribute(Attribute.Name name) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Predicate and(Predicate... predicates) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Predicate or(Predicate... predicates) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
}
