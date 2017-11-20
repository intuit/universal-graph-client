package com.intuit.ugc.api;

import com.intuit.ugc.api.AbstractValueSpecifier;
import com.intuit.ugc.api.Attribute;
import com.intuit.ugc.api.ValueSpecifier;

public class ValueBuilder {
    public AbstractValueSpecifier<ValueBuilder> withAttribute(Attribute.Name name) {
        return new AbstractValueSpecifier<ValueBuilder>(this,name) {

			@Override
			public void setValue(Object value) {
				//Mock implementation
			}
        	
        };
    }

}
