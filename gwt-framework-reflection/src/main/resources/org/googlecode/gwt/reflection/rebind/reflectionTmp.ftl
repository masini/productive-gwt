	@SuppressWarnings("unchecked")
	private final Map<String, Converter> convertersRegister = new HashMap<String, Converter>();
	{
	<#list fields as field>
		registerConverter("${field.propertyName}", new ${field.converter}());
	</#list>	
	}



	class WrappedPojo extends ${pojoClassName} implements
			WrapperFactory.Wrapper<${pojoClassName}> {

		private final ${pojoClassName} instance;

		private java.util.List<com.google.gwt.event.logical.shared.ValueChangeHandler<String>> handlers = new java.util.ArrayList<com.google.gwt.event.logical.shared.ValueChangeHandler<String>>(); 

		public WrappedPojo(${pojoClassName} instance) {
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
		
			<#list fields as field>		
			if ("${field.propertyName}".equals(name)) {
				instance.set${field.camelCasePropertyName}((${field.propertyType}) value);
				firePropertyChange("${field.propertyName}", value);
				return;
			}
			</#list>

			throw new UnsupportedOperationException(name);
		}

		@SuppressWarnings("unchecked")
		public <ReturnType> ReturnType getProperty(String name) {
		
		<#list fields as field>			
			if ("${field.propertyName}".equals(name)) {
				<#if field.isPrimitive>
					return (ReturnType) new ${field.propertyType}(instance.get${field.camelCasePropertyName}());
				<#else>
					return (ReturnType) instance.${field.prefix}${field.camelCasePropertyName}();
				</#if>
			}
		</#list>	
			throw new UnsupportedOperationException(name);
		}
		

		public ${pojoClassName} getWrapperObject() {
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



