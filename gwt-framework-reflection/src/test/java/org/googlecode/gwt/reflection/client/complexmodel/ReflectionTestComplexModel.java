package org.googlecode.gwt.reflection.client.complexmodel;

import org.googlecode.gwt.reflection.client.WrapperFactory;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;

public class ReflectionTestComplexModel extends GWTTestCase {

	private static final String STATO = "01";
	private static WrapperFactory<Articolo> factory;
	private static WrapperFactory.Wrapper<Articolo> wrapper;
//	private static String[] fieldName = {"nome","eta","dataNascita","stato","bella","indirizzo"};
	
	
	public static void testCreateArticoloWrapperFactory() {
		
		factory = GWT.create(Articolo.class);
		
		assertNotNull("Factory generata", factory);
	}
	
	public static void testCreateWrapper() {
		
		factory = GWT.create(Articolo.class);
		wrapper = factory.createWrapper(new Articolo());
		
		assertNotNull("Wrapper generato", wrapper);
	}
	
	public static void testNullProperty() {
		
		assertNull("Peso deve essere null su oggetto appena instanziato", wrapper.getPropertyAsString("peso"));
	}
	
	public static void testStatoArticolo() {
		
		StatoArticolo stato = new StatoArticolo();
		stato.setCodice(STATO);
		
		wrapper.getWrappedObject().setStato(stato);
		
		assertEquals("Codice non corretto in StatoArticolo", STATO, wrapper.getPropertyAsString("stato"));
		
	}
	
//	public static void testGetPropertiesName(){
//		
//		String[] propName = wrapper.getPropertiesName();
//		assertEquals("Property name list estratta", propName, fieldName);
//		
//	}
	@Override
	public String getModuleName() {
		return "org.googlecode.gwt.reflection.Reflection";
	}

}
