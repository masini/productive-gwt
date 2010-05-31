package org.googlecode.gwt.base.client;

import com.google.gwt.core.client.GWT;

public class ApplicationResources {
	
	private static ApplicationImageBundle imageBundle;
	
	private static ApplicationCostants costants;
	
	private ApplicationResources() {
	}
	
	public static void setCostants(ApplicationCostants costants) {
		if(ApplicationResources.costants != null) {
			GWT.log("Costants already set with class " + ApplicationResources.costants.getClass().getName());
			return;
		}
		ApplicationResources.costants = costants;
		GWT.log("Costants is: " + costants.getClass().getName());
	}

	public static ApplicationCostants getCostants() {
		if(ApplicationResources.costants == null) {
			ApplicationResources.costants = GWT.create(ApplicationCostants.class);
		}
		return ApplicationResources.costants;
	}

	public static void setImageBundle(ApplicationImageBundle imageBundle) {
		if(ApplicationResources.imageBundle != null) {
			GWT.log("Images already set with class " + ApplicationResources.imageBundle.getClass().getName());
			return;
		}
		ApplicationResources.imageBundle = imageBundle;
		GWT.log("Images is: " + imageBundle.getClass().getName());
	}

	public static ApplicationImageBundle getImageBundle() {
		if(ApplicationResources.imageBundle == null) {
			ApplicationResources.imageBundle = GWT.create(ApplicationImageBundle.class);
		}
		return ApplicationResources.imageBundle;
	}
	
}
