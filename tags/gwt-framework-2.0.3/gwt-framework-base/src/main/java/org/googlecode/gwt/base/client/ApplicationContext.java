package org.googlecode.gwt.base.client;


/**
 * Define applicationContext which contains a bootstrapData
 */
public class ApplicationContext {

    private final BootstrapData bootstrapData;
    private ApplicationContextData appContextData;
    
    ApplicationContext( BootstrapData bootstrapData ) {
        super();
        this.bootstrapData = bootstrapData;
    }        

    /**
     * Get a BootstrapData
     *
     * @return BootstrapData - BootstrapData
     */
    public BootstrapData getBootstrapData() {
        return bootstrapData;
	}
    
    public String toString()
    {
    	return bootstrapData.toString();
    }

	public ApplicationContextData getAppContextData() {
		return appContextData;
	}

	public void setAppContextData(ApplicationContextData appContextData) {
		this.appContextData = appContextData;
	}

}
