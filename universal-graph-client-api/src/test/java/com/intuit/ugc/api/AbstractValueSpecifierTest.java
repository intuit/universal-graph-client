package com.intuit.ugc.api;

import static org.testng.Assert.assertNotNull;

import java.time.Instant;

import org.testng.annotations.Test;

import com.toddfast.util.convert.TypeConverter.Conversion;
import com.toddfast.util.convert.conversion.StringTypeConversion;

public class AbstractValueSpecifierTest {

	@Test
	public void testAbstractValueSpecifierWithBoolean(){
		ValueBuilder valueBuilder = new ValueBuilder();
		String name = "attr-name";
		ValueBuilder builder = valueBuilder.withAttribute(Attribute.Name.valueOf(name)).value(true);
		assertNotNull(builder);
	}
	
	@Test
	public void testAbstractValueSpecifierWithString(){
		ValueBuilder valueBuilder = new ValueBuilder();
		String name = "attr-name";
		ValueBuilder builder = valueBuilder.withAttribute(Attribute.Name.valueOf(name)).value("test-value");
		assertNotNull(builder);
	}
	
	@Test
	public void testAbstractValueSpecifierWithInteger(){
		ValueBuilder valueBuilder = new ValueBuilder();
		String name = "attr-name";
		ValueBuilder builder = valueBuilder.withAttribute(Attribute.Name.valueOf(name)).value(1);
		assertNotNull(builder);
	}
	
	@Test
	public void testAbstractValueSpecifierWithDouble(){
		ValueBuilder valueBuilder = new ValueBuilder();
		String name = "attr-name";
		Double dbl = 1.5;
		ValueBuilder builder = valueBuilder.withAttribute(Attribute.Name.valueOf(name)).value(dbl);
		assertNotNull(builder);
	}
	
	@Test
	public void testAbstractValueSpecifierWithInstant(){
		ValueBuilder valueBuilder = new ValueBuilder();
		String name = "attr-name";
		ValueBuilder builder = valueBuilder.withAttribute(Attribute.Name.valueOf(name)).value(Instant.EPOCH);
		assertNotNull(builder);
	}
	
	@Test
	public void testAbstractValueSpecifierWithObjectTypeConversion(){
		ValueBuilder valueBuilder = new ValueBuilder();
		String name = "attr-name";
		Object value = "attr-value";
		@SuppressWarnings("unchecked")
		Conversion<String> converter = new StringTypeConversion();
		ValueBuilder builder = valueBuilder.withAttribute(Attribute.Name.valueOf(name)).value(value, converter);
		assertNotNull(builder);
	}
	
	@Test
	public void testAbstractValueSpecifierWithObject(){
		ValueBuilder valueBuilder = new ValueBuilder();
		String name = "attr-name";
		Object value = "attr-value";
		valueBuilder.withAttribute(Attribute.Name.valueOf(name)).setValue(value);
	}
}
