package org.googlecode.gwt.header.client;

import org.googlecode.gwt.base.client.ApplicationContext;
import org.googlecode.gwt.base.client.BootstrapData;

import com.google.gwt.user.client.ui.Image;

/**
 * Pulsante per la visualizzazione delle informazioni dell'applicazione.
 */
public class ApplicationHeaderButton extends BaseInfoPanelHeaderButton {
	
	/**
	 * @see it.esselunga.commons.gwt.framework.client.homepage.impl.header.BaseInfoPanelHeaderButton#onLoad()
	 */
	private ApplicationContext applicationContext; 
	
	public ApplicationHeaderButton(ApplicationContext applicationContext) {
		this.applicationContext=applicationContext;
	}
	
	protected void onLoad() {
		super.onLoad();

		BootstrapData bootstrapData = applicationContext.getBootstrapData();
		addInfo(HeaderConstantsFactory.getInstance().CLUSTER_NAME_LABEL() + " ", bootstrapData.getClusterName());
		addInfo(HeaderConstantsFactory.getInstance().SERVER_NAME_LABEL() + " ", bootstrapData.getServerName());
		addInfo(HeaderConstantsFactory.getInstance().APPLICATION_NAME_LABEL() + " ", bootstrapData.getApplicationName() + " " +	bootstrapData.getApplicationVersion());
		addInfo(HeaderConstantsFactory.getInstance().APPLICATION_CODE_LABEL() + " ", bootstrapData.getApplicationCode());
	}

	/**
	 * @see it.esselunga.commons.gwt.framework.client.homepage.impl.header.BaseInfoPanelHeaderButton#getPopupImage()
	 */
	protected Image getPopupImage() {
		return HeaderImagesFactory.getInstance().getApplicationInfoIcon().createImage();
	}
	
	/**
	 * @see it.esselunga.commons.gwt.framework.client.homepage.impl.header.BaseInfoPanelHeaderButton#addLabels()
	 */
	protected void addLabels() {
		add(HeaderImagesFactory.getInstance().getApplicationInfoIcon().createImage());
	}
}