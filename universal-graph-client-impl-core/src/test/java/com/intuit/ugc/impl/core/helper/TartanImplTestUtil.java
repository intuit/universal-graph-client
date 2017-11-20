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

package com.intuit.ugc.impl.core.helper;

import java.util.UUID;

import com.intuit.ugc.api.Entity;
import com.intuit.ugc.api.NewEntity;
import com.intuit.ugc.api.Relationship;
import com.intuit.ugc.api.Attribute.Name;
import com.intuit.ugc.impl.core.GraphAttribute;
import com.intuit.ugc.impl.core.GraphRelationship;

/**
 * 
 * @author nverma1
 *
 */
public class TartanImplTestUtil {
	public NewEntity createNewEntity(Name entityKey) {
		Entity.ID newEntityID = Entity.ID.valueOf("urn:entity:" + entityKey.getName()
				+ "." + entityKey.getName() + "/" + UUID.randomUUID());

		NewEntity newEntity = NewEntity.newInstance(newEntityID).withAttribute(TartanImplTestConstants.ATTR_PARENT_ID)
				.value("RandomTestRealm" + UUID.randomUUID().toString()).build();
		return newEntity;
	}
	
	public GraphRelationship buildGraphRelationship(Relationship.Name name, Entity.ID sourceEntityID,
			Entity.ID targetEntityID, GraphAttribute graphAttribute) {
		GraphRelationship.Builder builder = new GraphRelationship.Builder();
		builder.setName(name);
		builder.setSourceID(sourceEntityID);
		builder.setTargetID(targetEntityID);
		builder.addAttribute(graphAttribute);
		return builder.build();
	}
}
