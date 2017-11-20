package com.intuit.ugc.api;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.intuit.ugc.api.Queries.GraphTraversal;

public class GraphTraversalTest {
	@Test
	public void testGraphTraversalDirection(){
		GraphTraversal.Direction direction = GraphTraversal.Direction.IN;
		assertEquals(direction, GraphTraversal.Direction.IN);
		
	}
}
