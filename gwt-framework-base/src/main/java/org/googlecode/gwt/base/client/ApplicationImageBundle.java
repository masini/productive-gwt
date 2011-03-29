package org.googlecode.gwt.base.client;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface ApplicationImageBundle extends ClientBundle {
		
		@Source("img/logo.png")
		ImageResource HEADER_LOGO();
		
		@Source("img/empty.png")
		ImageResource HEADER_EMPTY();
		
		@Source("img/empty_short.png")
		ImageResource HEADER_EMPTY_SHORT();
		
		@Source("img/bullet_arrow_down.gif")
		ImageResource HEADER_ARROW_DOWN();
		
		@Source("img/information.png")
		ImageResource HEADER_INFORMATION();
		
		@Source("img/user.png")
		ImageResource HEADER_USER_INFO();
		
		@Source("img/help.png")
		ImageResource HEADER_HELP();
		
		@Source("img/application_terminal.png")
		ImageResource HEADER_APPLICATION_TERMINAL();
		
		@Source("img/application_put.png")
		ImageResource HEADER_APPLICATION_PUT();
		
		@Source("img/house.png")
		ImageResource HOME();
		
}
