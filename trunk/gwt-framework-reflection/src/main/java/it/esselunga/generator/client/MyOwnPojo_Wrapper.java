package it.esselunga.generator.client;

import java.util.Map;
import java.util.Date;
import java.util.HashMap;

public class MyOwnPojo_Wrapper implements WrapperFactory<it.esselunga.generator.client.MyOwnPojo> {
  	@SuppressWarnings("unchecked")
  	private final Map<String, Converter> convertersRegister = new HashMap<String, Converter>();
  	{
  			registerConverter("nome", new it.esselunga.generator.client.converters.NoConverter());
  		registerConverter("eta", new it.esselunga.generator.client.converters.IntegerConverter());
  		registerConverter("dataNascita", new it.esselunga.generator.client.converters.NoConverter());
  		registerConverter("stato", new it.esselunga.generator.client.converters.NoConverter());
  		registerConverter("bella", new it.esselunga.generator.client.converters.BooleanConverter());
  		registerConverter("indirizzo", new it.esselunga.generator.client.MyOwnPojo.IndirizzoConverter());
  	}
  
  
  
  	class WrappedPojo extends it.esselunga.generator.client.MyOwnPojo implements
  			WrapperFactory.Wrapper<it.esselunga.generator.client.MyOwnPojo> {
  
  		private final it.esselunga.generator.client.MyOwnPojo instance;
  
  		private java.util.List<com.google.gwt.event.logical.shared.ValueChangeHandler<String>> handlers = new java.util.ArrayList<com.google.gwt.event.logical.shared.ValueChangeHandler<String>>(); 
  
  		public WrappedPojo(it.esselunga.generator.client.MyOwnPojo instance) {
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
  				instance.setStato((it.esselunga.generator.client.MyOwnPojo.Stati) value);
  				firePropertyChange("stato", value);
  				return;
  			}
  			if ("bella".equals(name)) {
  				instance.setBella((java.lang.Boolean) value);
  				firePropertyChange("bella", value);
  				return;
  			}
  			if ("indirizzo".equals(name)) {
  				instance.setIndirizzo((it.esselunga.generator.client.MyOwnPojo.Indirizzo) value);
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
  		
  
  		public it.esselunga.generator.client.MyOwnPojo getWrapperObject() {
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
  
  	public it.esselunga.generator.client.WrapperFactory.Wrapper<it.esselunga.generator.client.MyOwnPojo> createWrapper(
  			final it.esselunga.generator.client.MyOwnPojo instance) {
  		return new WrappedPojo(instance);
  	}
  	
  	public <P> void registerConverter(String property,
  			it.esselunga.generator.client.WrapperFactory.Converter<P> converter) {
  
  		convertersRegister.put(property, converter);
  	}
  
  	public <P> it.esselunga.generator.client.WrapperFactory.Converter<P> getConverter(
  			String property) {
  		return convertersRegister.get(property);
  	}	
  
  
  
  
}
