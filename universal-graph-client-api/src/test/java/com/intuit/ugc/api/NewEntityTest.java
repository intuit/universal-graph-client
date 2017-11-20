package com.intuit.ugc.api;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import org.testng.annotations.Test;

import com.intuit.ugc.api.Attribute.Name;
import com.intuit.ugc.api.Entity.ID;

public class NewEntityTest {

	@Test
	public void testInit(){
		ID entityID = Entity.ID.valueOf("test-entity-id");
		Name attribute = Attribute.Name.valueOf("test-attribute");
		NewEntity newEntity = NewEntity.newInstance(entityID)
				.withAttribute(attribute).value(true).build();
		assertTrue(newEntity instanceof NewEntity);
		assertEquals(newEntity.getAttributes().get(attribute), true);
		assertEquals(newEntity.getEntityID(), entityID);
	}
	
	@Test
	public void testInitWithNullAttributeThrowsException(){
		ID entityID = Entity.ID.valueOf("test-entity-id");
		try{
			NewEntity newEntity = NewEntity.newInstance(entityID)
					.withAttribute(null).value(true).build();
			fail("Should throw an exception");
		}catch(Exception ex){
			assertTrue(ex instanceof IllegalArgumentException);
		}
	}
	
	@Test
	public void testEntityID(){
		String idStr = "test-entity-id";
		Entity.ID entityId = Entity.ID.valueOf(idStr);
		assertEquals(entityId.getRawID(), idStr);
		assertNotNull(entityId.hashCode());
		assertEquals(entityId.toString(), idStr);
	}
	
	@Test
	public void testEntityIDEquality(){
		String idStr = "test-entity-id";
		Entity.ID entityId1 = Entity.ID.valueOf(idStr+"1");
		Entity.ID entityId2 = Entity.ID.valueOf(idStr+"2");
		Entity.ID entityId3 = Entity.ID.valueOf(idStr+"1");
		assertTrue(entityId1.equals(entityId3));
		assertTrue(entityId1.equals(entityId1));
		assertFalse(entityId1.equals(entityId2));
		assertFalse(entityId1.equals(idStr));
		assertFalse(entityId1.equals(null));
	}
}
