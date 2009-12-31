package org.googlecode.gwt.menu.client;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface SMenuItem {
	
	int position();
	String label();
	String icon();
	String shortcut() default "";
	String[] userRoles() default {""};
}
