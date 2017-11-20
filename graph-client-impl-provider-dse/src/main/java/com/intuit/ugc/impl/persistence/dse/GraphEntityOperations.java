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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.datastax.driver.dse.graph.GraphNode;
import com.datastax.driver.dse.graph.GraphResultSet;
import com.datastax.driver.dse.graph.Vertex;
import com.datastax.driver.dse.graph.VertexProperty;
import com.intuit.ugc.api.Attribute;
import com.intuit.ugc.api.Attribute.Family;
import com.intuit.ugc.api.Attribute.Name;
import com.intuit.ugc.api.Entity;
import com.intuit.ugc.api.Entity.ID;
import com.intuit.ugc.api.NewEntity;
import com.intuit.ugc.api.Predicate;
import com.intuit.ugc.api.Queries.GraphTraversal.Direction;
import com.intuit.ugc.api.Relationship;
import com.intuit.ugc.impl.core.GraphAttribute;
import com.intuit.ugc.impl.core.GraphAttributeOperations;
import com.intuit.ugc.impl.core.GraphEntity;
import com.intuit.ugc.impl.core.GraphMutationResult;
import com.intuit.ugc.impl.core.VisitOperationResult;
import com.intuit.ugc.impl.core.queryplan.operations.GetEntityByProperty;
import com.intuit.ugc.impl.core.spi.QueryResult;
import com.intuit.ugc.impl.core.spi.QueryResult.CurrentOperationType;
import com.intuit.ugc.impl.core.spi.QueryResultImpl;

/**
 * Helper class that contains DSE Graph specific operations over graph Entities
 * (Vertices). Is used by
 * {@link com.intuit.ugc.impl.persistence.dse.DSEGraphVisitor}
 * 
 * @author nverma1
 *
 */
public class GraphEntityOperations extends GraphOperations {
	private GraphRelationshipOperations relationshipOperations;

	public GraphEntityOperations(DSEConnectionManager connectionManager,
			GraphRelationshipOperations relationshipOperations) {
		this.connectionManager = connectionManager;
		this.relationshipOperations = relationshipOperations;
	}

	public GraphMutationResult createEntity(NewEntity newEntity) {
		String entityId = newEntity.getEntityID().toString();
		Map<Attribute.Name, Object> attributes = newEntity.getAttributes();
		// convert the entity attributes to a valid gremlin string to save in
		// DSE
		String entityProperty = attributes.entrySet().stream()
				.map(e -> String.format(GraphOperations.ENTITY_PROPERTY_FORMAT, e.getKey(), e.getValue()))
				.collect(Collectors.joining());

		StringBuilder sb = new StringBuilder();

		Attribute.Name labelKey = Attribute.Name.valueOf("dse.vertex.Label");
		if (attributes.containsKey(labelKey)) {
			sb.append(String.format(GraphOperations.ADD_ENTITY_WITH_LABEL, (String) attributes.get(labelKey)));
		} else {
			sb.append(GraphOperations.ADD_ENTITY);
		}

		sb.append(String.format(GraphOperations.ENTITY_PROPERTY_FORMAT, ENTITY_ID.toString(), entityId));
		sb.append(entityProperty);

		// call a method to execute the query from this string and get the
		// result
		GraphResultSet result = executeGraphQuery(sb);
		
		return extractGraphQueryResult(result);
	}

	public VisitOperationResult getEntitiesByID(List<ID> entityIDs) {
		StringBuilder sb = new StringBuilder();
		sb.append(GraphOperations.GET_GRAPH_VERTEX);
		sb.append(".has('");
		sb.append(ENTITY_ID.toString());
		sb.append("',");
		sb.append("within(");
		for (int i = 0; i < entityIDs.size(); i++) {
			Entity.ID entityID = entityIDs.get(i);
			sb.append("'");
			sb.append(entityID.toString());
			sb.append("'");
			if (!(i == entityIDs.size() - 1)) {
				sb.append(",");
			}
		}
		sb.append("))");

		GraphResultSet result = executeGraphQuery(sb);
		return createQueryResult(result);
	}

	public GraphResultSet getEntityById(Entity.ID entityId) {
		StringBuilder sb = new StringBuilder();
		sb.append(GraphOperations.GET_GRAPH_VERTEX);
		sb.append(String.format(GraphOperations.GET_ENTITY_PROPERTY, ENTITY_ID.toString(), entityId));

		// call a method to execute the query from this string and get the
		// result
		GraphResultSet result = executeGraphQuery(sb);
		return result;
	}

	public VisitOperationResult selectEntities(com.intuit.ugc.api.Relationship.Name relationName,
			Direction direction, Predicate predicate, QueryResult<Entity, Relationship> context) {
		QueryResult<Entity, Relationship> relationshipContext = relationshipOperations.selectRelationships(relationName,
				direction, predicate, context);

		List<ID> entityIDs = relationshipContext.getRelationshipResponse().stream().map(e -> (e.getTargetID()))
				.collect(Collectors.toList());
		return getEntitiesByID(entityIDs);
	}

	public GraphResultSet getEntityByProperty(GetEntityByProperty operation) {
		StringBuilder sb = new StringBuilder();
		sb.append(GraphOperations.GET_GRAPH_VERTEX);
		sb.append(String.format(GraphOperations.GET_ENTITY_PROPERTY, operation.getName(), operation.getValue()));

		// call a method to execute the query from this string and get the
		// result
		GraphResultSet result = executeGraphQuery(sb);
		return result;
	}
	

	public void deleteEntity(Entity entity) {
		//query formation for the update operation
		StringBuilder sb = new StringBuilder();
		sb.append(String.format(GraphOperations.DELETE_ENTITY, ENTITY_ID.toString(), entity.getID().toString()));		
		executeGraphQuery(sb);
	}

	private GraphMutationResult extractGraphQueryResult(GraphResultSet graphQueryResult) {
		List<Entity> entityListFromGraphResult = getEntityListFromGraphResult(graphQueryResult);
		List<String> entityKeys = new ArrayList<String>(entityListFromGraphResult.size());
		for(Entity resultEntity : entityListFromGraphResult){
			entityKeys.add(resultEntity.getID().getRawID());
		}
		GraphMutationResult queryResult = new GraphMutationResult(entityKeys);
		return queryResult;
	}
	

    public VisitOperationResult updateEntity(Entity entity, GraphAttributeOperations operations) {
    	Map<Name, Object> setValueOperations = operations.getSetValueOperations();
    	List<Name> unsetValueOperations = operations.getUnsetValueOperations();
    	
    	//query formation for the update operation
		StringBuilder sb = new StringBuilder();
		//step1: fetch the entity by entity id
		sb.append(GraphOperations.GET_GRAPH_VERTEX);
		sb.append(String.format(GraphOperations.GET_ENTITY_PROPERTY, ENTITY_ID.toString(), entity.getID().toString()));
		//step2: add all the properties for update
		appendQueryStrWithPropertiesToUpdate(setValueOperations, sb);
		//step3: add all the properties for drop
		appendQueryStrWithPropertiesToRemove(unsetValueOperations, sb);
		
		GraphResultSet graphQueryResult = executeGraphQuery(sb);
		return extractGraphQueryResult(graphQueryResult);
	}

	/**
	 * Get a GraphResultSet obtained as a result of executing a DSE query. From
	 * this, parse and obtain the list of one or more entities.
	 * 
	 * @param result
	 *            a list of Entities
	 * @return
	 */
	private List<Entity> getEntityListFromGraphResult(GraphResultSet result) {
		if (result == null) {
			return Collections.emptyList();
		}

		// obtain all graph nodes from result. This list should have one or more
		// nodes depending on the query.
		// The list might be empty for some queries like a create query which
		// doesn't return anything
		List<GraphNode> graphNodes = result.all();
		if (null == graphNodes || graphNodes.isEmpty()) {
			return Collections.emptyList();
		}

		List<Entity> entityList = new ArrayList<Entity>();
		for (GraphNode node : graphNodes) {
			// de-serialize the node vertex and get the graphentity constructed
			// out of it
			Vertex vertex = node.asVertex();
			GraphEntity.Builder builder = new GraphEntity.Builder();

			// create entity ID out of the ID property of the vertex
			String entityId = vertex.getProperty(ENTITY_ID.toString()).getValue().asString();
			ID entityID = ID.valueOf(entityId);
			builder.setID(entityID);

			// iterate over the properties list and create a graphattribute out
			// of each property
			Iterator<VertexProperty> properties = vertex.getProperties();
			while (properties.hasNext()) {
				VertexProperty nextProperty = properties.next();
				Attribute.Name name = Attribute.Name.valueOf(nextProperty.getName());
				Attribute attribute = new GraphAttribute(name, nextProperty.getValue(),
						new DSEMetadata(nextProperty.getClass()));
				builder.addAttribute(attribute);
			}
			entityList.add(builder.build());
		}
		return entityList;
	}

	public QueryResultImpl createQueryResult(GraphResultSet result) {
		List<Entity> entityListFromGraphResult = getEntityListFromGraphResult(result);
		QueryResultImpl queryResultImpl = new QueryResultImpl(CurrentOperationType.ENTITY);
		queryResultImpl.setEntityOpResponse(entityListFromGraphResult);
		return queryResultImpl;
	}
	

    public List<Entity> filterEntity(List<Entity> entityResponse,
            List<Name> nameList, List<Family> namespaceList) {
        if (nameList.size() > 0 || namespaceList.size() > 0) {
            return entityResponse.stream().map(e -> {
                GraphEntity.Builder builder = new GraphEntity.Builder();
                builder.setID(e.getID());
                projectEntity(e, builder, nameList, namespaceList);
                return builder.build();
            }).collect(Collectors.toList());
        }
        return entityResponse;
    }
    

    private void projectEntity(Entity e, GraphEntity.Builder builder,
            List<Name> nameList, List<Family> namespaceList) {
        for (Family family : namespaceList) {
            throw new UnsupportedOperationException(
                    "Projections by attribute family are not current supported");
        }
        for (Name name : nameList) {
            builder.addAttribute(e.getAttribute(name));
        }
    }
}
