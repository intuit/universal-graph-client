package com.intuit.ugc.api.helper;

import com.intuit.ugc.api.AccessToken;

public class MockAccessToken implements AccessToken {
	private Type type;

	@Override
	public String getToken() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type getType() {
		return this.type;
	}

	@Override
	public Long getExpires() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setType(Type type) {
		this.type = type;
	}

}
