	class WrappedPojo extends MyOwnPojo implements
			WrapperFactory.Wrapper<MyOwnPojo> {

		private final MyOwnPojo instance;

		public WrappedPojo(MyOwnPojo instance) {
			super();

			this.instance = instance;
		}

		public void setProperty(String name, Object value) {
			
			if ("nome".equals(name)) {
				instance.setNome((String) value);

				return;
			}

			if ("eta".equals(name)) {
				instance.setEta((Integer) value);

				return;
			}

			if ("dataNascita".equals(name)) {
				instance.setDataNascita((Date) value);

				return;
			}
			if ("bella".equals(name)) {
				instance.setBella((Boolean) value);

				return;
			}			

			if ("stato".equals(name)) {
				instance.setStato((MyOwnPojo.Stati) Enum.valueOf(
						MyOwnPojo.Stati.class, (String) value));

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
				return (ReturnType) new Integer(instance.getEta());
			}

			if ("dataNascita".equals(name)) {
				return (ReturnType) instance.getDataNascita();
			}

			if ("stato".equals(name)) {
				return (ReturnType) instance.getStato();
			}
			
			if ("bella".equals(name)) {
				return (ReturnType) instance.isBella().toString();
			}

			throw new UnsupportedOperationException(name);
		}

		public MyOwnPojo getWrapperObject() {
			// TODO Auto-generated method stub
			return instance;
		}

	}

	public org.googlecode.gwt.reflection.client.WrapperFactory.Wrapper<MyOwnPojo> createWrapper(
			final MyOwnPojo instance) {
		// TODO Auto-generated method stub
		return new WrappedPojo(instance);
	}



