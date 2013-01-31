package org.googlecode.gwt.base.client;

import java.util.HashMap;
import java.util.Map;

public class DefaultApplicationBootstrapData implements ApplicationBootstrapData {

    private Map<String,String> applicationData = new HashMap<String,String>();

    public DefaultApplicationBootstrapData() { }

    public DefaultApplicationBootstrapData(Map<String, String> applicationData) {
        this.applicationData = applicationData;
    }

    public Map<String, String> getApplicationData() {
        return applicationData;
    }

    public void setApplicationData(Map<String, String> applicationData) {
        this.applicationData = applicationData;
    }
}
