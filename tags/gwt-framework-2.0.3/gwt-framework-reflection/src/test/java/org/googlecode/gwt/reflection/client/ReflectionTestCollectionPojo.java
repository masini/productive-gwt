package org.googlecode.gwt.reflection.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;

public class ReflectionTestCollectionPojo extends GWTTestCase {

	private static WrapperFactory<CollectionPojo> factoryCollectionPojo;
	private static WrapperFactory<MyOwnPojo> factoryMyOwnPojo;
	private static WrapperFactory.Wrapper<CollectionPojo> wrapper;

	
	
	public static void testCreateWrapperFactory() {
		
		factoryCollectionPojo = GWT.create(CollectionPojo.class);
		factoryMyOwnPojo = GWT.create(MyOwnPojo.class);
		
		assertNotNull("Factory CollectionPojo generata", factoryCollectionPojo);
		assertNotNull("Factory MyOwnPojo generata", factoryMyOwnPojo);
	}
	
	public static void testCreateWrapper() {
		
		wrapper = factoryCollectionPojo.createWrapper(new CollectionPojo());
		
		assertNotNull("Wrapper generato", wrapper);
	}
	
	public static void testPopolaCollection() {
		List<MyOwnPojo> myOwnPojoList = new ArrayList<MyOwnPojo>();
		for(int ii = 0; ii < 4; ii++){
			MyOwnPojo myOwnPojo = new MyOwnPojo();
			myOwnPojo.setNome("myOwnPojo"+String.valueOf(ii));
			myOwnPojo.setEta(ii);
			myOwnPojoList.add(myOwnPojo);
		}
		wrapper.setProperty("myOwnPojioList", myOwnPojoList);
		assertNotNull(wrapper.getProperty("myOwnPojioList"));
		
	}
	
	public static void testLeggiCollection() {
		List<MyOwnPojo> myOwnPojoList = wrapper.getProperty("myOwnPojioList");
		for(MyOwnPojo myOwnPojo : myOwnPojoList){
			WrapperFactory.Wrapper<MyOwnPojo> wrapperMyOwnPojo = factoryMyOwnPojo.createWrapper(myOwnPojo);
			assertNotNull("Property nome not null", wrapperMyOwnPojo.getProperty("nome"));
			assertNotNull("Property eta not null", wrapperMyOwnPojo.getProperty("eta"));
		}
		
	}
	
	@Override
	public String getModuleName() {
		return "org.googlecode.gwt.reflection.Reflection";
	}

}
