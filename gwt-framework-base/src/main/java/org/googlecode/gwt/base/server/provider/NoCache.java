package org.googlecode.gwt.base.server.provider;


import java.lang.annotation.*;

@Inherited
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NoCache {
    String[] fields() default {};
}
