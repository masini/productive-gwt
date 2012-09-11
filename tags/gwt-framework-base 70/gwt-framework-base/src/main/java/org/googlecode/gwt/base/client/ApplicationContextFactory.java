package org.googlecode.gwt.base.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Factory for create a ApplicationContext
 *
 */
public class ApplicationContextFactory implements EntryPoint {

	/**
	 * @gwt.typeArgs<com.google.gwt.user.client.rpc.AsyncCallback>
	 */
	private static List callBacks=new ArrayList();
	private static ApplicationContext applicationContext;
	
	private ApplicationContextFactory() {
		super();
	}

	public static void createApplicationContext(BootstrapData bootstrapData){
		applicationContext = new ApplicationContext(bootstrapData);
		if(callBacks != null) {
			for(int i=0;i<callBacks.size();i++) {
				AsyncCallback callback = (AsyncCallback)callBacks.get(i);
				callback.onSuccess(applicationContext);
			}
		}
	}
	
	/**
	 * Get application context
	 * @return ApplicationContext - ApplicationContext
	 */
	public static void getApplicationContext(AsyncCallback callback) {
		if(applicationContext != null) {
			callback.onSuccess(applicationContext);
		}
		else{
			callBacks.add(callback);
		}
	}

	public void onModuleLoad() {
		
	}
}