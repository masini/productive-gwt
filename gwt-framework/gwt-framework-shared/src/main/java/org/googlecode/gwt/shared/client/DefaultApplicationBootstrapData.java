package org.googlecode.gwt.shared.client;

import java.util.HashMap;

public class DefaultApplicationBootstrapData implements ApplicationBootstrapData {

    private HashMap<String,String> applicationData = new HashMap<String,String>();

    public DefaultApplicationBootstrapData() { }

    public DefaultApplicationBootstrapData(HashMap<String, String> applicationData) {
        this.applicationData = applicationData;
    }

    public HashMap<String, String> getApplicationData() {
        return applicationData;
    }

    public void setApplicationData(HashMap<String, String> applicationData) {
        this.applicationData = applicationData;
    }
}
