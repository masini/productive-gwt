package org.googlecode.gwt.reflection.client;


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
	
	@Override
	public String getModuleName() {
		return "org.googlecode.gwt.reflection.Reflection";
	}

}
