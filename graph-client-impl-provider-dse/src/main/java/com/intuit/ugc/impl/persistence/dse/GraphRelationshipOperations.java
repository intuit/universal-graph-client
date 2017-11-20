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

import com.datastax.driver.dse.graph.Edge;
import com.datastax.driver.dse.graph.GraphNode;
import com.datastax.driver.dse.graph.GraphResultSet;
import com.datastax.driver.dse.graph.Property;
import com.datastax.driver.dse.graph.Vertex;
import com.intuit.ugc.api.Attribute;
import com.intuit.ugc.api.Entity;
import com.intuit.ugc.api.NewRelationship;
import com.intuit.ugc.api.Predicate;
import com.intuit.ugc.api.Relationship;
import com.intuit.ugc.api.Attribute.Family;
import com.intuit.ugc.api.Attribute.Name;
import com.intuit.ugc.api.Entity.ID;
import com.intuit.ugc.api.Queries.GraphTraversal.Direction;
import com.intuit.ugc.impl.core.GraphAttribute;
import com.intuit.ugc.impl.core.GraphAttributeOperations;
import com.intuit.ugc.impl.core.GraphMutationResult;
import com.intuit.ugc.impl.core.GraphRelationship;
import com.intuit.ugc.impl.core.VisitOperationResult;
import com.intuit.ugc.impl.core.spi.QueryResult;
import com.intuit.ugc.impl.core.spi.QueryResultImpl;
import com.intuit.ugc.impl.core.spi.QueryResult.CurrentOperationType;

/**
 * Helper class that contains DSE Graph specific operations over graph Relationships (Edges).
 * Is used by {@link com.intuit.ugc.impl.persistence.dse.DSEGraphVisitor}
 * 
 * @author nverma1
 *
 */
public class GraphRelationshipOperations extends GraphOperations{

    public GraphRelationshipOperations(DSEConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }
    
	public GraphResultSet createRelationship(NewRelationship newRelationship) {
		ID sourceID = newRelationship.getSourceID();
		ID targetID = newRelationship.getTargetID();
		StringBuilder sb = new StringBuilder();
		
		sb.append(String.format(GraphOperations.ADD_RELATIONSHIP, ENTITY_ID.toString(), sourceID.toString(), ENTITY_ID.toString(), targetID.toString(), newRelationship.getName().toString()));
		Map<Attribute.Name, Object> attributes = newRelationship.getAttributes();
		//convert the entity attributes to a valid gremlin string to save in DSE
		String relationshipProperty = attributes.entrySet().stream()
				.map(e -> String.format(GraphOperations.RELATIONSHIP_PROPERTY_FORMAT, e.getKey(), e.getValue()))
				.collect(Collectors.joining());
		sb.append(relationshipProperty);
		sb.append(")");
		
		//since there is no ID for relationship, nothing is returned.
		GraphResultSet result = executeGraphQuery(sb);
		return result;
	}

	public QueryResult<Entity, Relationship> selectRelationships(com.intuit.ugc.api.Relationship.Name relationName,
			Direction direction, Predicate predicate, QueryResult<Entity, Relationship> context) {
		StringBuilder sb = new StringBuilder();
		sb.append(GraphOperations.GET_GRAPH_VERTEX);
		switch(direction){
		case IN:
			sb.append(String.format(GraphOperations.GET_IN_EDGE, relationName));
		case OUT:
			sb.append(String.format(GraphOperations.GET_OUT_EDGE, relationName));
		case IN_OUT:
			sb.append(String.format(GraphOperations.GET_BOTH_EDGE, relationName));
		}
		GraphResultSet graphQueryResult = executeGraphQuery(sb);
		QueryResult<Entity, Relationship> queryResultImpl = new QueryResultImpl(CurrentOperationType.RELATIONHIP);
		queryResultImpl.setRelationshipOpResponse(getRelationshipListFromGraphResult(graphQueryResult));
		return queryResultImpl;
	}
	
    public VisitOperationResult updateRelationship(Relationship relationship, GraphAttributeOperations operations) {
    	Map<Name, Object> setValueOperations = operations.getSetValueOperations();
    	List<Name> unsetValueOperations = operations.getUnsetValueOperations();
    	com.intuit.ugc.api.Relationship.Name name = relationship.getName();
    	ID sourceID = relationship.getSourceID();
    	ID targetID = relationship.getTargetID();
    	
    	StringBuilder sb = new StringBuilder();
    	//get the source vertex (out vertex)
		sb.append(String.format(GraphOperations.UPDATE_EDGE, ENTITY_ID.toString(),
				sourceID.toString(), ENTITY_ID.toString(), targetID.toString(), name.toString()));
    	//now add the properties to this edge
		appendQueryStrWithPropertiesToUpdate(setValueOperations, sb);
		appendQueryStrWithPropertiesToRemove(unsetValueOperations, sb);
		
		executeGraphQuery(sb);
		return new GraphMutationResult(Collections.emptyList());		
	}

	public void deleteRelationship(Relationship relationship) {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format(GraphOperations.DELETE_EDGE, ENTITY_ID.toString(),
				relationship.getSourceID().toString(), ENTITY_ID.toString(), relationship.getTargetID().toString()));
		executeGraphQuery(sb);
	}
	
    private List<Relationship> getRelationshipListFromGraphResult(GraphResultSet result){
    	if(result == null){
    		return Collections.emptyList();
    	}

    	//obtain all graph nodes from result. This list should have one or more nodes depending on the query.
    	//The list might be empty for some queries like a create query which doesn't return anything
    	List<GraphNode> graphNodes = result.all();
    	if(null == graphNodes || graphNodes.isEmpty()){
    		return Collections.emptyList();
    	}

    	List<Relationship> relationshipList = new ArrayList<Relationship>();
		for(GraphNode node : graphNodes){
			Edge edge = node.asEdge();
			GraphRelationship.Builder builder = new GraphRelationship.Builder();
			
			String relationshipName = edge.getLabel();
			builder.setName(Relationship.Name.valueOf(relationshipName));
			Iterator<? extends Property> properties = edge.getProperties();
			while(properties.hasNext()){
				Property nextProperty = properties.next();
				Attribute.Name name = Attribute.Name.valueOf(nextProperty.getName());
				Attribute attribute = new GraphAttribute(name, nextProperty.getValue(), new DSEMetadata(nextProperty.getClass()));
				builder.addAttribute(attribute);
			}
			Vertex sourceVertex = edge.getInV().asVertex();
			ID sourceEntityId = ID.valueOf(sourceVertex.getProperty(ENTITY_ID.toString()).getValue().toString());
			builder.setSourceID(sourceEntityId);
			
			Vertex targetVertex = edge.getOutV().asVertex();
			ID targetEntityId = ID.valueOf(targetVertex.getProperty(ENTITY_ID.toString()).getValue().asString());
			builder.setTargetID(targetEntityId);
			
			GraphRelationship relationship = builder.build();
			relationshipList.add(relationship);
		}
    	return relationshipList;
    }
    
    public List<Relationship> filterRelationship(
            List<Relationship> relationshipResponse, List<Name> nameList,
            List<Family> namespaceList) {
        if (nameList.size() > 0 || namespaceList.size() > 0) {
            return relationshipResponse.stream().map(r -> {
                GraphRelationship.Builder builder = new GraphRelationship.Builder();
                builder.setName(r.getName());
                builder.setSourceID(r.getSourceID());
                builder.setTargetID(r.getTargetID());
                projectRelationship(r, builder, nameList, namespaceList);
                return builder.build();
            }).collect(Collectors.toList());
        }
        return relationshipResponse;
    }
    
    private void projectRelationship(Relationship relationship,
            GraphRelationship.Builder builder, List<Name> nameList,
            List<Family> namespaceList) {

        for (Family family : namespaceList) {
            throw new UnsupportedOperationException(
                    "Projections by attribute family are not current supported");
        }
        for (Name name : nameList) {
            builder.addAttribute(relationship.getAttribute(name));
        }
    }

}
