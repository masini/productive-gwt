package org.googlecode.gwt.reflection.client;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.junit.client.GWTTestCase;

public class ReflectionTestPubSubscribe extends GWTTestCase {

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
	
	public static void testSubscribe() {

		final List<Object> monitor = new ArrayList<Object>();
		
		wrapper.addValueChangeHandler(new ValueChangeHandler<String>(){

			public void onValueChange(ValueChangeEvent<String> event) {
				assertEquals("Proprieta' scorretta: "+event.getValue(), "longField", event.getValue());
				
				assertEquals("Valore Proprieta' scorretto", 5L, wrapper.getProperty(event.getValue()));
				
				wrapper.setProperty("bigDecimal", new BigDecimal(PI));
				
				monitor.add(new Object());
			}
		});
		
		wrapper.setProperty("longField", 5L);
		
		assertTrue("Non e' stato chiamato il listener !!", monitor.size()==1);
	}
		
	public static void testPublish() { 
		
		wrapper.setProperty("longField", 5L);
	}
	
	
	@Override
	public String getModuleName() {
		return "org.googlecode.gwt.reflection.Reflection";
	}

}
