package org.googlecode.gwt.base.client;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;
import java.util.Map;

@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public interface ApplicationBootstrapData  extends Serializable {

    @JsonProperty
    Map<String, String> getApplicationData();

}
