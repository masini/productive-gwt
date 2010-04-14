package org.googlecode.gwt.header.client;

import org.googlecode.gwt.base.client.ApplicationContext;
import org.googlecode.gwt.base.client.UserInfo;
import org.googlecode.gwt.template.client.TemplateManager;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

/**
 * Pulsante per la visualizzazione delle informazioni dell'utente.
 */
public class UserHeaderButton extends BaseInfoPanelHeaderButton {

	private ApplicationContext applicationContext; 
	
	public UserHeaderButton(ApplicationContext applicationContext) {
		this.applicationContext=applicationContext;
	}
	
	protected void onLoad() {
		super.onLoad();

		UserInfo userInfo = applicationContext.getBootstrapData().getUserInfo();

		addInfo(HeaderConstantsFactory.getInstance().CURRENT_USER_NAME_LABEL(), userInfo.getFirstName() + " " + userInfo.getLastName());
		addInfo(HeaderConstantsFactory.getInstance().CURRENT_USER_ID_LABEL(), userInfo.getUsername());

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
		return new Image(TemplateManager.getImageBundle().HEADER_USER_INFO());
	}

	/**
	 * @see org.googlecode.gwt.header.client.BaseInfoPanelHeaderButton#addLabels()
	 */
	protected void addLabels() {
		UserInfo userInfo = applicationContext.getBootstrapData().getUserInfo();

		add(new Image(TemplateManager.getImageBundle().HEADER_EMPTY_SHORT()));
		
		Label info = new Label(userInfo.getFirstName() + " " + userInfo.getLastName());
		info.addStyleName("benvenuto");
		add(new Label(HeaderConstantsFactory.getInstance().WELCOME_LABEL() + " "));
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

