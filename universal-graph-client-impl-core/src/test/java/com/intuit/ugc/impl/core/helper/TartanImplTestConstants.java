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

import com.intuit.ugc.api.Attribute;
import com.intuit.ugc.api.Relationship;

/**
 * 
 * @author nverma1
 *
 */
public class TartanImplTestConstants {
    public static final Attribute.Name ATTR_PARENT_KEY = Attribute.Name
            .valueOf("test.ugc.core.entity.ID");
    public static final Attribute.Name ATTR_PARENT_ID = Attribute.Name
            .valueOf("test.ugc.core.entity.ParentId");
    public static final Attribute.Name ATTR_CHILD_ID = Attribute.Name
            .valueOf("test.ugc.core.entity.ChildId");
    public static final Attribute.Name ATTR_CHILD_KEY = Attribute.Name
            .valueOf("test.ugc.core.entity.ID");
    public static final Relationship.Name ATTR_PARENT_CHILD_KEY = Relationship.Name
            .valueOf("test.ugc.core.parent.has.Child");
    public static final Attribute.Name ATTR_DUMMY_ATTRIBUTE_NAME = Attribute.Name
            .valueOf("test.ugc.core.attribute.Dummy");
    public static final Relationship.Name ATTR_DUMMY_RELATIONSHIP_NAME = Relationship.Name
            .valueOf("test.ugc.core.attribute.Dummy");
    public static final Relationship.Name RELATIONSHIP_ENTITYA_ENTITYB =
            Relationship.Name.valueOf("test.ugc.core.entitya.has.Entityb");
	public static final Attribute.Family ATTR_DUMMY_FAMILY = Attribute.Family
			.valueOf("test.ugc.core.attribute.family");

}
