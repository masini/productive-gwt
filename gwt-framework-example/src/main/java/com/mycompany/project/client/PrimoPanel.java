package com.mycompany.project.client;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PrimoPanel extends Composite {
	private ListBox centrCostCB;
	private ListBox socCB;

	private Grid grid;

	public PrimoPanel() {
		
		VerticalPanel vp = new VerticalPanel();
		initWidget(vp);
		vp.setWidth("100%");
//		setWidth("100%");
		
		final VerticalPanel verticalPanel_1 = new VerticalPanel();
		vp.add(verticalPanel_1);

		final VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel_1.add(verticalPanel);

		final FlexTable flexTable = new FlexTable();
		verticalPanel.add(flexTable);
		flexTable.setWidth("100%");
		flexTable.setBorderWidth(1);

		final Label socLabel = new Label("Soc");
		flexTable.setWidget(0, 0, socLabel);
		flexTable.getCellFormatter().setWidth(0, 0, "16%");

		socCB = new ListBox();
		flexTable.setWidget(0, 1, socCB);
		socCB.addItem("aaaaa");
		socCB.addItem("bbbbbb");
		socCB.addItem("bbbbb");
		socCB.addItem("bbb");
		flexTable.getCellFormatter().setWidth(0, 1, "17%");

		final Label cetrCostLabel = new Label("Cetr cost");
		flexTable.setWidget(1, 0, cetrCostLabel);

		centrCostCB = new ListBox();
		flexTable.setWidget(1, 1, centrCostCB);
		centrCostCB.addItem("aaaaa");
		centrCostCB.addItem("bbbbbb");
		centrCostCB.addItem("bbbbb");
		centrCostCB.addItem("bbb");

		final Label nFatt = new Label("n° fatt");
		flexTable.setWidget(2, 0, nFatt);

		final Label annoContLabel = new Label("Anno cont");
		flexTable.setWidget(3, 0, annoContLabel);

		final TextBox nFattTB = new TextBox();
		flexTable.setWidget(2, 1, nFattTB);
		nFattTB.setVisibleLength(10);
		nFattTB.setWidth("100%");

		final TextBox annoContTB = new TextBox();
		flexTable.setWidget(3, 1, annoContTB);
		annoContTB.setVisibleLength(10);
		annoContTB.setWidth("100%");

		final Label codFiscLabel = new Label("COD FISC");
		flexTable.setWidget(0, 2, codFiscLabel);
		flexTable.getCellFormatter().setWidth(0, 2, "16%");

		final TextBox codFiscTB = new TextBox();
		flexTable.setWidget(0, 3, codFiscTB);
		flexTable.getCellFormatter().setWidth(0, 3, "17%");
		codFiscTB.setVisibleLength(10);
		codFiscTB.setWidth("100%");

		final Label pivaLabel = new Label("piva");
		flexTable.setWidget(1, 2, pivaLabel);

		final TextBox pivaTB = new TextBox();
		flexTable.setWidget(1, 3, pivaTB);
		pivaTB.setVisibleLength(10);
		pivaTB.setWidth("100%");

		final Label dtInizioLabel = new Label("dt inizio");
		flexTable.setWidget(2, 2, dtInizioLabel);

		final TextBox dtIniTB = new TextBox();
		flexTable.setWidget(2, 3, dtIniTB);
		dtIniTB.setVisibleLength(10);
		dtIniTB.setWidth("100%");

		final Label dtFineLabel = new Label("dt fine");
		flexTable.setWidget(3, 2, dtFineLabel);

		final TextBox dtFineTB = new TextBox();
		flexTable.setWidget(3, 3, dtFineTB);
		dtFineTB.setWidth("100%");

		final RadioButton daSedeRadioButton = new RadioButton("emessoDa");
		flexTable.setWidget(0, 4, daSedeRadioButton);
		flexTable.getCellFormatter().setWidth(0, 4, "16%");
		daSedeRadioButton.setValue(true);
		daSedeRadioButton.setText("Da sede");

		final RadioButton fuoriSedeRadioButton = new RadioButton("emessoDa");
		flexTable.setWidget(0, 5, fuoriSedeRadioButton);
		flexTable.getCellFormatter().setWidth(0, 5, "17%");
		fuoriSedeRadioButton.setText("Fuori Sede");

		final CheckBox docprovCheckBox = new CheckBox();
		flexTable.setWidget(1, 4, docprovCheckBox);
		docprovCheckBox.setText("docProv");

		final CheckBox docdefiCheckBox = new CheckBox();
		flexTable.setWidget(1, 5, docdefiCheckBox);
		docdefiCheckBox.setText("docDefi");

		final CheckBox fatterroreCheckBox = new CheckBox();
		flexTable.setWidget(2, 4, fatterroreCheckBox);
		fatterroreCheckBox.setText("fattErrore");

		final CheckBox perriferimentoCheckBox = new CheckBox();
		flexTable.setWidget(2, 5, perriferimentoCheckBox);
		perriferimentoCheckBox.setText("perRiferimento");

		final HorizontalPanel horizontalPanel = new HorizontalPanel();
		verticalPanel.add(horizontalPanel);

		final Button cercaButton = new Button();
		horizontalPanel.add(cercaButton);
		cercaButton.setText("cerca");
		

		final Button pulisciButton = new Button();
		horizontalPanel.add(pulisciButton);

		pulisciButton.setText("pulisci");

		grid = new Grid();
		verticalPanel_1.add(grid);
		grid.setBorderWidth(1);
		grid.resize(1, 2);

		final Label nomeLabel = new Label("numeo Fattura");
		grid.setWidget(0, 0, nomeLabel);
		grid.getCellFormatter().setWidth(0, 0, "15%");

		final Label cognomeLabel = new Label("descrizione");
		grid.setWidget(0, 1, cognomeLabel);
		grid.getCellFormatter().setWidth(0, 1, "85%");

	}
}