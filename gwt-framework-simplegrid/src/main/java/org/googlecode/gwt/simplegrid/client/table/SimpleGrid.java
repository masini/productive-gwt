package org.googlecode.gwt.simplegrid.client.table;

import org.googlecode.gwt.simplegrid.shared.Filter;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.gen2.table.client.FixedWidthFlexTable;
import com.google.gwt.gen2.table.client.SelectionGrid.SelectionPolicy;
import com.google.gwt.user.client.ui.ListBox;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * @deprecated in favor of {@link StandardGrid}
 */
@Deprecated
public class SimpleGrid<ROW>
	extends AbstractSimpleGrid<
		ROW,
		TableController<ROW>,
		TableConfigurer<ROW>
	>
{
	// Attributo ridondato, perche', per esigenze di retrocompatibilita', preferisco non renderlo protected nella superclasse
	private final TableConfigurer<ROW> tableConfigurer;

	SimpleGrid(TableController<ROW> tableController, TableConfigurer<ROW> tableConfigurer, String... columnsName) {
		super(tableController, tableConfigurer, columnsName);
		this.tableConfigurer = tableConfigurer;

		// Create the tables
		FixedWidthGridCustom dataTable = createDataTable();
		FixedWidthFlexTable headerTable = createHeaderTable();
		FixedWidthFlexTable footerTable = createFooterTable();

		createPagingScrollTable(headerTable, dataTable, footerTable);
	}

	/**
	 * @return the newly created data table.
	 */
	protected FixedWidthGridCustom createDataTable() {
		FixedWidthGridCustom dataTable = new FixedWidthGridCustom(tableConfigurer.getSelectionPolicy(), tableConfigurer.isRetrieveDataOnLoad(), columnsName) {
			//dimensione campo di selezione con checkbox
			@Override
			protected int getInputColumnWidth() {
				return 80;
			}
		};
		dataTable.setSelectionEnabled(true);

		return dataTable;
	}

	/**
	 * @return the new header table
	 */
	protected FixedWidthFlexTable createHeaderTable() {
		FixedWidthFlexTable headerTable = new FixedWidthFlexTable();

		if (columnsName != null && columnsName.length > 0) {
			//if checkbox selection add cell header
			if (tableConfigurer.getSelectionPolicy() == SelectionPolicy.CHECKBOX) {
				final ListBox lbxSeleziona = new ListBox();
				lbxSeleziona.setWidth("70px");
				lbxSeleziona.addItem("Selez.", "");
				lbxSeleziona.addItem("TUTTE", "0");
				lbxSeleziona.addItem("NESSUNA", "1");
				lbxSeleziona.addChangeHandler(
					new ChangeHandler() {
						public void onChange(ChangeEvent event) {
							if (lbxSeleziona.getValue(lbxSeleziona.getSelectedIndex()).equals("0"))
								selectAllRows();
							else
								deselectAllRows();

							//reset
							lbxSeleziona.setSelectedIndex(0);
						}
					}
				);

				headerTable.setWidget(0, 0, lbxSeleziona);

				for (int i = 0; i < columnsName.length; i++) {
					headerTable.setHTML(0, i + 1, columnsName[i]);
				}
			} else
				for (int i = 0; i < columnsName.length; i++) {
					headerTable.setHTML(0, i, columnsName[i]);
				}
		}

		return headerTable;
	}

	@SuppressWarnings("unchecked")
	public void reloadData(int page, boolean forced, Filter filter) {
		tableController.setFilter(filter);

		reloadData(page, forced);
	}
}

