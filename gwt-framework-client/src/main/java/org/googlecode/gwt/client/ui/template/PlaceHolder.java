package org.googlecode.gwt.client.ui.template;


import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;


public class PlaceHolder {

	public static class PlaceHolderConstant{
		private final Panel panel;

		public PlaceHolderConstant(Panel panel) {
			this.panel = panel;
		}

		public Panel getPanel() {
			return panel;
		}
	}
	
	public static final PlaceHolderConstant HEADER = new PlaceHolderConstant(new SimplePanel());
	public static final PlaceHolderConstant FOOTER = new PlaceHolderConstant(new SimplePanel());
	public static final PlaceHolderConstant EAST = new PlaceHolderConstant(new SimplePanel());
	public static final PlaceHolderConstant WEST = new PlaceHolderConstant(new SimplePanel());
	public static final PlaceHolderConstant CONTENT = new PlaceHolderConstant(new SimplePanel());
	public static final PlaceHolderConstant APPLICATION_TITLE = new PlaceHolderConstant(new SimplePanel());
	
	public static final PlaceHolderConstant NAVIGATION = new PlaceHolderConstant(new HorizontalPanel());
	public static final PlaceHolderConstant INFO = new PlaceHolderConstant(new SimplePanel());
	public static final PlaceHolderConstant MENU = new PlaceHolderConstant(new SimplePanel());
	
	public static Panel get(PlaceHolderConstant placeHolderConstant){
		return placeHolderConstant.getPanel();
	}
}
