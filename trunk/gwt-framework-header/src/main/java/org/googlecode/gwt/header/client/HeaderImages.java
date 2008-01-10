package org.googlecode.gwt.header.client;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;

/**
 * Immagini comuni utilizzate dalla DefaultHomePage
 */
public interface HeaderImages extends ImageBundle {

	/**
	 * @gwt.resource org/googlecode/gwt/header/public/images/empty.png
	 */
	public AbstractImagePrototype getEmptyIcon();

	/**
	 * @gwt.resource org/googlecode/gwt/header/public/images/empty_short.png
	 */
	public AbstractImagePrototype getEmptyShortIcon();

	/**
	 * @gwt.resource org/googlecode/gwt/header/public/images/logo.bmp
	 */
	public AbstractImagePrototype getIntranetIcon();

	/**
	 * @gwt.resource org/googlecode/gwt/header/public/images/bullet_arrow_down.gif
	 */
	public AbstractImagePrototype getArrowDownIcon(); 
	/**
	 * @gwt.resource org/googlecode/gwt/header/public/images/user.png
	 */
	public AbstractImagePrototype getUserInfoIcon();
	
	/**
	 * @gwt.resource org/googlecode/gwt/header/public/images/information.png
	 */
	public AbstractImagePrototype getApplicationInfoIcon();
	
	/**
	 * @gwt.resource org/googlecode/gwt/header/public/images/help.png
	 */
	public AbstractImagePrototype getHelpIcon();
	
	/**
	 * @gwt.resource org/googlecode/gwt/header/public/images/application_terminal.png
	 */
	public AbstractImagePrototype getLogIconStart();

	/**
	 * @gwt.resource org/googlecode/gwt/header/public/images/application_put.png
	 */
	public AbstractImagePrototype getLogIconStop();

}
