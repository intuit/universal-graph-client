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

import com.intuit.ugc.api.Attribute;

/**
 * 
 * @author nverma1
 *
 */
public class DSEGraphTestConstants {
	public static final Attribute.Name ATTR_DUMMY_ENTITY_KEY = Attribute.Name.valueOf("dse.dummy.entity.ID");
	public static final Attribute.Name ATTR_COMPANY_ID = Attribute.Name.valueOf("company.Id");
	public static final Attribute.Name ATTR_CUSTOMER_ID = Attribute.Name.valueOf("customer.Id");
	public static final Attribute.Name ATTR_COMPANY_NAME = Attribute.Name.valueOf("person.DisplayName");
	public static final Attribute.Name ATTR_COMPANY_CREATE_DATE = Attribute.Name.valueOf("company.Createdate");
	public static final Attribute.Name ATTR_PERSON_EMAIL = Attribute.Name.valueOf("person.Email");
	public static final Attribute.Name ATTR_PERSON_FULL_NAME = Attribute.Name.valueOf("person.FullName");
	public static final Attribute.Name ATTR_CUSTOMER_SINCE = Attribute.Name.valueOf("customer.CustomerSince");
}
