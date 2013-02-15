package org.googlecode.gwt.base.client;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.Map;

@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE)
public interface ApplicationBootstrapData  extends Serializable {

    @JsonProperty
    Map<String, String> getApplicationData();

}
