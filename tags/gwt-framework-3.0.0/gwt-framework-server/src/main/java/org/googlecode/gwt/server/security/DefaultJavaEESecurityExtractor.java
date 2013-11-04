package org.googlecode.gwt.server.security;


public class DefaultJavaEESecurityExtractor extends JavaEESecurityExtractorAdapter implements JavaEESecurityExtractor {

    public DefaultJavaEESecurityExtractor() {
        super();
    }

    public DefaultJavaEESecurityExtractor( RoleDefinitionExtractor extractionStrategy ) {
        super();

        //noinspection OverridableMethodCallDuringObjectConstruction
        setExtractionStrategy( extractionStrategy );
    }

    private RoleDefinitionExtractor extractionStrategy;

    @Override
    public RoleDefinitionExtractor getExtractionStrategy() {
        return this.extractionStrategy;
    }

    public void setExtractionStrategy( RoleDefinitionExtractor extractionStrategy ) {
        this.extractionStrategy = extractionStrategy;
    }
}
