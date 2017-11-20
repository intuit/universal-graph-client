package com.intuit.ugc.api.helper;

import com.intuit.ugc.api.AccessControlException;
import com.intuit.ugc.api.Attribute;
import com.intuit.ugc.api.Attribute.Name;
import com.intuit.ugc.api.Entity;

public class MockEntity implements Entity {
	private Entity.ID id;
	
	@Override
	public ID getID() {
		// TODO Auto-generated method stub
		return this.id;
	}

	@Override
	public Attribute getAttribute(Name name) throws AccessControlException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setID(Entity.ID id){
		this.id = id;
	}

}
