package org.googlecode.gwt.header.client;

import org.googlecode.gwt.base.client.ApplicationContext;
import org.googlecode.gwt.base.client.UserInfo;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

/**
 * Pulsante per la visualizzazione delle informazioni dell'utente.
 */
public class UserHeaderButton extends BaseInfoPanelHeaderButton {
	
	/**
	 * Stile associato al nome dell'utente
	 */
	public static final String USER_HEADER_BUTTON_NAME_CLASS = "userName"; 

	private ApplicationContext applicationContext; 
	
	public UserHeaderButton(ApplicationContext applicationContext) {
		this.applicationContext=applicationContext;
	}
	
	protected void onLoad() {
		super.onLoad();

		UserInfo userInfo = applicationContext.getBootstrapData().getUserInfo();

		addInfo(HeaderConstantsFactory.getInstance().CURRENT_USER_NAME_LABEL(), userInfo.getFirstName() + " " + userInfo.getLastName());
		addInfo(HeaderConstantsFactory.getInstance().CURRENT_USER_ID_LABEL(), userInfo.getUsername());
	}

	protected Image getPopupImage() {
		return HeaderImagesFactory.getInstance().getUserInfoIcon().createImage();
	}

	protected void addLabels() {
		UserInfo userInfo = applicationContext.getBootstrapData().getUserInfo();

		add(HeaderImagesFactory.getInstance().getEmptyShortIcon().createImage());
		
		Label info = new Label(HeaderConstantsFactory.getInstance().WELCOME_LABEL() + " " + userInfo.getFirstName() + " " + userInfo.getLastName());
		info.setStyleName(USER_HEADER_BUTTON_NAME_CLASS);
		add(info);
	}

}

