package com.intuit.ugc.api;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.intuit.ugc.api.AccessToken.Type;
import com.intuit.ugc.api.helper.MockAccessToken;

public class AccessTokenTest {
	@Test
	public void testAccessTokenType() {
		MockAccessToken mockToken = new MockAccessToken();
		mockToken.setType(Type.DEFAULT);
		assertEquals(Type.DEFAULT, mockToken.getType());
	}
}
