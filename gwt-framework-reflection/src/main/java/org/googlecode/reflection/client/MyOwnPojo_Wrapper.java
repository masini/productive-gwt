package org.googlecode.reflection.client;

import java.util.Map;
import java.util.Date;
import java.util.HashMap;

public class MyOwnPojo_Wrapper implements WrapperFactory<org.googlecode.reflection.client.MyOwnPojo> {
  	@SuppressWarnings("unchecked")
  	private final Map<String, Converter> convertersRegister = new HashMap<String, Converter>();
  	{
  			registerConverter("nome", new org.googlecode.reflection.client.converters.NoConverter());
  		registerConverter("eta", new org.googlecode.reflection.client.converters.IntegerConverter());
  		registerConverter("dataNascita", new org.googlecode.reflection.client.converters.NoConverter());
  		registerConverter("stato", new org.googlecode.reflection.client.converters.NoConverter());
  		registerConverter("bella", new org.googlecode.reflection.client.converters.BooleanConverter());
  		registerConverter("indirizzo", new org.googlecode.reflection.client.MyOwnPojo.IndirizzoConverter());
  	}
  
  
  
  	class WrappedPojo extends org.googlecode.reflection.client.MyOwnPojo implements
  			WrapperFactory.Wrapper<org.googlecode.reflection.client.MyOwnPojo> {
  
  		private final org.googlecode.reflection.client.MyOwnPojo instance;
  
  		private java.util.List<com.google.gwt.event.logical.shared.ValueChangeHandler<String>> handlers = new java.util.ArrayList<com.google.gwt.event.logical.shared.ValueChangeHandler<String>>(); 
  
  		public WrappedPojo(org.googlecode.reflection.client.MyOwnPojo instance) {
  			super();
  
  			this.instance = instance;
  		}
  
  		public void addValueChangeListener(com.google.gwt.event.logical.shared.ValueChangeHandler<String> listener) {
  			
  			handlers.add(listener);
  		}		
  
  		public void removeValueChangeListener(com.google.gwt.event.logical.shared.ValueChangeHandler<String> listener) {
  			
  			handlers.remove(listener);
  		}		
  
  		public class E extends com.google.gwt.event.logical.shared.ValueChangeEvent<String> {
  			public E(String instance) {
  				super(instance);
  			}
  		}
  		
  
  		private void firePropertyChange(String propertyName, Object value) {
  			com.google.gwt.core.client.GWT.log(propertyName+" = "+value,null);
  			
  			com.google.gwt.event.logical.shared.ValueChangeEvent<String> event = new E(propertyName); 
  			
  			for(com.google.gwt.event.logical.shared.ValueChangeHandler<String> listener: handlers) {
  				listener.onValueChange(event);
  			}
  		}
  
  		public void setProperty(String name, Object value) {
  		
  			if ("nome".equals(name)) {
  				instance.setNome((java.lang.String) value);
  				firePropertyChange("nome", value);
  				return;
  			}
  			if ("eta".equals(name)) {
  				instance.setEta((java.lang.Integer) value);
  				firePropertyChange("eta", value);
  				return;
  			}
  			if ("dataNascita".equals(name)) {
  				instance.setDataNascita((java.util.Date) value);
  				firePropertyChange("dataNascita", value);
  				return;
  			}
  			if ("stato".equals(name)) {
  				instance.setStato((org.googlecode.reflection.client.MyOwnPojo.Stati) value);
  				firePropertyChange("stato", value);
  				return;
  			}
  			if ("bella".equals(name)) {
  				instance.setBella((java.lang.Boolean) value);
  				firePropertyChange("bella", value);
  				return;
  			}
  			if ("indirizzo".equals(name)) {
  				instance.setIndirizzo((org.googlecode.reflection.client.MyOwnPojo.Indirizzo) value);
  				firePropertyChange("indirizzo", value);
  				return;
  			}
  
  			throw new UnsupportedOperationException(name);
  		}
  
  		@SuppressWarnings("unchecked")
  		public <ReturnType> ReturnType getProperty(String name) {
  		
  			if ("nome".equals(name)) {
  					return (ReturnType) instance.getNome();
  			}
  			if ("eta".equals(name)) {
  					return (ReturnType) new java.lang.Integer(instance.getEta());
  			}
  			if ("dataNascita".equals(name)) {
  					return (ReturnType) instance.getDataNascita();
  			}
  			if ("stato".equals(name)) {
  					return (ReturnType) instance.getStato();
  			}
  			if ("bella".equals(name)) {
  					return (ReturnType) instance.isBella();
  			}
  			if ("indirizzo".equals(name)) {
  					return (ReturnType) instance.getIndirizzo();
  			}
  			throw new UnsupportedOperationException(name);
  		}
  		
  
  		public org.googlecode.reflection.client.MyOwnPojo getWrapperObject() {
  			// TODO Auto-generated method stub
  			return instance;
  		}
  		
  		public String getPropertyAsString(String name) {
  			
  			Converter converter = getConverter(name);
  
  			return converter.convertToString(getProperty(name));
  		}
  
  		public void setPropertyAsString(String name, String value) {
  			Converter converter = getConverter(name);
  
  			setProperty(name, converter.convertFromString(value));
  		}		
  
  	}
  
  	public org.googlecode.reflection.client.WrapperFactory.Wrapper<org.googlecode.reflection.client.MyOwnPojo> createWrapper(
  			final org.googlecode.reflection.client.MyOwnPojo instance) {
  		return new WrappedPojo(instance);
  	}
  	
  	public <P> void registerConverter(String property,
  			org.googlecode.reflection.client.WrapperFactory.Converter<P> converter) {
  
  		convertersRegister.put(property, converter);
  	}
  
  	public <P> org.googlecode.reflection.client.WrapperFactory.Converter<P> getConverter(
  			String property) {
  		return convertersRegister.get(property);
  	}	
  
  
  
  
}
