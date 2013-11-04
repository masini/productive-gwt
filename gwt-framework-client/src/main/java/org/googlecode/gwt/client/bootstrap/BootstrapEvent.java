package org.googlecode.gwt.client.bootstrap;

import org.googlecode.gwt.shared.client.BootstrapData;

/**
 * Define de EventObject of return of bootstrap service 
 *
 */
public class BootstrapEvent {

    private BootstrapData bootstrapData;
    private String eventDescription;

    public BootstrapEvent() {}

    public BootstrapEvent( BootstrapData bootstrapData ) {
        this.bootstrapData = bootstrapData;
    }

    /**
     * Get bootstrapData
     * @return BootstrapData - BootstrapData
     */
    public BootstrapData getBootstrapData() {
        return bootstrapData;
    }

    /**
     * Set bootstrapData
     * @param bootstrapData - BootstrapData
     */
    public void setBootstrapData( BootstrapData bootstrapData ) {
        this.bootstrapData = bootstrapData;
    }

    /**
     * Get event description
     * @return String - vent description
     */
    public String getEventDescription() {
        return eventDescription;
    }

    /**
     * Set event description
     * @param eventDescription - event description
     */
    public void setEventDescription( String eventDescription ) {
        this.eventDescription = eventDescription;
    }

}
