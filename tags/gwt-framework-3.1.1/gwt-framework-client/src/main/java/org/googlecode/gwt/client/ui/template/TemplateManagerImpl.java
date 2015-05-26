package org.googlecode.gwt.client.ui.template;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.*;
import org.googlecode.gwt.client.ui.exception.PlaceHolderException;
import org.googlecode.gwt.client.ui.menu.SMenu;

public class TemplateManagerImpl {
	protected static String INTRANET_LOGO_STYLE_NAME = "intranet-logo";

    public static final String TOP_STYLE_NAME = "placeholder-top";
    public static final String NAVIGATION_STYLE_NAME = "placeholder-navigation";
    public static final String INFO_STYLE_NAME = "placeholder-info";


    protected static final String ERR_MSG = "Non trovata la zona ";
	protected static HTML home = null;
	protected static HTML intranet = null;
	protected static Image intranetLogo = null;
	protected static Widget firstPanel = null;
	protected static Place firstPlace = null;
	protected static Widget firstNavigation = null;

	protected static boolean showIntranetLink = true;
	
	protected Widget footer = null;
    protected Widget headerWidget;
    HorizontalPanel top = new HorizontalPanel();



    public  void setApplicationTitle(Widget widget) throws PlaceHolderException {}

	public  void setApplicationTitle(String applicationTitle) throws PlaceHolderException{}  

	public  void setHeader(Widget widget) throws PlaceHolderException {}

	public  void setInfo(Widget widget) throws PlaceHolderException {}

	public  void setFooter(Widget widget) throws PlaceHolderException {
		if(this.footer != null) {
			GWT.log("Footer already set with class " + footer.getClass().getName());
			return;
		}
		
		this.footer = widget;
		Panel root = PlaceHolder.get(PlaceHolder.FOOTER);
		if (root == null) {
			throw new PlaceHolderException(ERR_MSG + "FOOTER");
		}
		root.clear();
		root.add(widget);
		
		GWT.log("Footer is: " + this.footer.getClass().getName());
	}
	
	public  void setApplicationContent(Widget widget) throws PlaceHolderException {	}

	public  void setNavigationContent(Widget widget, boolean append) throws PlaceHolderException {

	}

	public  void setHomePage(Widget content, Widget navigation) throws PlaceHolderException {

	}

    public  void setHomePage(Place place) throws PlaceHolderException {

    }

	public  void setMenu(final SMenu menu) {

	}

	/**
	 * This method set the value of HistoryToken of homepage. 
	 * @param homePageHistoryToken HomePage token name
	 */
	public  void setHomePageHistoryToken(String homePageHistoryToken) {

	}

	protected Panel get(PlaceHolder.PlaceHolderConstant constant) {
		return null;
	}

	/*
	 * Path di navigaizone
	 */

	protected Image getIntranetLogo() {
		return null;
	}

	protected HTML getIntranetLink() {
		return null;
	}

	protected HTML getHomePageLink() {
		return null;
	}

	protected HTML newSeparator() {
		return null;
	}

	/*
	 * Navigaizone
	 */

	public void backToHome() {	}

	protected  void reloadFirstPanel() {

	}

	public void openIntranet() {
	}

	public native void redirect(String url)/*-{
			    $wnd.location = url;
			}-*/;

	public  void setShowIntranetLink(boolean showIntranetLink) {
	}

	public  void setHome(HTML home) {
	}

    public HorizontalPanel createMiddle() {
        HorizontalPanel middle = new HorizontalPanel();
        middle.add(PlaceHolder.WEST.getPanel());
        PlaceHolder.CONTENT.getPanel().setWidth("100%");
        middle.add(PlaceHolder.CONTENT.getPanel());
        middle.add(PlaceHolder.EAST.getPanel());
        middle.setWidth("100%");
        return middle;
    }

    public HorizontalPanel createTop() {
        top.setWidth("100%");
        top.setStyleName(TOP_STYLE_NAME);

        top.add(PlaceHolder.NAVIGATION.getPanel());
        top.setCellVerticalAlignment(PlaceHolder.NAVIGATION.getPanel(), HasVerticalAlignment.ALIGN_MIDDLE);
        top.setCellHorizontalAlignment(PlaceHolder.NAVIGATION.getPanel(), HasHorizontalAlignment.ALIGN_LEFT);
        top.setCellWidth(PlaceHolder.NAVIGATION.getPanel(), "50%");
        PlaceHolder.NAVIGATION.getPanel().setStyleName(NAVIGATION_STYLE_NAME);

        top.add(PlaceHolder.INFO.getPanel());
        top.setCellVerticalAlignment(PlaceHolder.INFO.getPanel(), HasVerticalAlignment.ALIGN_MIDDLE);
        top.setCellHorizontalAlignment(PlaceHolder.INFO.getPanel(), HasHorizontalAlignment.ALIGN_RIGHT);
        top.setCellWidth(PlaceHolder.INFO.getPanel(), "50%");
        PlaceHolder.INFO.getPanel().setStyleName(INFO_STYLE_NAME);

        return top;
    }

    public Widget getTopWidget() {
        return top;
    }

    public Widget getHeaderWidget() {
        return null;
    }

    public Widget getFooterWidget() {
        return footer;
    }

}
