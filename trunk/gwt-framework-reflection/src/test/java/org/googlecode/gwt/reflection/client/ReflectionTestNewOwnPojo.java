package org.googlecode.gwt.reflection.client;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;

public class ReflectionTestNewOwnPojo extends GWTTestCase {

	private static final String TEST = "Test";
	private static WrapperFactory<NewOwnPojo> factory;
	private static WrapperFactory.Wrapper<NewOwnPojo> wrapper;
	private static String PI = "3.14159265358979323846";
	
	public static void testCreateWrapperFactory() {
		
		factory = GWT.create(NewOwnPojo.class);
		
		assertNotNull("Factory non generata", factory);
	}
	
	public static void testCreateWrapper() {
		
		NewOwnPojo instance = new NewOwnPojo();
		instance.setListField(new ArrayList<String>());
		instance.getListField().add(TEST);
		instance.setLongField(28882);
		instance.setBigDecimal(new BigDecimal(PI));
		
		wrapper = factory.createWrapper(instance);
		
		assertNotNull("Wrapper non generato", wrapper);
	}
	
	public static void testNoProperty() {
		
		boolean exception = false;
		
		try {
			wrapper.getPropertyAsString("nome");
		}
		catch(UnsupportedOperationException ex) {
			exception = true;
		}
		
		assertTrue("Non rileva mancanza property",exception);
	}
	
	public static void testListProperty() {

		List<String> property = wrapper.getProperty("listField");
		
		assertNotNull("listField null !!", property);
		
		assertEquals("La stringa ritornata non è corretta", TEST,property.get(0));
	}
	
	public static void testBigDecimalProperty() {

		BigDecimal property = wrapper.getProperty("bigDecimal");
		
		assertNotNull("bigDecimal null !!", property);
		
		assertEquals("La stringa ritornata non è corretta", new BigDecimal(PI), property);
	}
	
	@Override
	public String getModuleName() {
		return "org.googlecode.gwt.reflection.Reflection";
	}

}
