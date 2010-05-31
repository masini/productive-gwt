package org.googlecode.gwt.reflection.client;

public interface WrapperFactory<T> {

	Wrapper<T> createWrapper(T instance);
	
	<P> void registerConverter(String property, Converter<P> converter);
	<P> Converter<P> getConverter(String property);
	
	public interface Wrapper<T> {
		public void setProperty(String name, Object value);	
		public <ReturnType> ReturnType getProperty(String name);

		public T getWrappedObject();

		public void setPropertyAsString(String name, String value);	
		public String getPropertyAsString(String name);

		public void addValueChangeHandler(com.google.gwt.event.logical.shared.ValueChangeHandler<String> handler);
		public void removeValueChangeHandler(com.google.gwt.event.logical.shared.ValueChangeHandler<String> handler);
		
		public void addValueChangeHandler(String property, com.google.gwt.event.logical.shared.ValueChangeHandler<String> handler);
		public void removeValueChangeHandler(String property, com.google.gwt.event.logical.shared.ValueChangeHandler<String> handler);

		public String[] getPropertiesName();
		public Class<?> getPropertyType(String propName);
		
	}

	public interface Converter<P> {
		
		String convertToString(P value);
		P convertFromString(String value);
	}
	
}
