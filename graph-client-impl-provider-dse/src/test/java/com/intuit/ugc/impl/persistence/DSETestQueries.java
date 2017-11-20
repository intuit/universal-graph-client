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

package com.intuit.ugc.impl.persistence;

import java.util.Map;
import java.util.UUID;

import com.intuit.ugc.api.Attribute;
import com.intuit.ugc.api.Entity;
import com.intuit.ugc.api.NewEntity;
import com.intuit.ugc.api.NewRelationship;
import com.intuit.ugc.api.Relationship;
import com.intuit.ugc.api.NewEntity.Builder;

/**
 * 
 * @author nverma1
 *
 */
public class DSETestQueries {

	public static NewEntity createNewEntity(String entityName, Map<String, String>attributes) {
		Attribute.Name entityKey = Attribute.Name.valueOf(entityName);
		Entity.ID newEntityID = Entity.ID
				.valueOf(entityKey.getName() + "/" + UUID.randomUUID());
		
		Builder entityBuilder = NewEntity.newInstance(newEntityID);
		if(attributes != null && !attributes.isEmpty()){
			for(String attributeKey : attributes.keySet()){
				Attribute.Name attrName = Attribute.Name.valueOf(attributeKey);
				entityBuilder = entityBuilder.withAttribute(attrName).value(attributes.get(attributeKey));
			}
		}
       
        NewEntity newEntity = entityBuilder.build();
        return newEntity;
	}

	public static NewRelationship createNewRelationship(String relationshipName,
			Map<String, String> edgeAttributesMap, Entity.ID sourceEntityId, Entity.ID targetEntityId) {
		Relationship.Name relationshipLabel = Relationship.Name.valueOf(relationshipName);
		com.intuit.ugc.api.NewRelationship.Builder builder = NewRelationship.between(sourceEntityId, targetEntityId);
		for(String attrStr : edgeAttributesMap.keySet()){
			Attribute.Name attrName = Attribute.Name.valueOf(attrStr);
			builder = builder.withAttribute(attrName).value(edgeAttributesMap.get(attrStr));
		}
		NewRelationship newRelationship = builder.withLabel(relationshipLabel).build();
		
		return newRelationship;
	}

}
