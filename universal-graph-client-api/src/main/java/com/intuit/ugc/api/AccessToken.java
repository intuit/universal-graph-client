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

/**
 * The access token required to access the service. This is modeled off of
 * a typical OAuth2-style approach for illustration purposes.
 *
 * @author ajain17
 */
public interface AccessToken {

    public String getToken();
    
    public AccessToken.Type getType();

    public Long getExpires();
        
        
    /**
     * The type of the access token
     * 
     */
    public enum Type {
        DEFAULT;
    }
}