package org.googlecode.gwt.reflection.client.complexmodel;

import java.util.Map;
import org.googlecode.gwt.reflection.client.WrapperFactory;
import java.util.Date;
import java.util.HashMap;

public class Articolo_Wrapper implements WrapperFactory<org.googlecode.gwt.reflection.client.complexmodel.Articolo> {
  	@SuppressWarnings("unchecked")
  	private final Map<String, Converter> convertersRegister = new HashMap<String, Converter>();
  	{
  		registerConverter("serialVersionUID", new org.googlecode.gwt.reflection.client.converters.LongConverter());
  	registerConverter("id", new org.googlecode.gwt.reflection.client.converters.LongConverter());
  	registerConverter("stato", new org.googlecode.gwt.reflection.client.converters.NoConverter());
  	registerConverter("compratore", new org.googlecode.gwt.reflection.client.converters.NoConverter());
  	registerConverter("peso", new org.googlecode.gwt.reflection.client.converters.NoConverter());
  	registerConverter("pesoForzato", new org.googlecode.gwt.reflection.client.converters.NoConverter());
  	registerConverter("flInFattura", new org.googlecode.gwt.reflection.client.converters.BooleanConverter());
  	}
  
  
  
  	class WrappedPojo extends org.googlecode.gwt.reflection.client.complexmodel.Articolo implements
  			WrapperFactory.Wrapper<org.googlecode.gwt.reflection.client.complexmodel.Articolo> {
  
  		private final org.googlecode.gwt.reflection.client.complexmodel.Articolo instance;
  
  		private java.util.List<com.google.gwt.event.logical.shared.ValueChangeHandler<String>> handlers = new java.util.ArrayList<com.google.gwt.event.logical.shared.ValueChangeHandler<String>>(); 
  
  		public WrappedPojo(org.googlecode.gwt.reflection.client.complexmodel.Articolo instance) {
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
  
  		private boolean enableFireProperty = true;
  
  		private void firePropertyChange(String propertyName, Object value) {
  			
  			if(enableFireProperty) {
  				enableFireProperty = false;
  				try {
  					
  					com.google.gwt.event.logical.shared.ValueChangeEvent<String> event = new E(propertyName); 
  					
  					for(com.google.gwt.event.logical.shared.ValueChangeHandler<String> listener: handlers) {
  						listener.onValueChange(event);
  					}
  				}
  				finally {
  					enableFireProperty = true;
  				}				
  			}			
  		
  		}
  
  		public void setProperty(String name, Object value) {
  		
  			if ("id".equals(name)) {
  				instance.setId((java.lang.Long) value);
  				firePropertyChange("id", value);
  				return;
  			}
  			if ("stato".equals(name)) {
  				instance.setStato((org.googlecode.gwt.reflection.client.complexmodel.StatoArticolo) value);
  				firePropertyChange("stato", value);
  				return;
  			}
  			if ("compratore".equals(name)) {
  				instance.setCompratore((org.googlecode.gwt.reflection.client.complexmodel.Compratore) value);
  				firePropertyChange("compratore", value);
  				return;
  			}
  			if ("peso".equals(name)) {
  				instance.setPeso((java.math.BigDecimal) value);
  				firePropertyChange("peso", value);
  				return;
  			}
  			if ("pesoForzato".equals(name)) {
  				instance.setPesoForzato((java.math.BigDecimal) value);
  				firePropertyChange("pesoForzato", value);
  				return;
  			}
  			if ("flInFattura".equals(name)) {
  				instance.setFlInFattura((java.lang.Boolean) value);
  				firePropertyChange("flInFattura", value);
  				return;
  			}
  
  			throw new UnsupportedOperationException(name);
  		}
  
  		@SuppressWarnings("unchecked")
  		public <ReturnType> ReturnType getProperty(String name) {
  		
  			if ("id".equals(name)) {
  					return (ReturnType) instance.getId();
  			}
  			if ("stato".equals(name)) {
  					return (ReturnType) instance.getStato();
  			}
  			if ("compratore".equals(name)) {
  					return (ReturnType) instance.getCompratore();
  			}
  			if ("peso".equals(name)) {
  					return (ReturnType) instance.getPeso();
  			}
  			if ("pesoForzato".equals(name)) {
  					return (ReturnType) instance.getPesoForzato();
  			}
  			if ("flInFattura".equals(name)) {
  					return (ReturnType) new java.lang.Boolean(instance.isFlInFattura());
  			}
  			throw new UnsupportedOperationException(name);
  		}
  		
  
  		public org.googlecode.gwt.reflection.client.complexmodel.Articolo getWrappedObject() {
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
  		
  		public String[] getPropertiesName(){
  			String[] propNameArray = {"serialVersionUID","id","stato","compratore","peso","pesoForzato","flInFattura"}; 
  			return propNameArray;
  		}		
  
  	}
  
  	public org.googlecode.gwt.reflection.client.WrapperFactory.Wrapper<org.googlecode.gwt.reflection.client.complexmodel.Articolo> createWrapper(
  			final org.googlecode.gwt.reflection.client.complexmodel.Articolo instance) {
  		return new WrappedPojo(instance);
  	}
  	
  	public <P> void registerConverter(String property,
  			org.googlecode.gwt.reflection.client.WrapperFactory.Converter<P> converter) {
  
  		convertersRegister.put(property, converter);
  	}
  
  	public <P> org.googlecode.gwt.reflection.client.WrapperFactory.Converter<P> getConverter(
  			String property) {
  		return convertersRegister.get(property);
  	}	
  
  
  
  
}
