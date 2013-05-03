package org.googlecode.gwt.header.client;

import org.googlecode.gwt.base.client.ApplicationContext;
import org.googlecode.gwt.base.client.ApplicationResources;
import org.googlecode.gwt.base.client.UserInfo;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import org.googlecode.gwt.bootstrap.client.DefaultUserInfo;

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

		DefaultUserInfo userInfo = new DefaultUserInfo(applicationContext.getBootstrapData().getUserInfo());

		addInfo(ApplicationResources.getCostants().CURRENT_USER_NAME_LABEL(), userInfo.getFirstName() + " " + userInfo.getLastName());
		addInfo(ApplicationResources.getCostants().CURRENT_USER_ID_LABEL(), userInfo.getUsername());

		String[] role = userInfo.getRoles().toArray(new String[]{});
		if (role != null && role.length > 0) {
			addInfo(ApplicationResources.getCostants().ROLES_LABEL(), getRoles(userInfo, role));
		}
		
		String[] parameterName = userInfo.getUserParameterNames().toArray(new String[]{});
		if (parameterName != null && parameterName.length > 0) {
			addInfo(ApplicationResources.getCostants().PARAMETERS_LABEL(), getParameters(userInfo, parameterName));
		}
	}
	
	/**
	 * @see org.googlecode.gwt.header.client.BaseInfoPanelHeaderButton#getPopupImage()
	 */
	protected Image getPopupImage() {
		return new Image(ApplicationResources.getImageBundle().HEADER_USER_INFO());
	}

	/**
	 * @see org.googlecode.gwt.header.client.BaseInfoPanelHeaderButton#addLabels()
	 */
	protected void addLabels() {
		UserInfo userInfo = applicationContext.getBootstrapData().getUserInfo();

		add(new Image(ApplicationResources.getImageBundle().HEADER_EMPTY_SHORT()));
		
		Label info = new Label(userInfo.getFirstName() + " " + userInfo.getLastName());
		info.addStyleName("benvenuto");
		add(new Label(ApplicationResources.getCostants().WELCOME_LABEL() + " "));
		add(info);
	}
	
	private String getRoles(DefaultUserInfo userInfo, String[] role) {

		StringBuffer roles = new StringBuffer();
		for (int i = 0; i < role.length; i++) {
			String roleDescription = userInfo.getRoleDescription(role[i]);
			roles.append(roleDescription != null ? roleDescription : role[i]).append("<br>");
		}
		return roles.toString();
	}

	private String getParameters(DefaultUserInfo userInfo, String[] parameterName) {
		StringBuffer params = new StringBuffer();
		for (int i = 0; i < parameterName.length; i++) {
			params.append(parameterName[i]).append(" (").append(userInfo.getUserParameter(parameterName[i])).append(")<br>");
		}
		return params.toString();
	}
}

