package com.intuit.ugc.api;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import org.testng.annotations.Test;

import com.intuit.ugc.api.helper.MockEntity;

public class NewRelationshipTest {

	@Test
	public void testInit(){
		Entity.ID sourceId = Entity.ID.valueOf("test-source-id");
		Entity.ID targetId = Entity.ID.valueOf("test-target-id");
		Relationship.Name relationshipName = Relationship.Name.valueOf("test-relationship");
		Attribute.Name attributeName = Attribute.Name.valueOf("test-attribute-name");
		NewRelationship relationship = NewRelationship.between(sourceId, targetId).withLabel(relationshipName)
				.withAttribute(attributeName).value(true).build();
		assertNotNull(relationship);
		assertEquals(relationship.getSourceID(), sourceId);
		assertEquals(relationship.getTargetID(), targetId);
		assertEquals(relationship.getName(), relationshipName);
		assertEquals(relationship.getAttributes().get(attributeName), true);
	}
	
	@Test
	public void testInitWithNullInstanceName() {
		Entity.ID sourceId = Entity.ID.valueOf("test-source-id");
		Entity.ID targetId = Entity.ID.valueOf("test-target-id");
		Attribute.Name attributeName = Attribute.Name.valueOf("test-attribute-name");
		try {
			NewRelationship relationship = NewRelationship.between(sourceId, targetId).withAttribute(attributeName)
					.value(true).build();
			fail("Should have thrown an exception");
		} catch (Exception e) {
			assertTrue(e instanceof IllegalArgumentException);
		}
	}
	
	@Test
	public void testInitWithNullSourceId(){
		Entity.ID targetId = Entity.ID.valueOf("test-target-id");
		Relationship.Name relationshipName = Relationship.Name.valueOf("test-relationship");
		Attribute.Name attributeName = Attribute.Name.valueOf("test-attribute-name");
		try {
			NewRelationship relationship = NewRelationship.between(null, targetId).withLabel(relationshipName).withAttribute(attributeName)
					.value(true).build();
			fail("Should have thrown an exception");
		} catch (Exception e) {
			assertTrue(e instanceof IllegalArgumentException);
		}
	}
	
	@Test
	public void testInitWithNullTargetId(){
		Entity.ID sourceId = Entity.ID.valueOf("test-source-id");
		Relationship.Name relationshipName = Relationship.Name.valueOf("test-relationship");
		Attribute.Name attributeName = Attribute.Name.valueOf("test-attribute-name");
		try {
			NewRelationship relationship = NewRelationship.between(sourceId, null).withLabel(relationshipName).withAttribute(attributeName)
					.value(true).build();
			fail("Should have thrown an exception");
		} catch (Exception e) {
			assertTrue(e instanceof IllegalArgumentException);
		}
	}
	
	@Test
	public void testInitWithNullAttribute(){
		Entity.ID sourceId = Entity.ID.valueOf("test-source-id");
		Entity.ID targetId = Entity.ID.valueOf("test-target-id");
		Relationship.Name relationshipName = Relationship.Name.valueOf("test-relationship");
		try {
			NewRelationship relationship = NewRelationship.between(sourceId, targetId).withLabel(relationshipName).withAttribute(null)
					.value(true).build();
			fail("Should have thrown an exception");
		} catch (Exception e) {
			assertTrue(e instanceof IllegalArgumentException);
		}
	}
	
	@Test
	public void testInitWithSourceAndTargetEntity() {
		Entity.ID sourceId = Entity.ID.valueOf("test-source-id");
		Entity.ID targetId = Entity.ID.valueOf("test-target-id");
		MockEntity sourceEntity = new MockEntity();
		sourceEntity.setID(sourceId);
		MockEntity targetEntity = new MockEntity();
		targetEntity.setID(targetId);
		Relationship.Name relationshipName = Relationship.Name.valueOf("test-relationship");
		Attribute.Name attributeName = Attribute.Name.valueOf("test-attribute-name");
		NewRelationship relationship = NewRelationship.between(sourceEntity, targetEntity).withLabel(relationshipName)
				.withAttribute(attributeName).value(true).build();
		assertNotNull(relationship);
		assertEquals(relationship.getSourceID(), sourceId);
		assertEquals(relationship.getTargetID(), targetId);
		assertEquals(relationship.getName(), relationshipName);
		assertEquals(relationship.getAttributes().get(attributeName), true);
	}
}
