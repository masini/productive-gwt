package org.googlecode.gwt.template.client.img;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface TemplateImageBundle extends ClientBundle {
		
		@Source("logo.png")
		ImageResource HEADER_LOGO();
		
		@Source("empty.png")
		ImageResource HEADER_EMPTY();
		
		@Source("empty_short.png")
		ImageResource HEADER_EMPTY_SHORT();
		
		@Source("bullet_arrow_down.gif")
		ImageResource HEADER_ARROW_DOWN();
		
		@Source("information.png")
		ImageResource HEADER_INFORMATION();
		
		@Source("user.png")
		ImageResource HEADER_USER_INFO();
		
		@Source("help.png")
		ImageResource HEADER_HELP();
		
		@Source("application_terminal.png")
		ImageResource HEADER_APPLICATION_TERMINAL();
		
		@Source("application_put.png")
		ImageResource HEADER_APPLICATION_PUT();
}
