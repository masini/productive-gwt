package org.googlecode.gwt.template.client;

import org.googlecode.gwt.menu.client.SMenu;
import org.googlecode.gwt.template.client.PlaceHolder.PlaceHolderConstant;
import org.googlecode.gwt.template.client.exception.PlaceHolderException;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
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
	
	protected Widget footer = null;
	protected TemplateCostants costants = null;
	protected TemplateImageBundle imageBundle = null;

	public  void setApplicationTitle(Widget widget) throws PlaceHolderException{}  

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

	public  void setMenu(final SMenu menu) {

	}

	/**
	 * This method set the value of HistoryToken of homepage. 
	 * @param tokenName HomePage token name
	 */
	public  void setHomePageHistoryToken(String homePageHistoryToken) {

	}

	protected  Panel get(PlaceHolderConstant constant) {
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
	
	public void setTemplateCostants(TemplateCostants costants) {
		if(this.costants != null) {
			GWT.log("Costants already set with class " + this.costants.getClass().getName());
			return;
		}
		this.costants = costants;
		GWT.log("Costants is: " + costants.getClass().getName());
	}

	public void setImageBundle(TemplateImageBundle imageBundle) {
		if(this.imageBundle != null) {
			GWT.log("Images already set with class " + this.imageBundle.getClass().getName());
			return;
		}
		this.imageBundle = imageBundle;
		GWT.log("Images is: " + imageBundle.getClass().getName());
	}

	public TemplateImageBundle getImageBundle() {
		return this.imageBundle;
	}

	public TemplateCostants getTemplateCostants() {
		return this.costants;
	}
	
}
