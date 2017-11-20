/*
 * Copyright (c) 2017.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    tfast - initial API and implementation and/or initial documentation
 *    ajain17 & nverma1 - API implementation, enhancements and extension
 */

package com.intuit.ugc.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.Instant;
import java.util.UUID;

import org.junit.Test;

import com.intuit.ugc.api.Attribute;
import com.intuit.ugc.api.Attribute.Family;

/**
 *
 * 
 */
public class AttributeTest {
    
    public AttributeTest() {
    }

    @Test
    public void testGetMetadata() {
    }

    @Test
    public void testGetName() {
    }

    @Test
    public void testGetValue() {
    }

    @Test
    public void testGetValues() {
    }

    @Test
    public void testGetInteger() {
    }

    @Test
    public void testGetDouble() {
    }

    @Test
    public void testGetBoolean() {
    }

    @Test
    public void testGetString() {
    }

    @Test
    public void testGetInstant() {
    }

    @Test
    public void testGetUUID() {
    }

    public class AttributeImpl implements Attribute {

        public Metadata getMetadata() {
            return null;
        }

        public Name getName() {
            return null;
        }

        public <V> V getValue(Class<V> valueType) {
            return null;
        }

        public Integer getInteger() {
            return null;
        }

        public Double getDouble() {
            return null;
        }

        public Boolean getBoolean() {
            return null;
        }

        public String getString() {
            return "";
        }

        public Instant getInstant() {
            return null;
        }

        public UUID getUUID() {
            return null;
        }
    }
    
    @Test
    public void testAttributeNameParsing() {
        Attribute.Name attr = null;

        String name = "foo.bar.Baz";
        attr = Attribute.Name.valueOf(name);
        assertEquals(attr.getName(),"foo.bar.Baz");
        
        attr = Attribute.Name.valueOf("a.b.C");
        assertEquals(attr.getName(),"a.b.C");

        attr = Attribute.Name.valueOf("a.B");
        assertEquals(attr.getName(),"a.B");

        attr = Attribute.Name.valueOf("B");
        assertEquals(attr.getName(),"B");
    }
    
    @Test
    public void testAttributeNamespaceParsing() {
        Attribute.Family ns = null;
        
        ns = Attribute.Family.valueOf("attribute-family:foo.bar");
        assertNotNull(ns.getName());
        
        ns = Attribute.Family.valueOf("attribute-family:foo.bar");
        assertNotNull(ns.getName());
        
        ns = Attribute.Family.valueOf("a.b.c");
        assertNotNull(ns.getName());
        
        ns = Attribute.Family.valueOf("a.b");
        assertNotNull(ns.getName());

        ns = Attribute.Family.valueOf("a");
        assertNotNull(ns.getName());
        
        ns = Attribute.Family.valueOf("foo-bar");
        assertNotNull(ns.getName());
        
        ns = Attribute.Family.valueOf("foo-bar.baz");
        assertNotNull(ns.getName());
    }
    
    @Test
    public void testAttributeFamily() {
    	Family attrFamily = Attribute.Family.valueOf("test-attr-family");
    	assertNotNull(attrFamily);
    	assertEquals(attrFamily.getName(), "test-attr-family");
    }
}
