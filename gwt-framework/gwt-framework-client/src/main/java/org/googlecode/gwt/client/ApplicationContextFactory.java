package org.googlecode.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.rpc.AsyncCallback;
import org.googlecode.gwt.client.logging.LogText;
import org.googlecode.gwt.shared.client.ApplicationContext;
import org.googlecode.gwt.shared.client.BootstrapData;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory for create a ApplicationContext
 *
 */
public class ApplicationContextFactory implements EntryPoint {

	private static List<AsyncCallback<ApplicationContext>> callBacks=new ArrayList<AsyncCallback<ApplicationContext>>();
	private static ApplicationContext applicationContext;
	
	static {
		LogText.writeOnLogText("==> static " + ApplicationContextFactory.class.getName());
	}
	
	public static void createApplicationContext(BootstrapData bootstrapData){
		applicationContext = new ApplicationContext(bootstrapData);
		if(callBacks != null) {
			for(int i=0;i<callBacks.size();i++) {
				AsyncCallback<ApplicationContext> callback = callBacks.get(i);
				callback.onSuccess(applicationContext);
			}
		}
	}
	
	/**
	 * Get application context
	 * @return ApplicationContext - ApplicationContext
	 */
	public static void getApplicationContext(AsyncCallback<ApplicationContext> callback) {
		if(applicationContext != null) {
			callback.onSuccess(applicationContext);
		}
		else{
			callBacks.add(callback);
		}
	}

	public void onModuleLoad() {
	}

    public static void failedCreatingApplicationContext() {
        if(callBacks != null) {
            for(int i=0;i<callBacks.size();i++) {
                AsyncCallback<ApplicationContext> callback = callBacks.get(i);
                callback.onFailure(new Exception("Errore nella chiamata al bootstrap service"));
            }
        }
    }
}
