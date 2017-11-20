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

import com.intuit.ugc.api.Predicate;
import com.intuit.ugc.api.Attribute.Name;
import com.toddfast.util.preconditions.Preconditions;

/**
 * Implements Predicate
 * Represents a Predicate in a graph persistence store
 * 
 * @author ajain17
 * 
 */
public class GraphPredicate implements Predicate {

	public enum PredicateComparison {
		EQUAL, NOT_EQUAL, GREATER, LESS;
	}

	public enum PredicateOperation {
		AND, OR, NONE;
	}

	private Name name;
	private Object value;
	private PredicateComparison pComparison;
	private PredicateOperation pOperation;
	private Predicate nextPredicate;

	public GraphPredicate() {
		this.pOperation = PredicateOperation.NONE;
	}

	private GraphPredicate(Name name, Object value,
                           PredicateComparison pComparison) {
		this.name = name;
		this.value = value;
		this.pComparison = pComparison;
		this.pOperation = PredicateOperation.NONE;
	}

	public Name getName() {
		return name;
	}

	public Object getValue() {
		return value;
	}

	public PredicateComparison getpComparison() {
		return pComparison;
	}

	private void setpOperation(PredicateOperation pOperation) {
		this.pOperation = pOperation;
	}

	private void setNextPredicate(Predicate nextPredicate) {
		this.nextPredicate = nextPredicate;
	}

	public PredicateOperation getpOperation() {
		return pOperation;
	}

	public Predicate getNextPredicate() {
		return nextPredicate;
	}

	private void conditionOp(PredicateOperation op, Predicate... predicate) {
		if (predicate.length >= 2) {
			for (int index = 0; index < predicate.length - 1; index++) {
				((GraphPredicate) predicate[index])
						.setNextPredicate(predicate[index + 1]);
				((GraphPredicate) predicate[index]).setpOperation(op);
			}
		}
	}

	@Override
	public Predicate and(Predicate... predicate) {
		Preconditions.argumentNotNull(predicate,"predicate::Predicate");
		Predicate root = predicate[0];
		conditionOp(PredicateOperation.AND, predicate);
		return root;
	}

	@Override
	public Predicate or(Predicate... predicate) {
		Preconditions.argumentNotNull(predicate,"predicate::Predicate");
		Predicate root = predicate[0];
		conditionOp(PredicateOperation.OR, predicate);
		return root;
	}

	@Override
	public Comparison attribute(Name name) {
		return new GraphComparison(
            Preconditions.argumentNotNull(name,"name"));
	}

	/**
	 * GraphComparison
	 * 
	 * @author ajain17
	 */
	public static class GraphComparison implements Comparison {
		private Name name;

		public GraphComparison(Name name) {
			this.name = name;
		}

		@Override
		public Predicate equalTo(Object value) {
			return new GraphPredicate(name, value, PredicateComparison.EQUAL);
		}

		@Override
		public Predicate notEqualTo(Object value) {
			return new GraphPredicate(name, value,
					PredicateComparison.NOT_EQUAL);
		}

		@Override
		public Predicate greaterThan(Object value) {
			return new GraphPredicate(name, value, PredicateComparison.GREATER);
		}

		@Override
		public Predicate lessThan(Object value) {
			return new GraphPredicate(name, value, PredicateComparison.LESS);
		}
	}
}
