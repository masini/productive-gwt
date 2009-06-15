package org.googlecode.gwt.reflection.client;

public interface WrapperFactory<T> {

	Wrapper<T> createWrapper(T instance);
	
	<P> void registerConverter(String property, Converter<P> converter);
	<P> Converter<P> getConverter(String property);
	
	public interface Wrapper<T> {
		public void setProperty(String name, Object value);	
		public <ReturnType> ReturnType getProperty(String name);
		public T getWrapperObject();

		public void setPropertyAsString(String name, String value);	
		public String getPropertyAsString(String name);

		public void addValueChangeListener(com.google.gwt.event.logical.shared.ValueChangeHandler<String> listener);
		public void removeValueChangeListener(com.google.gwt.event.logical.shared.ValueChangeHandler<String> listener);
		
		public String[] getPropertiesName();
		
	}

	public interface Converter<P> {
		
		String convertToString(P value);
		P convertFromString(String value);
	}
	
}
