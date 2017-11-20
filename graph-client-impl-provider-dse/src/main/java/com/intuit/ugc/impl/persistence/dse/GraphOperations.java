/*
 * Copyright (c) 2017.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    ajain17 & nverma1 - API , implementation and initial documentation
 */

package com.intuit.ugc.impl.persistence.dse;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.driver.dse.DseSession;
import com.datastax.driver.dse.graph.GraphResultSet;
import com.datastax.driver.dse.graph.SimpleGraphStatement;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.intuit.ugc.api.Attribute;
import com.intuit.ugc.api.Attribute.Name;
import com.intuit.ugc.impl.core.spi.RepositoryException;

/**
 * constants that represent partial or full DSE graph query statements, used to
 * parameterize a DSE Graph query Is used by
 * {@link com.intuit.ugc.impl.persistence.dse.DSEGraphVisitor}. Contains
 * specific graph query execution methods. Is extended by
 * {@link com.intuit.ugc.impl.persistence.dse.GraphEntityOperations} and
 * {@link com.intuit.ugc.impl.persistence.dse.GraphRelationshipOperations}
 * 
 * @author nverma1
 *
 */
public abstract class GraphOperations {
	/**
	 * Get all Vertex from DSE Graph
	 */
	public final static String GET_GRAPH_VERTEX = "g.V()";
	/**
	 * Add Vertex to DSE Graph
	 */
	public final static String ADD_ENTITY = "g.addV()";
	/**
	 * Add vertex to DSE Graph with label
	 */
	public final static String ADD_ENTITY_WITH_LABEL = "g.addV(label, '%s')";
	/**
	 * Get Vertex fro DSE Graph
	 */
	public final static String GET_ENTITY = "g.V(vertexId)";
	/**
	 * Get Vertex fro DSE Graph
	 */
	public final static String GET_ENTITY_PROPERTY = ".has('%s', '%s')";
	/**
	 * Format for Vertex properties
	 */
	public final static String ENTITY_PROPERTY_FORMAT = ".property('%s', '%s')";
	/**
	 * Select two vertices (and then use this as a query prefix to add/update/delete edge between these vertices
	 */
	public final static String SELECT_VERTICES = "def v1 = g.V().has('%s', '%s').next()\n"
			+ "def v2 = g.V().has('%s', '%s').next()\n";
	/**
	 * Command to add DSE Graph Edge
	 */
	public final static String ADD_RELATIONSHIP = SELECT_VERTICES + "v1.addEdge('%s', v2";
	/**
	 * DSE Graph Edge property format
	 */
	public final static String RELATIONSHIP_PROPERTY_FORMAT = ", '%s', '%s'";
	/**
	 * Get DSE Graph Relationship
	 */
	public final static String GET_RELATIONSHIP = "def v1 = g.V(id1)\n" + "v1.bothE(edgeRelation)";
	/**
	 * Get multiple values from DSE graph using within
	 */
	public static final String WITHIN_PREDICATE = "within('%s')";
	/**
	 * to unset multiple properties, this properties format can be used
	 */
	public static final String GRAPH_PROPERTIES = ".properties(%s)";
	/**
	 * query to delete an entity from the graph
	 */
	public static final String DELETE_ENTITY = "def v1 = g.V().has('%s', '%s').next()\n" + "v1.remove()";
	/**
	 * query prefix to update relationship / edge properties between two vertices in the graph
	 */
	public static final String UPDATE_EDGE = SELECT_VERTICES + "g.V(v1).bothE('%s').where(otherV().is(v2))";
    /**
     * Get out edge of all Vertex with a given label for the edge
     */
	public static final String DELETE_EDGE = SELECT_VERTICES + "g.V(v1).bothE().where(otherV().is(v2)).drop()";
    /**
     * Get out edge of all Vertex with a given label for the edge
     */
	public final static String GET_OUT_EDGE = ".outE('%s')";
	/**
	 * Get both edge of all Vertex with a given label for the edge
	 */
	public final static String GET_BOTH_EDGE = ".bothE('%s')";
	/**
	 * Get in edge of all Vertex with a given label for the edge
	 */
	public final static String GET_IN_EDGE = ".inE('%s')";
	/**
	 * represents the prefix for an entity id attribute
	 */
	public static final Attribute.Name ENTITY_ID = Attribute.Name.valueOf("entity.Id");
	/**
	 * {@link com.intuit.ugc.impl.persistence.dse.DSEConnectionManager} connection instance
	 */
	protected DSEConnectionManager connectionManager;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GraphOperations.class);

	/**
	 * Creates a SimpleGraphStatement out of the input string Runs it through a
	 * DSESession and returns the result
	 * 
	 * @param sb
	 * @return
	 */
	protected GraphResultSet executeGraphQuery(StringBuilder sb) {
		// create a SimpleGraphStatement out of the gremlin string created
		SimpleGraphStatement statement = connectionManager.getStatement(sb.toString());
		// obtain the DSE connection session and execute the query
		DseSession session = connectionManager.getDSEGraphSession().getSession();
		GraphResultSet graphResultSet = null;
		try {
			ListenableFuture<GraphResultSet> resultSet = session.executeGraphAsync(statement);
			GraphCallBack callBack = new GraphCallBack();
			Futures.addCallback(resultSet, callBack);
			if (resultSet != null) {
				graphResultSet = resultSet.get();
			} else {
				LOGGER.error("Failed to execute DSE Graph command " + statement.toString());
				return graphResultSet;
			}
		} catch (InterruptedException | ExecutionException e) {
			LOGGER.error("Failed to execute DSE Graph command " + statement.toString(), e);
			throw new RepositoryException(e.getMessage(), e);
		}
		return graphResultSet;
	}
	
	

	protected void appendQueryStrWithPropertiesToRemove(List<Name> unsetValueOperations, StringBuilder sb) {
		if (unsetValueOperations.size() > 0) {
			sb.append(".properties(");
			for (int i = 0; i < unsetValueOperations.size(); i++) {
				sb.append("'");
				sb.append(unsetValueOperations.get(i).toString());
				sb.append("'");
				if (!(i == unsetValueOperations.size() - 1)) {
					sb.append(",");
				}
			}
			sb.append(").drop()");
		}
	}

	protected void appendQueryStrWithPropertiesToUpdate(Map<Name, Object> setValueOperations, StringBuilder sb) {
		String setProperties = setValueOperations.entrySet().stream()
				.map(e -> String.format(GraphOperations.ENTITY_PROPERTY_FORMAT, e.getKey(), e.getValue()))
				.collect(Collectors.joining());
		sb.append(setProperties);
	}
}
