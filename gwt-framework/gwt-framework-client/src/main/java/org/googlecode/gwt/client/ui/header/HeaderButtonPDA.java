package org.googlecode.gwt.client.ui.header;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import org.googlecode.gwt.client.util.ApplicationResources;
import org.googlecode.gwt.shared.ApplicationContext;
import org.googlecode.gwt.shared.BootstrapData;
import org.googlecode.gwt.shared.DefaultUserInfo;
import org.googlecode.gwt.shared.UserInfo;

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
		String infoPanel=ApplicationResources.getCostants().CLUSTER_NAME_LABEL() + " "+ bootstrapData.getClusterName()+ "\n"+
		ApplicationResources.getCostants().SERVER_NAME_LABEL() + " "+ bootstrapData.getServerName()+"\n"+
		ApplicationResources.getCostants().APPLICATION_NAME_LABEL() + " "+ bootstrapData.getApplicationName() + " " +	bootstrapData.getApplicationVersion()+"\n"+
		ApplicationResources.getCostants().APPLICATION_CODE_LABEL() + " "+ bootstrapData.getApplicationCode() +"\n"+
		ApplicationResources.getCostants().APPLICATION_VERSION_LABEL() + " "+ bootstrapData.getApplicationVersion();
		Window.alert(infoPanel);
		//popup.show();
		//updatePopupSizeAndPosition(sender);
	}

	protected void onLoad() {
		super.onLoad();

		// INFO SERVER

		BootstrapData bootstrapData = applicationContext.getBootstrapData();
		addInfo(ApplicationResources.getCostants().CLUSTER_NAME_LABEL() + " ", bootstrapData.getClusterName());
		addInfo(ApplicationResources.getCostants().SERVER_NAME_LABEL() + " ", bootstrapData.getServerName());
		addInfo(ApplicationResources.getCostants().APPLICATION_NAME_LABEL() + " ", bootstrapData.getApplicationName() + " " +	bootstrapData.getApplicationVersion());
		addInfo(ApplicationResources.getCostants().APPLICATION_CODE_LABEL() + " ", bootstrapData.getApplicationCode());

		// INFO UTENTE

        DefaultUserInfo userInfo = new DefaultUserInfo(applicationContext.getBootstrapData().getUserInfo());

        //addInfo(HeaderConstantsFactory.getInstance().CURRENT_USER_NAME_LABEL(), userInfo.getFirstName() + " " + userInfo.getLastName());
		//addInfo(HeaderConstantsFactory.getInstance().CURRENT_USER_ID_LABEL(), userInfo.getUsername());

		String[] role = userInfo.getRoles().toArray(new String[]{});
		if (role != null && role.length > 0) {
			addInfo(ApplicationResources.getCostants().ROLES_LABEL(), getRoles(userInfo, role));
		}

		String[] parameterName = userInfo.getUserParameterNames().toArray(new String[]{});
		if (parameterName != null && parameterName.length > 0) {
			addInfo(ApplicationResources.getCostants().PARAMETERS_LABEL(), getParameters(userInfo, parameterName));
		}
	}

	protected Image getPopupImage() {
		return new Image();
		//return HeaderImagesFactory.getInstance().getUserInfoIcon().createImage();
	}

	protected void addLabels() {
		UserInfo userInfo = applicationContext.getBootstrapData().getUserInfo();

		add(new Image(ApplicationResources.getImageBundle().HEADER_EMPTY_SHORT()));

		Label info = new Label(userInfo.getUsername());

		//Label info = new Label("NEGOZIO X");
		info.addStyleName("benvenuto");
		//add(new Label(HeaderConstantsFactory.getInstance().WELCOME_LABEL() + " "));
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

