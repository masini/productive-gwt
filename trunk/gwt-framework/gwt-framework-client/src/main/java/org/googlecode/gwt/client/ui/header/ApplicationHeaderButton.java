package org.googlecode.gwt.client.ui.header;

import com.google.gwt.user.client.ui.Image;
import org.googlecode.gwt.client.util.ApplicationResources;
import org.googlecode.gwt.shared.ApplicationContext;
import org.googlecode.gwt.shared.BootstrapData;

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
		addInfo(ApplicationResources.getCostants().CLUSTER_NAME_LABEL() + " ", bootstrapData.getClusterName());
		addInfo(ApplicationResources.getCostants().SERVER_NAME_LABEL() + " ", bootstrapData.getServerName());
		addInfo(ApplicationResources.getCostants().APPLICATION_NAME_LABEL() + " ", bootstrapData.getApplicationName() + " " +	bootstrapData.getApplicationVersion());
		addInfo(ApplicationResources.getCostants().APPLICATION_CODE_LABEL() + " ", bootstrapData.getApplicationCode());
		
	}

	protected Image getPopupImage() {
		return new Image(ApplicationResources.getImageBundle().HEADER_INFORMATION());
	}
	
	protected void addLabels() {
		add(new Image(ApplicationResources.getImageBundle().HEADER_INFORMATION()));
	}
}