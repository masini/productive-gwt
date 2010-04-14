package org.googlecode.gwt.header.client;

import org.googlecode.gwt.base.client.ApplicationContext;
import org.googlecode.gwt.base.client.BootstrapData;
import org.googlecode.gwt.base.client.UserInfo;
import org.googlecode.gwt.template.client.TemplateManager;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * Pulsante per la visualizzazione delle informazioni dell'utente.
 */
public class HeaderButtonPDA extends BaseInfoPanelHeaderButton {

	private ApplicationContext applicationContext; 

	public HeaderButtonPDA(ApplicationContext applicationContext) {
		this.applicationContext=applicationContext;
	}


	protected void onClick(Widget sender, boolean isClicked) {
		BootstrapData bootstrapData = applicationContext.getBootstrapData();
		String infoPanel=HeaderConstantsFactory.getInstance().CLUSTER_NAME_LABEL() + " "+ bootstrapData.getClusterName()+ "\n"+
		HeaderConstantsFactory.getInstance().SERVER_NAME_LABEL() + " "+ bootstrapData.getServerName()+"\n"+
		HeaderConstantsFactory.getInstance().APPLICATION_NAME_LABEL() + " "+ bootstrapData.getApplicationName() + " " +	bootstrapData.getApplicationVersion()+"\n"+
		HeaderConstantsFactory.getInstance().APPLICATION_CODE_LABEL() + " "+ bootstrapData.getApplicationCode() +"\n"+
		HeaderConstantsFactory.getInstance().APPLICATION_VERSION_LABEL() + " "+ bootstrapData.getApplicationVersion();
		Window.alert(infoPanel);
		//popup.show();
		//updatePopupSizeAndPosition(sender);
	}

	protected void onLoad() {
		super.onLoad();

		// INFO SERVER

		BootstrapData bootstrapData = applicationContext.getBootstrapData();
		addInfo(HeaderConstantsFactory.getInstance().CLUSTER_NAME_LABEL() + " ", bootstrapData.getClusterName());
		addInfo(HeaderConstantsFactory.getInstance().SERVER_NAME_LABEL() + " ", bootstrapData.getServerName());
		addInfo(HeaderConstantsFactory.getInstance().APPLICATION_NAME_LABEL() + " ", bootstrapData.getApplicationName() + " " +	bootstrapData.getApplicationVersion());
		addInfo(HeaderConstantsFactory.getInstance().APPLICATION_CODE_LABEL() + " ", bootstrapData.getApplicationCode());

		// INFO UTENTE

		UserInfo userInfo = applicationContext.getBootstrapData().getUserInfo();

		//addInfo(HeaderConstantsFactory.getInstance().CURRENT_USER_NAME_LABEL(), userInfo.getFirstName() + " " + userInfo.getLastName());
		//addInfo(HeaderConstantsFactory.getInstance().CURRENT_USER_ID_LABEL(), userInfo.getUsername());

		String[] role = userInfo.getRoles();	
		if (role != null && role.length > 0) {
			addInfo(HeaderConstantsFactory.getInstance().ROLES_LABEL(), getRoles(userInfo, role));
		}

		String[] parameterName = userInfo.getUserParameterNames();
		if (parameterName != null && parameterName.length > 0) {
			addInfo(HeaderConstantsFactory.getInstance().PARAMETERS_LABEL(), getParameters(userInfo, parameterName));
		}
	}

	/**
	 * @see org.googlecode.gwt.header.client.BaseInfoPanelHeaderButton#getPopupImage()
	 */
	protected Image getPopupImage() {
		return new Image();
		//return HeaderImagesFactory.getInstance().getUserInfoIcon().createImage();
	}

	/**
	 * @see org.googlecode.gwt.header.client.BaseInfoPanelHeaderButton#addLabels()
	 */
	protected void addLabels() {
		UserInfo userInfo = applicationContext.getBootstrapData().getUserInfo();
		
		add(new Image(TemplateManager.getImageBundle().HEADER_EMPTY_SHORT()));
		
		Label info = new Label(userInfo.getUsername());
		
		//Label info = new Label("NEGOZIO X");
		info.addStyleName("benvenuto");
		//add(new Label(HeaderConstantsFactory.getInstance().WELCOME_LABEL() + " "));
		add(info);

	}

	private String getRoles(UserInfo userInfo, String[] role) {
		StringBuffer roles = new StringBuffer();
		for (int i = 0; i < role.length; i++) {
			String roleDescription = userInfo.getRoleDescription(role[i]);
			roles.append(roleDescription != null ? roleDescription : role[i]).append("<br>");
		}
		return roles.toString();
	}

	private String getParameters(UserInfo userInfo, String[] parameterName) {
		StringBuffer params = new StringBuffer();
		for (int i = 0; i < parameterName.length; i++) {
			params.append(parameterName[i]).append(" (").append(userInfo.getUserParameter(parameterName[i])).append(")<br>");
		}
		return params.toString();
	}
}

