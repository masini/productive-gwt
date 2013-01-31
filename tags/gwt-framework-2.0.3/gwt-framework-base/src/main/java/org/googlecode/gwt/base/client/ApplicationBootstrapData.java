package org.googlecode.gwt.base.client;

import java.io.Serializable;
import java.util.Map;

public interface ApplicationBootstrapData  extends Serializable {

    Map<String, String> getApplicationData();

}
