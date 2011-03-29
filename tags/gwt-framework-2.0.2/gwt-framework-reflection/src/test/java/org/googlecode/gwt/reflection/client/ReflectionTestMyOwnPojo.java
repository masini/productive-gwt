package org.googlecode.gwt.reflection.client;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;

public class ReflectionTestMyOwnPojo extends GWTTestCase {

	private static WrapperFactory<MyOwnPojo> factory;
	private static WrapperFactory.Wrapper<MyOwnPojo> wrapper;

	
	
	public static void testCreateWrapperFactory() {
		
		factory = GWT.create(MyOwnPojo.class);
		
		assertNotNull("Factory generata", factory);
	}
	
	public static void testCreateWrapper() {
		
		wrapper = factory.createWrapper(new MyOwnPojo());
		
		assertNotNull("Wrapper generato", wrapper);
	}
	
	public static void testNullProperty() {
		
		assertNull("Nome deve essere null su oggetto appena instanziato", wrapper.getPropertyAsString("nome"));
	}
	
	public static void testPropertyType() {
		
		assertEquals("Stesso tipo property nome", wrapper.getPropertyType("nome"), String.class);
		assertEquals("Stesso tipo property eta", wrapper.getPropertyType("eta"), Integer.class);
		assertEquals("Stesso tipo property dataNascita", wrapper.getPropertyType("dataNascita"), Date.class);
		assertEquals("Stesso tipo property stato", wrapper.getPropertyType("stato"), MyOwnPojo.Stati.class);
		assertEquals("Stesso tipo property bella", wrapper.getPropertyType("bella"), Boolean.class);
		assertEquals("Stesso tipo property indirizzo", wrapper.getPropertyType("indirizzo"), MyOwnPojo.Indirizzo.class);
	
	}	
	
	public static void testGetPropertiesName(){

	}

	@Override
	public String getModuleName() {
		return "org.googlecode.gwt.reflection.Reflection";
	}

}
