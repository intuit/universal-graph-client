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

package com.intuit.ugc.api.mogwai;

import java.util.List;

import org.junit.Test;

import com.intuit.ugc.api.Entity;
import com.intuit.ugc.api.OperationResult;
import com.intuit.ugc.api.Persistence;
import com.intuit.ugc.api.PersistenceImpl;
import com.intuit.ugc.api.Query;
import com.intuit.ugc.api.mogwai.Gizmo;

/**
 *
 * 
 */
public class GizmoTest {

    public GizmoTest() {
    }

    @Test
    public void testGetEntity() {
        
        Persistence.Configuration configuration = null; // ...
        Persistence.Factory factory = new PersistenceFactoryImpl();
        Persistence persistence = factory.newInstance(configuration);

        Gizmo gizmo = Gizmo.newInstance(persistence);
        
        OperationResult<Query.Result> opResult =
            gizmo.g()
                .v("test.Foo/00000000-0000-0000-0000-000000000001")
                .select("test.One","test.Two")
                .execute();

        Query.Result result = opResult.getResult();
        
        List<Entity> entities = result.getEntities();
    }
    
    @Test
    public void testIdiomaticQuery() {
        
        Persistence.Configuration configuration = null; // ...
        Persistence.Factory factory = new PersistenceFactoryImpl();
        Persistence persistence = factory.newInstance(configuration);

        Gizmo gizmo = Gizmo.newInstance(persistence);
        Gizmo.Predicate _ = gizmo._();

        OperationResult<Query.Result> opResult = 
            gizmo.g()
                // Pick a root entity
                .v("test.Foo/00000000-0000-0000-0000-000000000001")
                // Select all entities connected via REL_TEST_CHILD
                .out("test.Child")
                // Filter the selected set of entities
                .filter(_.and(
                    _.eq("test.One", "5"),
                    _.eq("test.Two", "red")))
                .select("test.Comment")
                .execute();

        Query.Result result = opResult.getResult();

        List<Entity> entities = result.getEntities();
    }
    
    
    public class PersistenceFactoryImpl implements Persistence.Factory {
        @Override
        public Persistence newInstance(Persistence.Configuration config) {
            return new PersistenceImpl();
        }
    }
}
