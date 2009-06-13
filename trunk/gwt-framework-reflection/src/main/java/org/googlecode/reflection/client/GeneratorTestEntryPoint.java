package org.googlecode.reflection.client;


import java.util.Date;

import org.googlecode.reflection.client.MyOwnPojo.Indirizzo;
import org.googlecode.reflection.client.WrapperFactory.Wrapper;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;

public class GeneratorTestEntryPoint implements EntryPoint {

	public void onModuleLoad() {

//		WrapperFactory<MyOwnPojo> myOwnPojoWrapperFactory = GWT.create(MyOwnPojo.class);
		
//		WrapperFactory<NewOwnPojo> newOwnPojoWrapperFactory = GWT.create(NewOwnPojo.class);
		WrapperFactory<MyOwnPojo> wrapperFactory = GWT.create(MyOwnPojo.class);
		
		final Wrapper<MyOwnPojo> myOwnPojo = wrapperFactory.createWrapper(new MyOwnPojo());
		
		myOwnPojo.addValueChangeListener(new ValueChangeHandler<String>(){

			public void onValueChange(ValueChangeEvent<String> event) {
				GWT.log("event: "+event.getValue(), null);
			}});
		
		myOwnPojo.getWrapperObject().setDataNascita(new Date());
		myOwnPojo.getWrapperObject().setNome("Graziella");
		myOwnPojo.getWrapperObject().setEta(80);
		
		MyOwnPojo.Indirizzo indirizzo = new Indirizzo("milano", 30, "giambellino");
		myOwnPojo.getWrapperObject().setIndirizzo(indirizzo);
		
		
		MyTextBox<MyOwnPojo> myTextBoxEta = new MyTextBox<MyOwnPojo>(myOwnPojo, MyOwnPojo.class, "eta");
		
		myTextBoxEta.addChangeHandler(new ChangeHandler(){

			public void onChange(ChangeEvent ce) {
				GWT.log("Eta: "+myOwnPojo.getWrapperObject().getEta(), null);
			}

		});
		
		MyTextBox<MyOwnPojo> myTextBoxNome = new MyTextBox<MyOwnPojo>(myOwnPojo, MyOwnPojo.class, "nome");
		
		myTextBoxNome.addChangeHandler(new ChangeHandler(){

			public void onChange(ChangeEvent ce) {
				GWT.log("Nome: "+myOwnPojo.getWrapperObject().getNome(), null);
			}

		});
		MyDateBox<MyOwnPojo> myDateBoxData = new MyDateBox<MyOwnPojo>(myOwnPojo, MyOwnPojo.class, "dataNascita");
		myDateBoxData.addValueChangeHandler(new ValueChangeHandler<Date>(){

			public void onValueChange(ValueChangeEvent<Date> arg0) {
				GWT.log("Data Nascita: "+myOwnPojo.getWrapperObject().getDataNascita(), null);
			}
		});
		
		MyTextBox<MyOwnPojo> myTextBoxIndirizzo = new MyTextBox<MyOwnPojo>(myOwnPojo, MyOwnPojo.class, "indirizzo");
		
		myTextBoxIndirizzo.addChangeHandler(new ChangeHandler(){

			public void onChange(ChangeEvent ce) {
				GWT.log("Indirizzo: "+myOwnPojo.getWrapperObject().getIndirizzo(), null);
			}

		});		

		VerticalPanel vpanel = new VerticalPanel();
		vpanel.add(myTextBoxNome);
		vpanel.add(myDateBoxData);
		vpanel.add(myTextBoxEta);
		vpanel.add(myTextBoxIndirizzo);
		
		RootPanel.get().add(vpanel);
		
	}

	public class MyTextBox<T extends DataBindable> extends TextBox implements ChangeHandler {

		private final WrapperFactory.Wrapper<T> wrapper;
		private final String property;
		
		public MyTextBox(WrapperFactory.Wrapper<T> wrapperBean, Class<T> clazz, String property) {
			


			this.wrapper = wrapperBean;
			this.property = property;
			
			setText(wrapper.getPropertyAsString(this.property));
			addChangeHandler(this);
		}

		public void onChange(ChangeEvent ce) {
			wrapper.setPropertyAsString(property, getText());
		}
		
	}
	
	public class MyDateBox<T extends DataBindable> extends DateBox implements ValueChangeHandler<Date>{

		private final WrapperFactory.Wrapper<T> wrapper;
		private final String property;
		
		public MyDateBox(WrapperFactory.Wrapper<T> wrapperBean, Class<T> clazz, String property) {
			
			
			this.wrapper = wrapperBean;
			this.property = property;
			
			Date property2 = wrapper.getProperty(property);
			setValue(property2);
			addValueChangeHandler(this);
		}

		public void onValueChange(ValueChangeEvent<Date> value) {
			wrapper.setProperty(property, value.getValue());
		}
		
	}
}
