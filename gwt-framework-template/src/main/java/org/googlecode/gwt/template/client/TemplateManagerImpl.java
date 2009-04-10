package org.googlecode.gwt.template.client;

import org.googlecode.gwt.menu.client.SMenu;
import org.googlecode.gwt.template.client.PlaceHolder.PlaceHolderConstant;
import org.googlecode.gwt.template.client.exception.PlaceHolderException;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class TemplateManagerImpl {
	protected static String INTRANET_LOGO_STYLE_NAME = "intranet-logo";

	protected static final String ERR_MSG = "Non trovata la zona ";
	protected static TemplateImageImageBundle images = (TemplateImageImageBundle) GWT.create(TemplateImageImageBundle.class);

	protected static HTML home = null;
	protected static HTML intranet = null;
	protected static Image intranetLogo = null;
	protected static Widget firstPanel = null;
	protected static Widget firstNavigation = null;

	protected static boolean showIntranetLink = true;

	public  void setApplicationTitle(Widget widget) throws PlaceHolderException{}  

	public  void setApplicationTitle(String applicationTitle) throws PlaceHolderException{}  

	public  void setHeader(Widget widget) throws PlaceHolderException {}

	public  void setInfo(Widget widget) throws PlaceHolderException {} 

	public  void setFooter(Widget widget) throws PlaceHolderException {}

	public  void setApplicationContent(Widget widget) throws PlaceHolderException {	}

	public  void setNavigationContent(Widget widget, boolean append) throws PlaceHolderException {

	}

	public  void setHomePage(Widget content, Widget navigation) throws PlaceHolderException {

	}

	public  void setMenu(final SMenu menu) {

	}

	/**
	 * This method set the value of HistoryToken of homepage. 
	 * @param tokenName HomePage token name
	 */
	public  void setHomePageHistoryToken(String homePageHistoryToken) {

	}

	protected  RootPanel get(PlaceHolderConstant constant) {
		return null;
	}

	/*
	 * Path di navigaizone
	 */

	protected  Image getIntranetLogo() {
		return null;
	}

	protected  HTML getIntranetLink() {
		return null;
	}

	protected  HTML getHomePageLink() {
		return null;
	}

	protected  HTML newSeparator() {
		return null;
	}

	/*
	 * Navigaizone
	 */

	protected  void reloadFirstPanel() {

	}

	public  void openIntranet() {
	}

	public  native void redirect(String url)/*-{
			    $wnd.location = url;
			}-*/;

	public  void setShowIntranetLink(boolean showIntranetLink) {
	}

	public  void setHome(HTML home) {
	}
}
