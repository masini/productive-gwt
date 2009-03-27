package org.googlecode.gwt.header.client;

import org.googlecode.gwt.base.client.ApplicationContext;
import org.googlecode.gwt.base.client.BootstrapData;

import com.google.gwt.user.client.ui.Image;

/**
 * Pulsante per la visualizzazione delle informazioni dell'applicazione.
 */
public class ApplicationHeaderButton extends BaseInfoPanelHeaderButton {
	
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

	protected Image getPopupImage() {
		return HeaderImagesFactory.getInstance().getApplicationInfoIcon().createImage();
	}
	
	protected void addLabels() {
		add(HeaderImagesFactory.getInstance().getApplicationInfoIcon().createImage());
	}
}