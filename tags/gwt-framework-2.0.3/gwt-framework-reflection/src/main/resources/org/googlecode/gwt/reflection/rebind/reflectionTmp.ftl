	@SuppressWarnings("unchecked")
	private final Map<String, Converter> convertersRegister = new HashMap<String, Converter>();
	{
	<#list fields as field>
		<#if field.canWrite | field.canRead>
		registerConverter("${field.propertyName}", new ${field.converter}());
		</#if>
	</#list>	
	}



	class WrappedPojo extends ${pojoClassName} implements
			WrapperFactory.Wrapper<${pojoClassName}> {

		private final ${pojoClassName} instance;

		private java.util.Map<String, ArrayList<com.google.gwt.event.logical.shared.ValueChangeHandler<String>>> handlersMap = new java.util.HashMap<String, ArrayList<com.google.gwt.event.logical.shared.ValueChangeHandler<String>>>();

		private java.util.List<com.google.gwt.event.logical.shared.ValueChangeHandler<String>> handlers = new java.util.ArrayList<com.google.gwt.event.logical.shared.ValueChangeHandler<String>>(); 

		public WrappedPojo(${pojoClassName} instance) {
			super();

			this.instance = instance;
			initializeHandlerMap();
		}
		
  		private void initializeHandlerMap() {
		<#list fields as field>
			<#if field.canWrite | field.canRead>
			handlersMap.put("${field.propertyName}", new ArrayList<com.google.gwt.event.logical.shared.ValueChangeHandler<String>>());
			</#if>
		</#list>	
		}
		

		public void addValueChangeHandler(com.google.gwt.event.logical.shared.ValueChangeHandler<String> handler) {
			
			handlers.add(handler);
		}		

		public void removeValueChangeHandler(com.google.gwt.event.logical.shared.ValueChangeHandler<String> handler) {
			
			handlers.remove(handler);
		}	
		
		public void addValueChangeHandler(String property, com.google.gwt.event.logical.shared.ValueChangeHandler<String> handler) {
  			
  			if(handlersMap.get(property)!= null){
  				handlersMap.get(property).add(handler);
  			}
  		}		
  
  		public void removeValueChangeHandler(String property, com.google.gwt.event.logical.shared.ValueChangeHandler<String> handler) {
  			
 			if(handlersMap.get(property)!= null){
  				handlersMap.get(property).remove(handler);
  			}
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
  		 			if(handlersMap.get(propertyName)!= null){
  		 				for(com.google.gwt.event.logical.shared.ValueChangeHandler<String> listener: handlersMap.get(propertyName)) {
  	  						listener.onValueChange(event);
  	  					}
  		  			}  										
				}
				finally {
					enableFireProperty = true;
				}				
			}			
		
		}

		public void setProperty(String name, Object value) {
		
			<#list fields as field>	
			<#if field.canWrite>
			if ("${field.propertyName}".equals(name)) {
				instance.${field.propDesc.writeMethod.name}((${field.propertyType}) value);
				firePropertyChange("${field.propertyName}", value);
				return;
			}
			</#if>
			</#list>

			throw new UnsupportedOperationException(name);
		}

		@SuppressWarnings("unchecked")
		public <ReturnType> ReturnType getProperty(String name) {
		
		<#list fields as field>	
		<#if field.canRead>		
			if ("${field.propertyName}".equals(name)) {
				<#if field.isPrimitive>
					return (ReturnType) new ${field.propertyType}(instance.${field.propDesc.readMethod.name}());
				<#else>
					return (ReturnType) instance.${field.propDesc.readMethod.name}();
				</#if>
			}
		</#if>
		</#list>	
			throw new UnsupportedOperationException(name);
		}
		

		public ${pojoClassName} getWrappedObject() {
			// TODO Auto-generated method stub
			return instance;
		}
		
		public Class<?> getPropertyType(String propertyName){
		<#list fields as field>	
			if ("${field.propertyName}".equals(propertyName)) {
				return ${field.propertyType}.class;
			}
		</#list>	
			throw new UnsupportedOperationException(propertyName);		
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
			String[] propNameArray = {<#list fields as field><#if field.canWrite | field.canRead>"${field.propertyName}"<#if fields[(fields?size)-1].propertyName!=field.propertyName>,</#if></#if></#list>}; 
			return propNameArray;
		}		

	}

	public org.googlecode.gwt.reflection.client.WrapperFactory.Wrapper<${pojoClassName}> createWrapper(
			final ${pojoClassName} instance) {
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



