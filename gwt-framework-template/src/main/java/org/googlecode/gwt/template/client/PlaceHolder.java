package org.googlecode.gwt.template.client;

import org.googlecode.gwt.base.client.util.IdUtil;

import com.google.gwt.user.client.ui.RootPanel;



public class PlaceHolder {

	public static class PlaceHolderConstant{
		private String id;

		public PlaceHolderConstant(String id) {
			this.id = id;
		}

		public String getId() {
			return id;
		}
	}
	
	public static final PlaceHolderConstant HEADER = new PlaceHolderConstant(IdUtil.getNextToken());
	public static final PlaceHolderConstant FOOTER = new PlaceHolderConstant(IdUtil.getNextToken());
	public static final PlaceHolderConstant EAST = new PlaceHolderConstant(IdUtil.getNextToken());
	public static final PlaceHolderConstant WEST = new PlaceHolderConstant(IdUtil.getNextToken());
	public static final PlaceHolderConstant CONTENT = new PlaceHolderConstant(IdUtil.getNextToken());
	public static final PlaceHolderConstant APPLICATION_TITLE = new PlaceHolderConstant(IdUtil.getNextToken());
	
	public static final PlaceHolderConstant NAVIGATION = new PlaceHolderConstant(IdUtil.getNextToken());
	public static final PlaceHolderConstant MENU = new PlaceHolderConstant(IdUtil.getNextToken());
	
	public static RootPanel get(PlaceHolderConstant placeHolderConstant){
		return RootPanel.get(placeHolderConstant.getId());
	}
	

	
}
