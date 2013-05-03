package com.mycompany.project.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.googlecode.gwt.reflection.client.Reflectable;
import org.googlecode.gwt.reflection.client.WrapperFactory;
import org.googlecode.gwt.reflection.client.WrapperFactory.Wrapper;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.mycompany.project.client.PersonPojo.Stati;

public class ReflectionPanel extends Composite {

	public final static List<String> statiPossibili = new ArrayList<String>();
	static {
		statiPossibili.add(PersonPojo.Stati.CELIBE.name());
		statiPossibili.add(PersonPojo.Stati.NUBILE.name());
		statiPossibili.add(PersonPojo.Stati.CONIUGATO.name());
	}
	
	MyTextBox<PersonPojo> wrapperTextBoxNome;
	MyTextBox<PersonPojo> wrapperTextBoxCognome;
	MyTextBox<PersonPojo> wrapperTextBoxEta;
	MyDateBox<PersonPojo> wrapperDateBoxData;
	MyListBox<PersonPojo> wrapperListBoxStato;
	MyTextBox<PersonPojo> wrapperTextBoxIndirizzo;
	
	TextBox pojoTextBoxNome;
	TextBox pojoTextBoxCognome;
	TextBox pojoTextBoxEta;
	DateBox pojoDateBoxData;
	ListBox pojoListBoxStato;
	TextBox pojoTextBoxIndirizzo;

	VerticalPanel vp = new VerticalPanel();
	Label messageLabel = new Label();
	WrapperFactory<PersonPojo> wrapperFactory = GWT.create(PersonPojo.class);	
	final Wrapper<PersonPojo> wrapperPersonPojo = wrapperFactory.createWrapper(new PersonPojo());

	public ReflectionPanel() {
		wrapperPersonPojo.addValueChangeHandler("nome", new ValueChangeHandler<String>() {
		
			public void onValueChange(ValueChangeEvent<String> event) {
				pojoTextBoxNome.setText(wrapperPersonPojo.getWrappedObject().getNome());
			}
		});

		wrapperPersonPojo.addValueChangeHandler("cognome", new ValueChangeHandler<String>() {
			
			public void onValueChange(ValueChangeEvent<String> event) {
				pojoTextBoxCognome.setText(wrapperPersonPojo.getWrappedObject().getCognome());
			}
		});
		wrapperPersonPojo.addValueChangeHandler("eta", new ValueChangeHandler<String>() {
			
			public void onValueChange(ValueChangeEvent<String> event) {
				pojoTextBoxEta.setText(String.valueOf(wrapperPersonPojo.getWrappedObject().getEta()));
			}
		});
		wrapperPersonPojo.addValueChangeHandler("indirizzo", new ValueChangeHandler<String>() {
			
			public void onValueChange(ValueChangeEvent<String> event) {
				pojoTextBoxIndirizzo.setText(wrapperPersonPojo.getWrappedObject().getIndirizzo().getVia()+", "+Integer.toString(wrapperPersonPojo.getWrappedObject().getIndirizzo().getNumero())+ " - "+wrapperPersonPojo.getWrappedObject().getIndirizzo().getCitta());
			}
		});
		wrapperPersonPojo.addValueChangeHandler("stato", new ValueChangeHandler<String>() {
			
			public void onValueChange(ValueChangeEvent<String> event) {
				for (int ii = 0; ii < statiPossibili.size(); ii++) {
					if (statiPossibili.get(ii).equalsIgnoreCase(wrapperPersonPojo.getWrappedObject().getStato().name())) {
						pojoListBoxStato.setSelectedIndex(ii);
					}
				}
			}
		});
		wrapperPersonPojo.addValueChangeHandler("dataNascita", new ValueChangeHandler<String>() {
			
			public void onValueChange(ValueChangeEvent<String> event) {
				pojoDateBoxData.setValue(wrapperPersonPojo.getWrappedObject().getDataNascita());
			}
		});
		wrapperPersonPojo.addValueChangeHandler(new ValueChangeHandler<String>() {

			public void onValueChange(ValueChangeEvent<String> event) {
				messageLabel.setText("Property cambiata: "+event.getValue());
			}
		});
		wrapperPersonPojo.getWrappedObject().setDataNascita(DateTimeFormat.getFormat("MM/dd/yyyy").parse("08/28/1982"));
		wrapperPersonPojo.getWrappedObject().setNome("Mario");
		wrapperPersonPojo.getWrappedObject().setCognome("Rossi");
		wrapperPersonPojo.getWrappedObject().setStato(PersonPojo.Stati.CELIBE);
		wrapperPersonPojo.getWrappedObject().setIndirizzo(
				new PersonPojo.Indirizzo("Roma", 100, "Via Gramsci"));
		wrapperPersonPojo.getWrappedObject().setEta(26);
		HorizontalPanel hpanel = new HorizontalPanel();
		hpanel.add(createWrapperPanel());
		hpanel.add(createPojoPanel());
		hpanel.setSpacing(100);
		vp.add(hpanel);
		messageLabel.setWidth("100%");
		messageLabel.setStyleName("reflection-footer");
		vp.add(messageLabel);
		DecoratorPanel dp = new DecoratorPanel();
		dp.add(vp);
		initWidget(dp);
		
	}
	
		private TitledPanel createPojoPanel() {
			pojoTextBoxNome = new TextBox();
			pojoTextBoxNome.setText(wrapperPersonPojo.getWrappedObject().getNome());
			pojoTextBoxNome.addValueChangeHandler(new ValueChangeHandler<String>() {
			
				public void onValueChange(ValueChangeEvent<String> event) {
					wrapperPersonPojo.getWrappedObject().setNome(event.getValue());
					wrapperTextBoxNome.refresh();
				}
			});

			pojoTextBoxCognome = new TextBox();
			pojoTextBoxCognome.setText(wrapperPersonPojo.getWrappedObject().getCognome());
			pojoTextBoxCognome.addValueChangeHandler(new ValueChangeHandler<String>() {
			
				public void onValueChange(ValueChangeEvent<String> event) {
					wrapperPersonPojo.getWrappedObject().setCognome(event.getValue());
					wrapperTextBoxCognome.refresh();
				}
			});
			pojoTextBoxEta = new TextBox();
			pojoTextBoxEta.setText(String.valueOf(wrapperPersonPojo.getWrappedObject().getEta()));
			pojoTextBoxEta.setEnabled(false);
			pojoDateBoxData = new DateBox();
			pojoDateBoxData.setValue(wrapperPersonPojo.getWrappedObject().getDataNascita());
			pojoDateBoxData
					.addValueChangeHandler(new ValueChangeHandler<Date>() {

						public void onValueChange(ValueChangeEvent<Date> event) {
							wrapperPersonPojo.getWrappedObject().setDataNascita(event.getValue());
							wrapperDateBoxData.refresh();
							}
					});
			
			pojoListBoxStato = new ListBox();
			for (int ii = 0; ii < statiPossibili.size(); ii++) {
				pojoListBoxStato.addItem(statiPossibili.get(ii));
				if (statiPossibili.get(ii).equalsIgnoreCase(wrapperPersonPojo.getWrappedObject().getStato().name())) {
					pojoListBoxStato.setSelectedIndex(ii);
				}
			}
			pojoListBoxStato.addChangeHandler(new ChangeHandler() {
			
				public void onChange(ChangeEvent event) {
					wrapperPersonPojo.getWrappedObject().setStato(Stati.valueOf(pojoListBoxStato.getValue(pojoListBoxStato.getSelectedIndex())));
					GWT.log("event: "+event.getSource(), null);
					wrapperListBoxStato.refresh();
			
				}
			});

			pojoTextBoxIndirizzo = new TextBox();
			pojoTextBoxIndirizzo.setText(wrapperPersonPojo.getWrappedObject().getIndirizzo().getVia()+", "+Integer.toString(wrapperPersonPojo.getWrappedObject().getIndirizzo().getNumero())+ " - "+wrapperPersonPojo.getWrappedObject().getIndirizzo().getCitta());
			pojoTextBoxIndirizzo.setEnabled(false);

			Map<String, Widget> gridMap = new LinkedHashMap<String, Widget>();
			gridMap.put("Nome", pojoTextBoxNome);
			gridMap.put("Cognome", pojoTextBoxCognome);
			gridMap.put("Indirizzo", pojoTextBoxIndirizzo);
			gridMap.put("Data di Nascita", pojoDateBoxData);
			gridMap.put("Eta'", pojoTextBoxEta);
			gridMap.put("Stato", pojoListBoxStato);

			Grid grid = createGridPanel(gridMap);
			grid.setWidth("50%");
			return new TitledPanel("Pojo Object", grid);
	}

		private TitledPanel createWrapperPanel(){
			wrapperTextBoxNome = new MyTextBox<PersonPojo>(wrapperPersonPojo,
					PersonPojo.class, "nome");

			wrapperTextBoxCognome = new MyTextBox<PersonPojo>(wrapperPersonPojo,
					PersonPojo.class, "cognome");

			wrapperTextBoxEta = new MyTextBox<PersonPojo>(wrapperPersonPojo,
					PersonPojo.class, "eta");
			wrapperTextBoxEta.setEnabled(false);

			wrapperDateBoxData = new MyDateBox<PersonPojo>(wrapperPersonPojo,
					PersonPojo.class, "dataNascita");
			wrapperDateBoxData
					.addValueChangeHandler(new ValueChangeHandler<Date>() {

						public void onValueChange(ValueChangeEvent<Date> arg0) {

							long time = new Date().getTime()
									- arg0.getValue().getTime();
							long anni = (long) (time / (1000 * 60 * 60 * 24 * 365.26));
							wrapperTextBoxEta.setValue(String.valueOf(anni));

						}
					});
			wrapperListBoxStato = new MyListBox<PersonPojo>(wrapperPersonPojo,
					PersonPojo.class, "stato", statiPossibili);

			wrapperTextBoxIndirizzo = new MyTextBox<PersonPojo>(wrapperPersonPojo,
					PersonPojo.class, "indirizzo");

			Map<String, Widget> gridMap = new LinkedHashMap<String, Widget>();
			gridMap.put("Nome", wrapperTextBoxNome);
			gridMap.put("Cognome", wrapperTextBoxCognome);
			gridMap.put("Indirizzo", wrapperTextBoxIndirizzo);
			gridMap.put("Data di Nascita", wrapperDateBoxData);
			gridMap.put("Eta'", wrapperTextBoxEta);
			gridMap.put("Stato", wrapperListBoxStato);

			Grid grid = createGridPanel(gridMap);
			grid.setWidth("50%");
			return new TitledPanel("Wrapper Object", grid);
	}

	private Grid createGridPanel(Map<String, Widget> map) {
		Grid grid = new Grid(map.size(), 2);

		List<String> labelList = new ArrayList<String>(map.keySet());
		for (int ii = 0; ii < labelList.size(); ii++) {
			GWT.log(labelList.get(ii), null);
			grid.setWidget(ii, 0, new Label(labelList.get(ii)));
			grid.setWidget(ii, 1, map.get(labelList.get(ii)));
		}

		return grid;
	}
	

	public class MyTextBox<T extends Reflectable> extends TextBox implements
			ChangeHandler {

		private final WrapperFactory.Wrapper<T> wrapper;
		private final String property;

		public MyTextBox(WrapperFactory.Wrapper<T> wrapperBean, Class<T> clazz,
				String property) {

			this.wrapper = wrapperBean;
			this.property = property;

			setText(wrapper.getPropertyAsString(this.property));
			addChangeHandler(this);
		}

		public void onChange(ChangeEvent ce) {
			wrapper.setPropertyAsString(property, getText());
		}
		
		public void refresh(){
			setText(wrapper.getPropertyAsString(this.property));
		}

	}

	public class MyListBox<T extends Reflectable> extends ListBox implements
			ChangeHandler {

		private final WrapperFactory.Wrapper<T> wrapper;
		private final String property;
		private final List<String> values;

		public MyListBox(WrapperFactory.Wrapper<T> wrapperBean, Class<T> clazz,
				String property, List<String> values) {

			this.wrapper = wrapperBean;
			this.property = property;
			this.values = values;

			for (int ii = 0; ii < values.size(); ii++) {
				addItem(values.get(ii));
				if (values.get(ii).equalsIgnoreCase(
						wrapper.getPropertyAsString(property))) {
					setSelectedIndex(ii);
				}
			}

			addChangeHandler(this);
		}

		public void onChange(ChangeEvent ce) {
			wrapper.setPropertyAsString(property, getValue(getSelectedIndex()));
		}
		
		public void refresh(){
			for (int ii = 0; ii < values.size(); ii++) {
				if (values.get(ii).equalsIgnoreCase(
						wrapper.getPropertyAsString(property))) {
					setSelectedIndex(ii);
				}
			}
		}		

	}

	public class MyDateBox<T extends Reflectable> extends DateBox implements
			ValueChangeHandler<Date> {

		private final WrapperFactory.Wrapper<T> wrapper;
		private final String property;

		public MyDateBox(WrapperFactory.Wrapper<T> wrapperBean, Class<T> clazz,
				String property) {
			this.wrapper = wrapperBean;
			this.property = property;

			setFormat(new DateBox.DefaultFormat());
			Date property2 = wrapper.getProperty(property);
			setValue(property2);
			addValueChangeHandler(this);
		}

		public void onValueChange(ValueChangeEvent<Date> value) {
			wrapper.setProperty(property, value.getValue());
		}
		
		public void refresh(){
			Date property2 = wrapper.getProperty(property);
			setValue(property2);
		}

	}

}
