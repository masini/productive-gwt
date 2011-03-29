package org.googlecode.gwt.simplegrid.client.table;

import com.google.gwt.gen2.table.client.FixedWidthGrid;
import com.google.gwt.gen2.table.client.SelectionGrid;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.gen2.table.override.client.HTMLTable;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;

public class SimpleGridPolicy {
	/**
	 * Selection policies:
	 * <ul>
	 * <li>{@link #ONE_ROW} - one row can be selected at a time</li>
	 * <li>{@link #MULTI_ROW} - multiple rows can be selected at a time</li>
	 * <li>{@link #CHECKBOX} - multiple rows can be selected using checkboxes</li>
	 * <li>{@link #CHECKBOX_IN_HEADER_TOO} - multiple rows can be selected using checkboxes; all rows can be selected via a header checkbox</li>
	 * <li>{@link #CHECKBOX_WITHOUT_ALL_SELECTOR} - multiple rows can be selected using checkboxes; no selector is available for all rows</li>
	 * <li>{@link #RADIO} - one row can be selected using radio buttons</li>
	 * </ul>
	 */
	public static enum SelectionPolicy {
		ONE_ROW(SelectionGrid.SelectionPolicy.ONE_ROW),
		MULTI_ROW(SelectionGrid.SelectionPolicy.MULTI_ROW),
		CHECKBOX(SelectionGrid.SelectionPolicy.CHECKBOX) {
			private static final int NO_SELECTION_INDEX = 0;
			private static final int ALL_SELECTION_INDEX = 1;

			@Override
			protected void configure(HTMLTable headerTable, final FixedWidthGrid dataGrid, String ... columnsName) {
				final ListBox lbxSeleziona = new ListBox();
				lbxSeleziona.setWidth("70px");
				lbxSeleziona.insertItem("Selez.", "", NO_SELECTION_INDEX);
				lbxSeleziona.insertItem("TUTTE", "0", ALL_SELECTION_INDEX);
				lbxSeleziona.insertItem("NESSUNA", "1", 2);

				lbxSeleziona.addChangeHandler(
					new ChangeHandler(){
						public void onChange(ChangeEvent event) {
							if (lbxSeleziona.getSelectedIndex() == ALL_SELECTION_INDEX) {
								dataGrid.selectAllRows();
							}
							else {
								dataGrid.deselectAllRows();
							}

							lbxSeleziona.setSelectedIndex(NO_SELECTION_INDEX); //reset
						}
					}
				);

				headerTable.setWidget(0, 0, lbxSeleziona);

				super.configure(headerTable, dataGrid, columnsName);
			}
		},
		CHECKBOX_IN_HEADER_TOO(CHECKBOX) {
			@Override
			protected void configure(HTMLTable headerTable, final FixedWidthGrid dataGrid, String ... columnsName) {
				final CheckBox selectAllCheckBox = new CheckBox();

				selectAllCheckBox.addValueChangeHandler(
					new ValueChangeHandler<Boolean>(){
						public void onValueChange(ValueChangeEvent<Boolean> event) {
							if (selectAllCheckBox.getValue()) {
								dataGrid.selectAllRows();
							}
							else {
								dataGrid.deselectAllRows();
							}
						}
					}
				);

				headerTable.setWidget(0, 0, selectAllCheckBox);

				super.configure(headerTable, dataGrid, columnsName);
			}
		},
		CHECKBOX_WITHOUT_ALL_SELECTOR(CHECKBOX) {
			@Override
			protected void configure(HTMLTable headerTable, final FixedWidthGrid dataGrid, String ... columnsName) {
				headerTable.setWidget(0, 0, null);

				super.configure(headerTable, dataGrid, columnsName);
			}
		},
		RADIO(SelectionGrid.SelectionPolicy.RADIO);

		public final SelectionGrid.SelectionPolicy equivalentStandardOne;

		private SelectionPolicy(SelectionPolicy selectionPolicyToMimic) {
			this(selectionPolicyToMimic.equivalentStandardOne);
		}

		private SelectionPolicy(SelectionGrid.SelectionPolicy equivalentStandardOne) {
			this.equivalentStandardOne = equivalentStandardOne;
		}

		int getOffset() {
			return (hasInputColumn() ? 1 : 0);
		}

		protected void configure(HTMLTable headerTable, FixedWidthGrid dataGrid, String ... columnsName) {
			int offset = getOffset();

			for (int i = 0; i < columnsName.length; i++) {
				headerTable.setHTML(0, i + offset, columnsName[i]);
			}
		}

		/**
		 * @return true if the policy requires a selection column
		 */
		public boolean hasInputColumn() {
			return equivalentStandardOne.hasInputColumn();
		}
	}

	public static enum ScrollPolicy {
		HORIZONTAL, BOTH, DISABLED
	}

	public static enum ResizePolicy {
		UNCONSTRAINED, FLOW, FIXED_WIDTH, FILL_WIDTH
	}
}
